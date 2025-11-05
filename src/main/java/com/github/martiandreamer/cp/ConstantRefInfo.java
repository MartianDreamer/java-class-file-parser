package com.github.martiandreamer.cp;

public abstract class ConstantRefInfo extends ConstantReferenceInfo {

    protected final int classIndex;
    protected final int nameAndTypeIndex;

    protected ConstantRefInfo(ConstantInfo[] constantPool, int classIndex, int nameAndTypeIndex) {
        super(constantPool);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
