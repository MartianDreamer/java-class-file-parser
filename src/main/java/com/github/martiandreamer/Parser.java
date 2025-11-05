package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantClassInfo;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolParser;
import com.github.martiandreamer.cp.ConstantRef;

import java.util.Arrays;
import java.util.List;

import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Utils.parseInt;

public class Parser {
    private static final byte[] MAGIC_NUMBER = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};

    private final String className;
    private final byte[] content;
    private int current;
    private ClassInfo result;

    public Parser(String className, byte[] content) throws InvalidClassFileFormatException {
        this.className = className;
        this.content = content;
        this.current = 4;
        if (!isClassFile()) {
            throw new InvalidClassFileFormatException("Not starting with magic numbers");
        }
    }

    public ClassInfo parse() throws InvalidClassFileFormatException {
        if (this.result != null) {
            return this.result;
        }
        int minor = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int major = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolParser constantPoolParser = new ConstantPoolParser(content, current);
        ConstantInfo[] constantPool = constantPoolParser.parse();
        current = constantPoolParser.getCurrent();
        int accessFlagsValue = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        List<AccessFlag> accessFlags = AccessFlag.getFlags(accessFlagsValue);
        int thisClassId = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int superClassId = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        this.result = new ClassInfo(className, major, minor, constantPool, accessFlags, new ConstantRef<>(thisClassId, constantPool, ConstantClassInfo.class), new ConstantRef<>(superClassId, constantPool, ConstantClassInfo.class));
        return result;
    }

    private boolean isClassFile() {
        return Arrays.compare(content, 0, 4, MAGIC_NUMBER, 0, 4) == 0;
    }

}
