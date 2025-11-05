package com.github.martiandreamer.cp;

public class ConstantFieldRefInfo extends ConstantRefInfo {

    public ConstantFieldRefInfo(ConstantInfo[] constantPool, int classIndex, int nameAndTypeIndex) {
        super(FIELD_REF, constantPool, classIndex, nameAndTypeIndex);
    }

    @Override
    public String getName() {
        return "CONSTANT_Fieldref_info";
    }
}
