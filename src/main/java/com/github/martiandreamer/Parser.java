package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantPool;
import com.github.martiandreamer.cp.ConstantPoolParser;

import java.util.Arrays;

import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Utils.parseInt;

public class Parser {
    private static final byte[] MAGIC_NUMBER = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};

    private final String className;
    private final byte[] content;

    public Parser(String className, byte[] content) throws InvalidClassFileFormatException {
        this.className = className;
        this.content = content;
        if (!isClassFile()) {
            throw new InvalidClassFileFormatException("Not starting with magic numbers");
        }
    }

    @SuppressWarnings("ReassignedVariable")
    public ClassInfo parse() throws InvalidClassFileFormatException {
        int current = 4;
        int minor = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int major = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPool constantPool = new ConstantPoolParser(content, current).parse();
        return new ClassInfo(className, major, minor, constantPool);
    }

    public boolean isClassFile() {
        return Arrays.compare(content, 0, 4, MAGIC_NUMBER, 0, 4) == 0;
    }

}
