package org.usfirst.frc.team2503.r2016.web;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.json.*;
import org.usfirst.frc.team2503.r2016.configuration.Configuration;
import org.usfirst.frc.team2503.websocket.WebSocket;
import org.usfirst.frc.team2503.websocket.handshake.ClientHandshake;
import org.usfirst.frc.team2503.websocket.server.WebSocketServer;

public class WebConfiguration extends Configuration {

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
			System.out.println("[WebConfiguration] Received data of length " + data.length() + "... converting to JSONObject.");

			JSONObject object = new JSONObject(data);

			System.out.println("[WebConfiguration] JSONObject has " + object.keySet().size() + " keys.");

			System.out.println("[WebConfiguration] Before merge: " + WebConfiguration.this.toString());

			for(String key : object.keySet()) {
				WebConfiguration.this.put(key, object.get(key));
			}

			System.out.println("[WebConfiguration] After merge: " + WebConfiguration.this.toString());
		}

		@Override
		public void onOpen(WebSocket connection, ClientHandshake handshake) {
			System.out.println("[WebConfiguration] Shook hands with a client! " + handshake.getResourceDescriptor());
		}

		@Override
		public void onClose(WebSocket connection, int code, String reason, boolean remote) {
			System.out.println("[WebConfiguration] Lost connection with a client. (code: " + code + ", reason: \"" + reason + "\", remote: " + (remote ? "true" : "false") + ")");
		}

		@Override
		public void onError(WebSocket connection, Exception exception) {
			System.out.println("[WebConfiguration] Error: " + exception.getMessage());
		}

	}

	private Server server;

	public Server getServerInstance() {
		return this.server;
	}

	public WebConfiguration(InetSocketAddress inetSocketAddress) {
		this.server = new Server(inetSocketAddress);
	}

	public WebConfiguration(final int port) {
		this.server = new Server(port);
	}

	public WebConfiguration() {
		this.server = new Server(5800);
	}

}
