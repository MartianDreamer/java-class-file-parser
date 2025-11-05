package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;
import com.github.martiandreamer.cp.ConstantValueInfo;

@SuppressWarnings("rawtypes")
public class ConstantValueAttributeInfo extends AttributeInfo {
    private final ConstantRef<ConstantValueInfo> value;

    public ConstantValueAttributeInfo(ConstantRef<ConstantUtf8Info> attributeName, ConstantRef<ConstantValueInfo> value) {
        super(attributeName);
        this.value = value;
    }

    public Object getValue() {
        return value.getContent().getValue();
    }
}
