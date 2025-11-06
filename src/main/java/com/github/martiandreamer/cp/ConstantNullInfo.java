package com.github.martiandreamer.cp;

public class ConstantNullInfo implements ConstantInfo {

    @Override
    public short getTag() {
        return NULL;
    }

    @Override
    public String getConstantType() {
        return "NULL";
    }

    @Override
    public int size() {
        return 0;
    }
}
