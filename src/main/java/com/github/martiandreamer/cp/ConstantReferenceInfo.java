package com.github.martiandreamer.cp;

public abstract class ConstantReferenceInfo implements ConstantInfo{
    protected final ConstantInfo[] constantPool;

    protected ConstantReferenceInfo(ConstantInfo[] constantPool) {
        this.constantPool = constantPool;
    }
}
