package com.github.martiandreamer.attribute;

import com.github.martiandreamer.AccessFlag;
import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.Parser;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Constant.WORD_SIZE;
import static com.github.martiandreamer.Utils.parseInt;
import static com.github.martiandreamer.Utils.parseLong;
import static com.github.martiandreamer.attribute.AttributeInfo.PredefinedAttributeType;
import static com.github.martiandreamer.attribute.InnerClassesAttributeInfo.ClassRecord;
import static com.github.martiandreamer.attribute.LineNumberTableAttributeInfo.LineNumberTableRecord;
import static com.github.martiandreamer.attribute.LocalVariableTypeTableAttributeInfo.LocalVariableTypeTableRecord;


public class AttributeParser extends Parser<AttributeInfo[]> {
    private AttributeInfo[] result;
    private final ConstantInfo[] constantPool;

    public AttributeParser(byte[] content, int from, ConstantInfo[] constantPool) {
        super(content, from);
        this.constantPool = constantPool;
    }

    public AttributeInfo[] parse() {
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

    public AttributeInfo parseAttributeInfo() {
        int index = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef attributeName = new ConstantPoolRef(index, constantPool);
        PredefinedAttributeType type;
        if (attributeName.getTag() != ConstantInfo.UTF8) {
            throw new InvalidClassFileFormatException("UTF8 constant pool reference not supported " + attributeName);
        }
        try {
            type = PredefinedAttributeType.valueOf(((ConstantUtf8Info) attributeName.getContent()).getContent());
        } catch (IllegalArgumentException _) {
            type = PredefinedAttributeType.Undefined;
        }
        return switch (type) {
            case ConstantValue -> parseConstantValueAttributeInfo(attributeName);
            case Code -> parseCodeAttributeInfo(attributeName);
            case StackMapTable -> {
                Parser<StackMapTableAttributeInfo> parser = new StackMapTableParser(content, current, attributeName, constantPool);
                StackMapTableAttributeInfo stackMapTableAttributeInfo = parser.parse();
                current = parser.getCurrent();
                yield stackMapTableAttributeInfo;
            }
            case Exceptions -> parseExceptionsAttributeInfo(attributeName);
            case InnerClasses -> parseInnerClassesAttributeInfo(attributeName);
            case EnclosingMethod -> parseEnclosingMethodAttributeInfo(attributeName);
            case Synthetic -> {
                current += WORD_SIZE;
                yield new SyntheticAttributeInfo(attributeName);
            }
            case Signature -> parseSignatureAttributeInfo(attributeName);
            case SourceFile -> parseSourceFileAttributeInfo(attributeName);
            case SourceDebugExtension -> parseSourceDebugExtensionAttributeInfo(attributeName);
            case LineNumberTable -> parseLineNumberTableAttributeInfo(attributeName);
            case LocalVariableTypeTable -> parseLocalVariableTypeTableAttributeInfo(attributeName);
            case Deprecated -> {
                current += WORD_SIZE;
                yield new DeprecatedAttributeInfo(attributeName);
            }
            case Undefined -> parseUndefinedAttributeInfo(attributeName);
        };
    }

    private ConstantValueAttributeInfo parseConstantValueAttributeInfo(ConstantPoolRef constantPoolRef) {
        current += WORD_SIZE;
        int index = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef valueRef = new ConstantPoolRef(index, constantPool);
        return new ConstantValueAttributeInfo(constantPoolRef, valueRef);
    }

    private UndefinedAttributeInfo parseUndefinedAttributeInfo(ConstantPoolRef constantPoolRef) {
        long length = parseLength();
        current += (int) length;
        return new UndefinedAttributeInfo(constantPoolRef, content, current, length);
    }

    private CodeAttributeInfo parseCodeAttributeInfo(ConstantPoolRef constantPoolRef) {
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
        return new CodeAttributeInfo(constantPoolRef, maxStack, maxLocals, content, offset, codeLength, exceptionTable, attributes);
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
        int catchTypeIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef catchType = new ConstantPoolRef(catchTypeIndex, constantPool);
        return new CodeAttributeInfo.ExceptionTableEntry(startPc, endPc, handlerPc, catchType);
    }

    private ExceptionsAttributeInfo parseExceptionsAttributeInfo(ConstantPoolRef constantPoolRef) {
        current += WORD_SIZE;
        int countExceptions = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef[] exceptionTable = new ConstantPoolRef[countExceptions];
        for (int i = 0; i < countExceptions; i++) {
            int index = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            exceptionTable[i] = new ConstantPoolRef(index, constantPool);
            if (exceptionTable[i].getTag() != ConstantInfo.CLASS) {
                throw new InvalidClassFileFormatException("Exception index does not point to a CONSTANT_Class_info " + exceptionTable[i]);
            }
        }
        return new ExceptionsAttributeInfo(constantPoolRef, exceptionTable);
    }

    private InnerClassesAttributeInfo parseInnerClassesAttributeInfo(ConstantPoolRef constantPoolRef) {
        current += WORD_SIZE;
        int countInnerClasses = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ClassRecord[] classes = new ClassRecord[countInnerClasses];
        for (int i = 0; i < countInnerClasses; i++) {
            int innerClassInfoIndex = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            ConstantPoolRef innerClassInfo = new ConstantPoolRef(innerClassInfoIndex, constantPool);

            int outerClassInfoIndex = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            ConstantPoolRef outerClassInfo = new ConstantPoolRef(outerClassInfoIndex, constantPool);

            int nameIndex = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            ConstantPoolRef name = new ConstantPoolRef(nameIndex, constantPool);

            int accessFlagsValue = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            AccessFlag[] accessFlags = AccessFlag.getFlags(accessFlagsValue);
            classes[i] = new ClassRecord(innerClassInfo, outerClassInfo, name, accessFlags);
        }
        return new InnerClassesAttributeInfo(constantPoolRef, classes);
    }

    private EnclosingMethodAttributeInfo parseEnclosingMethodAttributeInfo(ConstantPoolRef constantPoolRef) {
        current += WORD_SIZE;
        int classIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef clazz = new ConstantPoolRef(classIndex, constantPool);
        int methodIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef method = new ConstantPoolRef(methodIndex, constantPool);
        return new EnclosingMethodAttributeInfo(constantPoolRef, clazz, method);
    }

    private SignatureAttributeInfo parseSignatureAttributeInfo(ConstantPoolRef constantPoolRef) {
        current += WORD_SIZE;
        ConstantPoolRef signature = parseConstantPoolRef();
        return new SignatureAttributeInfo(constantPoolRef, signature);
    }

    private SourceFileAttributeInfo parseSourceFileAttributeInfo(ConstantPoolRef constantPoolRef) {
        current += WORD_SIZE;
        ConstantPoolRef sourcefile = parseConstantPoolRef();
        return new SourceFileAttributeInfo(constantPoolRef, sourcefile);
    }

    private SourceDebugExtensionAttributeInfo parseSourceDebugExtensionAttributeInfo(ConstantPoolRef constantPoolRef) {
        int length = Math.toIntExact(parseLength());
        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(content, current, length))) {
            String content = dis.readUTF();
            return new SourceDebugExtensionAttributeInfo(constantPoolRef, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            current += length;
        }
    }

