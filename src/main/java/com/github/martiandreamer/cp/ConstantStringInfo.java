package com.github.martiandreamer.cp;

public class ConstantStringInfo extends ConstantUtf8ReferenceInfo {

    public ConstantStringInfo(ConstantPool constantPool, int index) {
        super(STRING, constantPool, index);
    }

    @Override
    public String getName() {
        return "CONSTANT_String_info";
    }
}
