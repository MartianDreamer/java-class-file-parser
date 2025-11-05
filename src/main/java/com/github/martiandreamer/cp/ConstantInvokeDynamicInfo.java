package com.github.martiandreamer.cp;

public class ConstantInvokeDynamicInfo extends ConstantDynamicInfo {

    public ConstantInvokeDynamicInfo(ConstantInfo[] constantPool, int bootstrapMethodAttributeIndex, int nameAndTypeIndex) {
        super(constantPool, bootstrapMethodAttributeIndex, nameAndTypeIndex);
    }

    @Override
    public String getName() {
        return "CONSTANT_InvokeDynamic_info";
    }

    @Override
    public short getTag() {
        return INVOKE_DYNAMIC;
    }
}
