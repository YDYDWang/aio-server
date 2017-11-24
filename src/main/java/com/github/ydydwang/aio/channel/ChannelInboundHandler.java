package com.github.ydydwang.aio.channel;

public interface ChannelInboundHandler<I, O> {

	void channelActive(ChannelContext channelContext) throws Exception;

	void channelInactive(ChannelContext channelContext) throws Exception;

	O channelRead(ChannelContext channelContext, I msg) throws Exception;

	void readFailed(ChannelContext channelContext, Throwable cause) throws Exception;

	void acceptFailed(Throwable cause) throws Exception;
}
