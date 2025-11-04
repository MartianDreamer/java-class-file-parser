package com.github.martiandreamer.cp;

public class ConstantDoubleInfo extends ConstantInfo {
    protected final double value;

    public ConstantDoubleInfo(ConstantInfo[] constantPool, double value) {
        super(DOUBLE, constantPool);
        this.value = value;
    }

    @Override
    public String getName() {
        return "CONSTANT_Double_info";
    }

    public double getValue() {
        return value;
    }
}
