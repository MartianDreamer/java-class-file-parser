package com.github.martiandreamer.cp;

public abstract class ConstantUtf8ReferenceInfo extends ConstantReferenceInfo {

    protected final int index;

    protected ConstantUtf8ReferenceInfo(short tag, ConstantInfo[] constantPool, int index) {
        super(tag, constantPool);
        this.index = index;
    }

    public ConstantUtf8Info getContent() {
        ConstantInfo result = constantPool[index];
        if (result instanceof ConstantUtf8Info cui) {
            return cui;
        }
        return null;
    }

    public int getIndex() {
        return index;
    }
}
