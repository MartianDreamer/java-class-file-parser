package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantClassInfo;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantRef;

public record ClassInfo(String className, int major, int minor,
                        ConstantInfo[] constantPool,
                        AccessFlag[] accessFlags,
                        ConstantRef<ConstantClassInfo> thisClass,
                        ConstantRef<ConstantClassInfo> superClass,
                        ConstantRef<ConstantClassInfo>[] interfaces,
                        Field[] fields
) {
}
