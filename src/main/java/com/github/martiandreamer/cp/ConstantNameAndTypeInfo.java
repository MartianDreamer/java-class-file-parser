package com.github.martiandreamer.cp;

public class ConstantNameAndTypeInfo implements ConstantInfo {
    protected final ConstantPoolRef name;
    protected final ConstantPoolRef descriptor;

    public ConstantNameAndTypeInfo(ConstantInfo[] constantPool, int nameIndex, int descriptorIndex) {
        this.name = new ConstantPoolRef(nameIndex, constantPool);
        this.descriptor = new ConstantPoolRef(descriptorIndex, constantPool);
    }

    @Override
    public short getTag() {
        return NAME_AND_TYPE;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_NameAndType_info";
    }

    public ConstantPoolRef getName() {
        return name;
    }

    public ConstantPoolRef getDescriptor() {
        return descriptor;
    }
}
