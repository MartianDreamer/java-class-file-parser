package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantClassInfo;
import com.github.martiandreamer.cp.ConstantPool;

import java.util.List;

public record ClassInfo(String className, int major, int minor,
                        ConstantPool constantPool,
                        List<AccessFlag> accessFlags,
                        ConstantClassInfo thisClass,
                        ConstantClassInfo superClass) {
}
