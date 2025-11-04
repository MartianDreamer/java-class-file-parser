package com.github.martiandreamer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static com.github.martiandreamer.Constant.WORD_SIZE;

public final class Utils {
    private Utils() {
    }

    private static final byte[] MAGIC_NUMBER = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};


    public static boolean isClassFile(byte[] content) {
        return Arrays.compare(content, 0, 4, MAGIC_NUMBER, 0, 4) == 0;
    }

    public static int parseInt(byte[] content, int offset, int length) {
        byte[] buffer = new byte[4];
        System.arraycopy(content, offset, buffer, buffer.length - length, length);
        return ByteBuffer.wrap(buffer, 0, WORD_SIZE)
                .order(ByteOrder.BIG_ENDIAN)
                .getInt();
    }
}
