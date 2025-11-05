package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public record ClassInfo(String className, int major, int minor,
                        ConstantInfo[] constantPool,
                        AccessFlag[] accessFlags,
                        ConstantPoolRef thisClass,
                        ConstantPoolRef superClass,
                        ConstantPoolRef[] interfaces,
                        FieldAndMethod[] fields,
                        FieldAndMethod[] methods
) {
}
