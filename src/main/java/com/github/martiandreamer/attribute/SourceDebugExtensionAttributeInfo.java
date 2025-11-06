package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantPoolRef;

public class SourceDebugExtensionAttributeInfo extends AttributeInfo {

    private final String debugExtension;

    protected SourceDebugExtensionAttributeInfo(ConstantPoolRef attributeName, String debugExtension) {
        super(attributeName);
        this.debugExtension = debugExtension;
    }

    public String getDebugExtension() {
        return debugExtension;
    }
}
