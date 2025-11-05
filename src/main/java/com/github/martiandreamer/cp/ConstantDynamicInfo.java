package com.github.martiandreamer.cp;

public class ConstantDynamicInfo implements ConstantInfo {
    protected final int bootstrapMethodAttributeIndex;
    protected final ConstantPoolRef nameAndType;

    public ConstantDynamicInfo(ConstantInfo[] constantPool, int bootstrapMethodAttributeIndex, int nameAndTypeIndex) {
        this.bootstrapMethodAttributeIndex = bootstrapMethodAttributeIndex;
        this.nameAndType = new ConstantPoolRef(nameAndTypeIndex, constantPool);
    }

    @Override
    public short getTag() {
        return DYNAMIC;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_Dynamic_info";
    }

    public int getBootstrapMethodAttributeIndex() {
        return bootstrapMethodAttributeIndex;
    }

    public ConstantPoolRef getNameAndType() {
        return nameAndType;
    }
}
