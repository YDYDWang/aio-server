package com.github.ydydwang.aio.buffer;

public class OffHeapBufAllocator implements BufAllocator {

	public Buf allocate(int capacity) {
		return new OffHeapBuf(capacity);
	}
}
