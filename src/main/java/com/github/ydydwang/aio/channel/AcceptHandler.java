package com.github.ydydwang.aio.channel;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.github.ydydwang.aio.util.TriggerUtils;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, MainChannelContext> {
	public static final AcceptHandler INSTANCE = new AcceptHandler();

	public void completed(AsynchronousSocketChannel channel, MainChannelContext context) {
		context.getChannel().accept(context, AcceptHandler.INSTANCE);
		ChannelContext channelContext = new ChannelContext(channel, context);
		TriggerUtils.channelActive(channelContext.getHandlerList(), channelContext);
		try {
			channelContext.getChannel().read(channelContext.newBuffer(), channelContext, ReadHandler.INSTANCE);
		} catch (Exception e) {
			TriggerUtils.exceptionCaught(channelContext.getHandlerList(), channelContext, e.getCause());
		}
	}

	public void failed(Throwable cause, MainChannelContext context) {
		TriggerUtils.channelUnregistered(context.getHandlerList(), cause);
	}

}