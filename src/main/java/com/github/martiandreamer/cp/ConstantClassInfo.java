package com.github.martiandreamer.cp;

public class ConstantClassInfo implements ConstantInfo {

    private final ConstantPoolRef name;

    public ConstantClassInfo(ConstantInfo[] constantPool, int index) {
        this.name = new ConstantPoolRef(index, constantPool);
    }

    @Override
    public short getTag() {
        return CLASS;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_Class_info";
    }

    public ConstantPoolRef getName() {
        return name;
    }
}
