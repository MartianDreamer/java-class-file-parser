package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantPoolRef;

public class ExceptionsAttributeInfo extends AttributeInfo {
    private final ConstantPoolRef[] exceptionTable;
    protected ExceptionsAttributeInfo(ConstantPoolRef attributeName, ConstantPoolRef[] exceptionTable) {
        super(attributeName);
        this.exceptionTable = exceptionTable;
    }

    public ConstantPoolRef[] getExceptionTable() {
        return exceptionTable;
    }
}
