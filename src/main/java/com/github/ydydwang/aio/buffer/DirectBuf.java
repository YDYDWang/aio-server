package com.github.ydydwang.aio.buffer;

import java.nio.ByteBuffer;

import com.github.ydydwang.aio.util.ByteBufferUtils;

public class DirectBuf implements Buf {
	private ByteBuffer buffer;

	public DirectBuf(int capacity) {
		this.buffer = ByteBuffer.allocateDirect(capacity);
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

	public int readableBytes() {
		return buffer.remaining();
	}

	public byte getByte(int index) {
		return buffer.get(index);
	}

	public int read() {
		return buffer.get();
	}

	public void writeByte(byte b) {
		buffer.put(b);
	}

	public void realease() {
		ByteBufferUtils.releaseQuietly(buffer);
	}

}
