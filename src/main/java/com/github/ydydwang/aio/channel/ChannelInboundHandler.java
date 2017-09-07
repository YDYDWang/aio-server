package com.github.ydydwang.aio.channel;

import java.nio.ByteBuffer;

public interface ChannelInboundHandler {

	void channelActive(ChannelContext channelContext) throws Exception;

	void channelInactive(ChannelContext channelContext) throws Exception;

	void channelRead(ChannelContext channelContext, ByteBuffer buffer) throws Exception;

	void exceptionCaught(ChannelContext channelContext, Throwable cause) throws Exception;

	void channelUnregistered(Throwable cause) throws Exception;
}
