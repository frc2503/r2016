package org.usfirst.frc.team2503.lib.websocket.drafts;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.usfirst.frc.team2503.lib.websocket.WebSocket.Role;
import org.usfirst.frc.team2503.lib.websocket.exceptions.IncompleteHandshakeException;
import org.usfirst.frc.team2503.lib.websocket.exceptions.InvalidDataException;
import org.usfirst.frc.team2503.lib.websocket.exceptions.InvalidHandshakeException;
import org.usfirst.frc.team2503.lib.websocket.exceptions.LimitExceededException;
import org.usfirst.frc.team2503.lib.websocket.framing.CloseFrame;
import org.usfirst.frc.team2503.lib.websocket.framing.FrameBuilder;
import org.usfirst.frc.team2503.lib.websocket.framing.FrameData;
import org.usfirst.frc.team2503.lib.websocket.framing.FrameDataImplementation;
import org.usfirst.frc.team2503.lib.websocket.framing.FrameData.Opcode;
import org.usfirst.frc.team2503.lib.websocket.handshake.ClientHandshake;
import org.usfirst.frc.team2503.lib.websocket.handshake.ClientHandshakeBuilder;
import org.usfirst.frc.team2503.lib.websocket.handshake.HandshakeBuilder;
import org.usfirst.frc.team2503.lib.websocket.handshake.HandshakeData;
import org.usfirst.frc.team2503.lib.websocket.handshake.ClientHandshakeImplementation;
import org.usfirst.frc.team2503.lib.websocket.handshake.ServerHandshakeImplementation;
import org.usfirst.frc.team2503.lib.websocket.handshake.ServerHandshake;
import org.usfirst.frc.team2503.lib.websocket.handshake.ServerHandshakeBuilder;
import org.usfirst.frc.team2503.lib.websocket.util.CharsetHelper;

/**
 * Base class for everything of a websocket specification which is not common such as the way the handshake is read or frames are transfered.
 **/
public abstract class Draft {

	public enum HandshakeState {
		/** Handshake matched this Draft successfully */
		MATCHED,
		/** Handshake is does not match this Draft */
		NOT_MATCHED
	}
	public enum CloseHandshakeType {
		NONE, ONEWAY, TWOWAY
	}

	public static int MAX_FAME_SIZE = 1000 * 1;
	public static int INITIAL_FAMESIZE = 64;

	public static final byte[] FLASH_POLICY_REQUEST = CharsetHelper.utf8Bytes( "<policy-file-request/>\0" );

	/** In some cases the handshake will be parsed different depending on whether */
	protected Role role = null;

	protected Opcode continuousFrameType = null;

	public static ByteBuffer readLine( ByteBuffer buf ) {
		ByteBuffer sbuf = ByteBuffer.allocate( buf.remaining() );
		byte prev = '0';
		byte cur = '0';
		while ( buf.hasRemaining() ) {
			prev = cur;
			cur = buf.get();
			sbuf.put( cur );
			if( prev == (byte) '\r' && cur == (byte) '\n' ) {
				sbuf.limit( sbuf.position() - 2 );
				sbuf.position( 0 );
				return sbuf;

			}
		}
		// ensure that there wont be any bytes skipped
		buf.position( buf.position() - sbuf.position() );
		return null;
	}

	public static String readStringLine( ByteBuffer buf ) {
		ByteBuffer b = readLine( buf );
		return b == null ? null : CharsetHelper.stringAscii( b.array(), 0, b.limit() );
	}

	public static HandshakeBuilder translateHandshakeHttp( ByteBuffer buf, Role role ) throws InvalidHandshakeException , IncompleteHandshakeException {
		HandshakeBuilder handshake;

		String line = readStringLine( buf );
		if( line == null )
			throw new IncompleteHandshakeException( buf.capacity() + 128 );

		String[] firstLineTokens = line.split( " ", 3 );// eg. HTTP/1.1 101 Switching the Protocols
		if( firstLineTokens.length != 3 ) {
			throw new InvalidHandshakeException();
		}

		if( role == Role.CLIENT ) {
			// translating/parsing the response from the SERVER
			handshake = new ServerHandshakeImplementation();
			ServerHandshakeBuilder serverhandshake = (ServerHandshakeBuilder) handshake;
			serverhandshake.setHttpStatus( Short.parseShort( firstLineTokens[ 1 ] ) );
			serverhandshake.setHttpStatusMessage( firstLineTokens[ 2 ] );
		} else {
			// translating/parsing the request from the CLIENT
			ClientHandshakeBuilder clienthandshake = new ClientHandshakeImplementation();
			clienthandshake.setResourceDescriptor( firstLineTokens[ 1 ] );
			handshake = clienthandshake;
		}

		line = readStringLine( buf );
		while ( line != null && line.length() > 0 ) {
			String[] pair = line.split( ":", 2 );
			if( pair.length != 2 )
				throw new InvalidHandshakeException( "not an http header" );
			handshake.put( pair[ 0 ], pair[ 1 ].replaceFirst( "^ +", "" ) );
			line = readStringLine( buf );
		}
		if( line == null )
			throw new IncompleteHandshakeException();
		return handshake;
	}

