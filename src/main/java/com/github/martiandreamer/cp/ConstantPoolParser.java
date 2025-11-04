package com.github.martiandreamer.cp;

import com.github.martiandreamer.InvalidClassFileFormatException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.github.martiandreamer.Constant.BITE_SIZE;
import static com.github.martiandreamer.Constant.DOUBLE_SIZE;
import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Constant.WORD_SIZE;
import static com.github.martiandreamer.Utils.parseInt;
import static com.github.martiandreamer.Utils.parseShort;
import static com.github.martiandreamer.cp.ConstantInfo.CLASS;
import static com.github.martiandreamer.cp.ConstantInfo.DOUBLE;
import static com.github.martiandreamer.cp.ConstantInfo.DYNAMIC;
import static com.github.martiandreamer.cp.ConstantInfo.FIELD_REF;
import static com.github.martiandreamer.cp.ConstantInfo.FLOAT;
import static com.github.martiandreamer.cp.ConstantInfo.INTEGER;
import static com.github.martiandreamer.cp.ConstantInfo.INTERFACE_METHOD_REF;
import static com.github.martiandreamer.cp.ConstantInfo.INVOKE_DYNAMIC;
import static com.github.martiandreamer.cp.ConstantInfo.LONG;
import static com.github.martiandreamer.cp.ConstantInfo.METHOD_HANDLE;
import static com.github.martiandreamer.cp.ConstantInfo.METHOD_REF;
import static com.github.martiandreamer.cp.ConstantInfo.METHOD_TYPE;
import static com.github.martiandreamer.cp.ConstantInfo.MODULE;
import static com.github.martiandreamer.cp.ConstantInfo.NAME_AND_TYPE;
import static com.github.martiandreamer.cp.ConstantInfo.PACKAGE;
import static com.github.martiandreamer.cp.ConstantInfo.STRING;
import static com.github.martiandreamer.cp.ConstantInfo.UTF8;

public class ConstantPoolParser {
    private final byte[] content;
    private final int from;
    private int current;
    private ConstantPool constantPool;

    public ConstantPoolParser(byte[] content, int from) {
        this.content = content;
        this.from = from;
        this.current = from;
    }

    public ConstantPool parse() throws InvalidClassFileFormatException {
        if (constantPool != null) {
            return constantPool;
        }
        int count = parseInt(content, current, HALF_SIZE) - 1;
        current += HALF_SIZE;
        this.constantPool = new ConstantPool(count);
        for (int i = 0; i < count;) {
            ConstantInfo constantInfo = parseConstantInfo();
            constantPool.put(constantInfo, i);
            i += constantInfo.size();
        }
        return constantPool;
    }

    private ConstantInfo parseConstantInfo() throws InvalidClassFileFormatException {
        short tag = parseShort(content, current, BITE_SIZE);
        current += BITE_SIZE;
        return switch (tag) {
            case UTF8 -> parseConstantUtf8Info();
            case INTEGER -> parseConstantIntegerInfo();
            case FLOAT -> parseConstantFloatInfo();
            case LONG -> parseConstantLongInfo();
            case DOUBLE -> parseConstantDoubleInfo();
            case CLASS -> parseConstantClassInfo();
            case STRING -> parseConstantStringInfo();
            case FIELD_REF ->  parseConstantFieldRefInfo();
            case METHOD_REF -> parseConstantMethodRefInfo();
            case INTERFACE_METHOD_REF -> parseConstantInterfaceMethodRefInfo();
            case NAME_AND_TYPE -> parseConstantNameAndTypeInfo();
            case METHOD_HANDLE -> parseConstantMethodHandleInfo();
            case METHOD_TYPE -> parseConstantMethodTypeInfo();
            case DYNAMIC -> parseConstantDynamicInfo();
            case INVOKE_DYNAMIC -> parseConstantInvokeDynamicInfo();
            case MODULE -> parseConstantModuleInfo();
            case PACKAGE -> parseConstantPackageInfo();
            default -> throw new InvalidClassFileFormatException("Invalid CONSTANT_info tag: " + tag);
        };
    }

