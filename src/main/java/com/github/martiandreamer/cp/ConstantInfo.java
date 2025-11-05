package com.github.martiandreamer.cp;

public interface ConstantInfo {
    short UTF8 = 1;
    short INTEGER = 3;
    short FLOAT = 4;
    short LONG = 5;
    short DOUBLE = 6;
    short CLASS = 7;
    short STRING = 8;
    short FIELD_REF = 9;
    short METHOD_REF = 10;
    short INTERFACE_METHOD_REF = 11;
    short NAME_AND_TYPE = 12;
    short METHOD_HANDLE = 15;
    short METHOD_TYPE = 16;
    short DYNAMIC = 17;
    short INVOKE_DYNAMIC = 18;
    short MODULE = 19;
    short PACKAGE = 20;

    short getTag();
    String getConstantType();

    default int size() {
        return 1;
    }

}
