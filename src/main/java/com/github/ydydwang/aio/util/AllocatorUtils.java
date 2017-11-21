package com.github.ydydwang.aio.util;

import com.github.ydydwang.aio.buffer.Allocator;
import com.github.ydydwang.aio.buffer.Buf;
import com.github.ydydwang.aio.buffer.BufAllocator;
import com.github.ydydwang.aio.buffer.DirectBufAllocator;

public class AllocatorUtils {
	private static BufAllocator INSTANCE;

	static {
		String allcator = System.getProperty("-Dpers.yd.buffer.allocator", Allocator.DIRECT_BUFFER.toString());
		if (allcator.equals(Allocator.DIRECT_BUFFER.toString())) {
			AllocatorUtils.INSTANCE = new DirectBufAllocator();
		}
	}

	public static Buf allocate(int capacity) {
		return INSTANCE.allocate(capacity);
	}
}
