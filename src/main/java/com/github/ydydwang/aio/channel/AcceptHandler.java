package com.github.ydydwang.aio.channel;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, ChannelInboundHandler> {
	public static final AcceptHandler INSTANCE = new AcceptHandler();

	public void completed(AsynchronousSocketChannel channel, ChannelInboundHandler handler) {
		ChannelContext.getServerSocketChannel().accept(ChannelContext.getHandler(), AcceptHandler.INSTANCE);
		ChannelContext channelContext = new ChannelContext(channel);
		try {
			ChannelContext.getHandler().channelActive(channelContext);
			channelContext.getChannel().read(channelContext.newBuffer(), channelContext, ReadHandler.INSTANCE);
		} catch (Exception e) {
			try {
				handler.exceptionCaught(channelContext, e.getCause());
			} catch (Exception e1) {
			}
		}
	}

	public void failed(Throwable cause, ChannelInboundHandler handler) {
		try {
			handler.channelUnregistered(cause);
		} catch (Exception e) {
		}
	}

}