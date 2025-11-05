package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

public class StackMapTableAttributeInfo extends AttributeInfo {

    private final StackMapFrame[] entries;

    protected StackMapTableAttributeInfo(ConstantRef<ConstantUtf8Info> attributeName, StackMapFrame[] entries) {
        super(attributeName);
        this.entries = entries;
    }

    public StackMapFrame[] getEntries() {
        return entries;
    }
}
