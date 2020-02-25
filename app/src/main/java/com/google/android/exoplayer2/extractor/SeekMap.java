package com.google.android.exoplayer2.extractor;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;

public interface SeekMap {
    long getDurationUs();

    SeekPoints getSeekPoints(long j);

    boolean isSeekable();

    public static final class Unseekable implements SeekMap {
        private final long durationUs;
        private final SeekPoints startSeekPoints;

        public Unseekable(long durationUs2) {
            this(durationUs2, 0);
        }

        public Unseekable(long durationUs2, long startPosition) {
            this.durationUs = durationUs2;
            this.startSeekPoints = new SeekPoints(startPosition == 0 ? SeekPoint.START : new SeekPoint(0, startPosition));
        }

        public boolean isSeekable() {
            return false;
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public SeekPoints getSeekPoints(long timeUs) {
            return this.startSeekPoints;
        }
    }

    public static final class SeekPoints {
        public final SeekPoint first;
        public final SeekPoint second;

        public SeekPoints(SeekPoint point) {
            this(point, point);
        }

        public SeekPoints(SeekPoint first2, SeekPoint second2) {
            this.first = (SeekPoint) Assertions.checkNotNull(first2);
            this.second = (SeekPoint) Assertions.checkNotNull(second2);
        }

        public String toString() {
            String str;
            String valueOf = String.valueOf(this.first);
            if (this.first.equals(this.second)) {
                str = "";
            } else {
                String valueOf2 = String.valueOf(this.second);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 2);
                sb.append(", ");
                sb.append(valueOf2);
                str = sb.toString();
            }
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 2 + String.valueOf(str).length());
            sb2.append("[");
            sb2.append(valueOf);
            sb2.append(str);
            sb2.append("]");
            return sb2.toString();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SeekPoints other = (SeekPoints) obj;
            if (!this.first.equals(other.first) || !this.second.equals(other.second)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.first.hashCode() * 31) + this.second.hashCode();
        }
    }
}
