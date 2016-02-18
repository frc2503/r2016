package org.usfirst.frc.team2503.r2016.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import org.json.JSONObject;
import org.usfirst.frc.team2503.websocket.WebSocket;
import org.usfirst.frc.team2503.websocket.handshake.ClientHandshake;

public class MessageServer extends WebSocketServer {
		
	public MessageServer() throws UnknownHostException {
		super();
	}

	public MessageServer(InetSocketAddress address) {
		super(address);
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		JSONObject object = new JSONObject(message);
		
		if(object.get("sender") == null) {
			return;
		} else {
			HashSet<WebSocket> connections = (HashSet<WebSocket>) super.connections();
			
			System.err.println("[MessageServer.onMessage] Received message of length " + message.length() + ", sending to maximum of " + connections.size() + " connections...");
	
			for(WebSocket connection : connections) {
				connection.send(message);
			}
		}
	}
	
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.err.println("[MessageServer.onOpen] Opened connection with handshake " + handshake.getContent());
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.err.println("[MessageServer.onClose] Closed connection with code " + code);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace(System.err);
	}

	public void sendMessage(String type, String message) {
		JSONObject object = new JSONObject();
		
		object.put("sender", "robot");
		object.put("type", type);
		object.put("message", message);
		
		this.send(object.toString());
	}
	
}
