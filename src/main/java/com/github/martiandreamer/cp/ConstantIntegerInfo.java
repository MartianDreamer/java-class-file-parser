package com.github.martiandreamer.cp;

public class ConstantIntegerInfo extends ConstantInfo {
    protected final int value;

    public ConstantIntegerInfo(int value) {
        super(INTEGER);
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
