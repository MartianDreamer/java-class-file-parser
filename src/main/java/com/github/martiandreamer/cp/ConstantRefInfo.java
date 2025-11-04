package com.github.martiandreamer.cp;

public abstract class ConstantRefInfo extends ConstantInfo {

    protected final int classIndex;
    protected final int nameAndTypeIndex;

    protected ConstantRefInfo(short tag, ConstantInfo[] constantPool, int classIndex, int nameAndTypeIndex) {
        super(tag, constantPool);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public ConstantClassInfo getClassInfo() {
        if (constantPool[classIndex] == null) {
            return null;
        } else if (constantPool[classIndex] instanceof ConstantClassInfo cpci) {
            return cpci;
        }
        return null;
    }

    public ConstantNameAndTypeInfo getNameAndTypeInfo() {
        if (constantPool[nameAndTypeIndex] == null) {
            return null;
        } else if (constantPool[nameAndTypeIndex] instanceof ConstantNameAndTypeInfo cpti) {
            return cpti;
        }
        return null;
    }
}
