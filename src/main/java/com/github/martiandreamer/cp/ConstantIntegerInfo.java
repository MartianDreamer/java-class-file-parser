package com.github.martiandreamer.cp;

public class ConstantIntegerInfo implements ConstantValueInfo<Integer> {
    protected final int value;

    public ConstantIntegerInfo(int value) {
        this.value = value;
    }

    @Override
    public short getTag() {
        return INTEGER;
    }

    @Override
    public String getName() {
        return "CONSTANT_Integer_info";
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
