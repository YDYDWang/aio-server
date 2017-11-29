package com.github.ydydwang.aio.channel.ssl;

import javax.net.ssl.SSLEngineResult;

public class SSLHandshakeUnwrapHandler extends SSLHandshakeHandler {

	@Override
	protected void doCompleted(SSLChannelContext channelContext) {
		try {
			channelContext.getInNetBuffer().flip();
			SSLEngineResult result = channelContext.getEngine().unwrap(channelContext.getInNetBuffer(), channelContext.getBuffer());
			System.out.println("SSLUnwrapHandler:" + result);
			channelContext.getInNetBuffer().compact();
			channelContext.setHandshakeStatus(result);
			doNext(channelContext);
		} catch (Exception e) {
			this.failed(e, channelContext);
		}
	}
}