package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantClassInfo;
import com.github.martiandreamer.cp.ConstantRef;

public class VariableInfo {
    private static final String[] names = new String[]{
            "Top_variable_info",
            "Integer_variable_info",
            "Float_variable_info",
            "Double_variable_info",
            "Long_variable_info",
            "Null_variable_info",
            "UninitializedThis_variable_info",
            "Object_variable_info",
            "Uninitialized_variable_info",
    };
    public static final short ITEM_Top = 0;
    public static final short ITEM_Integer = 1;
    public static final short ITEM_Float = 2;
    public static final short ITEM_Double = 3;
    public static final short ITEM_Long = 4;
    public static final short ITEM_Null = 5;
    public static final short ITEM_UninitializedThis = 6;
    public static final short ITEM_Object = 7;
    public static final short ITEM_Uninitialized = 8;

    private final short tag;
    private final int offset;
    private final ConstantRef<ConstantClassInfo> cpoolIndex;

    public VariableInfo(short tag, int offset, ConstantRef<ConstantClassInfo> cpoolIndex) {
        this.tag = tag;
        this.offset = offset;
        this.cpoolIndex = cpoolIndex;
    }

    public String getName(){
        return names[tag];
    }

    public int getOffset(){
        return offset;
    }

    public ConstantRef<ConstantClassInfo> getCpoolIndex(){
        return cpoolIndex;
    }
}
