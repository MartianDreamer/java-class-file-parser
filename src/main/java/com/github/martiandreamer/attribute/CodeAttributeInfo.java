package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class CodeAttributeInfo extends AttributeInfo {
    private final int maxStack;
    private final int maxLocals;
    private final byte[] code;
    private final long offset;
    private final long codeLength;
    private final ExceptionTableEntry[] exceptionTable;
    private final AttributeInfo[] attributes;

    protected CodeAttributeInfo(ConstantPoolRef attributeName, int maxStack, int maxLocals, byte[] code, long offset, long codeLength, ExceptionTableEntry[] exceptionTable, AttributeInfo[] attributes) {
        super(attributeName);
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.code = code;
        this.offset = offset;
        this.codeLength = codeLength;
        this.exceptionTable = exceptionTable;
        this.attributes = attributes;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public ExceptionTableEntry[] getExceptionTable() {
        return exceptionTable;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public long getOffset() {
        return offset;
    }

    public long getCodeLength() {
        return codeLength;
    }

    public static class ExceptionTableEntry {
        private final int startPc;
        private final int endPc;
        private final int handlerPc;
        private final ConstantPoolRef catchType;

        public ExceptionTableEntry(int startPc, int endPc, int handlerPc, ConstantPoolRef catchType) {
            if (catchType.getTag() != ConstantInfo.CLASS) {
                throw new InvalidClassFileFormatException("Invalid catch type " + catchType);
            }
            this.startPc = startPc;
            this.endPc = endPc;
            this.handlerPc = handlerPc;
            this.catchType = catchType;
        }

        public int getStartPc() {
            return startPc;
        }

        public int getEndPc() {
            return endPc;
        }

        public int getHandlerPc() {
            return handlerPc;
        }

        public ConstantPoolRef getCatchType() {
            return catchType;
        }
    }
}
