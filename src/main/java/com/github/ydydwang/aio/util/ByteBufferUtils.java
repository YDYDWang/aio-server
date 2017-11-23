package com.github.ydydwang.aio.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ydydwang.aio.common.Numbers;

public class ByteBufferUtils {
	private static final Logger logger = LoggerFactory.getLogger(ByteBufferUtils.class);
	private static final String STRING_ATT = "att";
	private static final String STRING_CLEANER = "cleaner";
	private static final String STRING_CLEAN = "clean";
	private static final String STRING_DIRECT_BUFFER_CLASS_PATH = "java.nio.DirectByteBuffer";

	public static ByteBuffer toByteBuffer(byte[] bytes) {
		ByteBuffer byteBuffer = null;
		try {
			byteBuffer = ByteBuffer.allocateDirect(bytes.length);
			byteBuffer.put(bytes, Numbers.INT_ZERO, bytes.length);
		} catch (Exception e) {
			if (byteBuffer != null) {
				releaseQuietly(byteBuffer);
			}
			logger.error(e.getMessage(), e.getCause());
		}
		return byteBuffer;
	}

	public static ByteBuffer toByteBuffer(String string) {
		return ByteBufferUtils.toByteBuffer(string.getBytes());
	}

	public static void releaseQuietly(Buffer buffer) {
		if (buffer.isDirect()) {
			try {
				if (!buffer.getClass().getName().equals(STRING_DIRECT_BUFFER_CLASS_PATH)) {
					Field field = buffer.getClass().getDeclaredField(STRING_ATT);
					field.setAccessible(Boolean.TRUE);
					buffer = (Buffer) field.get(buffer);
				}
				Method cleanerMethod = buffer.getClass().getMethod(STRING_CLEANER);
				cleanerMethod.setAccessible(Boolean.TRUE);
				Object cleaner = cleanerMethod.invoke(buffer);
				Method cleanMethod = cleaner.getClass().getMethod(STRING_CLEAN);
				cleanMethod.setAccessible(Boolean.TRUE);
				cleanMethod.invoke(cleaner);
			} catch(Exception e) {
			}
		}
	}

	public static String toString(ByteBuffer buffer) {
		buffer.flip();
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		return new String(bytes);
	}
}
