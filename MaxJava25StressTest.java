/**
 * MaxJava25StressTest – SINGLE FILE, FULLY SELF-CONTAINED, NO PACKAGE
 *
 * This version **removes the `package` declaration** so it compiles from any directory.
 * All classes are in the default  **default package** — perfect for testing your parser.
 *
 * Save this file as: `MaxJava25StressTest.java`
 *
 * Compile & run:
 *     javac --release 25 MaxJava25StressTest.java
 *     java MaxJava25StressTest
 */
import java.io.*;
import java.lang.annotation.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

// ===================================================================
// 1. ANNOTATIONS
// ===================================================================
@Retention(RetentionPolicy.RUNTIME)
@interface Debug { boolean value() default true; }

@Debug
class PackageAnnotationHolder {}

// ===================================================================
// 2. SUPPORTING TYPES
// ===================================================================
enum Color { RED, GREEN, BLUE }
enum Gender { MALE, FEMALE, OTHER }
enum Scope { SINGLETON, PROTOTYPE }

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@interface NonNull {}

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Metas.class)
@interface Meta {
    String value() default "";
    int[] ints() default {};
    Class<?> clazz() default void.class;
    Color color() default Color.RED;
}

@Retention(RetentionPolicy.RUNTIME)
@interface Metas { Meta[] value(); }

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
@interface Column {
    String name();
    String type() default "VARCHAR";
    boolean nullable() default true;
}

// ===================================================================
// 3. MAIN CLASS – SEALED, GENERIC, ANNOTATED
// ===================================================================
@Meta(value = "stress", color = Color.BLUE)
@SuppressWarnings("all")
@Deprecated(since = "1.0")
public sealed class MaxJava25StressTest<T extends Number & Serializable>
        permits FinalChild, NonSealedChild {

    // FIELDS
    public static final double PI = 3.14159_26535;
    private volatile String state = "init";
    protected List<@NonNull String> names = new ArrayList<>();
    @Column(name = "data", nullable = false)
    final byte[] buffer = {0x00, (byte)0xFF};
    private final Map<String, String> config = Map.of("mode", "max");

    // CONSTRUCTORS
    public MaxJava25StressTest(T value) {
        this.state = value.toString();
    }

    // Initializers
    { state += "-instance"; }
    static { System.out.println("Static init"); }

    // METHODS
    public <K, V> Map<K, V> merge(Map<K, V> a, Map<K, V> b) {
        return Stream.concat(a.entrySet().stream(), b.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));
    }

    @SafeVarargs
    public final void varargs(String... args) {
        Arrays.stream(args).forEach(System.out::println);
    }

    public void lambdas() {
        Runnable r = () -> System.out.println("lambda");
        r.run();
    }

    public void local() {
        class Local { void run() { System.out.println("local"); } }
        new Local().run();
    }

    public String describe(Object o) {
        return switch (o) {
            case null -> "null";
            case String s -> "string:" + s.length();
            case Integer i -> "int:" + i;
            case List<?> l -> "list:" + l.size();
            default -> "other";
        };
    }

    public String doc() {
        return """
            Multi-line
            "text block"
            with \t tabs
            """;
    }

    public void resources() throws IOException {
        try (var in = new StringReader("data");
             var out = new PrintWriter(System.out)) {
            out.println(in.read());
        }
    }

    record Pair(Object a, Object b) {}
    boolean same(Pair p) {
        return p instanceof Pair(var x, var y) && Objects.equals(x, y);
    }

    // MAIN
    public static void main(String[] args) throws Exception {
        System.out.println("=== Java 25 Stress Test ===");
        var test = new MaxJava25StressTest<>(42);
        System.out.println("State: " + test.state);
        System.out.println(test.describe("hello"));
        System.out.println(test.doc());
        test.varargs("a", "b");
        test.lambdas();
        test.local();
        test.resources();
        System.out.println(test.same(new Pair(1, 1)));
    }
}

// ===================================================================
// 4. SEALED HIERARCHY
// ===================================================================
non-sealed class NonSealedChild extends MaxJava25StressTest<Integer> {
    public NonSealedChild() { super(0); }
}

final class FinalChild extends MaxJava25StressTest<Double> {
    public FinalChild() { super(0.0); }
}