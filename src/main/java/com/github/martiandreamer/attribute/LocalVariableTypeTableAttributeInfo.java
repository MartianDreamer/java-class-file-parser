package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class LocalVariableTypeTableAttributeInfo extends AttributeInfo {

    private final LocalVariableTypeTableRecord[] localVariableTypeTable;

    protected LocalVariableTypeTableAttributeInfo(ConstantPoolRef attributeName, LocalVariableTypeTableRecord[] localVariableTypeTable) {
        super(attributeName);
        this.localVariableTypeTable = localVariableTypeTable;
    }

    public LocalVariableTypeTableRecord[] getLocalVariableTypeTable() {
        return localVariableTypeTable;
    }

    public static class LocalVariableTypeTableRecord {
        private final int startPC;
        private final int length;
        private final ConstantPoolRef name;
        private final ConstantPoolRef signature;
        private final int index;

        public LocalVariableTypeTableRecord(int startPC, int length, ConstantPoolRef name, ConstantPoolRef signature, int index) {
            if (name.getTag() != ConstantInfo.UTF8) {
                throw new InvalidClassFileFormatException("LocalVariableTypeTableRecord: name should be UTF8 " + name);
            }
            if (signature.getTag() != ConstantInfo.UTF8) {
                throw new InvalidClassFileFormatException("LocalVariableTypeTableRecord: signature should be UTF8 " + signature);
            }
            this.startPC = startPC;
            this.length = length;
            this.name = name;
            this.signature = signature;
            this.index = index;
        }

        public int getStartPC() {
            return startPC;
        }

        public int getLength() {
            return length;
        }

        public ConstantPoolRef getName() {
            return name;
        }

        public ConstantPoolRef getSignature() {
            return signature;
        }

        public int getIndex() {
            return index;
        }
    }
}
