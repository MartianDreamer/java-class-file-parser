package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantInfo;

import java.util.List;

public record ClassInfo(String className, int major, int minor, List<ConstantInfo> constantPool) {
}
