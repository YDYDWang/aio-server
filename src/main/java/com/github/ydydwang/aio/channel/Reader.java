package com.github.ydydwang.aio.channel;

public class Reader implements Runnable {
	private static final ReadHandler readhandler = new ReadHandler();

	private final ChannelContext channelContext;

	public Reader(ChannelContext channelContext) {
		this.channelContext = channelContext;
	}

	public void run() {
		channelContext.getChannel().read(channelContext.newBuffer(), channelContext, readhandler);
		System.out.println("read");
	}

}