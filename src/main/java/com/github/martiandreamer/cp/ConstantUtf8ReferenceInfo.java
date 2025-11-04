package com.github.martiandreamer.cp;

public abstract class ConstantUtf8ReferenceInfo extends ConstantInfo {

    protected final int index;

    protected ConstantUtf8ReferenceInfo(short tag, ConstantInfo[] constantPool, int index) {
        super(tag, constantPool);
        this.index = index;
    }

    public ConstantUtf8Info getContent() {
        if (constantPool[index] == null) {
            return null;
        } else if (constantPool[index] instanceof ConstantUtf8Info cpui) {
            return cpui;
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean isValid() {
        return constantPool[index] instanceof ConstantUtf8Info;
    }
}
