package org.usfirst.frc.team2503.r2016.data;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.json.*;
import org.usfirst.frc.team2503.websocket.WebSocket;
import org.usfirst.frc.team2503.websocket.handshake.ClientHandshake;
import org.usfirst.frc.team2503.websocket.server.WebSocketServer;

public class DataServer extends Data {
	
	public class Server extends WebSocketServer {

		public Server() throws UnknownHostException {
			super();
		}

		public Server(int port) {
			super(new InetSocketAddress(port));
		}

		public Server(InetSocketAddress inetSocketAddress) {
			super(inetSocketAddress);
		}

		@Override
		public void onMessage(WebSocket connection, String data) {
			System.out.println("[DataServer] Received data of length " + data.length() + "... converting to JSONObject.");

			JSONObject object = new JSONObject(data);

			System.out.println("[DataServer] JSONObject has " + object.keySet().size() + " keys.");

			System.out.println("[DataServer] Before merge: " + DataServer.this.toString());

			for(String key : object.keySet()) {
				DataServer.this.put(key, object.get(key));
			}

			System.out.println("[DataServer] After merge: " + DataServer.this.toString());
			
			connection.send(DataServer.this.toString());
		}

		@Override
		public void onOpen(WebSocket connection, ClientHandshake handshake) {
			System.out.println("[DataServer] Shook hands with a client! " + handshake.getResourceDescriptor());
		}

		@Override
		public void onClose(WebSocket connection, int code, String reason, boolean remote) {
			System.out.println("[DataServer] Lost connection with a client. (code: " + code + ", reason: \"" + reason + "\", remote: " + (remote ? "true" : "false") + ")");
		}

		@Override
		public void onError(WebSocket connection, Exception exception) {
			System.out.println("[DataServer] Error: " + exception.getMessage());
		}

	}

	public Server server = new Server(5800);

	public Server getServerInstance() {
		return this.server;
	}

	public DataServer(InetSocketAddress inetSocketAddress) {
		this.server = new Server(inetSocketAddress);
	}

	public DataServer(final int port) {
		this.server = new Server(port);
	}

	public DataServer() {
		this.server = new Server(5800);
	}

}
