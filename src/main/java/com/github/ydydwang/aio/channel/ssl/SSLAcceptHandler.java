package com.github.ydydwang.aio.channel.ssl;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import com.github.ydydwang.aio.channel.MainChannelContext;
import com.github.ydydwang.aio.util.TriggerUtils;

public class SSLAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, MainChannelContext> {
	private final SSLEngine engine;

	public SSLAcceptHandler(SSLContext context) {
		this.engine = context.createSSLEngine();
	}

	@Override
	public void completed(AsynchronousSocketChannel channel, MainChannelContext context) {
		context.getChannel().accept(context, context.getAcceptHandler());
		SSLChannelContext channelContext = new SSLChannelContext(channel, context, engine);
		if (channelContext.getChannel().isOpen()) {
			try {
				channelContext.getChannel().read(channelContext.newBuffer(), channelContext, channelContext.getHandshakeHandler());
			} catch (Exception e) {
				this.failed(e.getCause(), context);
			}
		} else {
			TriggerUtils.channelInactive(channelContext.getHandlerList(), channelContext);
		}
	}

	@Override
	public void failed(Throwable cause, MainChannelContext context) {
		TriggerUtils.acceptFailed(context.getHandlerList(), cause);
	}

}