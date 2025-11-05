package com.github.martiandreamer.cp;

public abstract class ConstantReferenceInfo extends ConstantInfo{
    protected final ConstantInfo[] constantPool;

    protected ConstantReferenceInfo(short tag, ConstantInfo[] constantPool) {
        super(tag);
        this.constantPool = constantPool;
    }
}
