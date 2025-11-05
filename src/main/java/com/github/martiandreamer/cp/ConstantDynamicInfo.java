package com.github.martiandreamer.cp;

public class ConstantDynamicInfo extends ConstantReferenceInfo {
    protected final int bootstrapMethodAttributeIndex;
    protected final int nameAndTypeIndex;

    protected ConstantDynamicInfo(short tag, ConstantInfo[] constantPool, int bootstrapMethodAttributeIndex, int nameAndTypeIndex) {
        super(tag, constantPool);
        this.bootstrapMethodAttributeIndex = bootstrapMethodAttributeIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public ConstantDynamicInfo(ConstantInfo[] constantPool, int bootstrapMethodAttributeIndex, int nameAndTypeIndex) {
        super(DYNAMIC, constantPool);
        this.bootstrapMethodAttributeIndex = bootstrapMethodAttributeIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
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
