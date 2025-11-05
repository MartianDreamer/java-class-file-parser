package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class UndefinedAttributeInfo extends AttributeInfo {

    private final byte[] content;
    private final long from;
    private final long length;

    protected UndefinedAttributeInfo(ConstantPoolRef attributeName, byte[] content, long from, long length) throws InvalidClassFileFormatException {
        super(attributeName);
        this.content = content;
        this.from = from;
        this.length = length;
    }

    @Override
    public PredefinedAttributeType getType() {
        return PredefinedAttributeType.Undefined;
    }
}
