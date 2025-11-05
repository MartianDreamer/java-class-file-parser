package com.github.martiandreamer.cp;

public class ConstantPoolRef implements ConstantInfo {
    private final int index;
    private final ConstantInfo[] constantPool;

    public ConstantPoolRef(int index, ConstantInfo[] constantPool) {
        this.index = index;
        this.constantPool = constantPool;
    }

    public int getIndex() {
        return index;
    }

    public ConstantInfo getContent() {
        return constantPool[index];
    }

    @Override
    public short getTag() {
        return constantPool[index].getTag();
    }

    public String getName() {
        return constantPool[index].getName();
    }

    @Override
    public int size() {
        return constantPool[index].size();
    }
}
