package com.github.martiandreamer.cp;

public class ConstantUtf8Info extends ConstantInfo {
    protected String content;

    public ConstantUtf8Info(ConstantPool constantPool, String content) {
        super(UTF8, constantPool);
        this.content = content;
    }

    @Override
    public String getName() {
        return "CONSTANT_Utf8_info";
    }

    public String getContent() {
        return content;
    }
}
