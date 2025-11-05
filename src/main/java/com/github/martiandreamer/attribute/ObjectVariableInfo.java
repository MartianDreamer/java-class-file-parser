package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class ObjectVariableInfo extends VariableInfo {
    private final ConstantPoolRef cpoolIndex;

    public ObjectVariableInfo(ConstantPoolRef cpoolIndex) {
        super(ITEM_Object);
        if (cpoolIndex.getTag() != ConstantInfo.CLASS) {
            throw new InvalidClassFileFormatException("Invalid cpool index " + cpoolIndex);
        }
        this.cpoolIndex = cpoolIndex;
    }

    public ConstantPoolRef getCpoolIndex() {
        return cpoolIndex;
    }
}
