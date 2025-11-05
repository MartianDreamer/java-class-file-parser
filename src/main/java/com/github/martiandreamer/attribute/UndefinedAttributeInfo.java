package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

public class UndefinedAttributeInfo extends AttributeInfo {

    private final byte[] content;
    private final long from;
    private final long length;

    protected UndefinedAttributeInfo(ConstantRef<ConstantUtf8Info> attributeName, byte[] content, long from, long length) {
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
