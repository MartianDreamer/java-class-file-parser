package com.github.martiandreamer.cp;

public class ConstantNameAndTypeInfo extends ConstantInfo {
    protected final int nameIndex;
    protected final int descriptorIndex;

    public ConstantNameAndTypeInfo(ConstantInfo[] constantPool, int nameIndex, int descriptorIndex) {
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

    public ConstantUtf8Info getNameContent() {
        if (constantPool[nameIndex] == null) {
            return null;
        } else if (constantPool[nameIndex] instanceof ConstantUtf8Info cpui) {
            return cpui;
        }
        return null;
    }

    public ConstantUtf8Info getDescriptorContent() {
        if (constantPool[descriptorIndex] == null) {
            return null;
        }  else if (constantPool[descriptorIndex] instanceof ConstantUtf8Info cpui) {
            return cpui;
        }
        return null;
    }
}
