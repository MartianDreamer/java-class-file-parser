package com.github.martiandreamer.cp;

public class ConstantPackageInfo extends ConstantUtf8ReferenceInfo {
    protected ConstantPackageInfo(ConstantInfo[] constantPool, int index) {
        super(constantPool, index);
    }

    @Override
    public short getTag() {
        return PACKAGE;
    }

    @Override
    public String getName() {
        return "CONSTANT_Package_info";
    }
}
