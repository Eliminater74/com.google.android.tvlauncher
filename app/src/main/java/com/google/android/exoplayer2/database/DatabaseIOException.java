package com.google.android.exoplayer2.database;

import android.database.SQLException;
import java.io.IOException;

public final class DatabaseIOException extends IOException {
    public DatabaseIOException(SQLException cause) {
        super(cause);
    }

    public DatabaseIOException(SQLException cause, String message) {
        super(message, cause);
    }
}
