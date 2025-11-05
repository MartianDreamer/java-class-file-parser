package com.github.martiandreamer.cp;

public class ConstantFloatInfo implements ConstantValueInfo<Float> {
    protected final float value;

    public ConstantFloatInfo(float value) {
        this.value = value;
    }

    @Override
    public short getTag() {
        return FLOAT;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_Float_info";
    }

    @Override
    public Float getValue() {
        return value;
    }
}
