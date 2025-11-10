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

    public static class ElementValuePair {

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
        private final byte tag;
        private final ConstantPoolRef constValue;
        private final Enum enumConstValue;
        private final ConstantPoolRef classInfo;
        private final Annotation annotation;
        private final ElementValuePair[] arrayValue;

        public ElementValuePair(ConstantPoolRef elementName, byte tag, ConstantPoolRef constValue, Enum enumConstValue, ConstantPoolRef classInfo, Annotation annotation, ElementValuePair[] arrayValue) {
            if (elementName.getTag() != ConstantInfo.UTF8) {
                throw new InvalidClassFileFormatException("elementName tag should be UTF8");
            }
            switch (tag) {
                case BYTE, CHAR, INT, SHORT, BOOLEAN:
                    if (constValue == null || constValue.getTag() != ConstantInfo.INTEGER) {
                        throw new InvalidClassFileFormatException("constValue tag should be INTEGER " + constValue);
                    }
                case DOUBLE:
                    if (constValue == null || constValue.getTag() != ConstantInfo.DOUBLE) {
                        throw new InvalidClassFileFormatException("constValue tag should be DOUBLE " + constValue);
                    }
                case FLOAT:
                    if (constValue == null || constValue.getTag() != ConstantInfo.FLOAT) {
                        throw new InvalidClassFileFormatException("constValue tag should be FLOAT " + constValue);
                    }
                case LONG:
                    if (constValue == null || constValue.getTag() != ConstantInfo.LONG) {
                        throw new InvalidClassFileFormatException("constValue tag should be LONG " + constValue);
                    }
                case STRING:
                    if (constValue == null || constValue.getTag() != ConstantInfo.UTF8) {
                        throw new InvalidClassFileFormatException("constValue tag should be STRING " + constValue);
                    }
                case CLASS:
                    if (constValue == null || constValue.getTag() != ConstantInfo.UTF8) {
                        throw new InvalidClassFileFormatException("constValue tag should be UTF8 " + constValue);
                    }
            }
            this.elementName = elementName;
            this.tag = tag;
            this.constValue = constValue;
            this.enumConstValue = enumConstValue;
            this.classInfo = classInfo;
            this.annotation = annotation;
            this.arrayValue = arrayValue;
        }

        public ConstantPoolRef getElementName() {
            return elementName;
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

        public ElementValuePair[] getArrayValue() {
            return arrayValue;
        }
    }
}
