package com.github.martiandreamer.cp;

public class ConstantUtf8Info implements ConstantInfo {
    protected String content;

    public ConstantUtf8Info(String content) {
        this.content = content;
    }

    @Override
    public short getTag() {
        return UTF8;
    }

    @Override
    public String getName() {
        return "CONSTANT_Utf8_info";
    }

    public String getContent() {
        return content;
    }
}
