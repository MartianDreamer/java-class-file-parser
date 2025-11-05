package com.github.martiandreamer.cp;

public abstract class ConstantUtf8ReferenceInfo extends ConstantReferenceInfo {

    protected final int index;

    protected ConstantUtf8ReferenceInfo(ConstantInfo[] constantPool, int index) {
        super(constantPool);
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
