package com.github.ydydwang.aio.util;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

@SuppressWarnings("restriction")
public class UnsafeUtils {
	private static final Unsafe UNSAFE;
	private static final long BYTE_ARRAY_OFFSET;
	private static final int BYTE_ARRAY_SCALE;
	
	static {
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			UNSAFE = (Unsafe) theUnsafe.get(null);
			BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
			BYTE_ARRAY_SCALE = UNSAFE.arrayIndexScale(byte[].class);
		} catch (IllegalAccessException e) {
			throw new ExceptionInInitializerError("Cannot access Unsafe");
		} catch (NoSuchFieldException e) {
			throw new ExceptionInInitializerError("Cannot access Unsafe");
		} catch (SecurityException e) {
			throw new ExceptionInInitializerError("Cannot access Unsafe");
		}
	}

	public static byte readByte(byte[] src, int srcOff) {
		return UNSAFE.getByte(src, BYTE_ARRAY_OFFSET + BYTE_ARRAY_SCALE * srcOff);
	}

	public static void writeByte(byte[] src, int srcOff, byte value) {
		UNSAFE.putByte(src, BYTE_ARRAY_OFFSET + BYTE_ARRAY_SCALE * srcOff, (byte) value);
	}

	public static void writeByte(byte[] src, int srcOff, int value) {
		writeByte(src, srcOff, (byte) value);
	}

}