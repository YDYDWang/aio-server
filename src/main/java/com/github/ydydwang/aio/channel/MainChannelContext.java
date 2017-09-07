package com.github.ydydwang.aio.channel;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.github.ydydwang.aio.list.ListNode;

public class MainChannelContext  {
	private final ListNode<ChannelInboundHandler> handlerList;
	private final AsynchronousServerSocketChannel channel;
	private final CompletionHandler<AsynchronousSocketChannel, MainChannelContext> acceptHandler;
	private final CompletionHandler<Integer, ChannelContext> readHandler;

	public MainChannelContext(ListNode<ChannelInboundHandler> handlerList
			, AsynchronousServerSocketChannel channel
			, CompletionHandler<AsynchronousSocketChannel, MainChannelContext> acceptHandler
			, CompletionHandler<Integer, ChannelContext> readHandler) {
		this.handlerList = handlerList;
		this.channel = channel;
		this.acceptHandler = acceptHandler;
		this.readHandler = readHandler;
	}

	public ListNode<ChannelInboundHandler> getHandlerList() {
		return handlerList;
	}

	public AsynchronousServerSocketChannel getChannel() {
		return channel;
	}

	public CompletionHandler<AsynchronousSocketChannel, MainChannelContext> getAcceptHandler() {
		return acceptHandler;
	}

	public CompletionHandler<Integer, ChannelContext> getReadHandler() {
		return readHandler;
	}
}
