package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolParser;
import com.github.martiandreamer.cp.ConstantPoolRef;

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

        // This class and super class
        int thisClassId = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int superClassId = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef thisClass = new ConstantPoolRef(thisClassId, constantPool);
        if (thisClass.getTag() != ConstantInfo.CLASS) {
            throw new InvalidClassFileFormatException("Invalid this class index " + thisClass.getContent());
        }
        ConstantPoolRef superClass = new ConstantPoolRef(superClassId, constantPool);
        if (superClass.getTag() != ConstantInfo.CLASS) {
            throw new InvalidClassFileFormatException("Invalid super class index " + superClass.getContent());
        }

        // parse interfaces
        ConstantPoolRef[] interfaces = parseInterfaces(constantPool);

        // Parse fields
        Parser<FieldAndMethod[]> fieldParser = new FieldAndMethodParser(content, current, constantPool);
        FieldAndMethod[] fields = fieldParser.parse();
        current = fieldParser.getCurrent();

        // Parse methods
        Parser<FieldAndMethod[]> methodParser = new FieldAndMethodParser(content, current, constantPool);
        FieldAndMethod[] methods = methodParser.parse();
        current = methodParser.getCurrent();

        this.result = new ClassInfo(className, major, minor, constantPool, accessFlags, thisClass, superClass, interfaces, fields, methods);
        return result;
    }

    private ConstantPoolRef[] parseInterfaces(ConstantInfo[] constantPool) throws InvalidClassFileFormatException {
        int interfaceCount = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef[] result = new ConstantPoolRef[interfaceCount];
        for (int i = 0; i < interfaceCount; i++) {
            int index = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            ConstantPoolRef constantPoolRef = new ConstantPoolRef(index, constantPool);
            if (ConstantInfo.CLASS != constantPoolRef.getTag()) {
                throw new InvalidClassFileFormatException("Invalid interface index " + constantPoolRef.getContent());
            }
        }
        return result;
    }

    private boolean isClassFile() {
        return Arrays.compare(content, 0, 4, MAGIC_NUMBER, 0, 4) == 0;
    }

}
