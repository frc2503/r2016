package org.usfirst.frc.team2503.r2016.server;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.usfirst.frc.team2503.websocket.WebSocket;
import org.usfirst.frc.team2503.websocket.handshake.ClientHandshake;

public class ImageServer extends WebSocketServer {
	public Object latestImage = null;
	
	public ImageServer() throws UnknownHostException {
		super();
	}

	public ImageServer(InetSocketAddress address) {
		super(address);
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
	}
	
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.err.println("[ImageServer.onOpen] Opened connection with handshake " + handshake.getContent());

		conn.send((this.latestImage != null ? this.latestImage.toString() : null));
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.err.println("[ImageServer.onClose] Closed connection with code " + code);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace(System.err);
	}
	
	public void sendImage(Object image) {
		this.latestImage = image;

		this.send((this.latestImage != null ? this.latestImage.toString() : null));
	}
	
}
