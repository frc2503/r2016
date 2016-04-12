package org.usfirst.frc.team2503.lib.websocket.framing;

import java.nio.ByteBuffer;

import org.usfirst.frc.team2503.lib.websocket.exceptions.InvalidDataException;
import org.usfirst.frc.team2503.lib.websocket.exceptions.InvalidFrameException;
import org.usfirst.frc.team2503.lib.websocket.util.CharsetHelper;

public class CloseFrameBuilder extends FrameDataImplementation implements CloseFrame {

	static final ByteBuffer emptybytebuffer = ByteBuffer.allocate( 0 );

	private int code;
	private String reason;

	public CloseFrameBuilder() {
		super( Opcode.CLOSING );
		setFin( true );
	}

	public CloseFrameBuilder( int code ) throws InvalidDataException {
		super( Opcode.CLOSING );
		setFin( true );
		setCodeAndMessage( code, "" );
	}

	public CloseFrameBuilder( int code , String m ) throws InvalidDataException {
		super( Opcode.CLOSING );
		setFin( true );
		setCodeAndMessage( code, m );
	}

	private void setCodeAndMessage( int code, String m ) throws InvalidDataException {
		if( m == null ) {
			m = "";
		}
		// CloseFrame.TLS_ERROR is not allowed to be transfered over the wire
		if( code == CloseFrame.TLS_ERROR ) {
			code = CloseFrame.NOCODE;
			m = "";
		}
		if( code == CloseFrame.NOCODE ) {
			if( 0 < m.length() ) {
				throw new InvalidDataException( PROTOCOL_ERROR, "A close frame must have a closecode if it has a reason" );
			}
			return;// empty payload
		}

		byte[] by = CharsetHelper.utf8Bytes( m );
		ByteBuffer buf = ByteBuffer.allocate( 4 );
		buf.putInt( code );
		buf.position( 2 );
		ByteBuffer pay = ByteBuffer.allocate( 2 + by.length );
		pay.put( buf );
		pay.put( by );
		pay.rewind();
		setPayload( pay );
	}

	private void initCloseCode() throws InvalidFrameException {
		code = CloseFrame.NOCODE;
		ByteBuffer payload = super.getPayloadData();
		payload.mark();
		if( payload.remaining() >= 2 ) {
			ByteBuffer bb = ByteBuffer.allocate( 4 );
			bb.position( 2 );
			bb.putShort( payload.getShort() );
			bb.position( 0 );
			code = bb.getInt();

			if( code == CloseFrame.ABNORMAL_CLOSE || code == CloseFrame.TLS_ERROR || code == CloseFrame.NOCODE || code > 4999 || code < 1000 || code == 1004 ) {
				throw new InvalidFrameException( "closecode must not be sent over the wire: " + code );
			}
		}
		payload.reset();
	}

	@Override
	public int getCloseCode() {
		return code;
	}

	private void initMessage() throws InvalidDataException {
		if( code == CloseFrame.NOCODE ) {
			reason = CharsetHelper.stringUtf8( super.getPayloadData() );
		} else {
			ByteBuffer b = super.getPayloadData();
			int mark = b.position();// because stringUtf8 also creates a mark
			try {
				b.position( b.position() + 2 );
				reason = CharsetHelper.stringUtf8( b );
			} catch ( IllegalArgumentException e ) {
				throw new InvalidFrameException( e );
			} finally {
				b.position( mark );
			}
		}
	}

	@Override
	public String getMessage() {
		return reason;
	}

	@Override
	public String toString() {
		return super.toString() + "code: " + code;
	}

	@Override
	public void setPayload( ByteBuffer payload ) throws InvalidDataException {
		super.setPayload( payload );
		initCloseCode();
		initMessage();
	}
	@Override
	public ByteBuffer getPayloadData() {
		if( code == NOCODE )
			return emptybytebuffer;
		return super.getPayloadData();
	}

}
