package com.google.android.libraries.performance.primes.transmitter.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Qualifier
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface ClearcutLogSource {
}
