package com.github.martiandreamer.cp;

public class ConstantMethodRefInfo extends ConstantRefInfo {
    public ConstantMethodRefInfo(ConstantInfo[] constantPool, int classIndex, int nameAndTypeIndex) {
        super(constantPool, classIndex, nameAndTypeIndex);
    }

    @Override
    public short getTag() {
        return METHOD_REF;
    }

    @Override
    public String getName() {
        return "CONSTANT_Methodref_info";
    }
}
