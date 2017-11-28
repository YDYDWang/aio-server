package com.github.ydydwang.aio.channel.ssl;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;

import javax.net.ssl.SSLEngineResult;

import com.github.ydydwang.aio.common.Buffers;
import com.github.ydydwang.aio.util.ByteBufferUtils;
import com.github.ydydwang.aio.util.TriggerUtils;

public class SSLReadHandler extends SSLHandshakeHandler {

	@Override
	protected void doCompleted(Integer count, SSLChannelContext channelContext) {
		try {
			SSLEngineResult result = channelContext.getEngine().wrap(Buffers.EMPTY_BUFFER, channelContext.getOutNetBuffer());
			System.out.println("SSLWrapHandler:" + result);
			channelContext.setHandshakeStatus(channelContext.getEngine().getHandshakeStatus());
			channelContext.getOutNetBuffer().flip();
		} catch (Exception e) {
			this.failed(e, channelContext);
		}
	}

	@Override
	public void failed(Throwable cause, SSLChannelContext channelContext) {
		if (cause instanceof IOException || cause instanceof ClosedChannelException
				|| cause instanceof AsynchronousCloseException) {
			TriggerUtils.readFailed(channelContext.getHandlerList(), channelContext, cause);
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