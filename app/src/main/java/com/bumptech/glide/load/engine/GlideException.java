package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GlideException extends Exception {
    private static final StackTraceElement[] EMPTY_ELEMENTS = new StackTraceElement[0];
    private static final long serialVersionUID = 1;
    private final List<Throwable> causes;
    private Class<?> dataClass;
    private DataSource dataSource;
    private String detailMessage;
    @Nullable
    private Exception exception;
    private Key key;

    public GlideException(String message) {
        this(message, Collections.emptyList());
    }

    public GlideException(String detailMessage2, Throwable cause) {
        this(detailMessage2, Collections.singletonList(cause));
    }

    public GlideException(String detailMessage2, List<Throwable> causes2) {
        this.detailMessage = detailMessage2;
        setStackTrace(EMPTY_ELEMENTS);
        this.causes = causes2;
    }

    /* access modifiers changed from: package-private */
    public void setLoggingDetails(Key key2, DataSource dataSource2) {
        setLoggingDetails(key2, dataSource2, null);
    }

    /* access modifiers changed from: package-private */
    public void setLoggingDetails(Key key2, DataSource dataSource2, Class<?> dataClass2) {
        this.key = key2;
        this.dataSource = dataSource2;
        this.dataClass = dataClass2;
    }

    public void setOrigin(@Nullable Exception exception2) {
        this.exception = exception2;
    }

    @Nullable
    public Exception getOrigin() {
        return this.exception;
    }

    public Throwable fillInStackTrace() {
        return this;
    }

    public List<Throwable> getCauses() {
        return this.causes;
    }

    public List<Throwable> getRootCauses() {
        List<Throwable> rootCauses = new ArrayList<>();
        addRootCauses(this, rootCauses);
        return rootCauses;
    }

    public void logRootCauses(String tag) {
        List<Throwable> causes2 = getRootCauses();
        int size = causes2.size();
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder(39);
            sb.append("Root cause (");
            sb.append(i + 1);
            sb.append(" of ");
            sb.append(size);
            sb.append(")");
            Log.i(tag, sb.toString(), causes2.get(i));
        }
    }

    private void addRootCauses(Throwable throwable, List<Throwable> rootCauses) {
        if (throwable instanceof GlideException) {
            for (Throwable t : ((GlideException) throwable).getCauses()) {
                addRootCauses(t, rootCauses);
            }
            return;
        }
        rootCauses.add(throwable);
    }

    public void printStackTrace() {
        ThrowableExtension.printStackTrace(this, System.err);
    }

    public void printStackTrace(PrintStream err) {
        printStackTrace((Appendable) err);
    }

    public void printStackTrace(PrintWriter err) {
        printStackTrace((Appendable) err);
    }

    private void printStackTrace(Appendable appendable) {
        appendExceptionMessage(this, appendable);
        appendCauses(getCauses(), new IndentedAppendable(appendable));
    }

    public String getMessage() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder(71);
        sb.append(this.detailMessage);
        Class<?> cls = this.dataClass;
        String str3 = "";
        if (cls != null) {
            String valueOf = String.valueOf(cls);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 2);
            sb2.append(", ");
            sb2.append(valueOf);
            str = sb2.toString();
        } else {
            str = str3;
        }
        sb.append(str);
        DataSource dataSource2 = this.dataSource;
        if (dataSource2 != null) {
            String valueOf2 = String.valueOf(dataSource2);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 2);
            sb3.append(", ");
            sb3.append(valueOf2);
            str2 = sb3.toString();
        } else {
            str2 = str3;
        }
        sb.append(str2);
        Key key2 = this.key;
        if (key2 != null) {
            String valueOf3 = String.valueOf(key2);
            StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf3).length() + 2);
            sb4.append(", ");
            sb4.append(valueOf3);
            str3 = sb4.toString();
        }
        StringBuilder result = sb.append(str3);
        List<Throwable> rootCauses = getRootCauses();
        if (rootCauses.isEmpty()) {
            return result.toString();
        }
        if (rootCauses.size() == 1) {
            result.append("\nThere was 1 cause:");
        } else {
            result.append("\nThere were ");
            result.append(rootCauses.size());
            result.append(" causes:");
        }
        for (Throwable cause : rootCauses) {
            result.append(10);
            result.append(cause.getClass().getName());
            result.append('(');
            result.append(cause.getMessage());
            result.append(')');
        }
        result.append("\n call GlideException#logRootCauses(String) for more detail");
        return result.toString();
    }

    private static void appendExceptionMessage(Throwable t, Appendable appendable) {
        try {
            appendable.append(t.getClass().toString()).append(": ").append(t.getMessage()).append(10);
        } catch (IOException e) {
            throw new RuntimeException(t);
        }
    }

    private static void appendCauses(List<Throwable> causes2, Appendable appendable) {
        try {
            appendCausesWrapped(causes2, appendable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void appendCausesWrapped(List<Throwable> causes2, Appendable appendable) throws IOException {
        int size = causes2.size();
        for (int i = 0; i < size; i++) {
            appendable.append("Cause (").append(String.valueOf(i + 1)).append(" of ").append(String.valueOf(size)).append("): ");
            Throwable cause = causes2.get(i);
            if (cause instanceof GlideException) {
                ((GlideException) cause).printStackTrace(appendable);
            } else {
                appendExceptionMessage(cause, appendable);
            }
        }
    }

    private static final class IndentedAppendable implements Appendable {
        private static final String EMPTY_SEQUENCE = "";
        private static final String INDENT = "  ";
        private final Appendable appendable;
        private boolean printedNewLine = true;

        IndentedAppendable(Appendable appendable2) {
            this.appendable = appendable2;
        }

        public Appendable append(char c) throws IOException {
            boolean z = false;
            if (this.printedNewLine) {
                this.printedNewLine = false;
                this.appendable.append(INDENT);
            }
            if (c == 10) {
                z = true;
            }
            this.printedNewLine = z;
            this.appendable.append(c);
            return this;
        }

        public Appendable append(@Nullable CharSequence charSequence) throws IOException {
            CharSequence charSequence2 = safeSequence(charSequence);
            return append(charSequence2, 0, charSequence2.length());
        }

        public Appendable append(@Nullable CharSequence charSequence, int start, int end) throws IOException {
            CharSequence charSequence2 = safeSequence(charSequence);
            boolean z = false;
            if (this.printedNewLine) {
                this.printedNewLine = false;
                this.appendable.append(INDENT);
            }
            if (charSequence2.length() > 0 && charSequence2.charAt(end - 1) == 10) {
                z = true;
            }
            this.printedNewLine = z;
            this.appendable.append(charSequence2, start, end);
            return this;
        }

        @NonNull
        private CharSequence safeSequence(@Nullable CharSequence sequence) {
            if (sequence == null) {
                return "";
            }
            return sequence;
        }
    }
}
