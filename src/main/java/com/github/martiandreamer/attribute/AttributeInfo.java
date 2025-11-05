package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

public abstract class AttributeInfo {
    private final ConstantPoolRef attributeName;

    protected AttributeInfo(ConstantPoolRef attributeName) {
        if (attributeName.getTag() != ConstantInfo.UTF8) {
            throw new InvalidClassFileFormatException("Attribute name must be UTF8 " + attributeName);
        }
        this.attributeName = attributeName;
    }

    public PredefinedAttributeType getType() {
        return PredefinedAttributeType.valueOf(((ConstantUtf8Info) attributeName.getContent()).getContent());
    }

    public enum PredefinedAttributeType {
        Undefined, ConstantValue, Code, StackMapTable
    }
}
