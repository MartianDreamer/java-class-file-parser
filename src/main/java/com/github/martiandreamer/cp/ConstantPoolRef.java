package com.github.martiandreamer.cp;

@SuppressWarnings("unchecked")
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

    public <T extends ConstantInfo> T getContent() {
        return (T) constantPool[index];
    }

    public <T extends ConstantInfo> T getContent(Class<T> tClass) {
        if  (tClass.isInstance(constantPool[index])) {
            return (T) constantPool[index];
        }
        throw new IllegalArgumentException("Invalid type");
    }

    @Override
    public short getTag() {
        return constantPool[index].getTag();
    }

    @Override
    public String getConstantType() {
        return constantPool[index].getConstantType();
    }

    @Override
    public int size() {
        return constantPool[index].size();
    }

    @Override
    public String toString() {
        return constantPool[index].toString();
    }
}
