package com.github.martiandreamer.cp;

public class ConstantFloatInfo extends ConstantInfo {
    protected final float value;

    public ConstantFloatInfo(float value) {
        super(FLOAT);
        this.value = value;
    }

    @Override
    public String getName() {
        return "CONSTANT_Float_info";
    }

    public float getValue() {
        return value;
    }
}
