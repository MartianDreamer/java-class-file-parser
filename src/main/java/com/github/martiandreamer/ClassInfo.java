package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantPool;

public record ClassInfo(String className, int major, int minor, ConstantPool constantPool) {
}
