package com.github.ydydwang.aio.channel;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

import com.github.ydydwang.aio.buffer.Buf;
import com.github.ydydwang.aio.list.ListNode;
import com.github.ydydwang.aio.util.AllocatorUtils;

public class ChannelContext  {
	private static final int DEFAULT_CAPACITY = 1024;

	private final MainChannelContext mainChannelContext;
	private final AsynchronousSocketChannel channel;
	private int capacity = DEFAULT_CAPACITY;
	private Buf buffer;

	public ChannelContext(AsynchronousSocketChannel channel
			, MainChannelContext mainChannelContext) {
		this.channel = channel;
		this.mainChannelContext = mainChannelContext;
	}

	public ListNode<ChannelInboundHandler> getHandlerList() {
		return mainChannelContext.getHandlerList();
	}

	public AsynchronousServerSocketChannel getServerSocketChannel() {
		return mainChannelContext.getChannel();
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
