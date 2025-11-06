package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class EnclosingMethodAttributeInfo extends AttributeInfo {

    private final ConstantPoolRef clazz;
    private final ConstantPoolRef method;

    protected EnclosingMethodAttributeInfo(ConstantPoolRef attributeName, ConstantPoolRef clazz, ConstantPoolRef method) {
        super(attributeName);
        if (clazz.getTag() != ConstantInfo.CLASS) {
            throw new InvalidClassFileFormatException("Class index does not point to a CONSTANT_Class_info " + clazz);
        }
        if (method.getTag() != ConstantInfo.NAME_AND_TYPE && method.getTag() != ConstantInfo.NULL) {
            throw new InvalidClassFileFormatException("Method index does not point to a CONSTANT_NameAndType_info or NULL" + method);
        }
        this.clazz = clazz;
        this.method = method;
    }

    public ConstantPoolRef getClazz() {
        return clazz;
    }

    public ConstantPoolRef getMethod() {
        return method;
    }
}
