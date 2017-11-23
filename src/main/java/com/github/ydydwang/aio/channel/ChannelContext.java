package com.github.ydydwang.aio.channel;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.github.ydydwang.aio.buffer.Buf;
import com.github.ydydwang.aio.collection.ListNode;
import com.github.ydydwang.aio.common.Numbers;
import com.github.ydydwang.aio.util.AllocatorUtils;

public class ChannelContext {
	private static final int DEFAULT_CAPACITY = 1024;

	private final MainChannelContext mainChannelContext;
	private final AsynchronousSocketChannel channel;
	private SocketAddress remoteAddress;
	private int capacity = DEFAULT_CAPACITY;
	private int count;
	private Buf buffer;

	public ChannelContext(AsynchronousSocketChannel channel
			, MainChannelContext mainChannelContext) {
		this.channel = channel;
		try {
			this.remoteAddress = channel.getRemoteAddress();
		} catch (IOException e) {
		}
		this.mainChannelContext = mainChannelContext;
	}

	public ListNode<ChannelInboundHandler> getHandlerList() {
		return mainChannelContext.getHandlerList();
	}

	public AsynchronousServerSocketChannel getServerSocketChannel() {
		return mainChannelContext.getChannel();
	}

	public CompletionHandler<AsynchronousSocketChannel, MainChannelContext> getAcceptHandler() {
		return mainChannelContext.getAcceptHandler();
	}

	public CompletionHandler<Integer, ChannelContext> getReadHandler() {
		return mainChannelContext.getReadHandler();
	}

	public AsynchronousSocketChannel getChannel() {
		return channel;
	}

	public SocketAddress getRemoteAddress() {
		return remoteAddress;
	}

	public ByteBuffer getBuffer() {
		return buffer.getBuffer();
	}

	public ByteBuffer newBuffer() {
		checkIncrement();
		this.buffer = AllocatorUtils.allocate(capacity);
		return getBuffer();
	}

	private void checkIncrement() {
		if (++count > Numbers.INT_SIXTEEN) {
			capacity = capacity << Numbers.INT_TWO;
			count = Numbers.INT_ZERO;
		}
	}
}
