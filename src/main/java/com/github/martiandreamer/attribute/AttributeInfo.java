package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

public abstract class AttributeInfo {
    private final ConstantRef<ConstantUtf8Info> attributeName;

    protected AttributeInfo(ConstantRef<ConstantUtf8Info> attributeName) {
        this.attributeName = attributeName;
    }

    public PredefinedAttributeType getType() {
        return PredefinedAttributeType.valueOf(attributeName.getContent().getContent());
    }

    public enum PredefinedAttributeType {
        Undefined, ConstantValue, Code, StackMapTable
    }
}
