package dagger.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface ConfigureInitializationParameters {
    String[] value() default {};
}
