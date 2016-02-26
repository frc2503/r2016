package org.usfirst.frc.team2503.r2016.server;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.usfirst.frc.team2503.lib.websocket.WebSocket;

public abstract class WebSocketServer extends org.usfirst.frc.team2503.lib.websocket.server.WebSocketServer {

	public void send(byte[] bytes) {
		for(WebSocket connection : this.connections()) {
			connection.send(bytes);
		}
	}

	public void send(ByteBuffer bytes) {
		for(WebSocket connection : this.connections()) {
			connection.send(bytes);
		}
	}
	
	public void send(String text) {
		for(WebSocket connection : this.connections()) {
			connection.send(text);
		}
	}
	
	public WebSocketServer(InetSocketAddress address) {
		super(address);
	}
	
	public WebSocketServer() throws UnknownHostException {
		super();
	}
	
}
