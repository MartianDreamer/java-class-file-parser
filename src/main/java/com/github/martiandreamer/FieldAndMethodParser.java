package com.github.martiandreamer;

import com.github.martiandreamer.attribute.AttributeInfo;
import com.github.martiandreamer.attribute.AttributeParser;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Utils.parseInt;

public class FieldAndMethodParser extends Parser<FieldAndMethod[]> {
    private final ConstantInfo[] constantPool;
    private FieldAndMethod[] result;

    protected FieldAndMethodParser(byte[] content, int from, ConstantInfo[] constantPool) {
        super(content, from);
        this.constantPool = constantPool;
    }

    @Override
    public FieldAndMethod[] parse() {
        if (result != null) {
            return result;
        }
        int count = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        result = new FieldAndMethod[count];
        for (int i = 0; i < count; i++) {
            result[i] = parseField();
        }
        return result;
    }

    private FieldAndMethod parseField() {
        int accessFlagVal = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        AccessFlag[] accessFlags = AccessFlag.getFlags(accessFlagVal);
        int nameIndexVal = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef nameIndex = new ConstantPoolRef(nameIndexVal, constantPool);
        int descriptorIndexVal = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef descriptorIndex = new ConstantPoolRef(descriptorIndexVal, constantPool);

        // Parse attributes
        AttributeParser attributeParser = new AttributeParser(content, current, constantPool);
        AttributeInfo[] attributes = attributeParser.parse();
        current = attributeParser.getCurrent();
        return new FieldAndMethod(accessFlags, nameIndex, descriptorIndex, attributes);
    }
}
