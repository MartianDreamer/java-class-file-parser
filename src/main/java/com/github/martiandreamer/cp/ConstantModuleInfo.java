package com.github.martiandreamer.cp;

public class ConstantModuleInfo extends ConstantUtf8ReferenceInfo {

    protected ConstantModuleInfo(ConstantInfo[] constantPool, int index) {
        super(constantPool, index);
    }

    @Override
    public short getTag() {
        return MODULE;
    }

    @Override
    public String getName() {
        return "CONSTANT_Module_info";
    }
}
