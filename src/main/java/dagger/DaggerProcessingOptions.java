package dagger;

import com.google.common.annotations.GoogleInternal;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@GoogleInternal
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface DaggerProcessingOptions {

    public enum ValidationType {
        ERROR,
        WARNING,
        NONE
    }

    ValidationType fullBindingGraphValidation();
}
