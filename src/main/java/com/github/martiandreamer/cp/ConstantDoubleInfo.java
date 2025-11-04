package com.github.martiandreamer.cp;

public class ConstantDoubleInfo extends ConstantInfo {
    protected final double value;

    public ConstantDoubleInfo(double value) {
        super(DOUBLE);
        this.value = value;
    }

    @Override
    public String getName() {
        return "CONSTANT_Double_info";
    }

    public double getValue() {
        return value;
    }

    @Override
    public int size() {
        return 2;
    }
}
