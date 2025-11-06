package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantPoolRef;

public class LineNumberTableAttributeInfo extends AttributeInfo {
    private final LineNumberTableRecord[] lineNumberTable;

    protected LineNumberTableAttributeInfo(ConstantPoolRef attributeName, LineNumberTableRecord[] lineNumberTable) {
        super(attributeName);
        this.lineNumberTable = lineNumberTable;
    }

    public LineNumberTableRecord[] getLineNumberTable() {
        return lineNumberTable;
    }

    public static class LineNumberTableRecord {
        private final int startPc;
        private final int lineNumber;

        public LineNumberTableRecord(int startPc, int lineNumber) {
            this.startPc = startPc;
            this.lineNumber = lineNumber;
        }

        public int getStartPc() {
            return startPc;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }
}
