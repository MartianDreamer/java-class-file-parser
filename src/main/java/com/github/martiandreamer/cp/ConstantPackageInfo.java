package com.github.martiandreamer.cp;

public class ConstantPackageInfo implements ConstantInfo {

    private final ConstantPoolRef name;

    protected ConstantPackageInfo(ConstantInfo[] constantPool, int nameIndex) {
        this.name = new ConstantPoolRef(nameIndex, constantPool);
    }

    public ConstantPoolRef getName() {
        return name;
    }

    @Override
    public short getTag() {
        return PACKAGE;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_Package_info";
    }
}
