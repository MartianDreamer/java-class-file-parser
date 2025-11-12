package com.github.martiandreamer.attribute;

import com.github.martiandreamer.ElementValue;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class AnnotationDefaultAttributeInfo extends AttributeInfo {
    private final ElementValue defaultValue;
    protected AnnotationDefaultAttributeInfo(ConstantPoolRef attributeName, ElementValue defaultValue) {
        super(attributeName);
        this.defaultValue = defaultValue;
    }

    public ElementValue getDefaultValue() {
        return defaultValue;
    }
}
