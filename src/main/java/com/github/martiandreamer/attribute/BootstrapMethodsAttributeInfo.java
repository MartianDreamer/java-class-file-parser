package com.github.martiandreamer.attribute;

import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.cp.ConstantInfo;
import com.github.martiandreamer.cp.ConstantPoolRef;

import java.util.Arrays;
import java.util.stream.Stream;

public class BootstrapMethodsAttributeInfo extends AttributeInfo {
    private final BootstrapMethod[] bootstrapMethods;
    protected BootstrapMethodsAttributeInfo(ConstantPoolRef attributeName, BootstrapMethod[] bootstrapMethods) {
        super(attributeName);
        this.bootstrapMethods = bootstrapMethods;
    }

    public BootstrapMethod[] getBootstrapMethods() {
        return bootstrapMethods;
    }

    public static class BootstrapMethod {
        private final ConstantPoolRef bootstrapMethodRef;
        private final ConstantPoolRef[] bootstrapArguments;

        public BootstrapMethod(ConstantPoolRef bootstrapMethodRef, ConstantPoolRef[] bootstrapArguments) {
            if (bootstrapMethodRef == null || bootstrapMethodRef.getTag() != ConstantInfo.METHOD_HANDLE) {
                throw new InvalidClassFileFormatException("bootstrapMethodRef must be a CONSTANT_MethodHandle_info " + bootstrapMethodRef);
            }
            if (bootstrapArguments == null ||
                    Stream.of(bootstrapArguments).map(
                            e -> e.getTag() != ConstantInfo.INTEGER &&
                                    e.getTag() != ConstantInfo.FLOAT &&
                                    e.getTag() != ConstantInfo.LONG &&
                                    e.getTag() != ConstantInfo.DOUBLE &&
                                    e.getTag() != ConstantInfo.CLASS &&
                                    e.getTag() != ConstantInfo.STRING &&
                                    e.getTag() != ConstantInfo.METHOD_HANDLE &&
                                    e.getTag() != ConstantInfo.METHOD_TYPE &&
                                    e.getTag() != ConstantInfo.DYNAMIC
                            )
                            .reduce(Boolean.FALSE, Boolean::logicalOr)
            ) {
                throw new InvalidClassFileFormatException("bootstrapArguments is invalid " + Arrays.toString(bootstrapArguments));
            }
            this.bootstrapMethodRef = bootstrapMethodRef;
            this.bootstrapArguments = bootstrapArguments;
        }

        public ConstantPoolRef getBootstrapMethodRef() {
            return bootstrapMethodRef;
        }

        public ConstantPoolRef[] getBootstrapArguments() {
            return bootstrapArguments;
        }
    }
}
