package com.github.martiandreamer.cp;

public class ConstantStringInfo implements ConstantValueInfo<String> {
    private final ConstantInfo[] constantPool;
    private final int index;

    public ConstantStringInfo(ConstantInfo[] constantPool, int index) {
        this.constantPool = constantPool;
        this.index = index;
    }

    @Override
    public short getTag() {
        return STRING;
    }

    @Override
    public String getName() {
        return "CONSTANT_String_info";
    }

    @Override
    public String getValue() {
        if (constantPool[index] instanceof ConstantUtf8Info cu8i) {
            return cu8i.getContent();
        }
        return null;
    }
}
