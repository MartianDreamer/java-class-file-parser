package com.github.martiandreamer.cp;

public class ConstantDynamicInfo extends ConstantReferenceInfo {
    protected final int bootstrapMethodAttributeIndex;
    protected final int nameAndTypeIndex;

    public ConstantDynamicInfo(ConstantInfo[] constantPool, int bootstrapMethodAttributeIndex, int nameAndTypeIndex) {
        super(constantPool);
        this.bootstrapMethodAttributeIndex = bootstrapMethodAttributeIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public short getTag() {
        return DYNAMIC;
    }

    @Override
    public String getName() {
        return "CONSTANT_Dynamic_info";
    }

    public int getBootstrapMethodAttributeIndex() {
        return bootstrapMethodAttributeIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
