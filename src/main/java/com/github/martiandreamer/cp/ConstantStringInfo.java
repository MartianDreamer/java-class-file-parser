package com.github.martiandreamer.cp;

public class ConstantStringInfo extends ConstantUtf8ReferenceInfo {

    public ConstantStringInfo(ConstantInfo[] constantPool, int index) {
        super(STRING, constantPool, index);
    }

    @Override
    public String getName() {
        return "CONSTANT_String_info";
    }
}
