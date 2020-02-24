package dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    Class<?> addsTo() default Void.class;

    boolean complete() default true;

    Class<?>[] includes() default {};

    Class<?>[] injects() default {};

    boolean library() default true;

    boolean overrides() default false;

    Class<?>[] staticInjections() default {};

    Class<?>[] subcomponents() default {};
}
