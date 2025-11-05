package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantClassInfo;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantRef;

import java.util.List;

public record ClassInfo(String className, int major, int minor,
                        ConstantInfo[] constantPool,
                        List<AccessFlag> accessFlags,
                        ConstantRef<ConstantClassInfo> thisClass,
                        ConstantRef<ConstantClassInfo> superClass,
                        ConstantRef<ConstantClassInfo>[] interfaces
) {
}
