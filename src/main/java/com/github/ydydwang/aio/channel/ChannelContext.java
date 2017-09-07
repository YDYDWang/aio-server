package com.github.ydydwang.aio.channel;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

import com.github.ydydwang.aio.buffer.Buf;
import com.github.ydydwang.aio.util.AllocatorUtils;

public class ChannelContext  {
	private static final int DEFAULT_CAPACITY = 1024;
	static ChannelInboundHandler handler;
	static AsynchronousServerSocketChannel serverSocketChannel;

	private final AsynchronousSocketChannel channel;
	private int capacity = DEFAULT_CAPACITY;
	private Buf buffer;

	public ChannelContext(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}

	public static void init(ChannelInboundHandler handler
			, AsynchronousServerSocketChannel serverSocketChannel) {
		ChannelContext.handler = handler;
		ChannelContext.serverSocketChannel = serverSocketChannel;
	}

	public static ChannelInboundHandler getHandler() {
		return handler;
	}

	public static AsynchronousServerSocketChannel getServerSocketChannel() {
		return serverSocketChannel;
	}

	public AsynchronousSocketChannel getChannel() {
		return channel;
	}

	public ByteBuffer getBuffer() {
		return buffer.getBuffer();
	}

	public ByteBuffer newBuffer() {
		this.buffer = AllocatorUtils.allocate(capacity);
		return getBuffer();
	}
}
