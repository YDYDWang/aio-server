package com.github.ydydwang.aio.channel.ssl;

import javax.net.ssl.SSLEngineResult;

import com.github.ydydwang.aio.common.Buffers;

public class SSLHandshakeWrapHandler extends SSLHandshakeHandler {
	private static final SSLHandshakeWrapWriteHandler writeHandler = new SSLHandshakeWrapWriteHandler();

	@Override
	protected void doCompleted(Integer count, SSLChannelContext channelContext) {
		try {
			SSLEngineResult result = channelContext.getEngine().wrap(Buffers.EMPTY_BUFFER, channelContext.getOutNetBuffer());
			System.out.println("SSLWrapHandler:" + result);
			channelContext.setHandshakeStatus(channelContext.getEngine().getHandshakeStatus());
			channelContext.getOutNetBuffer().flip();
			channelContext.getChannel().write(channelContext.getOutNetBuffer(), channelContext, writeHandler);
		} catch (Exception e) {
			this.failed(e, channelContext);
		}
	}
}