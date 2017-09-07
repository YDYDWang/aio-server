package com.github.ydydwang.aio.channel;

import java.io.IOException;
import java.nio.channels.CompletionHandler;

public class ReadHandler implements CompletionHandler<Integer, ChannelContext> {
	public static final ReadHandler INSTANCE = new ReadHandler();

	public void completed(Integer bytes, ChannelContext channelContext) {
		try {
			ChannelContext.getHandler().channelRead(channelContext, channelContext.getBuffer());
			channelContext.getChannel().read(channelContext.newBuffer(), channelContext, ReadHandler.INSTANCE);
		} catch (Exception e) {
		}
	}

	public void failed(Throwable cause, ChannelContext channelContext) {
		try {
			ChannelContext.getHandler().exceptionCaught(channelContext, cause);
			if (cause instanceof IOException) {
				try {
					ChannelContext.getHandler().channelInactive(channelContext);
					channelContext.getChannel().close();
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
	}

}