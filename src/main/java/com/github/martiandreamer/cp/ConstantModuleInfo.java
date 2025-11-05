package com.github.martiandreamer.cp;

public class ConstantModuleInfo implements ConstantInfo {
    private final ConstantPoolRef name;

    protected ConstantModuleInfo(ConstantInfo[] constantPool, int nameIndex) {
        this.name = new ConstantPoolRef(nameIndex, constantPool);
    }

    @Override
    public short getTag() {
        return MODULE;
    }

    @Override
    public String getConstantType() {
        return "CONSTANT_Module_info";
    }

    public ConstantPoolRef getName() {
        return name;
    }
}
