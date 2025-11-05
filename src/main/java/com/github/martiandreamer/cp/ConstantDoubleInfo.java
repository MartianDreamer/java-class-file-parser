package com.github.martiandreamer.cp;

public class ConstantDoubleInfo implements ConstantValueInfo<Double> {
    protected final double value;

    public ConstantDoubleInfo(double value) {
        this.value = value;
    }

    @Override
    public short getTag() {
        return DOUBLE;
    }

    @Override
    public String getName() {
        return "CONSTANT_Double_info";
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public int size() {
        return 2;
    }
}
