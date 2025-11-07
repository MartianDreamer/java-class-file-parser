package com.github.martiandreamer.cp;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.ModifiedUtf8Parser;
import com.github.martiandreamer.Parser;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.github.martiandreamer.Constant.*;
import static com.github.martiandreamer.Utils.parseInt;
import static com.github.martiandreamer.Utils.parseShort;
import static com.github.martiandreamer.cp.ConstantInfo.*;

public class ConstantPoolParser extends Parser<ConstantInfo[]> {
    private ConstantInfo[] constantPool;

    public ConstantPoolParser(byte[] content, int from) {
        super(content, from);
    }

    public ConstantInfo[] parse() {
        if (constantPool != null) {
            return constantPool;
        }
        int count = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        this.constantPool = new ConstantInfo[count];
        this.constantPool[0] = new ConstantNullInfo();
        for (int i = 1; i < count;) {
            ConstantInfo constantInfo = parseConstantInfo();
            constantPool[i] = constantInfo;
            i += constantInfo.size();
        }
        return constantPool;
    }

    private ConstantInfo parseConstantInfo() {
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
        Parser<String> stringParser = new ModifiedUtf8Parser(content, current, HALF_SIZE);
        String content = stringParser.parse();
        current = stringParser.getCurrent();
        return new ConstantUtf8Info(content);
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