    private ConstantUtf8Info parseConstantUtf8Info() {
        int length = parseInt(content, current, HALF_SIZE);
        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(content, current, HALF_SIZE + length))) {
            String content = dis.readUTF();
            return new ConstantUtf8Info(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            current += HALF_SIZE + length;
        }
    }

    private ConstantIntegerInfo parseConstantIntegerInfo() {
        int content = ByteBuffer.wrap(this.content, current, WORD_SIZE)
                .order(ByteOrder.BIG_ENDIAN)
                .getInt();
        current += WORD_SIZE;
        return new ConstantIntegerInfo(content);
    }

    private ConstantFloatInfo parseConstantFloatInfo() {
        float content = ByteBuffer.wrap(this.content, current, WORD_SIZE)
                .order(ByteOrder.BIG_ENDIAN)
                .getFloat();
        current += WORD_SIZE;
        return new ConstantFloatInfo(content);
    }

    private ConstantLongInfo parseConstantLongInfo() {
        long content = ByteBuffer.wrap(this.content, current, DOUBLE_SIZE)
                .order(ByteOrder.BIG_ENDIAN)
                .getLong();
        current += DOUBLE_SIZE;
        return new ConstantLongInfo(content);
    }

    private ConstantDoubleInfo parseConstantDoubleInfo() {
        double content = ByteBuffer.wrap(this.content, current, DOUBLE_SIZE)
                .order(ByteOrder.BIG_ENDIAN)
                .getDouble();
        current += DOUBLE_SIZE;
        return new ConstantDoubleInfo(content);
    }

    private ConstantClassInfo parseConstantClassInfo() {
        int index = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantClassInfo(constantPool, index);
    }

    private ConstantStringInfo parseConstantStringInfo() {
        int index = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantStringInfo(constantPool, index);
    }

    private ConstantFieldRefInfo parseConstantFieldRefInfo() {
        int classIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int nameAndTypeIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantFieldRefInfo(constantPool, classIndex, nameAndTypeIndex);
    }

    private ConstantMethodRefInfo parseConstantMethodRefInfo() {
        int classIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int nameAndTypeIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantMethodRefInfo(constantPool, classIndex, nameAndTypeIndex);
    }

    private ConstantInterfaceMethodRefInfo parseConstantInterfaceMethodRefInfo() {
        int classIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int nameAndTypeIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantInterfaceMethodRefInfo(constantPool, classIndex, nameAndTypeIndex);
    }

    private ConstantNameAndTypeInfo parseConstantNameAndTypeInfo() {
        int nameIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int descriptorIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantNameAndTypeInfo(constantPool, nameIndex, descriptorIndex);
    }

    private ConstantMethodHandleInfo parseConstantMethodHandleInfo() {
        short referenceKind = parseShort(content, current, BITE_SIZE);
        current += BITE_SIZE;
        int referenceIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantMethodHandleInfo(constantPool, referenceKind, referenceIndex);
    }

    private ConstantMethodTypeInfo parseConstantMethodTypeInfo() {
        int descriptorIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantMethodTypeInfo(constantPool, descriptorIndex);
    }

    private ConstantDynamicInfo parseConstantDynamicInfo() {
        int bootstrapMethodAttributeIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int nameAndTypeIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantDynamicInfo(constantPool, bootstrapMethodAttributeIndex, nameAndTypeIndex);
    }

    private ConstantInvokeDynamicInfo parseConstantInvokeDynamicInfo() {
        int bootstrapMethodAttributeIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        int nameAndTypeIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantInvokeDynamicInfo(constantPool, bootstrapMethodAttributeIndex, nameAndTypeIndex);
    }

    private ConstantModuleInfo parseConstantModuleInfo() {
        int index = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantModuleInfo(constantPool, index);
    }

    private ConstantPackageInfo parseConstantPackageInfo() {
        int index = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return new ConstantPackageInfo(constantPool, index);
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
