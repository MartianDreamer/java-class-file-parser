package com.github.martiandreamer.cp;

public class ConstantPackageInfo extends ConstantUtf8ReferenceInfo {
    protected ConstantPackageInfo(ConstantInfo[] constantPool, int index) {
        super(PACKAGE, constantPool, index);
    }

    @Override
    public String getName() {
        return "CONSTANT_Package_info";
    }
}
