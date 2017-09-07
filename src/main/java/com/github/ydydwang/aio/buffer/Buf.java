package com.github.ydydwang.aio.buffer;

import java.nio.ByteBuffer;

public interface Buf {

	public ByteBuffer getBuffer();

	public int readableBytes();

	public byte getByte(int index);

	public int read();

	public void writeByte(byte b);

	public void realease();
}