	public abstract HandshakeState acceptHandshakeAsClient( ClientHandshake request, ServerHandshake response ) throws InvalidHandshakeException;

	public abstract HandshakeState acceptHandshakeAsServer( ClientHandshake handshakedata ) throws InvalidHandshakeException;

	protected boolean basicAccept( HandshakeData handshakeData ) {
		return handshakeData.getFieldValue( "Upgrade" ).equalsIgnoreCase( "websocket" ) && handshakeData.getFieldValue( "Connection" ).toLowerCase( Locale.ENGLISH ).contains( "upgrade" );
	}

	public abstract ByteBuffer createBinaryFrame( FrameData framedata ); // TODO Allow to send data on the base of an Iterator or InputStream

	public abstract List<FrameData> createFrames( ByteBuffer binary, boolean mask );

	public abstract List<FrameData> createFrames( String text, boolean mask );

	public List<FrameData> continuousFrame( Opcode op, ByteBuffer buffer, boolean fin ) {
		if( op != Opcode.BINARY && op != Opcode.TEXT && op != Opcode.TEXT ) {
			throw new IllegalArgumentException( "Only Opcode.BINARY or  Opcode.TEXT are allowed" );
		}

		if( continuousFrameType != null ) {
			continuousFrameType = Opcode.CONTINUOUS;
		} else {
			continuousFrameType = op;
		}

		FrameBuilder bui = new FrameDataImplementation( continuousFrameType );
		try {
			bui.setPayload( buffer );
		} catch ( InvalidDataException e ) {
			throw new RuntimeException( e ); // can only happen when one builds close frames(Opcode.Close)
		}
		bui.setFin( fin );
		if( fin ) {
			continuousFrameType = null;
		} else {
			continuousFrameType = op;
		}
		return Collections.singletonList( (FrameData) bui );
	}

	public abstract void reset();

	public List<ByteBuffer> createHandshake( HandshakeData handshakeData, Role ownrole ) {
		return createHandshake( handshakeData, ownrole, true );
	}

	public List<ByteBuffer> createHandshake( HandshakeData handshakeData, Role ownrole, boolean withcontent ) {
		StringBuilder bui = new StringBuilder( 100 );
		if( handshakeData instanceof ClientHandshake ) {
			bui.append( "GET " );
			bui.append( ( (ClientHandshake) handshakeData ).getResourceDescriptor() );
			bui.append( " HTTP/1.1" );
		} else if( handshakeData instanceof ServerHandshake ) {
			bui.append( "HTTP/1.1 101 " + ( (ServerHandshake) handshakeData ).getHttpStatusMessage() );
		} else {
			throw new RuntimeException( "unknow role" );
		}
		bui.append( "\r\n" );
		Iterator<String> it = handshakeData.iterateHttpFields();
		while ( it.hasNext() ) {
			String fieldname = it.next();
			String fieldvalue = handshakeData.getFieldValue( fieldname );
			bui.append( fieldname );
			bui.append( ": " );
			bui.append( fieldvalue );
			bui.append( "\r\n" );
		}
		bui.append( "\r\n" );
		byte[] httpheader = CharsetHelper.asciiBytes( bui.toString() );

		byte[] content = withcontent ? handshakeData.getContent() : null;
		ByteBuffer bytebuffer = ByteBuffer.allocate( ( content == null ? 0 : content.length ) + httpheader.length );
		bytebuffer.put( httpheader );
		if( content != null )
			bytebuffer.put( content );
		bytebuffer.flip();
		return Collections.singletonList( bytebuffer );
	}

	public abstract ClientHandshakeBuilder postProcessHandshakeRequestAsClient( ClientHandshakeBuilder request ) throws InvalidHandshakeException;

	public abstract HandshakeBuilder postProcessHandshakeResponseAsServer( ClientHandshake request, ServerHandshakeBuilder response ) throws InvalidHandshakeException;

	public abstract List<FrameData> translateFrame( ByteBuffer buffer ) throws InvalidDataException;

	public abstract CloseHandshakeType getCloseHandshakeType();

	/**
	 * Drafts must only be by one websocket at all. To prevent drafts to be used more than once the Websocket implementation should call this method in order to create a new usable version of a given draft instance.<br>
	 * The copy can be safely used in conjunction with a new websocket connection.
	 * */
	public abstract Draft copyInstance();

	public HandshakeData translateHandshake( ByteBuffer buf ) throws InvalidHandshakeException {
		return translateHandshakeHttp( buf, role );
	}

	public int checkAlloc( int bytecount ) throws LimitExceededException , InvalidDataException {
		if( bytecount < 0 )
			throw new InvalidDataException( CloseFrame.PROTOCOL_ERROR, "Negative count" );
		return bytecount;
	}

	public void setParseMode( Role role ) {
		this.role = role;
	}
	
	public Role getRole() {
		return role;
	}

}
