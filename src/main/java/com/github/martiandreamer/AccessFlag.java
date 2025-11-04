package com.github.martiandreamer;

import java.util.ArrayList;
import java.util.List;

public enum AccessFlag {

    PUBLIC(0x0001), FINAL(0x0010), SUPER(0x0020), INTERFACE(0x0200), ABSTRACT(0x0400), SYNTHETIC(0x1000), ANNOTATION(0x2000), ENUM(0x4000), MODULE(0x8000);

    private final int VALUE;

    AccessFlag(int value) {
        VALUE = value;
    }

    public static List<AccessFlag> getFlags(int value) {
        AccessFlag[] accessFlags = values();
        List<AccessFlag> flags = new ArrayList<>(accessFlags.length);
        for (AccessFlag flag : accessFlags) {
            if ((flag.VALUE & value) != 0) {
                flags.add(flag);
            }
        }
        return flags;
    }
}
