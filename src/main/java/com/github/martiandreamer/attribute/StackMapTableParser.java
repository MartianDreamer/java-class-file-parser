package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.Parser;
import com.github.martiandreamer.cp.ConstantClassInfo;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

import static com.github.martiandreamer.Constant.*;
import static com.github.martiandreamer.Utils.parseInt;
import static com.github.martiandreamer.Utils.parseShort;

public class StackMapTableParser extends Parser<StackMapTableAttributeInfo> {
    private final ConstantInfo[] constantPool;
    private final ConstantRef<ConstantUtf8Info> attributeName;
    private StackMapTableAttributeInfo result;

    protected StackMapTableParser(byte[] content, int from, ConstantRef<ConstantUtf8Info> attributeName, ConstantInfo[] constantPool) {
        super(content, from);
        this.constantPool = constantPool;
        this.attributeName = attributeName;
    }

    @Override
    public StackMapTableAttributeInfo parse() throws InvalidClassFileFormatException {
        if (result != null) {
            return result;
        }
        current += WORD_SIZE;
        int entryCount = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        StackMapFrame[] entries = new StackMapFrame[entryCount];
        for (int i = 0; i < entryCount; i++) {
            entries[i] = parseStackMapFrame();
        }
        this.result = new StackMapTableAttributeInfo(attributeName, entries);
        return result;
    }

    private StackMapFrame parseStackMapFrame() throws InvalidClassFileFormatException {
        short frameType = parseShort(content, current, BITE_SIZE);
        current += BITE_SIZE;
        if (frameType >= 0 && frameType <= 63) {
            return new StackMapFrame(frameType, null, new VariableInfo[0], new VariableInfo[0]);
        } else if (frameType >= 64 && frameType <= 127) {
            return new StackMapFrame(frameType, null, new VariableInfo[0], new VariableInfo[]{parseVariableInfo()});
        } else if (frameType >= 128 && frameType <= 246) {
            return new StackMapFrame(frameType, parseOffsetDelta(), new VariableInfo[0], new VariableInfo[]{parseVariableInfo()});
        } else if (frameType >= 248 && frameType <= 250) {
            return new StackMapFrame(frameType, parseOffsetDelta(), new VariableInfo[0], new VariableInfo[0]);
        } else if (frameType == 251) {
            return new StackMapFrame(frameType, parseOffsetDelta(), new VariableInfo[0], new VariableInfo[0]);
        } else if (frameType >= 252 && frameType <= 254) {
            int offsetDelta = parseOffsetDelta();
            VariableInfo[] locals = new VariableInfo[frameType - 251];
            for (int i = 0; i < frameType - 251; i++) {
                locals[i] = parseVariableInfo();
            }
            return new StackMapFrame(frameType, offsetDelta, locals, new VariableInfo[0]);
        } else if (frameType == 255) {
            int offsetDelta = parseOffsetDelta();

            // parse verification_type_info for locals
            int localCount = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            VariableInfo[] locals = new VariableInfo[localCount];
            for (int i = 0; i < localCount; i++) {
                locals[i] = parseVariableInfo();
            }

            // parse verification_type_info for stack
            int stackCount = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            VariableInfo[] stacks = new VariableInfo[stackCount];
            for (int i = 0; i < stackCount; i++) {
                stacks[i] = parseVariableInfo();
            }
            return new StackMapFrame(frameType, offsetDelta, locals, stacks);
        }
        throw new InvalidClassFileFormatException("Invalid frame_type " + frameType);
    }

    private int parseOffsetDelta() {
        int offsetDelta = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        return offsetDelta;
    }

    private VariableInfo parseVariableInfo() throws InvalidClassFileFormatException {
        short tag = parseShort(content, current, BITE_SIZE);
        current += BITE_SIZE;
        if (tag >= 0 && tag < 7) {
            return new VariableInfo(tag);
        } else if (tag == 7) {
            int index = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            return new ObjectVariableInfo(new ConstantRef<>(index, constantPool, ConstantClassInfo.class));
        } else if (tag == 8) {
            int offset = parseInt(content, current, HALF_SIZE);
            current += HALF_SIZE;
            return new UninitializedVariableInfo(offset);
        }
        throw new InvalidClassFileFormatException("Invalid verification type tag: " + tag);
    }
}
