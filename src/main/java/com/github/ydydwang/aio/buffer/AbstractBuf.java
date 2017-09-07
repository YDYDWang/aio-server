package com.github.ydydwang.aio.buffer;

public abstract class AbstractBuf implements Buf {
	protected int readerIndex;
	protected int writerIndex;

	public int readableBytes() {
		return writerIndex - readerIndex;
	}
}
