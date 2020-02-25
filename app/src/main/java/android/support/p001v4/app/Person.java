package android.support.p001v4.app;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.graphics.drawable.IconCompat;

import androidx.versionedparcelable.VersionedParcelable;

/* renamed from: android.support.v4.app.Person */
public class Person implements VersionedParcelable {
    private static final String ICON_KEY = "icon";
    private static final String IS_BOT_KEY = "isBot";
    private static final String IS_IMPORTANT_KEY = "isImportant";
    private static final String KEY_KEY = "key";
    private static final String NAME_KEY = "name";
    private static final String URI_KEY = "uri";
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public IconCompat mIcon;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean mIsBot;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean mIsImportant;
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mKey;
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public CharSequence mName;
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mUri;

    Person(Builder builder) {
        this.mName = builder.mName;
        this.mIcon = builder.mIcon;
        this.mUri = builder.mUri;
        this.mKey = builder.mKey;
        this.mIsBot = builder.mIsBot;
        this.mIsImportant = builder.mIsImportant;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Person() {
    }

    @NonNull
    public static Person fromBundle(@NonNull Bundle bundle) {
        Bundle iconBundle = bundle.getBundle("icon");
        return new Builder().setName(bundle.getCharSequence("name")).setIcon(iconBundle != null ? IconCompat.createFromBundle(iconBundle) : null).setUri(bundle.getString("uri")).setKey(bundle.getString("key")).setBot(bundle.getBoolean(IS_BOT_KEY)).setImportant(bundle.getBoolean(IS_IMPORTANT_KEY)).build();
    }

    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @NonNull
    public static Person fromPersistableBundle(@NonNull PersistableBundle bundle) {
        return new Builder().setName(bundle.getString("name")).setUri(bundle.getString("uri")).setKey(bundle.getString("key")).setBot(bundle.getBoolean(IS_BOT_KEY)).setImportant(bundle.getBoolean(IS_IMPORTANT_KEY)).build();
    }

    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @NonNull
    public static Person fromAndroidPerson(@NonNull android.app.Person person) {
        return new Builder().setName(person.getName()).setIcon(person.getIcon() != null ? IconCompat.createFromIcon(person.getIcon()) : null).setUri(person.getUri()).setKey(person.getKey()).setBot(person.isBot()).setImportant(person.isImportant()).build();
    }

    @NonNull
    public Bundle toBundle() {
        Bundle result = new Bundle();
        result.putCharSequence("name", this.mName);
        IconCompat iconCompat = this.mIcon;
        result.putBundle("icon", iconCompat != null ? iconCompat.toBundle() : null);
        result.putString("uri", this.mUri);
        result.putString("key", this.mKey);
        result.putBoolean(IS_BOT_KEY, this.mIsBot);
        result.putBoolean(IS_IMPORTANT_KEY, this.mIsImportant);
        return result;
    }

    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @NonNull
    public PersistableBundle toPersistableBundle() {
        PersistableBundle result = new PersistableBundle();
        CharSequence charSequence = this.mName;
        result.putString("name", charSequence != null ? charSequence.toString() : null);
        result.putString("uri", this.mUri);
        result.putString("key", this.mKey);
        result.putBoolean(IS_BOT_KEY, this.mIsBot);
        result.putBoolean(IS_IMPORTANT_KEY, this.mIsImportant);
        return result;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this);
    }

    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @NonNull
    public android.app.Person toAndroidPerson() {
        return new Person.Builder().setName(getName()).setIcon(getIcon() != null ? getIcon().toIcon() : null).setUri(getUri()).setKey(getKey()).setBot(isBot()).setImportant(isImportant()).build();
    }

    @Nullable
    public CharSequence getName() {
        return this.mName;
    }

    @Nullable
    public IconCompat getIcon() {
        return this.mIcon;
    }

    @Nullable
    public String getUri() {
        return this.mUri;
    }

    @Nullable
    public String getKey() {
        return this.mKey;
    }

    public boolean isBot() {
        return this.mIsBot;
    }

    public boolean isImportant() {
        return this.mIsImportant;
    }

    /* renamed from: android.support.v4.app.Person$Builder */
    public static class Builder {
        @Nullable
        IconCompat mIcon;
        boolean mIsBot;
        boolean mIsImportant;
        @Nullable
        String mKey;
        @Nullable
        CharSequence mName;
        @Nullable
        String mUri;

        public Builder() {
        }

        Builder(Person person) {
            this.mName = person.mName;
            this.mIcon = person.mIcon;
            this.mUri = person.mUri;
            this.mKey = person.mKey;
            this.mIsBot = person.mIsBot;
            this.mIsImportant = person.mIsImportant;
        }

        @NonNull
        public Builder setName(@Nullable CharSequence name) {
            this.mName = name;
            return this;
        }

        @NonNull
        public Builder setIcon(@Nullable IconCompat icon) {
            this.mIcon = icon;
            return this;
        }

        @NonNull
        public Builder setUri(@Nullable String uri) {
            this.mUri = uri;
            return this;
        }

        @NonNull
        public Builder setKey(@Nullable String key) {
            this.mKey = key;
            return this;
        }

        @NonNull
        public Builder setBot(boolean bot) {
            this.mIsBot = bot;
            return this;
        }

        @NonNull
        public Builder setImportant(boolean important) {
            this.mIsImportant = important;
            return this;
        }

        @NonNull
        public Person build() {
            return new Person(this);
        }
    }
}
