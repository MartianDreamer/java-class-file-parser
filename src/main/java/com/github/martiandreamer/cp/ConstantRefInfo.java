package com.github.martiandreamer.cp;

public abstract class ConstantRefInfo implements ConstantInfo {

    protected final ConstantPoolRef clazz;
    protected final ConstantPoolRef nameAndType;

    protected ConstantRefInfo(ConstantInfo[] constantPool, int classIndex, int nameAndTypeIndex) {
        this.clazz = new ConstantPoolRef(classIndex, constantPool);
        this.nameAndType = new ConstantPoolRef(nameAndTypeIndex, constantPool);
    }

    public ConstantPoolRef getClazz() {
        return clazz;
    }

    public ConstantPoolRef getNameAndType() {
        return nameAndType;
    }
}
