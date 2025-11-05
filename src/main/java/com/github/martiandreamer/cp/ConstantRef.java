package com.github.martiandreamer.cp;

public class ConstantRef<T extends ConstantInfo> {
    private final int index;
    private final T content;
    public ConstantRef(int index, ConstantInfo[] constantPool, Class<T> tClass) {
        this.index = index;
        if (constantPool[index] == null || constantPool[index].getClass() != tClass) {
            throw new IllegalArgumentException();
        }
        this.content = tClass.cast(constantPool[index]);
    }

    public int getIndex() {
        return index;
    }

    public T getContent() {
        return content;
    }
}
