package dagger.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface ModifiableBinding {
    String bindingRequest();

    String modifiableBindingType();

    String[] multibindingContributions() default {};
}
