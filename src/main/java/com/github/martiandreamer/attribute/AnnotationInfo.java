package com.github.martiandreamer.attribute;

import com.github.martiandreamer.Annotation;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class AnnotationInfo extends AttributeInfo {

    private final Annotation[] annotations;

    protected AnnotationInfo(ConstantPoolRef attributeName, Annotation[] annotations) {
        super(attributeName);
        this.annotations = annotations;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }
}
