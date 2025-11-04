package com.github.martiandreamer.cp;

public class ConstantLongInfo extends ConstantInfo {
    protected final long value;

    public ConstantLongInfo(long value) {
        super(LONG);
        this.value = value;
    }

    @Override
    public String getName() {
        return "CONSTANT_Long_info";
    }

    public long getValue() {
        return value;
    }

    @Override
    public int size() {
        return 2;
    }
}
