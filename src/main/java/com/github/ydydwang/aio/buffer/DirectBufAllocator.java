package com.github.ydydwang.aio.buffer;

public class DirectBufAllocator implements BufAllocator {

	public Buf allocate(int capacity) {
		return new DirectBuf(capacity);
	}
}
