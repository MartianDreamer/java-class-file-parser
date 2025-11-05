package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.Parser;
import com.github.martiandreamer.cp.*;

import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Constant.WORD_SIZE;
import static com.github.martiandreamer.Utils.parseInt;
import static com.github.martiandreamer.Utils.parseLong;
import static com.github.martiandreamer.attribute.AttributeInfo.PredefinedAttributeType;

public class AttributeParser extends Parser<AttributeInfo[]> {
    private AttributeInfo[] result;
    private final ConstantInfo[] constantPool;

    public AttributeParser(byte[] content, int from, ConstantInfo[] constantPool) {
        super(content, from);
        this.constantPool = constantPool;
    }

    public AttributeInfo[] parse() throws InvalidClassFileFormatException {
        if (result != null) {
            return result;
        }
        int count = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        AttributeInfo[] attributes = new AttributeInfo[count];
        for (int i = 0; i < count; i++) {
            attributes[i] = parseAttributeInfo();
        }
        this.result = attributes;
        return result;
    }

    public AttributeInfo parseAttributeInfo() throws InvalidClassFileFormatException {
        int index = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantRef<ConstantUtf8Info> constantRef = new ConstantRef<>(index, constantPool, ConstantUtf8Info.class);
        PredefinedAttributeType type;
        try {
            type = PredefinedAttributeType.valueOf(constantRef.getContent().getContent());
        } catch (IllegalArgumentException _) {
            type = PredefinedAttributeType.Undefined;
        }
        return switch (type) {
            case ConstantValue -> parseConstantValueAttributeInfo(constantRef);
            case Code -> parseCodeAttributeInfo(constantRef);
            case StackMapTable -> {
                Parser<StackMapTableAttributeInfo> parser = new StackMapTableParser(content, current, constantRef, constantPool);
                StackMapTableAttributeInfo stackMapTableAttributeInfo = parser.parse();
                current = parser.getCurrent();
                yield stackMapTableAttributeInfo;
            }
            case Undefined -> parseUndefinedAttributeInfo(constantRef);
        };
    }

    @SuppressWarnings("rawtypes")
    private ConstantValueAttributeInfo parseConstantValueAttributeInfo(ConstantRef<ConstantUtf8Info> constantRef) throws InvalidClassFileFormatException {
        current += WORD_SIZE;
        int index = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantRef<ConstantValueInfo> valueRef = new ConstantRef<>(index, constantPool, ConstantValueInfo.class);
        return new ConstantValueAttributeInfo(constantRef, valueRef);
    }

    private UndefinedAttributeInfo parseUndefinedAttributeInfo(ConstantRef<ConstantUtf8Info> constantRef) throws InvalidClassFileFormatException {
        long length = parseLength();
        current += (int) length;
        return new UndefinedAttributeInfo(constantRef, content, current, length);
    }

    private CodeAttributeInfo parseCodeAttributeInfo(ConstantRef<ConstantUtf8Info> constantRef) throws InvalidClassFileFormatException {
        current += WORD_SIZE;
        int maxStack = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int maxLocals = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        long codeLength = parseLength();
        long offset = current;
        current += (int) codeLength;
        CodeAttributeInfo.ExceptionTableEntry[] exceptionTable = parseExceptionTable();
        AttributeParser attributeParser = new AttributeParser(content, current, constantPool);
        AttributeInfo[] attributes = attributeParser.parse();
        current = attributeParser.current;
        return new CodeAttributeInfo(constantRef, maxStack, maxLocals, content, offset, codeLength, exceptionTable, attributes);
    }

    private CodeAttributeInfo.ExceptionTableEntry[] parseExceptionTable() {
        int exceptionTableLength = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        CodeAttributeInfo.ExceptionTableEntry[] exceptionTable = new CodeAttributeInfo.ExceptionTableEntry[exceptionTableLength];
        for (int i = 0; i < exceptionTableLength; i++) {
            exceptionTable[i] = parseExceptionTableEntry();
        }
        return exceptionTable;
    }

    private CodeAttributeInfo.ExceptionTableEntry parseExceptionTableEntry() {
        int startPc = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int endPc = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int handlerPc = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int catchTypeIndex =  parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantRef<ConstantClassInfo> catchType = new ConstantRef<>(catchTypeIndex, constantPool, ConstantClassInfo.class);
        return new CodeAttributeInfo.ExceptionTableEntry(startPc, endPc, handlerPc, catchType);
    }

    private long parseLength() {
        long length = parseLong(content, current, WORD_SIZE);
        current += WORD_SIZE;
        return length;
    }

    public byte[] getContent() {
        return content;
    }

    public int getFrom() {
        return from;
    }

    public int getCurrent() {
        return current;
    }
}
