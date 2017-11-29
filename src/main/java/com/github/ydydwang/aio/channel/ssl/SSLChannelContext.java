package com.github.ydydwang.aio.channel.ssl;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

import com.github.ydydwang.aio.buffer.Buf;
import com.github.ydydwang.aio.channel.ChannelContext;
import com.github.ydydwang.aio.channel.MainChannelContext;
import com.github.ydydwang.aio.util.AllocatorUtils;

public class SSLChannelContext extends ChannelContext {
	private final SSLEngine engine;
	private final SSLSession session;
	private HandshakeStatus handshakeStatus;
	private Buf inNetBuffer;
	private Buf outNetBuffer;

	public SSLChannelContext(AsynchronousSocketChannel channel, MainChannelContext mainChannelContext
			, SSLEngine engine) throws SSLException {
		super(channel, mainChannelContext);
		this.engine = engine;
		this.session = engine.getSession();
		this.capacity = session.getApplicationBufferSize();
		this.engine.setUseClientMode(Boolean.FALSE);
		this.engine.beginHandshake();
		this.handshakeStatus = engine.getHandshakeStatus();
		this.newBuffer();
		this.inNetBuffer = AllocatorUtils.allocate(session.getPacketBufferSize());
		this.outNetBuffer = AllocatorUtils.allocate(session.getPacketBufferSize());
	}

	public SSLEngine getEngine() {
		return engine;
	}

	public SSLSession getSession() {
		return session;
	}

	public HandshakeStatus getHandshakeStatus() {
		return handshakeStatus;
	}

	public void setHandshakeStatus(HandshakeStatus handshakeStatus) {
		this.handshakeStatus = handshakeStatus;
	}

	public void setHandshakeStatus(SSLEngineResult result) {
		this.handshakeStatus = result.getHandshakeStatus();
	}

	public ByteBuffer getInNetBuffer() {
		return inNetBuffer.getBuffer();
	}

	
	public ByteBuffer getOutNetBuffer() {
		return outNetBuffer.getBuffer();
	}

	public void write() {
		
	}

	public void release() {
		buffer.realease();
		inNetBuffer.realease();
		outNetBuffer.realease();
	}

	@Override
	protected void checkIncrement() {
	}

}
