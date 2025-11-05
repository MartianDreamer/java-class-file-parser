package com.github.martiandreamer.attribute;

public class StackMapFrame {
    private final short frameType;
    private final int offsetDelta;
    private final VariableInfo[] locals;
    private final VariableInfo[] stack;

    public StackMapFrame(short frameType, int offsetDelta, VariableInfo[] locals, VariableInfo[] stack) {
        this.frameType = frameType;
        this.offsetDelta = offsetDelta;
        this.locals = locals;
        this.stack = stack;
    }

    public short getFrameType() {
        return frameType;
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }

    public VariableInfo[] getLocals() {
        return locals;
    }

    public VariableInfo[] getStack() {
        return stack;
    }
}
