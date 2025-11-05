package com.github.martiandreamer.cp;

public class ConstantMethodHandleInfo extends ConstantReferenceInfo {
    protected final short referenceKind;
    protected final int referenceIndex;

    public ConstantMethodHandleInfo(ConstantInfo[] constantPool, short referenceKind, int referenceIndex) {
        super(constantPool);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    @Override
    public short getTag() {
        return METHOD_HANDLE;
    }

    @Override
    public String getName() {
        return "CONSTANT_MethodHandle_info";
    }
}
