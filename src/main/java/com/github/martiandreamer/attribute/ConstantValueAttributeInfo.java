package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;
import com.github.martiandreamer.cp.ConstantValueInfo;

public class ConstantValueAttributeInfo<T> extends AttributeInfo {
    private final ConstantRef<ConstantValueInfo<T>> value;

    public ConstantValueAttributeInfo(ConstantRef<ConstantUtf8Info> attributeName, ConstantRef<ConstantValueInfo<T>> value) {
        super(attributeName);
        this.value = value;
    }

    public T getValue() {
        return value.getContent().getValue();
    }
}
