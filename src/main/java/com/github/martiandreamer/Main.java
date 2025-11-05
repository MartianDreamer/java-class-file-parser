import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.martiandreamer.ClassFileParser;

void main(String[] args) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    if (args.length < 1) {
        System.err.println("Usage: Main <program>\nEnter class file path to parse.");
        System.exit(1);
        return;
    }
    final String pathString = args[args.length - 1];
    System.out.printf("Parsing file %s\n", pathString);
    final byte[] content;
    try {
        Path path = Paths.get(pathString);
        content = Files.readAllBytes(path);
    } catch (IOException e) {
        System.err.println("Error reading file " + pathString);
        System.exit(1);
        return;
    }
    ClassFileParser classFileParser = new ClassFileParser(content);
    System.out.println(objectMapper.writeValueAsString(classFileParser.parse()));
    System.exit(0);
}