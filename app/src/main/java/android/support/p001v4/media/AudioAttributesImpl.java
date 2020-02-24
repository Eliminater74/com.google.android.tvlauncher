package android.support.p001v4.media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import androidx.versionedparcelable.VersionedParcelable;

/* renamed from: android.support.v4.media.AudioAttributesImpl */
interface AudioAttributesImpl extends VersionedParcelable {

    /* renamed from: android.support.v4.media.AudioAttributesImpl$Builder */
    public interface Builder {
        AudioAttributesImpl build();

        Builder setContentType(int i);

        Builder setFlags(int i);

        Builder setLegacyStreamType(int i);

        Builder setUsage(int i);
    }

    Object getAudioAttributes();

    int getContentType();

    int getFlags();

    int getLegacyStreamType();

    int getRawLegacyStreamType();

    int getUsage();

    int getVolumeControlStream();

    @NonNull
    Bundle toBundle();
}
