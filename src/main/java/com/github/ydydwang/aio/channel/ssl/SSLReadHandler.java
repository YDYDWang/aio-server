package com.github.ydydwang.aio.channel.ssl;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;

import javax.net.ssl.SSLEngineResult;

import com.github.ydydwang.aio.channel.ChannelContext;
import com.github.ydydwang.aio.util.ByteBufferUtils;
import com.github.ydydwang.aio.util.TriggerUtils;

public class SSLReadHandler extends SSLHandshakeHandler {
	public static final SSLReadHandler INSTANSE = new SSLReadHandler();

	@Override
	protected void doCompleted(SSLChannelContext channelContext) {
		try {
			channelContext.getInNetBuffer().flip();
			SSLEngineResult result = channelContext.getEngine().unwrap(channelContext.getInNetBuffer(), channelContext.newBuffer());
			System.out.println("SSLReadHandler:" + result);
			System.out.println(ByteBufferUtils.toString(channelContext.getBuffer()));
			channelContext.getInNetBuffer().clear();
			TriggerUtils.channelRead(channelContext.getHandlerList(), channelContext, channelContext.getBuffer());
			if (channelContext.getChannel().isOpen()) {
				try {
					channelContext.getChannel().read(channelContext.getInNetBuffer(), channelContext, SSLReadHandler.INSTANSE);
				} catch (Exception e) {
					this.failed(e, channelContext);
				}
			} else {
				TriggerUtils.channelInactive(channelContext.getHandlerList(), channelContext);
			}
		} catch (Exception e) {
			this.failed(e, channelContext);
		}
	}

	@Override
	public void failed(Throwable cause, ChannelContext channelContext) {
		SSLChannelContext sslChannelContext = (SSLChannelContext) channelContext;
		if (cause instanceof IOException || cause instanceof ClosedChannelException
				|| cause instanceof AsynchronousCloseException) {
			TriggerUtils.readFailed(sslChannelContext.getHandlerList(), sslChannelContext, cause);
			try {
				sslChannelContext.getChannel().close();
			} catch (Exception e) {
			} finally {
				ByteBufferUtils.releaseQuietly(sslChannelContext.getBuffer());
			}
		}
		TriggerUtils.channelInactive(sslChannelContext.getHandlerList(), sslChannelContext);
	}
}