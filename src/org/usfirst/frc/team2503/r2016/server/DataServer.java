package org.usfirst.frc.team2503.r2016.server;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.json.JSONObject;
import org.usfirst.frc.team2503.websocket.WebSocket;
import org.usfirst.frc.team2503.websocket.handshake.ClientHandshake;

public class DataServer extends WebSocketServer {

	public JSONObject serverData = new JSONObject();
	public JSONObject clientData = new JSONObject();
	
	public JSONObject put(String key, Object value) {
		JSONObject returnValue = this.serverData.put(key, value);
		
		this.send(returnValue.toString());
		
		return returnValue;
	}
	
	public JSONObject getClientData() {
		return this.clientData;
	}
	
	public void send() {
		this.send(this.serverData.toString());
	}
	
	public DataServer() throws UnknownHostException {
		super();
	}

	public DataServer(InetSocketAddress address) {
		super(address);
	}
	
	@Override
	public void onMessage(WebSocket conn, String message) {
		JSONObject parsedMessage = new JSONObject(message);

		for(String key : parsedMessage.keySet()) {
			this.clientData.put(key, parsedMessage.get(key));
		}
		
		this.send(this.getClientData().toString());
		
		System.out.println("[DataServer.onMessage] Received and sent ClientData: " + this.getClientData().toString());
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.err.println("[DataServer.onOpen] Opened connection with handshake " + handshake.getContent());
		
		conn.send(this.serverData.toString());
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.err.println("[DataServer.onClose] Closed connection with code " + code);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace(System.err);
	}
	
}
