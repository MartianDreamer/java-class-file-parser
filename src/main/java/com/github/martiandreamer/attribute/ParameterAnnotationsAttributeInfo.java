package com.github.martiandreamer.attribute;

import com.github.martiandreamer.Annotation;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class ParameterAnnotationsAttributeInfo extends AttributeInfo {

    private final Annotation[][] parameterAnnotations;

    protected ParameterAnnotationsAttributeInfo(ConstantPoolRef attributeName, Annotation[][] parameterAnnotations) {
        super(attributeName);
        this.parameterAnnotations = parameterAnnotations;
    }

    public Annotation[][] getParameterAnnotations() {
        return parameterAnnotations;
    }
}
