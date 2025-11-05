package com.github.martiandreamer.cp;

public class ConstantMethodTypeInfo implements ConstantInfo {
    protected final ConstantPoolRef descriptor;

    public ConstantMethodTypeInfo(ConstantInfo[] constantPool, int descriptorIndex) {
        this.descriptor = new ConstantPoolRef(descriptorIndex, constantPool);
    }

    @Override
    public short getTag() {
        return METHOD_TYPE;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_MethodType_info";
    }
}
