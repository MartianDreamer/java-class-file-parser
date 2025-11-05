package com.github.martiandreamer.cp;

public class ConstantFieldRefInfo extends ConstantRefInfo {

    public ConstantFieldRefInfo(ConstantInfo[] constantPool, int classIndex, int nameAndTypeIndex) {
        super(constantPool, classIndex, nameAndTypeIndex);
    }

    @Override
    public short getTag() {
        return FIELD_REF;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_Fieldref_info";
    }
}
