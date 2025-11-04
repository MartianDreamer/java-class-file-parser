package com.github.martiandreamer.cp;

public class ConstantMethodRefInfo extends ConstantRefInfo {
    public ConstantMethodRefInfo(ConstantInfo[] constantPool, int classIndex, int nameAndTypeIndex) {
        super(METHOD_REF, constantPool, classIndex, nameAndTypeIndex);
    }

    @Override
    public String getName() {
        return "CONSTANT_Methodref_info";
    }
}