    private LineNumberTableAttributeInfo parseLineNumberTableAttributeInfo(ConstantPoolRef constantPoolRef) {
        current += WORD_SIZE;
        int count = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        LineNumberTableRecord[] lineNumberTable = new LineNumberTableRecord[count];
        for (int i = 0; i < count; i++) {
            int startPc = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            int lineNumber = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            lineNumberTable[i] = new LineNumberTableRecord(lineNumber, startPc);
        }
        return new LineNumberTableAttributeInfo(constantPoolRef, lineNumberTable);
    }

    private LocalVariableTypeTableAttributeInfo parseLocalVariableTypeTableAttributeInfo(ConstantPoolRef constantPoolRef) {
        current += WORD_SIZE;
        int count = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        LocalVariableTypeTableRecord[] localVariableTypeTable = new LocalVariableTypeTableRecord[count];
        for (int i = 0; i < count; i++) {
            int startPc = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            int length = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            int nameIndex = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            ConstantPoolRef name = new ConstantPoolRef(nameIndex, constantPool);
            int signatureIndex = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            ConstantPoolRef signature = new ConstantPoolRef(signatureIndex, constantPool);
            int index = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            localVariableTypeTable[i] = new LocalVariableTypeTableRecord(startPc, length, name, signature, index);
        }
        return new LocalVariableTypeTableAttributeInfo(constantPoolRef, localVariableTypeTable);
    }

    private ConstantPoolRef parseConstantPoolRef() {
        int index = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantPoolRef(index, constantPool);
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
