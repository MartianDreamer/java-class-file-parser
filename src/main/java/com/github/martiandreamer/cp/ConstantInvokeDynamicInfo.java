package com.github.martiandreamer.cp;

public class ConstantInvokeDynamicInfo extends ConstantDynamicInfo {

    public ConstantInvokeDynamicInfo(ConstantInfo[] constantPool, int bootstrapMethodAttributeIndex, int nameAndTypeIndex) {
        super(INVOKE_DYNAMIC, constantPool, bootstrapMethodAttributeIndex, nameAndTypeIndex);
    }

    @Override
    public String getName() {
        return "CONSTANT_InvokeDynamic_info";
    }
}
