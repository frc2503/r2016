package org.usfirst.frc.team2503.r2016.server;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.json.JSONObject;
import org.usfirst.frc.team2503.lib.websocket.WebSocket;
import org.usfirst.frc.team2503.lib.websocket.handshake.ClientHandshake;

public class DataServer extends WebSocketServer {

	public JSONObject data = new JSONObject();
	
	public JSONObject put(String key, Object value) {
		JSONObject returnValue = this.data.put(key, value);
		
		this.send(returnValue.toString());
		
		return returnValue;
	}
	
	public void send() {
		this.send(this.data.toString());
	}
	
	public DataServer() throws UnknownHostException {
		super();
	}

	public DataServer(InetSocketAddress address) {
		super(address);
	}
	
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.err.println("[DataServer.onOpen] Opened connection with handshake " + handshake.getContent());
		
		conn.send(this.data.toString());
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.err.println("[DataServer.onClose] Closed connection with code " + code);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace(System.err);
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		conn.send(message);
	}
	
}
