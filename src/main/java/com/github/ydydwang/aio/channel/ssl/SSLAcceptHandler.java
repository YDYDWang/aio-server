package com.github.ydydwang.aio.channel.ssl;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import javax.net.ssl.SSLContext;

import com.github.ydydwang.aio.channel.MainChannelContext;
import com.github.ydydwang.aio.util.TriggerUtils;

public class SSLAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, MainChannelContext> {
	private static final SSLHandshakeHandler handshakeHandler = new SSLHandshakeHandler();

	private final SSLContext context;

	public SSLAcceptHandler(SSLContext context) {
		this.context = context;
	}

	@Override
	public void completed(AsynchronousSocketChannel channel, MainChannelContext context) {
		context.getChannel().accept(context, context.getAcceptHandler());
		SSLChannelContext channelContext = null;
		try {
			channelContext = new SSLChannelContext(channel, context, this.context.createSSLEngine());
			if (channelContext.getChannel().isOpen()) {
				channelContext.getChannel().read(channelContext.getInNetBuffer(), channelContext, handshakeHandler);
			} else {
				TriggerUtils.channelInactive(channelContext.getHandlerList(), channelContext);
			}
		} catch (Exception e) {
			if (channelContext != null) {
				channelContext.release();
			}
			this.failed(e, context);
		}
	}

	@Override
	public void failed(Throwable cause, MainChannelContext context) {
		TriggerUtils.acceptFailed(context.getHandlerList(), cause);
	}

}