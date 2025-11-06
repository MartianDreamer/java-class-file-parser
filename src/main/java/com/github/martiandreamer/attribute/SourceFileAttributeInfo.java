package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class SourceFileAttributeInfo extends AttributeInfo {
    private final ConstantPoolRef sourceFile;

    protected SourceFileAttributeInfo(ConstantPoolRef attributeName, ConstantPoolRef sourceFile) {
        super(attributeName);
        if (sourceFile.getTag() != ConstantInfo.UTF8) {
            throw new InvalidClassFileFormatException("sourcefile index must point to CONSTANT_Utf8_info " + sourceFile);
        }
        this.sourceFile = sourceFile;
    }

    public ConstantPoolRef getSourceFile() {
        return sourceFile;
    }
}
