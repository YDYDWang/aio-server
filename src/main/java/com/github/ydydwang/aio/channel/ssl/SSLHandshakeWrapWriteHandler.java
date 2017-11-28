package com.github.ydydwang.aio.channel.ssl;

public class SSLHandshakeWrapWriteHandler extends SSLHandshakeHandler {

	@Override
	protected void doCompleted(Integer count, SSLChannelContext channelContext) {
		channelContext.getOutNetBuffer().clear();
		doNext(count, channelContext);
	}

}