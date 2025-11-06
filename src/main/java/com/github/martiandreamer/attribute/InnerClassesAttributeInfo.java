package com.github.martiandreamer.attribute;

import com.github.martiandreamer.AccessFlag;
import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class InnerClassesAttributeInfo extends AttributeInfo {

    private final ClassRecord[] classes;

    protected InnerClassesAttributeInfo(ConstantPoolRef attributeName, ClassRecord[] classes) {
        super(attributeName);
        this.classes = classes;
    }

    public ClassRecord[] getClasses() {
        return classes;
    }

    public static class ClassRecord {
        private final ConstantPoolRef innerClassInfo;
        private final ConstantPoolRef outerClassInfo;
        private final ConstantPoolRef name;
        private final AccessFlag[] accessFlags;

        public ClassRecord(ConstantPoolRef innerClassInfo, ConstantPoolRef outerClassInfo, ConstantPoolRef name, AccessFlag[] accessFlags) {
            if (innerClassInfo.getTag() != ConstantInfo.CLASS) {
                throw new InvalidClassFileFormatException("Inner class info index does not point to a CONSTANT_Class_info " + innerClassInfo);
            }
            if (outerClassInfo.getTag() != ConstantInfo.CLASS && outerClassInfo.getTag() != ConstantInfo.NULL) {
                throw new InvalidClassFileFormatException("Outer class info index does not point to a CONSTANT_Class_info " + outerClassInfo);
            }
            if (name.getTag() != ConstantInfo.UTF8) {
                throw new InvalidClassFileFormatException("Name index does not point to a CONSTANT_Utf8_info " + name);
            }
            this.innerClassInfo = innerClassInfo;
            this.outerClassInfo = outerClassInfo;
            this.name = name;
            this.accessFlags = accessFlags;
        }

        public ConstantPoolRef getInnerClassInfo() {
            return innerClassInfo;
        }

        public ConstantPoolRef getOuterClassInfo() {
            return outerClassInfo;
        }

        public ConstantPoolRef getName() {
            return name;
        }

        public AccessFlag[] getAccessFlags() {
            return accessFlags;
        }
    }
}
