package com.github.ydydwang.aio.buffer;

public interface BufAllocator {

	public Buf allocate(int capacity);
}
