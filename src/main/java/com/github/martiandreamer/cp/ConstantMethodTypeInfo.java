package com.github.martiandreamer.cp;

public class ConstantMethodTypeInfo extends ConstantReferenceInfo {
    protected final int descriptorIndex;

    public ConstantMethodTypeInfo(ConstantInfo[] constantPool, int descriptorIndex) {
        super(METHOD_TYPE, constantPool);
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    public String getName() {
        return "CONSTANT_MethodType_info";
    }
}
