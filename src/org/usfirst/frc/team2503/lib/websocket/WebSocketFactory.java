package org.usfirst.frc.team2503.lib.websocket;

import java.net.Socket;
import java.util.List;

import org.usfirst.frc.team2503.lib.websocket.drafts.Draft;

public interface WebSocketFactory {
	public WebSocket createWebSocket( WebSocketAdaptor a, Draft d, Socket s );
	public WebSocket createWebSocket( WebSocketAdaptor a, List<Draft> drafts, Socket s );
}
