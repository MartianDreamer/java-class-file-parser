package com.github.martiandreamer.cp;

import java.util.Arrays;

public class ConstantPool {
    private final ConstantInfo[] constantPool;

    public ConstantPool(int size) {
        constantPool = new ConstantInfo[size];
    }

    public ConstantInfo get(int index) {
        if (index - 1 >= constantPool.length || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + constantPool.length);
        } else if (index == 0) {
            return null;
        }
        return constantPool[index - 1];
    }

    public void put(ConstantInfo constantInfo, int index) {
        constantPool[index] = constantInfo;
    }

    public ConstantInfo[] getConstantPool() {
        return constantPool;
    }

    @Override
    public String toString() {
        return "constantPool=" + Arrays.toString(constantPool);
    }
}
