package com.github.martiandreamer.attribute;

import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

public class SignatureAttributeInfo extends AttributeInfo {
    private final ConstantPoolRef signature;

    protected SignatureAttributeInfo(ConstantPoolRef attributeName, ConstantPoolRef signature) {
        super(attributeName);
        if (signature.getTag() != ConstantInfo.UTF8) {
            throw new IllegalArgumentException("Signature attributes must have UTF8 tag " + signature);
        }
        this.signature = signature;
    }

    public ConstantPoolRef getSignature() {
        return signature;
    }
}
