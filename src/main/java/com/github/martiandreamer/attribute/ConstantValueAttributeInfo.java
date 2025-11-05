package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantPoolRef;
import com.github.martiandreamer.cp.ConstantValueInfo;

public class ConstantValueAttributeInfo extends AttributeInfo {
    private final ConstantPoolRef value;

    public ConstantValueAttributeInfo(ConstantPoolRef attributeName, ConstantPoolRef value) {
        super(attributeName);
        if (!(value.getContent() instanceof ConstantValueInfo)) {
            throw new InvalidClassFileFormatException("value must be a ConstantValueInfo " + value);
        }
        this.value = value;
    }


    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return ((ConstantValueInfo<T>)value.getContent()).getValue();
    }

    public <T> T getValue(Class<T> clazz) {
        return clazz.cast(getValue());
    }
}
