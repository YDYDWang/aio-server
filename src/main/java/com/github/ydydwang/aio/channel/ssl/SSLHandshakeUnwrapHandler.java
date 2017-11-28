package com.github.ydydwang.aio.channel.ssl;

import javax.net.ssl.SSLEngineResult;

public class SSLHandshakeUnwrapHandler extends SSLHandshakeHandler {

	@Override
	protected void doCompleted(Integer count, SSLChannelContext channelContext) {
		try {
			channelContext.getInNetBuffer().flip();
			SSLEngineResult result = channelContext.getEngine().unwrap(channelContext.getInNetBuffer(), channelContext.getBuffer());
			System.out.println("SSLUnwrapHandler:" + result);
			channelContext.getInNetBuffer().compact();
			channelContext.setHandshakeStatus(channelContext.getEngine().getHandshakeStatus());
			doNext(count, channelContext);
		} catch (Exception e) {
			this.failed(e, channelContext);
		}
	}
}