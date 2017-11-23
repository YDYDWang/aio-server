package com.github.ydydwang.aio.channel;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;

import com.github.ydydwang.aio.common.Numbers;
import com.github.ydydwang.aio.util.ByteBufferUtils;
import com.github.ydydwang.aio.util.TriggerUtils;

public final class ReadHandler implements CompletionHandler<Integer, ChannelContext> {

	public void completed(Integer count, ChannelContext channelContext) {
		if (count != Numbers.INT_MINUS_ONE) {
			TriggerUtils.channelRead(channelContext.getHandlerList(), channelContext, channelContext.getBuffer());
			if (channelContext.getChannel().isOpen()) {
				try {
					channelContext.getChannel().read(channelContext.newBuffer(), channelContext, channelContext.getReadHandler());
				} catch (Exception e) {
					channelContext.getReadHandler().failed(e.getCause(), channelContext);
				}
			} else {
				TriggerUtils.channelInactive(channelContext.getHandlerList(), channelContext);
			}
		} else {
			TriggerUtils.channelInactive(channelContext.getHandlerList(), channelContext);
		}
	}

	public void failed(Throwable cause, ChannelContext channelContext) {
		if (cause instanceof IOException || cause instanceof ClosedChannelException
				|| cause instanceof AsynchronousCloseException) {
			TriggerUtils.exceptionCaught(channelContext.getHandlerList(), channelContext, cause);
			try {
				channelContext.getChannel().close();
			} catch (Exception e) {
			} finally {
				ByteBufferUtils.releaseQuietly(channelContext.getBuffer());
			}
		}
		TriggerUtils.channelInactive(channelContext.getHandlerList(), channelContext);
	}

}