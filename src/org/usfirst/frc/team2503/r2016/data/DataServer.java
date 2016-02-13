package org.usfirst.frc.team2503.r2016.data;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.json.JSONObject;
import org.usfirst.frc.team2503.r2016.Constants;
import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.websocket.WebSocket;
import org.usfirst.frc.team2503.websocket.handshake.ClientHandshake;
import org.usfirst.frc.team2503.websocket.server.WebSocketServer;

public class DataServer {

	public JSONObject data = new JSONObject();
	
	public class Server extends WebSocketServer {

		public Server(InetSocketAddress address) {
			super(address);
		}

		@Override
		public void onOpen(WebSocket conn, ClientHandshake handshake) {
		}

		@Override
		public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		}

		@Override
		public void onMessage(WebSocket conn, String message) {
			JSONObject object = new JSONObject(message);
			
			for(String key : object.keySet()) {
				DataServer.this.data.put(key, object.get(key));
			}
		}

		@Override
		public void onError(WebSocket conn, Exception ex) {
		}

		public void send(String text) {
			for(WebSocket conn : this.connections()) {
				conn.send(text);
			}
		}
		
	}
	
	public JSONObject put(String key, Object value) {
		data.put(key, value);
		
		server.send(data.toString());
		
		return data;
	}
	
	public Object get(String key) {
		return data.get(key);
	}
	
	public Server server;
	
	
	public DataServer() {
		server = new Server(new InetSocketAddress(5800));		
		Logger.println(server.getAddress().toString());
		
		new Thread(server).start();
	}

	public DataServer(int port) {
		server = new Server(new InetSocketAddress(port));
		Logger.println(server.getAddress().toString());
		
		new Thread(server).start();
	}

}
