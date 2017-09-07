package com.github.ydydwang.aio.channel;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;

import com.github.ydydwang.aio.util.ByteBufferUtils;
import com.github.ydydwang.aio.util.TriggerUtils;

public class ReadHandler implements CompletionHandler<Integer, ChannelContext> {

	public void completed(Integer bytes, ChannelContext channelContext) {
		TriggerUtils.channelRead(channelContext.getHandlerList(), channelContext, channelContext.getBuffer());
		try {
			channelContext.getChannel().read(channelContext.newBuffer(), channelContext, channelContext.getReadHandler());
		} catch (Exception e) {
			channelContext.getReadHandler().failed(e.getCause(), channelContext);
		}
	}

	public void failed(Throwable cause, ChannelContext channelContext) {
		TriggerUtils.exceptionCaught(channelContext.getHandlerList(), channelContext, cause);
		if (cause instanceof IOException || cause instanceof ClosedChannelException
				|| cause instanceof AsynchronousCloseException) {
			TriggerUtils.channelInactive(channelContext.getHandlerList(), channelContext);
			try {
				channelContext.getChannel().close();
			} catch (Exception e) {
			} finally {
				ByteBufferUtils.releaseQuietly(channelContext.getBuffer());
			}
		}
	}

}