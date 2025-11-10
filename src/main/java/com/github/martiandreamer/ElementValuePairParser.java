package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

import static com.github.martiandreamer.Annotation.ElementValuePair;
import static com.github.martiandreamer.Annotation.ElementValuePair.*;
import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Utils.parseInt;

public class ElementValuePairParser extends Parser<ElementValuePair> {

    private final ConstantInfo[] constantPool;

    public ElementValuePairParser(byte[] content, int from, ConstantInfo[] constantPool) {
        super(content, from);
        this.constantPool = constantPool;
    }

    @Override
    public ElementValuePair parse() {
        int elementNameIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef elementName = new ConstantPoolRef(elementNameIndex, constantPool);

        byte tag = content[current++];

        return switch (tag) {
            case BYTE, CHAR, DOUBLE, FLOAT, INT, LONG, SHORT, BOOLEAN, STRING -> {
                int constValueIndex = parseInt(content, current, HALF_SIZE);
                current+= HALF_SIZE;
                ConstantPoolRef constValue = new ConstantPoolRef(constValueIndex, constantPool);
                yield new ElementValuePair(elementName, tag, constValue, null, null, null, null);
            }
            case ENUM -> {
                int typeNameIndex = parseInt(content, current, HALF_SIZE);
                current+= HALF_SIZE;
                ConstantPoolRef typeName = new ConstantPoolRef(typeNameIndex, constantPool);

                int constNameIndex = parseInt(content, current, HALF_SIZE);
                current+= HALF_SIZE;
                ConstantPoolRef constName = new ConstantPoolRef(constNameIndex, constantPool);
                yield new ElementValuePair(elementName, tag, null, new Enum(typeName, constName), null, null, null);
            }
            case CLASS -> {
                int classInfoIndex = parseInt(content, current, HALF_SIZE);
                current+= HALF_SIZE;
                ConstantPoolRef classInfo = new ConstantPoolRef(classInfoIndex, constantPool);
                yield new ElementValuePair(elementName, tag, null, null, classInfo, null, null);
            }
            case ANNOTATION -> {
                Parser<Annotation> annotationParser = new AnnotationParser(content, current, constantPool);
                Annotation annotation = annotationParser.parse();
                current+= annotationParser.getCurrent();
                yield new ElementValuePair(elementName, tag, null, null, null, annotation, null);
            }
            case ARRAY -> {
                int valueCount = parseInt(content, current, HALF_SIZE);
                current+= HALF_SIZE;
                ElementValuePair[] arrayValue = new ElementValuePair[valueCount];

                for (int i = 0; i < valueCount; i++) {
                    Parser<ElementValuePair> elementValuePairParser = new ElementValuePairParser(content, current, constantPool);
                    ElementValuePair elementValuePair = elementValuePairParser.parse();
                    current = elementValuePairParser.getCurrent();
                    arrayValue[i] = elementValuePair;
                }
                yield new ElementValuePair(elementName, tag, null, null, null, null, arrayValue);
            }
            default -> throw new IllegalStateException("Unexpected value: " + tag);
        };
    }
}
