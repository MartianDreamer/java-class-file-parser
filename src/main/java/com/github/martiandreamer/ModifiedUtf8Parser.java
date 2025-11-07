package com.github.martiandreamer;

import java.nio.charset.StandardCharsets;

import static com.github.martiandreamer.Utils.parseInt;

public class ModifiedUtf8Parser extends Parser<String> {

    private static final byte PAIR_FIRST_BYTE_MASK = 0b0001_1111;
    private static final byte LATER_BYTE_MASK = 0b0011_1111;
    private static final byte TRIPLET_FIRST_BYTE_MASK = 0b0000_1111;
    private static final byte IS_SURROGATE_PAIR_FIRST_BYTE_CHECK_RESULT = -19; // 0b1110_1101
    private static final byte IS_SURROGATE_PAIR_SECOND_BYTE_CHECK_MASK = -16; // 0b1111_0000
    private static final byte IS_SURROGATE_PAIR_SECOND_BYTE_CHECK_RESULT = -96; // 0b1010_0000
    private static final byte IS_SURROGATE_PAIR_THIRD_BYTE_CHECK_MASK = -64; // 0b1100_0000
    private static final byte IS_SURROGATE_PAIR_THIRD_BYTE_CHECK_RESULT = -128; // 0b1000_0000
    private static final byte IS_ONE_BYTE_CHECK_MASK = -128; //0b1000_0000
    private static final byte IS_ONE_BYTE_CHECK_RESULT = 0;
    private static final byte IS_PAIR_CHECK_MASK = -32; //0b1110_0000
    private static final byte IS_PAIR_CHECK_RESULT = -64; //0b1100_0000
    private static final byte IS_TRIPLET_CHECK_MASK = -16; //0b1111_0000
    private static final byte IS_TRIPLET_CHECK_RESULT = -32; //0b1110_0000
    // UTF8 2 byte chars
    private static final int UTF_8_TWO_BYTE_FIRST_BYTE_MASK = 0b111_1100_0000;
    private static final int UTF_8_TWO_BYTE_FIRST_BYTE_HOLDER = 0b110_000_00;
    private static final int UTF_8_TWO_BYTE_SECOND_BYTE_MASK = 0b11_1111;
    private static final int UTF_8_TWO_BYTE_SECOND_BYTE_HOLDER = 0b10_00_0000;
    // UTF8 3 byte chars
    private static final int UTF_8_THREE_BYTE_FIRST_BYTE_MASK = 0b1111_0000_0000_0000;
    private static final int UTF_8_THREE_BYTE_FIRST_BYTE_HOLDER = 0b1110_0000;
    private static final int UTF_8_THREE_BYTE_SECOND_BYTE_MASK = 0b1111_1100_0000;
    private static final int UTF_8_THREE_BYTE_SECOND_BYTE_HOLDER = 0b10_0000_00;
    private static final int UTF_8_THREE_BYTE_THIRD_BYTE_MASK = 0b11_1111;
    private static final int UTF_8_THREE_BYTE_THIRD_BYTE_HOLDER = 0b10_00_0000;
    // UTF8 4 byte chars
    private static final int UTF_8_FOUR_BYTE_FIRST_BYTE_MASK = 0b1_1100_0000_0000_0000_0000;
    private static final int UTF_8_FOUR_BYTE_FIRST_BYTE_HOLDER = 0b11110_0_00;
    private static final int UTF_8_FOUR_BYTE_SECOND_BYTE_MASK = 0b11_1111_0000_0000_0000;
    private static final int UTF_8_FOUR_BYTE_SECOND_BYTE_HOLDER = 0b10_00_0000;
    private static final int UTF_8_FOUR_BYTE_THIRD_BYTE_MASK = 0b1111_1100_0000;
    private static final int UTF_8_FOUR_BYTE_THIRD_BYTE_HOLDER = 0b10_0000_00;
    private static final int UTF_8_FOUR_BYTE_FORTH_BYTE_MASK = 0b11_1111;
    private static final int UTF_8_FOUR_BYTE_FORTH_BYTE_HOLDER = 0b10_00_0000;


    private final int lengthSize;

    public ModifiedUtf8Parser(byte[] content, int from, int lengthSize) {
        super(content, from);
        this.lengthSize = lengthSize;
    }

    @Override
    public String parse() {
        int length = parseInt(content, current, lengthSize);
        current += lengthSize;
        int to = current + length;
        byte[] bytes = new byte[length];
        int pos = 0;
        while (current < to) {
            int codePoint = modifiedUtf8ToCodePoint();
            pos += codePointToStandardUtf8(codePoint, bytes, pos);
        }
        return new String(bytes, 0, pos, StandardCharsets.UTF_8);
    }

    private boolean isPair() {
        return (content[current] & IS_PAIR_CHECK_MASK) == IS_PAIR_CHECK_RESULT;
    }

