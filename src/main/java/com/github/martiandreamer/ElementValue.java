package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class ElementValue {
    private final byte tag;
    private final ConstantPoolRef constValue;
    private final Enum enumConstValue;
    private final ConstantPoolRef classInfo;
    private final Annotation annotation;
    private final ElementValue[] arrayValue;

    public ElementValue(byte tag, ConstantPoolRef constValue, Enum enumConstValue, ConstantPoolRef classInfo, Annotation annotation, ElementValue[] arrayValue) {
        switch (tag) {
            case ElementValuePair.BYTE, ElementValuePair.CHAR, ElementValuePair.INT, ElementValuePair.SHORT,
                 ElementValuePair.BOOLEAN:
                if (constValue == null || constValue.getTag() != ConstantInfo.INTEGER) {
                    throw new InvalidClassFileFormatException("constValue tag should be INTEGER " + constValue);
                }
                break;
            case ElementValuePair.DOUBLE:
                if (constValue == null || constValue.getTag() != ConstantInfo.DOUBLE) {
                    throw new InvalidClassFileFormatException("constValue tag should be DOUBLE " + constValue);
                }
                break;
            case ElementValuePair.FLOAT:
                if (constValue == null || constValue.getTag() != ConstantInfo.FLOAT) {
                    throw new InvalidClassFileFormatException("constValue tag should be FLOAT " + constValue);
                }
                break;
            case ElementValuePair.LONG:
                if (constValue == null || constValue.getTag() != ConstantInfo.LONG) {
                    throw new InvalidClassFileFormatException("constValue tag should be LONG " + constValue);
                }
                break;
            case ElementValuePair.STRING:
                if (constValue == null || constValue.getTag() != ConstantInfo.UTF8) {
                    throw new InvalidClassFileFormatException("constValue tag should be STRING " + constValue);
                }
                break;
            case ElementValuePair.CLASS:
                if (classInfo == null || classInfo.getTag() != ConstantInfo.UTF8) {
                    throw new InvalidClassFileFormatException("constValue tag should be UTF8 " + classInfo);
                }
                break;
        }
        this.tag = tag;
        this.constValue = constValue;
        this.enumConstValue = enumConstValue;
        this.classInfo = classInfo;
        this.annotation = annotation;
        this.arrayValue = arrayValue;
    }

    public byte getTag() {
        return tag;
    }

    public ConstantPoolRef getConstValue() {
        return constValue;
    }

    public Enum getEnumConstValue() {
        return enumConstValue;
    }

    public ConstantPoolRef getClassInfo() {
        return classInfo;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public ElementValue[] getArrayValue() {
        return arrayValue;
    }
}
