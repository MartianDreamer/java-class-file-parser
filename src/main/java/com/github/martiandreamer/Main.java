import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.martiandreamer.InvalidClassFileFormatException;
import com.github.martiandreamer.Parser;

void main(String[] args) throws InvalidClassFileFormatException, JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
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
    Parser parser = new Parser(className, content);
    System.out.println(objectMapper.writeValueAsString(parser.parse()));
}