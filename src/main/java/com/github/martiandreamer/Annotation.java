package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class Annotation {
    private final ConstantPoolRef type;
    private final ElementValuePair[] elementValuePairs;

    public Annotation(ConstantPoolRef type, ElementValuePair[] elementValuePairs) {
        if (type.getTag() != ConstantInfo.UTF8) {
            throw new InvalidClassFileFormatException("type tag should be UTF8");
        }
        this.type = type;
        this.elementValuePairs = elementValuePairs;
    }

    public ConstantPoolRef getType() {
        return type;
    }

    public ElementValuePair[] getElementValuePairs() {
        return elementValuePairs;
    }

}
