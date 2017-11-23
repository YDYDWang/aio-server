package com.github.ydydwang.aio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;

import com.github.ydydwang.aio.collection.ListNode;

public class AioServerBuilder {
	private AsynchronousChannelGroup group;
	private String host;
	private Integer port;
	private final List<ChannelInboundHandler> handlerList = new LinkedList<ChannelInboundHandler>();

	public AioServerBuilder threadCount(int count) throws IOException {
		this.group = AsynchronousChannelGroup
				.withFixedThreadPool(count, Executors.defaultThreadFactory());
		return this;
	}

	public AioServerBuilder host(String host) {
		this.host = host;
		return this;
	}

	public AioServerBuilder port(Integer port) {
		this.port = port;
		return this;
	}

	public AioServerBuilder addHandler(ChannelInboundHandler handler) {
		this.handlerList.add(handler);
		return this;
	}

	public void start() throws Exception {
		validate();
		AsynchronousServerSocketChannel serverSocketChannel = 
				AsynchronousServerSocketChannel.open(group)
						.bind(getAddress());
		MainChannelContext mainChannelContext = new MainChannelContext(toListNode(handlerList)
				, serverSocketChannel
				, new AcceptHandler()
				, new ReadHandler()); 
		serverSocketChannel.accept(mainChannelContext, mainChannelContext.getAcceptHandler());
	}

	private InetSocketAddress getAddress() {
		if (host == null) {
			return new InetSocketAddress(this.port);
		}
		return new InetSocketAddress(this.host, this.port);
	}

	private void validate() throws Exception {
		assert this.group != null;
		assert this.port != null;
		assert !this.handlerList.isEmpty();
	}

	public ListNode<ChannelInboundHandler> toListNode(List<ChannelInboundHandler> handlerList) {
		if (handlerList.isEmpty()) {
			return null;
		}
		Collections.reverse(handlerList);
		ListIterator<ChannelInboundHandler> listIterator = handlerList.listIterator();
		ListNode<ChannelInboundHandler> listNode = null;
		while (listIterator.hasNext()) {
			ListNode<ChannelInboundHandler> temp = listNode;
			listNode = new ListNode<ChannelInboundHandler>(listIterator.next());
			listNode.setNext(temp);
		}
		return listNode;
	}
}
