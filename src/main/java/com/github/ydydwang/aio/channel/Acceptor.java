package com.github.ydydwang.aio.channel;

import java.nio.channels.AsynchronousServerSocketChannel;

public class Acceptor implements Runnable {
	private final AsynchronousServerSocketChannel serverSocketChannel;
	private final ChannelInboundHandler channelInboundHandler;
	private final AcceptHandler acceptHandler;

	public Acceptor(AsynchronousServerSocketChannel serverSocketChannel
			, ChannelInboundHandler channelInboundHandler, AcceptHandler acceptHandler) {
		this.serverSocketChannel = serverSocketChannel;
		this.channelInboundHandler = channelInboundHandler;
		this.acceptHandler = acceptHandler;
	}

	public void run() {
		while (Boolean.TRUE) {
			// FIXME need to try catch
			serverSocketChannel.accept(channelInboundHandler, acceptHandler);
			System.out.println("accept");
		}
	}

}