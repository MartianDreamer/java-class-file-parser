package com.github.martiandreamer;

import com.github.martiandreamer.attribute.AttributeInfo;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class FieldAndMethod {
    private final AccessFlag[] accessFlags;
    private final ConstantPoolRef nameIndex;
    private final ConstantPoolRef descriptorIndex;
    private final AttributeInfo[] attributes;

    public FieldAndMethod(AccessFlag[] accessFlags, ConstantPoolRef nameIndex, ConstantPoolRef descriptorIndex, AttributeInfo[] attributes) throws InvalidClassFileFormatException {
        if (nameIndex.getTag() != ConstantInfo.UTF8) {
            throw new InvalidClassFileFormatException("Invalid contant info type " + nameIndex);
        }
        if (descriptorIndex.getTag() != ConstantInfo.UTF8) {
            throw new InvalidClassFileFormatException("Invalid contant info type " + descriptorIndex);
        }
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    public AccessFlag[] getAccessFlags() {
        return accessFlags;
    }

    public ConstantPoolRef getNameIndex() {
        return nameIndex;
    }

    public ConstantPoolRef getDescriptorIndex() {
        return descriptorIndex;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }
}
