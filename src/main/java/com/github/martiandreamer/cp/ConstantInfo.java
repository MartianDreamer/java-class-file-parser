package com.github.martiandreamer.cp;

public abstract class ConstantInfo {
    public static final short UTF8 = 1;
    public static final short INTEGER = 3;
    public static final short FLOAT = 4;
    public static final short LONG = 5;
    public static final short DOUBLE = 6;
    public static final short CLASS = 7;
    public static final short STRING = 8;
    public static final short FIELD_REF = 9;
    public static final short METHOD_REF = 10;
    public static final short INTERFACE_METHOD_REF = 11;
    public static final short NAME_AND_TYPE = 12;
    public static final short METHOD_HANDLE = 15;
    public static final short METHOD_TYPE = 16;
    public static final short DYNAMIC = 17;
    public static final short INVOKE_DYNAMIC = 18;
    public static final short MODULE = 19;
    public static final short PACKAGE = 20;

    protected final short tag;

    protected ConstantInfo(final short tag) {
        this.tag = tag;
    }

    public short getTag() {
        return tag;
    }

    public int size() {
        return 1;
    }

    public abstract String getName();
}
