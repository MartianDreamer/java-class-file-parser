package com.github.martiandreamer.cp;

import java.util.ArrayList;
import java.util.List;

public class ConstantPool {
    private final List<ConstantInfo> constantPool;

    public ConstantPool(int size) {
        constantPool = new ArrayList<>(size);
    }

    public ConstantInfo get(int index) {
        if (index - 1 >= constantPool.size()) {
            return null;
        }
        return constantPool.get(index - 1);
    }

    public void add(ConstantInfo constantInfo) {
        constantPool.add(constantInfo);
    }

    public List<ConstantInfo> getConstantPool() {
        return constantPool;
    }

    @Override
    public String toString() {
        return "constantPool=" + constantPool;
    }
}
