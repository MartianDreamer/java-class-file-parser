package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class Enum {
    private final ConstantPoolRef typeName;
    private final ConstantPoolRef constName;

    public Enum(ConstantPoolRef typeName, ConstantPoolRef constName) {
        if (typeName.getTag() != ConstantInfo.UTF8 || constName.getTag() != ConstantInfo.UTF8) {
            throw new InvalidClassFileFormatException("TypeName and ConstName should be UTF8 " + typeName + constName);
        }
        this.typeName = typeName;
        this.constName = constName;
    }

    public ConstantPoolRef getTypeName() {
        return typeName;
    }

    public ConstantPoolRef getConstName() {
        return constName;
    }
}
