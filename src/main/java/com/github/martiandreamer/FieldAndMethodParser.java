package com.github.martiandreamer;

import com.github.martiandreamer.attribute.AttributeInfo;
import com.github.martiandreamer.attribute.AttributeParser;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantRef;
import com.github.martiandreamer.cp.ConstantUtf8Info;

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
    public FieldAndMethod[] parse() throws InvalidClassFileFormatException {
        if (result != null) {
            return result;
        }
        int count = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        FieldAndMethod[] fields = new FieldAndMethod[count];
        for (int i = 0; i < count; i++) {
            fields[i] = parseField();
        }
        this.result = fields;
        return result;
    }

    private FieldAndMethod parseField() throws InvalidClassFileFormatException {
        int accessFlagVal = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        AccessFlag[] accessFlags = AccessFlag.getFlags(accessFlagVal);
        int nameIndexVal = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantRef<ConstantUtf8Info> nameIndex = new ConstantRef<>(nameIndexVal, constantPool, ConstantUtf8Info.class);
        int descriptorIndexVal = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantRef<ConstantUtf8Info> descriptorIndex = new ConstantRef<>(descriptorIndexVal, constantPool, ConstantUtf8Info.class);
        AttributeParser attributeParser = new AttributeParser(content, current, constantPool);
        AttributeInfo[] attributes = attributeParser.parse();
        current = attributeParser.getCurrent();
        return new FieldAndMethod(accessFlags, nameIndex, descriptorIndex, attributes);
    }
}
