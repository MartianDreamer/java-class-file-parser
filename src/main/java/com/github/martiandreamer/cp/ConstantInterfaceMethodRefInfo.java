package com.github.martiandreamer.cp;

public class ConstantInterfaceMethodRefInfo extends ConstantRefInfo {

    public ConstantInterfaceMethodRefInfo(ConstantPool constantPool, int classIndex, int nameAndTypeIndex) {
        super(INTERFACE_METHOD_REF, constantPool, classIndex, nameAndTypeIndex);
    }

    @Override
    public String getName() {
        return "CONSTANT_InterfaceMethodref_info";
    }
}
