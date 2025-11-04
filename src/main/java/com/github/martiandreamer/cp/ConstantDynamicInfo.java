package com.github.martiandreamer.cp;

public class ConstantDynamicInfo extends ConstantInfo {
    protected final int bootstrapMethodAttributeIndex;
    protected final int nameAndTypeIndex;

    protected ConstantDynamicInfo(short tag, ConstantPool constantPool, int bootstrapMethodAttributeIndex, int nameAndTypeIndex) {
        super(tag, constantPool);
        this.bootstrapMethodAttributeIndex = bootstrapMethodAttributeIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public ConstantDynamicInfo(ConstantPool constantPool, int bootstrapMethodAttributeIndex, int nameAndTypeIndex) {
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
