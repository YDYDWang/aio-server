package com.github.ydydwang.aio;

import com.github.ydydwang.aio.channel.AioServerBuilder;

public class Main {
	private static final int DEFAULT_THREAD_COUNT = 10;
	private static final int DEFAULT_PORT = 9999;

	public static void main(String[] args) throws Exception {
		new AioServerBuilder().threadCount(DEFAULT_THREAD_COUNT)
				.port(DEFAULT_PORT)
				.addHandler(new DefaultChannelInboundHandler())
				.start();
	}
}

