package com.github.ydydwang.aio.channel;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.github.ydydwang.aio.util.TriggerUtils;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, MainChannelContext> {

	@Override
	public void completed(AsynchronousSocketChannel channel, MainChannelContext context) {
		context.getChannel().accept(context, context.getAcceptHandler());
		ChannelContext channelContext = new ChannelContext(channel, context);
		TriggerUtils.channelActive(channelContext.getHandlerList(), channelContext);
		if (channelContext.getChannel().isOpen()) {
			try {
				channelContext.getChannel().read(channelContext.newBuffer(), channelContext, channelContext.getReadHandler());
			} catch (Exception e) {
				channelContext.getReadHandler().failed(e, channelContext);
			}
		} else {
			TriggerUtils.channelInactive(channelContext.getHandlerList(), channelContext);
		}
	}

	@Override
	public void failed(Throwable cause, MainChannelContext context) {
		TriggerUtils.acceptFailed(context.getHandlerList(), cause);
	}

}