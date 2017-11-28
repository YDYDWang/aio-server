package com.github.ydydwang.aio.channel.ssl;

import java.nio.channels.CompletionHandler;

import com.github.ydydwang.aio.common.Numbers;
import com.github.ydydwang.aio.util.ByteBufferUtils;
import com.github.ydydwang.aio.util.SSLHandshakeUtils;
import com.github.ydydwang.aio.util.TriggerUtils;

public class SSLHandshakeHandler implements CompletionHandler<Integer, SSLChannelContext> {
	private static final SSLHandshakeUnwrapHandler unwrapHandler = new SSLHandshakeUnwrapHandler();
	private static final SSLHandshakeWrapHandler wrapHandler = new SSLHandshakeWrapHandler();

	@Override
	public final void completed(Integer count, SSLChannelContext channelContext) {
		if (count != Numbers.INT_MINUS_ONE) {
			doCompleted(count, channelContext);
		} else {
			this.failed(SSLHandshakeUtils.newSocketClosedWhileHandshake(), channelContext);
		}
	}

	@Override
	public void failed(Throwable cause, SSLChannelContext channelContext) {
		channelContext.release();
		TriggerUtils.acceptFailed(channelContext.getHandlerList(), cause);
	}

	protected void doCompleted(Integer count, SSLChannelContext channelContext) {
		switch (channelContext.getHandshakeStatus()) {
			case NEED_UNWRAP:
				SSLHandshakeHandler.unwrapHandler.doCompleted(count, channelContext);
				break;
			case NEED_WRAP:
				SSLHandshakeHandler.wrapHandler.doCompleted(count, channelContext);
				break;
			default:
				this.failed(SSLHandshakeUtils.newWrongHearbeatStatus(channelContext.getHandshakeStatus()), channelContext);
				break;
		}
	}

	protected final void doNext(Integer count, SSLChannelContext channelContext) {
		switch (channelContext.getHandshakeStatus()) {
			case NEED_UNWRAP:
				channelContext.getChannel().read(channelContext.getInNetBuffer(), channelContext, unwrapHandler);
				break;
			case NEED_WRAP:
				System.out.println(ByteBufferUtils.toString(channelContext.getBuffer()));
				channelContext.getBuffer().clear();
				SSLHandshakeHandler.wrapHandler.doCompleted(count, channelContext);
				break;
			case FINISHED:
				channelContext.getChannel().read(channelContext.getInNetBuffer(), channelContext, channelContext.getReadHandler());
				break;
			default:
				break;
		}
	}

}