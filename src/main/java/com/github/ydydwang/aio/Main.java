package com.github.ydydwang.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.Executors;

import com.github.ydydwang.aio.channel.AcceptHandler;
import com.github.ydydwang.aio.channel.ChannelContext;

public class Main {
	private static final int DEFAULT_THREAD_COUNT = 10;
	private static final int DEFAULT_PORT = 9999;

	public static void main(String[] args) throws IOException {
		AsynchronousChannelGroup group = AsynchronousChannelGroup
				.withFixedThreadPool(DEFAULT_THREAD_COUNT, Executors.defaultThreadFactory());
		AsynchronousServerSocketChannel serverSocketChannel = 
				AsynchronousServerSocketChannel.open(group)
						.bind(new InetSocketAddress(DEFAULT_PORT));
		ChannelContext.init(new DefaultChannelInboundHandler(), serverSocketChannel);
		ChannelContext.getServerSocketChannel().accept(ChannelContext.getHandler(), AcceptHandler.INSTANCE);
	}
}

