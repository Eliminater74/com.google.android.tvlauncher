package com.google.android.libraries.performance.primes;

import android.support.annotation.NonNull;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackTraceSanitizer {
    private static final String getStackTracePattern() {
        return "([^:^\n]+).*((?:\n\\s*at [^:~\n]*:?~?[0-9]*[^\n]*)+)(?:(\nCaused by: )([^:^\n]+).*((?:\n\\s*at [^:~\n]*:?~?[0-9]*[^\n]*)+))?(?:(\nCaused by: )([^:^\n]+).*((?:\n\\s*at [^:~\n]*:?~?[0-9]*[^\n]*)+))?";
    }

    private static final String getStackTraceString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        ThrowableExtension.printStackTrace(throwable, new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String getSanitizedStackTrace(@NonNull Throwable throwable) {
        return getSanitizedStackTrace(getStackTraceString(throwable));
    }

    public static String getSanitizedStackTrace(String stackTraceString) {
        StringBuilder sanitizedTraceBuilder = new StringBuilder();
        Matcher matcher = Pattern.compile(getStackTracePattern()).matcher(stackTraceString);
        if (matcher.find()) {
            int groupIndex = 1;
            while (groupIndex <= matcher.groupCount() && matcher.group(groupIndex) != null) {
                sanitizedTraceBuilder.append(matcher.group(groupIndex));
                groupIndex++;
            }
        }
        return sanitizedTraceBuilder.toString();
    }
}
