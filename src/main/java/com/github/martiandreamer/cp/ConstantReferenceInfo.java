package com.github.martiandreamer.cp;

public abstract class ConstantReferenceInfo extends ConstantInfo{
    protected final ConstantPool constantPool;

    protected ConstantReferenceInfo(short tag, ConstantPool constantPool) {
        super(tag);
        this.constantPool = constantPool;
    }
}
