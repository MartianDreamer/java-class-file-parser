package com.github.martiandreamer.attribute;

public class UninitializedVariableInfo extends VariableInfo {
    private final int offset;

    public UninitializedVariableInfo(int offset) {
        super(ITEM_Uninitialized);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
