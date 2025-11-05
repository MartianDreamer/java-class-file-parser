package com.github.martiandreamer.cp;

public class ConstantInterfaceMethodRefInfo extends ConstantRefInfo {

    public ConstantInterfaceMethodRefInfo(ConstantInfo[] constantPool, int classIndex, int nameAndTypeIndex) {
        super(constantPool, classIndex, nameAndTypeIndex);
    }

    @Override
    public short getTag() {
        return INTERFACE_METHOD_REF;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_InterfaceMethodref_info";
    }

}
