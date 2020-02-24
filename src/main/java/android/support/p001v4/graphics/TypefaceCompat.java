package android.support.p001v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.content.res.FontResourcesParserCompat;
import android.support.p001v4.content.res.ResourcesCompat;
import android.support.p001v4.provider.FontsContractCompat;
import android.support.p001v4.util.LruCache;

/* renamed from: android.support.v4.graphics.TypefaceCompat */
public class TypefaceCompat {
    private static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);
    private static final TypefaceCompatBaseImpl sTypefaceCompatImpl;

    static {
        if (Build.VERSION.SDK_INT >= 28) {
            sTypefaceCompatImpl = new TypefaceCompatApi28Impl();
        } else if (Build.VERSION.SDK_INT >= 26) {
            sTypefaceCompatImpl = new TypefaceCompatApi26Impl();
        } else if (Build.VERSION.SDK_INT >= 24 && TypefaceCompatApi24Impl.isUsable()) {
            sTypefaceCompatImpl = new TypefaceCompatApi24Impl();
        } else if (Build.VERSION.SDK_INT >= 21) {
            sTypefaceCompatImpl = new TypefaceCompatApi21Impl();
        } else {
            sTypefaceCompatImpl = new TypefaceCompatBaseImpl();
        }
    }

    private TypefaceCompat() {
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface findFromCache(@NonNull Resources resources, int id, int style) {
        return sTypefaceCache.get(createResourceUid(resources, id, style));
    }

    private static String createResourceUid(Resources resources, int id, int style) {
        return resources.getResourcePackageName(id) + "-" + id + "-" + style;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface createFromResourcesFamilyXml(@NonNull Context context, @NonNull FontResourcesParserCompat.FamilyResourceEntry entry, @NonNull Resources resources, int id, int style, @Nullable ResourcesCompat.FontCallback fontCallback, @Nullable Handler handler, boolean isRequestFromLayoutInflator) {
        Typeface typeface;
        FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry = entry;
        ResourcesCompat.FontCallback fontCallback2 = fontCallback;
        Handler handler2 = handler;
        if (familyResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry) {
            FontResourcesParserCompat.ProviderResourceEntry providerEntry = (FontResourcesParserCompat.ProviderResourceEntry) familyResourceEntry;
            typeface = FontsContractCompat.getFontSync(context, providerEntry.getRequest(), fontCallback, handler, !isRequestFromLayoutInflator ? fontCallback2 == null : providerEntry.getFetchStrategy() == 0, isRequestFromLayoutInflator ? providerEntry.getTimeout() : -1, style);
        } else {
            typeface = sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(context, (FontResourcesParserCompat.FontFamilyFilesResourceEntry) familyResourceEntry, resources, style);
            if (fontCallback2 != null) {
                if (typeface != null) {
                    fontCallback2.callbackSuccessAsync(typeface, handler2);
                } else {
                    fontCallback2.callbackFailAsync(-3, handler2);
                }
            }
        }
        if (typeface != null) {
            sTypefaceCache.put(createResourceUid(resources, id, style), typeface);
        }
        return typeface;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface createFromResourcesFontFile(@NonNull Context context, @NonNull Resources resources, int id, String path, int style) {
        Typeface typeface = sTypefaceCompatImpl.createFromResourcesFontFile(context, resources, id, path, style);
        if (typeface != null) {
            sTypefaceCache.put(createResourceUid(resources, id, style), typeface);
        }
        return typeface;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface createFromFontInfo(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fonts, int style) {
        return sTypefaceCompatImpl.createFromFontInfo(context, cancellationSignal, fonts, style);
    }

    @Nullable
    private static Typeface getBestFontFromFamily(Context context, Typeface typeface, int style) {
        FontResourcesParserCompat.FontFamilyFilesResourceEntry families = sTypefaceCompatImpl.getFontFamily(typeface);
        if (families == null) {
            return null;
        }
        return sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(context, families, context.getResources(), style);
    }

    @NonNull
    public static Typeface create(@NonNull Context context, @Nullable Typeface family, int style) {
        Typeface typefaceFromFamily;
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        } else if (Build.VERSION.SDK_INT >= 21 || (typefaceFromFamily = getBestFontFromFamily(context, family, style)) == null) {
            return Typeface.create(family, style);
        } else {
            return typefaceFromFamily;
        }
    }
}
