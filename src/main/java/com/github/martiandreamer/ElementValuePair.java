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

}
