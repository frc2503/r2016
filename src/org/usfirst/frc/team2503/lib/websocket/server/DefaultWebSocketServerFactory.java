package org.usfirst.frc.team2503.lib.websocket.server;

import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.usfirst.frc.team2503.lib.websocket.WebSocketAdaptor;
import org.usfirst.frc.team2503.lib.websocket.WebSocketImplementation;
import org.usfirst.frc.team2503.lib.websocket.drafts.Draft;
import org.usfirst.frc.team2503.lib.websocket.server.WebSocketServer.WebSocketServerFactory;

public class DefaultWebSocketServerFactory implements WebSocketServerFactory {
	@Override
	public WebSocketImplementation createWebSocket( WebSocketAdaptor a, Draft d, Socket s ) {
		return new WebSocketImplementation( a, d );
	}
	@Override
	public WebSocketImplementation createWebSocket( WebSocketAdaptor a, List<Draft> d, Socket s ) {
		return new WebSocketImplementation( a, d );
	}
	@Override
	public SocketChannel wrapChannel( SocketChannel channel, SelectionKey key ) {
		return (SocketChannel) channel;
	}
}