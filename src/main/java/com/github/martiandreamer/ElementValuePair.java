package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class ElementValuePair {

    public static final byte BYTE = 'B';
    public static final byte DOUBLE = 'D';
    public static final byte FLOAT = 'F';
    public static final byte CHAR = 'C';
    public static final byte INT = 'I';
    public static final byte LONG = 'J';
    public static final byte SHORT = 'S';
    public static final byte BOOLEAN = 'Z';
    public static final byte STRING = 's';
    public static final byte ENUM = 'e';
    public static final byte CLASS = 'c';
    public static final byte ANNOTATION = '@';
    public static final byte ARRAY = '[';

    private final ConstantPoolRef elementName;
    private final ElementValue elementValue;

    public ElementValuePair(ConstantPoolRef elementName, ElementValue elementValue) {
        if (elementName.getTag() != ConstantInfo.UTF8) {
            throw new InvalidClassFileFormatException("elementName tag should be UTF8");
        }
        this.elementName = elementName;
        this.elementValue = elementValue;
    }

    public ConstantPoolRef getElementName() {
        return elementName;
    }

    public ElementValue getElementValue() {
        return elementValue;
    }

    public static class ElementValue {
        private final byte tag;
        private final ConstantPoolRef constValue;
        private final Enum enumConstValue;
        private final ConstantPoolRef classInfo;
        private final Annotation annotation;
        private final ElementValue[] arrayValue;

        public ElementValue(byte tag, ConstantPoolRef constValue, Enum enumConstValue, ConstantPoolRef classInfo, Annotation annotation, ElementValue[] arrayValue) {
            switch (tag) {
                case BYTE, CHAR, INT, SHORT, BOOLEAN:
                    if (constValue == null || constValue.getTag() != ConstantInfo.INTEGER) {
                        throw new InvalidClassFileFormatException("constValue tag should be INTEGER " + constValue);
                    }
                    break;
                case DOUBLE:
                    if (constValue == null || constValue.getTag() != ConstantInfo.DOUBLE) {
                        throw new InvalidClassFileFormatException("constValue tag should be DOUBLE " + constValue);
                    }
                    break;
                case FLOAT:
                    if (constValue == null || constValue.getTag() != ConstantInfo.FLOAT) {
                        throw new InvalidClassFileFormatException("constValue tag should be FLOAT " + constValue);
                    }
                    break;
                case LONG:
                    if (constValue == null || constValue.getTag() != ConstantInfo.LONG) {
                        throw new InvalidClassFileFormatException("constValue tag should be LONG " + constValue);
                    }
                    break;
                case STRING:
                    if (constValue == null || constValue.getTag() != ConstantInfo.UTF8) {
                        throw new InvalidClassFileFormatException("constValue tag should be STRING " + constValue);
                    }
                    break;
                case CLASS:
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
}
