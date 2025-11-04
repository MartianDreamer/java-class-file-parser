package com.github.martiandreamer.cp;

public class ConstantIntegerInfo extends ConstantInfo {
    protected final int value;

    public ConstantIntegerInfo(ConstantPool constantPool, int value) {
        super(INTEGER, constantPool);
        this.value = value;
    }

    @Override
    public String getName() {
        return "CONSTANT_Integer_info";
    }

    public int getValue() {
        return value;
    }
}
