package com.github.martiandreamer.cp;

public class ConstantMethodHandleInfo extends ConstantInfo {
    protected final short referenceKind;
    protected final short referenceIndex;

    public ConstantMethodHandleInfo(ConstantInfo[] constantPool, short referenceKind, short referenceIndex) {
        super(METHOD_HANDLE, constantPool);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    @Override
    public String getName() {
        return "CONSTANT_MethodHandle_info";
    }
}
