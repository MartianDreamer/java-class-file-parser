package com.github.martiandreamer;

import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Utils.parseInt;
import static com.github.martiandreamer.Annotation.ElementValuePair;

public class AnnotationParser extends Parser<Annotation>{
    private final ConstantInfo[] constantPool;

    public AnnotationParser(byte[] content, int from, ConstantInfo[] constantPool) {
        super(content, from);
        this.constantPool = constantPool;
    }

    @Override
    public Annotation parse() {
        int typeIndex = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ConstantPoolRef type = new ConstantPoolRef(typeIndex, constantPool);

        int elementValuePairCount = parseInt(content, current, HALF_SIZE);
        current += HALF_SIZE;
        ElementValuePair[] elementValuePairs = new ElementValuePair[elementValuePairCount];
        for (int i = 0; i < elementValuePairCount; i++) {
            Parser<ElementValuePair> elementValuePairParser = new ElementValuePairParser(content, current, constantPool);
            elementValuePairs[i] = elementValuePairParser.parse();
            current = elementValuePairParser.getCurrent();
        }
        return new Annotation(type, elementValuePairs);
    }
}
