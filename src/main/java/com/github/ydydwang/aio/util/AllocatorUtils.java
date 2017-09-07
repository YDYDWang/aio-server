package com.github.ydydwang.aio.util;

import com.github.ydydwang.aio.buffer.Allocator;
import com.github.ydydwang.aio.buffer.Buf;
import com.github.ydydwang.aio.buffer.BufAllocator;
import com.github.ydydwang.aio.buffer.DirectBufAllocator;
import com.github.ydydwang.aio.buffer.OffHeapBufAllocator;

public abstract class AllocatorUtils {
	private static BufAllocator INSTANCE;

	static {
		String allcator = System.getProperty("-Dpers.yd.buffer.allocator", Allocator.OFF_HEAP.toString());
		if (allcator.equals(Allocator.DIRECT_BUFFER.toString())) {
			AllocatorUtils.INSTANCE = new DirectBufAllocator();
		} else {
			AllocatorUtils.INSTANCE = new OffHeapBufAllocator();
		}
	}

	public static Buf allocate(int capacity) {
		return INSTANCE.allocate(capacity);
	}
}
