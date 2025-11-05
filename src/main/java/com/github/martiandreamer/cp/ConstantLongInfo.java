package com.github.martiandreamer.cp;

public class ConstantLongInfo implements ConstantValueInfo<Long> {
    protected final long value;

    public ConstantLongInfo(long value) {
        this.value = value;
    }

    @Override
    public short getTag() {
        return LONG;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_Long_info";
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public int size() {
        return 2;
    }
}
