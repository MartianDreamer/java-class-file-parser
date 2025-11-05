package com.github.martiandreamer.cp;

public class ConstantClassInfo extends ConstantUtf8ReferenceInfo {

    public ConstantClassInfo(ConstantInfo[] constantPool, int index) {
        super(constantPool, index);
    }

    @Override
    public short getTag() {
        return CLASS;
    }

    @Override
    public String getName() {
        return "CONSTANT_Class_info";
    }
}
