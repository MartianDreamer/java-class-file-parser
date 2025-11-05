package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantClassInfo;
import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

public class CodeAttributeInfo extends AttributeInfo {
    private final int maxStack;
    private final int maxLocals;
    private final byte[] code;
    private final ExceptionTableEntry[] exceptionTable;
    private final AttributeInfo[] attributes;

    protected CodeAttributeInfo(ConstantRef<ConstantUtf8Info> attributeName, int maxStack, int maxLocals, byte[] code, ExceptionTableEntry[] exceptionTable, AttributeInfo[] attributes) {
        super(attributeName);
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.code = code;
        this.exceptionTable = exceptionTable;
        this.attributes = attributes;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public ExceptionTableEntry[] getExceptionTable() {
        return exceptionTable;
    }

    public byte[] getCode() {
        return code;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public static class ExceptionTableEntry {
        private final int startPc;
        private final int endPc;
        private final int handlerPc;
        private final ConstantRef<ConstantClassInfo> catchType;

        public ExceptionTableEntry(int startPc, int endPc, int handlerPc, ConstantRef<ConstantClassInfo> catchType) {
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

        public ConstantRef<ConstantClassInfo> getCatchType() {
            return catchType;
        }
    }
}
