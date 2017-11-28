package com.github.ydydwang.aio.common;

import java.nio.ByteBuffer;

import com.github.ydydwang.aio.util.ByteBufferUtils;

public class Buffers {
	public static final ByteBuffer EMPTY_BUFFER = ByteBufferUtils.toByteBuffer(new byte[Numbers.INT_ZERO]);
}
