package com.github.ydydwang.aio.channel.ssl;

import java.nio.channels.AsynchronousSocketChannel;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;

import com.github.ydydwang.aio.channel.ChannelContext;
import com.github.ydydwang.aio.channel.MainChannelContext;

public class SSLChannelContext extends ChannelContext {
	private final SSLEngine engine;
	private final SSLSession session;
	private final SSLHandshakeHandler handshakeHandler;

	public SSLChannelContext(AsynchronousSocketChannel channel, MainChannelContext mainChannelContext
			, SSLEngine engine) {
		super(channel, mainChannelContext);
		this.engine = engine;
		this.session = engine.getSession();
		this.capacity = session.getPacketBufferSize();
		this.handshakeHandler = new SSLHandshakeHandler();
	}

	public SSLEngine getEngine() {
		return engine;
	}

	public SSLSession getSession() {
		return session;
	}

	public SSLHandshakeHandler getHandshakeHandler() {
		return handshakeHandler;
	}

	@Override
	protected void checkIncrement() {
	}
}
