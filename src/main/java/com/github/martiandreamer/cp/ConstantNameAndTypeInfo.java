package com.github.martiandreamer.cp;

public class ConstantNameAndTypeInfo extends ConstantReferenceInfo {
    protected final int nameIndex;
    protected final int descriptorIndex;

    public ConstantNameAndTypeInfo(ConstantPool constantPool, int nameIndex, int descriptorIndex) {
        super(NAME_AND_TYPE, constantPool);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    public String getName() {
        return "CONSTANT_NameAndType_info";
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }
}
