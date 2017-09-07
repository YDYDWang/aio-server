package com.github.ydydwang.aio;

import java.nio.ByteBuffer;

import com.github.ydydwang.aio.channel.ChannelContext;
import com.github.ydydwang.aio.channel.ChannelInboundHandler;
import com.github.ydydwang.aio.util.ByteBufferUtils;

public class DefaultChannelInboundHandler implements ChannelInboundHandler {

	public void channelActive(ChannelContext channelContext) throws Exception {
		System.out.println("channelActive" + channelContext.getChannel().getRemoteAddress());
	}

	public void channelInactive(ChannelContext channelContext) throws Exception {
		System.out.println("channelInactive" + channelContext.getChannel().getRemoteAddress());
	}

	public void channelRead(ChannelContext channelContext, ByteBuffer buffer) throws Exception {
		System.out.println("channelRead" + ByteBufferUtils.toString(buffer));
		System.out.println("channelRead" + channelContext.getChannel().getRemoteAddress());
	}

	public void exceptionCaught(ChannelContext channelContext, Throwable cause) throws Exception {
		System.out.println("exceptionCaught" + channelContext.getChannel().getRemoteAddress());
	}

	public void channelUnregistered(Throwable cause) throws Exception {
		System.out.println("channelUnregistered");
	}

}
