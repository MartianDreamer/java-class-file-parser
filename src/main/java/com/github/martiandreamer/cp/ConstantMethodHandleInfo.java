package com.github.martiandreamer.cp;

public class ConstantMethodHandleInfo implements ConstantInfo {
    protected final short referenceKind;
    protected final ConstantPoolRef reference;

    public ConstantMethodHandleInfo(ConstantInfo[] constantPool, short referenceKind, int referenceIndex) {
        this.referenceKind = referenceKind;
        this.reference = new ConstantPoolRef(referenceIndex, constantPool);
    }

    @Override
    public short getTag() {
        return METHOD_HANDLE;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_MethodHandle_info";
    }
}
