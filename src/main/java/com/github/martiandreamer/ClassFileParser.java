package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantClassInfo;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolParser;
import com.github.martiandreamer.cp.ConstantRef;

import java.util.Arrays;

import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Utils.parseInt;

public class ClassFileParser extends Parser<ClassInfo> {
    private static final byte[] MAGIC_NUMBER = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};

    private final String className;
    private ClassInfo result;

    public ClassFileParser(String className, byte[] content) throws InvalidClassFileFormatException {
        super(content, MAGIC_NUMBER.length);
        this.className = className;
        if (!isClassFile()) {
            throw new InvalidClassFileFormatException("Not starting with magic numbers");
        }
    }

    @Override
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
        AccessFlag[] accessFlags = AccessFlag.getFlags(accessFlagsValue);
        int thisClassId = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int superClassId = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantRef<ConstantClassInfo>[] interfaces = parseInterfaces(constantPool);
        Parser<Field[]> fieldParser = new FieldParser(content, current, constantPool);
        Field[] fields = fieldParser.parse();
        current = fieldParser.getCurrent();
        this.result = new ClassInfo(className, major, minor, constantPool, accessFlags, new ConstantRef<>(thisClassId, constantPool, ConstantClassInfo.class), new ConstantRef<>(superClassId, constantPool, ConstantClassInfo.class), interfaces, fields);
        return result;
    }

    @SuppressWarnings("unchecked")
    private ConstantRef<ConstantClassInfo>[] parseInterfaces(ConstantInfo[] constantPool) {
        int interfaceCount = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantRef<ConstantClassInfo>[] result = new ConstantRef[interfaceCount];
        for (int i = 0; i < interfaceCount; i++) {
            int index = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            result[i] = new ConstantRef<>(index, constantPool,  ConstantClassInfo.class);
        }
        return result;
    }

    private boolean isClassFile() {
        return Arrays.compare(content, 0, 4, MAGIC_NUMBER, 0, 4) == 0;
    }

}
