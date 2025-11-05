package com.github.martiandreamer.cp;

import com.github.martiandreamer.InvalidClassFileFormatException;

public class ConstantStringInfo implements ConstantValueInfo<String> {
    private final ConstantPoolRef string;

    public ConstantStringInfo(ConstantInfo[] constantPool, int stringIndex) {
        this.string = new ConstantPoolRef(stringIndex, constantPool);
    }

    @Override
    public short getTag() {
        return STRING;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_String_info";
    }

    @Override
    public String getValue() {
        if (string.getContent() instanceof ConstantUtf8Info cu8i) {
            return cu8i.getContent();
        }
        throw new InvalidClassFileFormatException("Invalid constant pool entry " + string);
    }
}
