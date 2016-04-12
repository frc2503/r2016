package org.usfirst.frc.team2503.lib.websocket.server;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.usfirst.frc.team2503.lib.websocket.SSLSocketChannel;
import org.usfirst.frc.team2503.lib.websocket.WebSocketAdaptor;
import org.usfirst.frc.team2503.lib.websocket.WebSocketImplementation;
import org.usfirst.frc.team2503.lib.websocket.drafts.Draft;


public class DefaultSSLWebSocketServerFactory implements WebSocketServer.WebSocketServerFactory {
	protected SSLContext sslcontext;
	protected ExecutorService exec;

	public DefaultSSLWebSocketServerFactory(SSLContext sslContext) {
		this(sslContext, Executors.newSingleThreadScheduledExecutor());
	}

	public DefaultSSLWebSocketServerFactory(SSLContext sslContext, ExecutorService exec) {
		if(sslContext == null || exec == null)
			throw new IllegalArgumentException();
		this.sslcontext = sslContext;
		this.exec = exec;
	}

	@Override
	public ByteChannel wrapChannel(SocketChannel channel, SelectionKey key) throws IOException {
		SSLEngine e = sslcontext.createSSLEngine();
		e.setUseClientMode(false);
		return new SSLSocketChannel(channel, e, exec, key);
	}

	@Override
	public WebSocketImplementation createWebSocket(WebSocketAdaptor a, Draft d, Socket c) {
		return new WebSocketImplementation(a, d);
	}

	@Override
	public WebSocketImplementation createWebSocket(WebSocketAdaptor a, List<Draft> d, Socket s) {
		return new WebSocketImplementation(a, d);
	}
}