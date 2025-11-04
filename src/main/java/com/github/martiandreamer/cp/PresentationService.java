package com.github.martiandreamer.cp;

import com.github.martiandreamer.ClassInfo;

public class PresentationService {
    public String present(ClassInfo classInfo) {
        StringBuilder sb = new StringBuilder().append("# ")
                .append(classInfo.className())
                .append("\n\n")
                .append("Version: ")
                .append(classInfo.major())
                .append(".")
                .append(classInfo.minor())
                .append("\n");
        return sb.toString();
    }
}
