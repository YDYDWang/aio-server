package com.github.ydydwang.aio.channel;

import java.io.IOException;
import java.nio.channels.CompletionHandler;

import com.github.ydydwang.aio.util.TriggerUtils;

public class ReadHandler implements CompletionHandler<Integer, ChannelContext> {
	public static final ReadHandler INSTANCE = new ReadHandler();

	public void completed(Integer bytes, ChannelContext channelContext) {
		TriggerUtils.channelRead(channelContext.getHandlerList(), channelContext, channelContext.getBuffer());
		try {
			channelContext.getChannel().read(channelContext.newBuffer(), channelContext, ReadHandler.INSTANCE);
		} catch (Exception e) {
			TriggerUtils.exceptionCaught(channelContext.getHandlerList(), channelContext, e.getCause());
		}
	}

	public void failed(Throwable cause, ChannelContext channelContext) {
		TriggerUtils.exceptionCaught(channelContext.getHandlerList(), channelContext, cause);
		if (cause instanceof IOException) {
			TriggerUtils.channelInactive(channelContext.getHandlerList(), channelContext);
			try {
				channelContext.getChannel().close();
			} catch (Exception e) {
			}
		}
	}

}