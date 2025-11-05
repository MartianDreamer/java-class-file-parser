package com.github.martiandreamer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class Utils {
    private Utils() {
    }

    public static int parseInt(byte[] content, int offset, int length) {
        byte[] buffer = new byte[4];
        System.arraycopy(content, offset, buffer, buffer.length - length, length);
        return ByteBuffer.wrap(buffer)
                .order(ByteOrder.BIG_ENDIAN)
                .getInt();
    }

    public static short parseShort(byte[] content, int offset, int length) {
        byte[] buffer = new byte[2];
        System.arraycopy(content, offset, buffer, buffer.length - length, length);
        return ByteBuffer.wrap(buffer)
                .order(ByteOrder.BIG_ENDIAN)
                .getShort();
    }

    public static long parseLong(byte[] content, int offset, int length) {
        byte[] buffer = new byte[8];
        System.arraycopy(content, offset, buffer, buffer.length - length, length);
        return ByteBuffer.wrap(buffer)
                .order(ByteOrder.BIG_ENDIAN)
                .getLong();
    }
}
