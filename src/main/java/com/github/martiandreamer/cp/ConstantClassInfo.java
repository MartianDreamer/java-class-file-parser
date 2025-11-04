package com.github.martiandreamer.cp;

public class ConstantClassInfo extends ConstantUtf8ReferenceInfo {

    public ConstantClassInfo(ConstantInfo[] constantPool, int index) {
        super(CLASS, constantPool, index);
    }

    @Override
    public String getName() {
        return "CONSTANT_Class_info";
    }
}
