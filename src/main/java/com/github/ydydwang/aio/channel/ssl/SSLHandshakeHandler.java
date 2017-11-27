package com.github.ydydwang.aio.channel.ssl;

import java.nio.channels.CompletionHandler;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLException;

import com.github.ydydwang.aio.util.TriggerUtils;

public final class SSLHandshakeHandler  implements CompletionHandler<Integer, SSLChannelContext> {
	private HandshakeStatus handshakeStatus;

	@Override
	public void completed(Integer result, SSLChannelContext channelContext) {
		try {
			SSLEngine engine = channelContext.getEngine();
			engine.beginHandshake();
			this.handshakeStatus = engine.getHandshakeStatus();
		} catch (SSLException e) {
			this.failed(e.getCause(), channelContext);
		}
	}

	@Override
	public void failed(Throwable cause, SSLChannelContext channelContext) {
		TriggerUtils.acceptFailed(channelContext.getHandlerList(), cause);
	}

}