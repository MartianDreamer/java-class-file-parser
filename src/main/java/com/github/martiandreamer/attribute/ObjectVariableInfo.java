package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantClassInfo;
import com.github.martiandreamer.cp.ConstantRef;

public class ObjectVariableInfo extends VariableInfo {
    private final ConstantRef<ConstantClassInfo> cpoolIndex;

    public ObjectVariableInfo(ConstantRef<ConstantClassInfo> cpoolIndex) {
        super(ITEM_Object);
        this.cpoolIndex = cpoolIndex;
    }

    public ConstantRef<ConstantClassInfo> getCpoolIndex() {
        return cpoolIndex;
    }
}
