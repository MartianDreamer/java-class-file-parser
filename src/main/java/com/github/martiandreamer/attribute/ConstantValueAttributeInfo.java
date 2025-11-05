package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantPoolRef;
import com.github.martiandreamer.cp.ConstantValueInfo;

public class ConstantValueAttributeInfo extends AttributeInfo {
    private final ConstantPoolRef value;

    public ConstantValueAttributeInfo(ConstantPoolRef attributeName, ConstantPoolRef value) throws InvalidClassFileFormatException {
        super(attributeName);
        if (!(value.getContent() instanceof ConstantValueInfo)) {
            throw new InvalidClassFileFormatException("value must be a ConstantValueInfo " + value);
        }
        this.value = value;
    }


    public Object getValue() {
        return ((ConstantValueInfo<?>)value.getContent()).getValue();
    }
}
