package com.github.ydydwang.aio.channel.ssl;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.ydydwang.aio.channel.ChannelContext;
import com.github.ydydwang.aio.common.Numbers;
import com.github.ydydwang.aio.util.ByteBufferUtils;
import com.github.ydydwang.aio.util.SSLHandshakeUtils;
import com.github.ydydwang.aio.util.TriggerUtils;

public class SSLHandshakeHandler implements CompletionHandler<Integer, ChannelContext> {
	private static final SSLHandshakeUnwrapHandler unwrapHandler = new SSLHandshakeUnwrapHandler();
	private static final SSLHandshakeWrapHandler wrapHandler = new SSLHandshakeWrapHandler();
	private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

	@Override
	public final void completed(Integer count, ChannelContext channelContext) {
		if (count != Numbers.INT_MINUS_ONE) {
			SSLChannelContext sslChannelContext = (SSLChannelContext) channelContext;
			doCompleted(sslChannelContext);
		} else {
			this.failed(SSLHandshakeUtils.newSocketClosedWhileHandshake(), channelContext);
		}
	}

	@Override
	public void failed(Throwable cause, ChannelContext channelContext) {
		SSLChannelContext sslChannelContext = (SSLChannelContext) channelContext;
		sslChannelContext.release();
		TriggerUtils.acceptFailed(sslChannelContext.getHandlerList(), cause);
	}

	protected void doCompleted(SSLChannelContext channelContext) {
		switch (channelContext.getHandshakeStatus()) {
			case NEED_UNWRAP:
				SSLHandshakeHandler.unwrapHandler.doCompleted(channelContext);
				break;
			case NEED_WRAP:
				SSLHandshakeHandler.wrapHandler.doCompleted(channelContext);
				break;
			default:
				this.failed(SSLHandshakeUtils.newWrongHearbeatStatus(channelContext.getHandshakeStatus()), channelContext);
				break;
		}
	}

	@SuppressWarnings("incomplete-switch")
	protected final void doNext(SSLChannelContext channelContext) {
		switch (channelContext.getHandshakeStatus()) {
			case NEED_UNWRAP:
				channelContext.getChannel().read(channelContext.getInNetBuffer(), channelContext, unwrapHandler);
				break;
			case NEED_WRAP:
				SSLHandshakeHandler.wrapHandler.doCompleted(channelContext);
				break;
			case FINISHED:
				channelContext.getChannel().read(channelContext.getInNetBuffer(), channelContext, SSLReadHandler.INSTANSE);
				break;
			case NEED_TASK:
				doTask(channelContext);
				break;
		}
	}

	private final void doTask(SSLChannelContext channelContext) {
		Runnable delegatedTask = channelContext.getEngine().getDelegatedTask();
		if (delegatedTask != null) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					delegatedTask.run();;
					channelContext.setHandshakeStatus(channelContext.getEngine().getHandshakeStatus());
					doNext(channelContext);
				}
			};
			executorService.execute(runnable);
		} else {
			this.failed(SSLHandshakeUtils.newSocketClosedWhileHandshake(), channelContext);
		}
	}
}