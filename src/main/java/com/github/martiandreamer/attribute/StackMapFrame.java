package com.github.martiandreamer.attribute;

public class StackMapFrame {
    private final short frameType;
    private final Integer offsetDelta;
    private final VariableInfo[] locals;
    private final VariableInfo[] stack;

    public StackMapFrame(short frameType, Integer offsetDelta, VariableInfo[] locals, VariableInfo[] stack) {
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

    public String getFrameName() {
        if (frameType >= 0 && frameType <= 63) {
            return "SAME";
        } else if (frameType >= 64 && frameType <= 127) {
            return "SAME_LOCALS_1_STACK_ITEM";
        } else if (frameType >= 128 && frameType <= 246) {
            return "SAME_LOCALS_1_STACK_ITEM_EXTENDED";
        } else if (frameType >= 248 && frameType <= 250) {
            return "CHOP";
        } else if (frameType == 251) {
            return "SAME_FRAME_EXTENDED";
        } else if (frameType >= 252 && frameType <= 254) {
            return "APPEND";
        } else if (frameType == 255) {
            return "FULL_FRAME";
        }
        return "RESERVED";
    }
}
