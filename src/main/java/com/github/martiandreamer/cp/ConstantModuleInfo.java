package com.github.martiandreamer.cp;

public class ConstantModuleInfo extends ConstantUtf8ReferenceInfo {

    protected ConstantModuleInfo(ConstantPool constantPool, int index) {
        super(MODULE, constantPool, index);
    }

    @Override
    public String getName() {
        return "CONSTANT_Module_info";
    }
}
