package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

public abstract class AttributeInfo {
    private final ConstantRef<ConstantUtf8Info> attributeName;

    protected AttributeInfo(ConstantRef<ConstantUtf8Info> attributeName) {
        this.attributeName = attributeName;
    }

    public AttributeType getType() {
        return AttributeType.valueOf(attributeName.getContent().getContent());
    }

    public enum AttributeType {
        ConstantValue, Code, StackMapTable
    }
}
