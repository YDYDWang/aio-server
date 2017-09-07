package com.github.ydydwang.aio.channel;

import java.nio.channels.AsynchronousServerSocketChannel;

import com.github.ydydwang.aio.list.ListNode;

public class MainChannelContext  {
	private final ListNode<ChannelInboundHandler> handlerList;
	private final AsynchronousServerSocketChannel channel;

	public MainChannelContext(ListNode<ChannelInboundHandler> handlerList
			, AsynchronousServerSocketChannel channel) {
		this.handlerList = handlerList;
		this.channel = channel;
	}

	public ListNode<ChannelInboundHandler> getHandlerList() {
		return handlerList;
	}

	public AsynchronousServerSocketChannel getChannel() {
		return channel;
	}
}
