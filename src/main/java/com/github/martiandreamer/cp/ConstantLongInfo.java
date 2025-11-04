package com.github.martiandreamer.cp;

public class ConstantLongInfo extends ConstantInfo {
    protected final long value;

    public ConstantLongInfo(ConstantPool constantPool, long value) {
        super(LONG, constantPool);
        this.value = value;
    }

    @Override
    public String getName() {
        return "CONSTANT_Long_info";
    }

    public long getValue() {
        return value;
    }
}
