package com.github.martiandreamer;

import com.github.martiandreamer.cp.PresentationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.github.martiandreamer.Constant.HALF_SIZE;
import static com.github.martiandreamer.Utils.isClassFile;
import static com.github.martiandreamer.Utils.parseInt;

public class Main {
    static void main(String[] args) {
        final PresentationService presentationService = new PresentationService();
        if (args.length < 1) {
            System.err.println("Usage: Main <program>\nEnter class file path to parse.");
            System.exit(1);
            return;
        }
        final String pathString = args[args.length - 1];
        System.out.printf("Parsing file %s\n", pathString);
        final byte[] content;
        final String className;
        try {
            Path path = Paths.get(pathString);
            className = path.getFileName().toString();
            content = Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Error reading file " + pathString);
            System.exit(1);
            return;
        }
        if (!isClassFile(content)) {
            System.err.println("Given file is not a class file");
            System.exit(1);
            return;
        }
        final ClassInfo classInfo = new ClassInfo(className, parseInt(content, 6, HALF_SIZE), parseInt(content, 4, HALF_SIZE), List.of());
        System.out.println(presentationService.present(classInfo));
    }
}
