package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantPoolRef;

public class StackMapTableAttributeInfo extends AttributeInfo {

    private final StackMapFrame[] entries;

    protected StackMapTableAttributeInfo(ConstantPoolRef attributeName, StackMapFrame[] entries) {
        super(attributeName);
        this.entries = entries;
    }

    public StackMapFrame[] getEntries() {
        return entries;
    }
}
