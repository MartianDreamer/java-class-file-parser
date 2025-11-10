/**
 * RuntimeAnnotationStressTest.java
 *
 * Stress-test for:
 *   RuntimeVisibleAnnotations
 *   RuntimeInvisibleAnnotations
 *   RuntimeVisibleTypeAnnotations
 *   RuntimeInvisibleTypeAnnotations
 *
 * Compile:
 *   javac --release 25 RuntimeAnnotationStressTest.java
 *
 * Inspect:
 *   javap -v -p RuntimeAnnotationStressTest.class | grep -A6 -B2 "Annotations"
 */
import java.lang.annotation.*;
import java.util.List;

// ---------------------------------------------------------------------
// 1. Enums used inside annotations
// ---------------------------------------------------------------------
enum Priority { LOW, MEDIUM, HIGH }
enum Scope { SINGLETON, PROTOTYPE }

// ---------------------------------------------------------------------
// 2. Nested annotation
// ---------------------------------------------------------------------
@Retention(RetentionPolicy.RUNTIME)
@interface Author {
    String name();
    String email() default "";
}

// ---------------------------------------------------------------------
// 3. Helper annotation (visible + invisible)
// ---------------------------------------------------------------------
@Retention(RetentionPolicy.RUNTIME)
@interface Meta {
    String value() default "";
    int[] ids() default {};
    Class<?> clazz() default Object.class;
    Priority priority() default Priority.MEDIUM;
    Author author() default @Author(name = "Anonymous");
}

// ---------------------------------------------------------------------
// 4. RuntimeVisible annotation
// ---------------------------------------------------------------------
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@interface Visible {
    String desc();
    boolean active() default true;
    String[] tags() default {};
    Scope scope() default Scope.SINGLETON;
    Meta meta() default @Meta(value = "default");
}

// ---------------------------------------------------------------------
// 5. RuntimeInvisible annotation
// ---------------------------------------------------------------------
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@interface Invisible {
    String reason();
    double weight() default 1.0;
    byte flag() default 0;
    char symbol() default '*';
    long timestamp() default 0L;
    float precision() default 0.0f;
    short code() default 0;
    boolean debug() default false;
    Class<?> target() default void.class;
    String[] notes() default {};
    Meta meta() default @Meta(value = "hidden");
}

// ---------------------------------------------------------------------
// 6. The class under test
// ---------------------------------------------------------------------
@Visible(
        desc = "Stress test for RuntimeVisibleAnnotations",
        tags = {"parser", "jvm", "annotation"},
        meta = @Meta(
                value = "main",
                ids = {1, 2, 3},
                priority = Priority.HIGH,
                author = @Author(name = "JVM", email = "jvm@oracle.com")
        )
)
@Invisible(
        reason = "Internal debugging only",
        weight = 9.99,
        flag = 0x1F,
        symbol = '\u2620',
        timestamp = 1731222720000L,          // compile-time constant
        precision = 0.0001f,
        code = 404,
        debug = true,
        target = RuntimeAnnotationStressTest.class,
        notes = {"do not remove", "internal use"},
        meta = @Meta(value = "secret", priority = Priority.LOW)
)
public class RuntimeAnnotationStressTest {

    // -----------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------
    @Visible(desc = "Visible field", tags = {"field", "test"})
    @Invisible(reason = "legacy", debug = true)
    private String name;

    @Visible(desc = "Type-use visible", scope = Scope.PROTOTYPE)
    @Invisible(reason = "type-use hidden")
    private List<@Visible(desc = "type param visible")
    @Invisible(reason = "type param hidden") String> items;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
    public RuntimeAnnotationStressTest(
            @Visible(desc = "param visible", active = false)
            @Invisible(reason = "param invisible", code = 100)
            String input
    ) {
        this.name = input;
    }

    // -----------------------------------------------------------------
    // Method with many annotations
    // -----------------------------------------------------------------
    @Visible(
            desc = "Visible method",
            tags = {"method", "stress"},
            meta = @Meta(value = "method", author = @Author(name = "Parser"))
    )
    @Invisible(
            reason = "profiling",
            timestamp = 1731222720000L,          // constant literal
            notes = {"slow", "optimize"}
    )
    public void process(
            @Visible(desc = "input param")
            @Invisible(reason = "trace")
            Object data,
            // ---- synthetic parameter to force a LocalVariableTable entry ----
            @Visible(desc = "local-var visible (via synthetic param)")
            @Invisible(reason = "local-var invisible (via synthetic param)")
            @SuppressWarnings("unused") String syntheticLocal
    ) {
        // The synthetic parameter appears in the LocalVariableTable,
        // so the type-annotations end up in Runtime*TypeAnnotations.
        System.out.println(name + " " + data.hashCode());
    }

    // -----------------------------------------------------------------
    // Type-use annotations on instanceof and cast
    // Desert
    // -----------------------------------------------------------------
    public boolean check(Object obj) {
        if (obj instanceof @Visible(desc = "instanceof visible")
        @Invisible(reason = "instanceof hidden") String s) {
            return ((@Visible(desc = "cast visible")
            @Invisible(reason = "cast hidden") String) obj).isEmpty();
        }
        return false;
    }

    // -----------------------------------------------------------------
    // main – trigger code generation
    // -----------------------------------------------------------------
    public static void main(String[] args) {
        var test = new RuntimeAnnotationStressTest("stress");
        test.process("data", "unused");   // synthetic param is ignored
        System.out.println(test.check("hello"));
        System.out.println("Runtime annotation stress test completed.");
    }
}