package com.github.martiandreamer;

import com.github.martiandreamer.attribute.AttributeInfo;
import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

public class FieldAndMethod {
    private final AccessFlag[] accessFlags;
    private final ConstantRef<ConstantUtf8Info> nameIndex;
    private final ConstantRef<ConstantUtf8Info> descriptorIndex;
    private final AttributeInfo[] attributes;

    public FieldAndMethod(AccessFlag[] accessFlags, ConstantRef<ConstantUtf8Info> nameIndex, ConstantRef<ConstantUtf8Info> descriptorIndex, AttributeInfo[] attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    public AccessFlag[] getAccessFlags() {
        return accessFlags;
    }

    public ConstantRef<ConstantUtf8Info> getNameIndex() {
        return nameIndex;
    }

    public ConstantRef<ConstantUtf8Info> getDescriptorIndex() {
        return descriptorIndex;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }
}