    private boolean isTriplet() {
        return (content[current] & IS_TRIPLET_CHECK_MASK) == IS_TRIPLET_CHECK_RESULT;
    }

    private boolean isOneByte() {
        return (content[current] & IS_ONE_BYTE_CHECK_MASK) == IS_ONE_BYTE_CHECK_RESULT;
    }

    private boolean isSurrogatePair() {
        return content[current] == IS_SURROGATE_PAIR_FIRST_BYTE_CHECK_RESULT
                && (content[current + 1] & IS_SURROGATE_PAIR_SECOND_BYTE_CHECK_MASK) == IS_SURROGATE_PAIR_SECOND_BYTE_CHECK_RESULT
                && (content[current + 2] & IS_SURROGATE_PAIR_THIRD_BYTE_CHECK_MASK) == IS_SURROGATE_PAIR_THIRD_BYTE_CHECK_RESULT;
    }

    private int codePointToStandardUtf8(int codePoint, byte[] bytes, int pos) {
        if (codePoint < 0b1_000_0000) {
            bytes[pos] = (byte) codePoint;
            return 1;
        } else if (codePoint < 0b1_000_0000_0000) {
            bytes[pos++] = (byte) ((codePoint & UTF_8_TWO_BYTE_FIRST_BYTE_MASK) >> 6 | UTF_8_TWO_BYTE_FIRST_BYTE_HOLDER);
            bytes[pos] = (byte) (codePoint & UTF_8_TWO_BYTE_SECOND_BYTE_MASK | UTF_8_TWO_BYTE_SECOND_BYTE_HOLDER);
            return 2;
        } else if (codePoint < 0b1_0000_0000_0000_0000) {
            bytes[pos++] = (byte) ((codePoint & UTF_8_THREE_BYTE_FIRST_BYTE_MASK) >> 12 | UTF_8_THREE_BYTE_FIRST_BYTE_HOLDER);
            bytes[pos++] = (byte) ((codePoint & UTF_8_THREE_BYTE_SECOND_BYTE_MASK) >> 6 | UTF_8_THREE_BYTE_SECOND_BYTE_HOLDER);
            bytes[pos] = (byte) (codePoint & UTF_8_THREE_BYTE_THIRD_BYTE_MASK | UTF_8_THREE_BYTE_THIRD_BYTE_HOLDER);
            return 3;
        } else if (codePoint < 0b1_0_0000_0000_0000_0000_0000) {
            bytes[pos++] = (byte) ((codePoint & UTF_8_FOUR_BYTE_FIRST_BYTE_MASK) >> 18 | UTF_8_FOUR_BYTE_FIRST_BYTE_HOLDER);
            bytes[pos++] = (byte) ((codePoint & UTF_8_FOUR_BYTE_SECOND_BYTE_MASK) >> 12 | UTF_8_FOUR_BYTE_SECOND_BYTE_HOLDER);
            bytes[pos++] = (byte) ((codePoint & UTF_8_FOUR_BYTE_THIRD_BYTE_MASK) >> 6 | UTF_8_FOUR_BYTE_THIRD_BYTE_HOLDER);
            bytes[pos] = (byte) (codePoint & UTF_8_FOUR_BYTE_FORTH_BYTE_MASK | UTF_8_FOUR_BYTE_FORTH_BYTE_HOLDER);
            return 4;
        }
        throw new IllegalArgumentException("Invalid code point " + codePoint);
    }

    private int modifiedUtf8ToCodePoint() {
        if (isOneByte()) {
            return content[current++];
        } else if (isPair()) {
            byte firstByte = content[current++];
            byte secondByte = content[current++];
            return ((firstByte & PAIR_FIRST_BYTE_MASK) << 6) | (secondByte & LATER_BYTE_MASK);
        } else if (isSurrogatePair()) {
            byte firstByte = content[current++];
            byte secondByte = content[current++];
            byte thirdByte = content[current++];
            int highByte = ((firstByte & TRIPLET_FIRST_BYTE_MASK) << 12) | ((secondByte & LATER_BYTE_MASK) << 6) | (thirdByte & LATER_BYTE_MASK);
            firstByte = content[current++];
            secondByte = content[current++];
            thirdByte = content[current++];
            int lowByte = ((firstByte & TRIPLET_FIRST_BYTE_MASK) << 12) | ((secondByte & LATER_BYTE_MASK) << 6) | (thirdByte & LATER_BYTE_MASK);
            return 0x10000 + (((highByte - 0xD800) << 10) + (lowByte - 0xDC00));
        } else if (isTriplet()) {
            byte firstByte = content[current++];
            byte secondByte = content[current++];
            byte thirdByte = content[current++];
            return ((firstByte & TRIPLET_FIRST_BYTE_MASK) << 12) | ((secondByte & LATER_BYTE_MASK) << 6) | (thirdByte & LATER_BYTE_MASK);
        }
        throw new InvalidClassFileFormatException("Invalid UTF8 code point " + content[current]);
    }
}
