package com.bumptech.glide;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.GuardedBy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.data.InputStreamRewinder;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.prefill.BitmapPreFiller;
import com.bumptech.glide.load.engine.prefill.PreFillType;
import com.bumptech.glide.load.model.AssetUriLoader;
import com.bumptech.glide.load.model.ByteArrayLoader;
import com.bumptech.glide.load.model.ByteBufferEncoder;
import com.bumptech.glide.load.model.ByteBufferFileLoader;
import com.bumptech.glide.load.model.DataUrlLoader;
import com.bumptech.glide.load.model.FileLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.MediaStoreFileLoader;
import com.bumptech.glide.load.model.ResourceLoader;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.StringLoader;
import com.bumptech.glide.load.model.UnitModelLoader;
import com.bumptech.glide.load.model.UriLoader;
import com.bumptech.glide.load.model.UrlUriLoader;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.bumptech.glide.load.model.stream.HttpUriLoader;
import com.bumptech.glide.load.model.stream.MediaStoreImageThumbLoader;
import com.bumptech.glide.load.model.stream.MediaStoreVideoThumbLoader;
import com.bumptech.glide.load.model.stream.UrlLoader;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableDecoder;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableEncoder;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.ByteBufferBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.ExifInterfaceImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.ResourceBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.UnitBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.bytes.ByteBufferRewinder;
import com.bumptech.glide.load.resource.drawable.ResourceDrawableDecoder;
import com.bumptech.glide.load.resource.drawable.UnitDrawableDecoder;
import com.bumptech.glide.load.resource.file.FileDecoder;
import com.bumptech.glide.load.resource.gif.ByteBufferGifDecoder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawableEncoder;
import com.bumptech.glide.load.resource.gif.GifFrameResourceDecoder;
import com.bumptech.glide.load.resource.gif.StreamGifDecoder;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.bumptech.glide.load.resource.transcode.BitmapDrawableTranscoder;
import com.bumptech.glide.load.resource.transcode.DrawableBytesTranscoder;
import com.bumptech.glide.load.resource.transcode.GifDrawableBytesTranscoder;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.module.ManifestParser;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTargetFactory;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Glide implements ComponentCallbacks2 {
    private static final String DEFAULT_DISK_CACHE_DIR = "image_manager_disk_cache";
    private static final String TAG = "Glide";
    private static volatile Glide glide;
    private static volatile boolean isInitializing;
    private final ArrayPool arrayPool;
    private final BitmapPool bitmapPool;
    @Nullable
    @GuardedBy("this")
    private BitmapPreFiller bitmapPreFiller;
    private final ConnectivityMonitorFactory connectivityMonitorFactory;
    private final RequestOptionsFactory defaultRequestOptionsFactory;
    private final Engine engine;
    private final GlideContext glideContext;
    private final List<RequestManager> managers = new ArrayList();
    private final MemoryCache memoryCache;
    private MemoryCategory memoryCategory = MemoryCategory.NORMAL;
    private final Registry registry;
    private final RequestManagerRetriever requestManagerRetriever;

    public interface RequestOptionsFactory {
        @NonNull
        RequestOptions build();
    }

    @Nullable
    public static File getPhotoCacheDir(@NonNull Context context) {
        return getPhotoCacheDir(context, "image_manager_disk_cache");
    }

    @Nullable
    public static File getPhotoCacheDir(@NonNull Context context, @NonNull String cacheName) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            File result = new File(cacheDir, cacheName);
            if (result.mkdirs() || (result.exists() && result.isDirectory())) {
                return result;
            }
            return null;
        }
        if (Log.isLoggable(TAG, 6)) {
            Log.e(TAG, "default disk cache dir is null");
        }
        return null;
    }

    @NonNull
    public static Glide get(@NonNull Context context) {
        if (glide == null) {
            synchronized (Glide.class) {
                if (glide == null) {
                    checkAndInitializeGlide(context);
                }
            }
        }
        return glide;
    }

    private static void checkAndInitializeGlide(@NonNull Context context) {
        if (!isInitializing) {
            isInitializing = true;
            initializeGlide(context);
            isInitializing = false;
            return;
        }
        throw new IllegalStateException("You cannot call Glide.get() in registerComponents(), use the provided Glide instance instead");
    }

    @VisibleForTesting
    @Deprecated
    public static synchronized void init(Glide glide2) {
        synchronized (Glide.class) {
            if (glide != null) {
                tearDown();
            }
            glide = glide2;
        }
    }

    @VisibleForTesting
    public static synchronized void init(@NonNull Context context, @NonNull GlideBuilder builder) {
        synchronized (Glide.class) {
            if (glide != null) {
                tearDown();
            }
            initializeGlide(context, builder);
        }
    }

    @VisibleForTesting
    public static synchronized void tearDown() {
        synchronized (Glide.class) {
            if (glide != null) {
                glide.getContext().getApplicationContext().unregisterComponentCallbacks(glide);
                glide.engine.shutdown();
            }
            glide = null;
        }
    }

    private static void initializeGlide(@NonNull Context context) {
        initializeGlide(context, new GlideBuilder());
    }

    private static void initializeGlide(@NonNull Context context, @NonNull GlideBuilder builder) {
        Context applicationContext = context.getApplicationContext();
        GeneratedAppGlideModule annotationGeneratedModule = getAnnotationGeneratedGlideModules();
        List<GlideModule> manifestModules = Collections.emptyList();
        if (annotationGeneratedModule == null || annotationGeneratedModule.isManifestParsingEnabled()) {
            manifestModules = new ManifestParser(applicationContext).parse();
        }
        if (annotationGeneratedModule != null && !annotationGeneratedModule.getExcludedModuleClasses().isEmpty()) {
            Set<Class<?>> excludedModuleClasses = annotationGeneratedModule.getExcludedModuleClasses();
            Iterator<GlideModule> iterator = manifestModules.iterator();
            while (iterator.hasNext()) {
                GlideModule current = iterator.next();
                if (excludedModuleClasses.contains(current.getClass())) {
                    if (Log.isLoggable(TAG, 3)) {
                        String valueOf = String.valueOf(current);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46);
                        sb.append("AppGlideModule excludes manifest GlideModule: ");
                        sb.append(valueOf);
                        Log.d(TAG, sb.toString());
                    }
                    iterator.remove();
                }
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            for (GlideModule glideModule : manifestModules) {
                String valueOf2 = String.valueOf(glideModule.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 38);
                sb2.append("Discovered GlideModule from manifest: ");
                sb2.append(valueOf2);
                Log.d(TAG, sb2.toString());
            }
        }
        builder.setRequestManagerFactory(annotationGeneratedModule != null ? annotationGeneratedModule.getRequestManagerFactory() : null);
        for (GlideModule module : manifestModules) {
            module.applyOptions(applicationContext, builder);
        }
        if (annotationGeneratedModule != null) {
            annotationGeneratedModule.applyOptions(applicationContext, builder);
        }
        Glide glide2 = builder.build(applicationContext);
        for (GlideModule module2 : manifestModules) {
            try {
                module2.registerComponents(applicationContext, glide2, glide2.registry);
            } catch (AbstractMethodError e) {
                String valueOf3 = String.valueOf(module2.getClass().getName());
                throw new IllegalStateException(valueOf3.length() != 0 ? "Attempting to register a Glide v3 module. If you see this, you or one of your dependencies may be including Glide v3 even though you're using Glide v4. You'll need to find and remove (or update) the offending dependency. The v3 module name is: ".concat(valueOf3) : new String("Attempting to register a Glide v3 module. If you see this, you or one of your dependencies may be including Glide v3 even though you're using Glide v4. You'll need to find and remove (or update) the offending dependency. The v3 module name is: "), e);
            }
        }
        if (annotationGeneratedModule != null) {
            annotationGeneratedModule.registerComponents(applicationContext, glide2, glide2.registry);
        }
        applicationContext.registerComponentCallbacks(glide2);
        glide = glide2;
    }

    @Nullable
    private static GeneratedAppGlideModule getAnnotationGeneratedGlideModules() {
        try {
            return (GeneratedAppGlideModule) Class.forName("com.bumptech.glide.GeneratedAppGlideModuleImpl").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (ClassNotFoundException e) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored");
            return null;
        } catch (InstantiationException e2) {
            throwIncorrectGlideModule(e2);
            return null;
        } catch (IllegalAccessException e3) {
            throwIncorrectGlideModule(e3);
            return null;
        } catch (NoSuchMethodException e4) {
            throwIncorrectGlideModule(e4);
            return null;
        } catch (InvocationTargetException e5) {
            throwIncorrectGlideModule(e5);
            return null;
        }
    }

    private static void throwIncorrectGlideModule(Exception e) {
        throw new IllegalStateException("GeneratedAppGlideModuleImpl is implemented incorrectly. If you've manually implemented this class, remove your implementation. The Annotation processor will generate a correct implementation.", e);
    }

    /* JADX INFO: Multiple debug info for r12v15 android.content.ContentResolver: [D('resourceLoaderAssetFileDescriptorFactory' com.bumptech.glide.load.model.ResourceLoader$AssetFileDescriptorFactory), D('contentResolver' android.content.ContentResolver)] */
    /* JADX INFO: Multiple debug info for r12v16 com.bumptech.glide.load.resource.transcode.GifDrawableBytesTranscoder: [D('contentResolver' android.content.ContentResolver), D('gifDrawableBytesTranscoder' com.bumptech.glide.load.resource.transcode.GifDrawableBytesTranscoder)] */
    /* JADX INFO: Multiple debug info for r5v1 com.bumptech.glide.request.target.ImageViewTargetFactory: [D('imageViewTargetFactory' com.bumptech.glide.request.target.ImageViewTargetFactory), D('streamBitmapDecoder' com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder)] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.ResourceEncoder):com.bumptech.glide.Registry
     arg types: [java.lang.Class, com.bumptech.glide.load.resource.gif.GifDrawableEncoder]
     candidates:
      com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.Encoder):com.bumptech.glide.Registry
      com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.ResourceEncoder):com.bumptech.glide.Registry */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.ResourceEncoder):com.bumptech.glide.Registry
     arg types: [java.lang.Class, com.bumptech.glide.load.resource.bitmap.BitmapDrawableEncoder]
     candidates:
      com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.Encoder):com.bumptech.glide.Registry
      com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.ResourceEncoder):com.bumptech.glide.Registry */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.ResourceEncoder):com.bumptech.glide.Registry
     arg types: [java.lang.Class, com.bumptech.glide.load.resource.bitmap.BitmapEncoder]
     candidates:
      com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.Encoder):com.bumptech.glide.Registry
      com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.ResourceEncoder):com.bumptech.glide.Registry */
    Glide(@NonNull Context context, @NonNull Engine engine2, @NonNull MemoryCache memoryCache2, @NonNull BitmapPool bitmapPool2, @NonNull ArrayPool arrayPool2, @NonNull RequestManagerRetriever requestManagerRetriever2, @NonNull ConnectivityMonitorFactory connectivityMonitorFactory2, int logLevel, @NonNull RequestOptionsFactory defaultRequestOptionsFactory2, @NonNull Map<Class<?>, TransitionOptions<?, ?>> defaultTransitionOptions, @NonNull List<RequestListener<Object>> defaultRequestListeners, boolean isLoggingRequestOriginsEnabled) {
        Context context2 = context;
        BitmapPool bitmapPool3 = bitmapPool2;
        ArrayPool arrayPool3 = arrayPool2;
        this.engine = engine2;
        this.bitmapPool = bitmapPool3;
        this.arrayPool = arrayPool3;
        this.memoryCache = memoryCache2;
        this.requestManagerRetriever = requestManagerRetriever2;
        this.connectivityMonitorFactory = connectivityMonitorFactory2;
        this.defaultRequestOptionsFactory = defaultRequestOptionsFactory2;
        Resources resources = context.getResources();
        this.registry = new Registry();
        this.registry.register(new DefaultImageHeaderParser());
        if (Build.VERSION.SDK_INT >= 27) {
            this.registry.register(new ExifInterfaceImageHeaderParser());
        }
        List<ImageHeaderParser> imageHeaderParsers = this.registry.getImageHeaderParsers();
        Downsampler downsampler = new Downsampler(imageHeaderParsers, resources.getDisplayMetrics(), bitmapPool3, arrayPool3);
        ByteBufferGifDecoder byteBufferGifDecoder = new ByteBufferGifDecoder(context2, imageHeaderParsers, bitmapPool3, arrayPool3);
        ResourceDecoder<ParcelFileDescriptor, Bitmap> parcelFileDescriptorVideoDecoder = VideoDecoder.parcel(bitmapPool2);
        ByteBufferBitmapDecoder byteBufferBitmapDecoder = new ByteBufferBitmapDecoder(downsampler);
        StreamBitmapDecoder streamBitmapDecoder = new StreamBitmapDecoder(downsampler, arrayPool3);
        ResourceDrawableDecoder resourceDrawableDecoder = new ResourceDrawableDecoder(context2);
        ResourceLoader.StreamFactory resourceLoaderStreamFactory = new ResourceLoader.StreamFactory(resources);
        ResourceLoader.UriFactory resourceLoaderUriFactory = new ResourceLoader.UriFactory(resources);
        ResourceLoader.FileDescriptorFactory resourceLoaderFileDescriptorFactory = new ResourceLoader.FileDescriptorFactory(resources);
        ResourceLoader.AssetFileDescriptorFactory resourceLoaderAssetFileDescriptorFactory = new ResourceLoader.AssetFileDescriptorFactory(resources);
        BitmapEncoder bitmapEncoder = new BitmapEncoder(arrayPool3);
        BitmapBytesTranscoder bitmapBytesTranscoder = new BitmapBytesTranscoder();
        GifDrawableBytesTranscoder gifDrawableBytesTranscoder = new GifDrawableBytesTranscoder();
        ResourceLoader.AssetFileDescriptorFactory resourceLoaderAssetFileDescriptorFactory2 = resourceLoaderAssetFileDescriptorFactory;
        ResourceLoader.FileDescriptorFactory resourceLoaderFileDescriptorFactory2 = resourceLoaderFileDescriptorFactory;
        ResourceLoader.UriFactory resourceLoaderUriFactory2 = resourceLoaderUriFactory;
        ResourceLoader.AssetFileDescriptorFactory resourceLoaderAssetFileDescriptorFactory3 = resourceLoaderAssetFileDescriptorFactory2;
        Context context3 = context;
        ContentResolver contentResolver = context.getContentResolver();
        BitmapBytesTranscoder bitmapBytesTranscoder2 = bitmapBytesTranscoder;
        GifDrawableBytesTranscoder gifDrawableBytesTranscoder2 = gifDrawableBytesTranscoder;
        this.registry.append(ByteBuffer.class, new ByteBufferEncoder()).append(InputStream.class, new StreamEncoder(arrayPool3)).append(Registry.BUCKET_BITMAP, ByteBuffer.class, Bitmap.class, byteBufferBitmapDecoder).append(Registry.BUCKET_BITMAP, InputStream.class, Bitmap.class, streamBitmapDecoder).append(Registry.BUCKET_BITMAP, ParcelFileDescriptor.class, Bitmap.class, parcelFileDescriptorVideoDecoder).append(Registry.BUCKET_BITMAP, AssetFileDescriptor.class, Bitmap.class, VideoDecoder.asset(bitmapPool2)).append(Bitmap.class, Bitmap.class, UnitModelLoader.Factory.getInstance()).append(Registry.BUCKET_BITMAP, Bitmap.class, Bitmap.class, new UnitBitmapDecoder()).append(Bitmap.class, (ResourceEncoder) bitmapEncoder).append(Registry.BUCKET_BITMAP_DRAWABLE, ByteBuffer.class, BitmapDrawable.class, new BitmapDrawableDecoder(resources, byteBufferBitmapDecoder)).append(Registry.BUCKET_BITMAP_DRAWABLE, InputStream.class, BitmapDrawable.class, new BitmapDrawableDecoder(resources, streamBitmapDecoder)).append(Registry.BUCKET_BITMAP_DRAWABLE, ParcelFileDescriptor.class, BitmapDrawable.class, new BitmapDrawableDecoder(resources, parcelFileDescriptorVideoDecoder)).append(BitmapDrawable.class, (ResourceEncoder) new BitmapDrawableEncoder(bitmapPool3, bitmapEncoder)).append(Registry.BUCKET_GIF, InputStream.class, GifDrawable.class, new StreamGifDecoder(imageHeaderParsers, byteBufferGifDecoder, arrayPool3)).append(Registry.BUCKET_GIF, ByteBuffer.class, GifDrawable.class, byteBufferGifDecoder).append(GifDrawable.class, (ResourceEncoder) new GifDrawableEncoder()).append(GifDecoder.class, GifDecoder.class, UnitModelLoader.Factory.getInstance()).append(Registry.BUCKET_BITMAP, GifDecoder.class, Bitmap.class, new GifFrameResourceDecoder(bitmapPool3)).append(Uri.class, Drawable.class, resourceDrawableDecoder).append(Uri.class, Bitmap.class, new ResourceBitmapDecoder(resourceDrawableDecoder, bitmapPool3)).register(new ByteBufferRewinder.Factory()).append(File.class, ByteBuffer.class, new ByteBufferFileLoader.Factory()).append(File.class, InputStream.class, new FileLoader.StreamFactory()).append(File.class, File.class, new FileDecoder()).append(File.class, ParcelFileDescriptor.class, new FileLoader.FileDescriptorFactory()).append(File.class, File.class, UnitModelLoader.Factory.getInstance()).register(new InputStreamRewinder.Factory(arrayPool3)).append(Integer.TYPE, InputStream.class, resourceLoaderStreamFactory).append(Integer.TYPE, ParcelFileDescriptor.class, resourceLoaderFileDescriptorFactory2).append(Integer.class, InputStream.class, resourceLoaderStreamFactory).append(Integer.class, ParcelFileDescriptor.class, resourceLoaderFileDescriptorFactory2).append(Integer.class, Uri.class, resourceLoaderUriFactory2).append(Integer.TYPE, AssetFileDescriptor.class, resourceLoaderAssetFileDescriptorFactory3).append(Integer.class, AssetFileDescriptor.class, resourceLoaderAssetFileDescriptorFactory3).append(Integer.TYPE, Uri.class, resourceLoaderUriFactory2).append(String.class, InputStream.class, new DataUrlLoader.StreamFactory()).append(Uri.class, InputStream.class, new DataUrlLoader.StreamFactory()).append(String.class, InputStream.class, new StringLoader.StreamFactory()).append(String.class, ParcelFileDescriptor.class, new StringLoader.FileDescriptorFactory()).append(String.class, AssetFileDescriptor.class, new StringLoader.AssetFileDescriptorFactory()).append(Uri.class, InputStream.class, new HttpUriLoader.Factory()).append(Uri.class, InputStream.class, new AssetUriLoader.StreamFactory(context.getAssets())).append(Uri.class, ParcelFileDescriptor.class, new AssetUriLoader.FileDescriptorFactory(context.getAssets())).append(Uri.class, InputStream.class, new MediaStoreImageThumbLoader.Factory(context3)).append(Uri.class, InputStream.class, new MediaStoreVideoThumbLoader.Factory(context3)).append(Uri.class, InputStream.class, new UriLoader.StreamFactory(contentResolver)).append(Uri.class, ParcelFileDescriptor.class, new UriLoader.FileDescriptorFactory(contentResolver)).append(Uri.class, AssetFileDescriptor.class, new UriLoader.AssetFileDescriptorFactory(contentResolver)).append(Uri.class, InputStream.class, new UrlUriLoader.StreamFactory()).append(URL.class, InputStream.class, new UrlLoader.StreamFactory()).append(Uri.class, File.class, new MediaStoreFileLoader.Factory(context3)).append(GlideUrl.class, InputStream.class, new HttpGlideUrlLoader.Factory()).append(byte[].class, ByteBuffer.class, new ByteArrayLoader.ByteBufferFactory()).append(byte[].class, InputStream.class, new ByteArrayLoader.StreamFactory()).append(Uri.class, Uri.class, UnitModelLoader.Factory.getInstance()).append(Drawable.class, Drawable.class, UnitModelLoader.Factory.getInstance()).append(Drawable.class, Drawable.class, new UnitDrawableDecoder()).register(Bitmap.class, BitmapDrawable.class, new BitmapDrawableTranscoder(resources)).register(Bitmap.class, byte[].class, bitmapBytesTranscoder2).register(Drawable.class, byte[].class, new DrawableBytesTranscoder(bitmapPool3, bitmapBytesTranscoder2, gifDrawableBytesTranscoder2)).register(GifDrawable.class, byte[].class, gifDrawableBytesTranscoder2);
        this.glideContext = new GlideContext(context, arrayPool2, this.registry, new ImageViewTargetFactory(), defaultRequestOptionsFactory2, defaultTransitionOptions, defaultRequestListeners, engine2, isLoggingRequestOriginsEnabled, logLevel);
    }

    @NonNull
    public BitmapPool getBitmapPool() {
        return this.bitmapPool;
    }

    @NonNull
    public ArrayPool getArrayPool() {
        return this.arrayPool;
    }

    @NonNull
    public Context getContext() {
        return this.glideContext.getBaseContext();
    }

    /* access modifiers changed from: package-private */
    public ConnectivityMonitorFactory getConnectivityMonitorFactory() {
        return this.connectivityMonitorFactory;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public GlideContext getGlideContext() {
        return this.glideContext;
    }

    public synchronized void preFillBitmapPool(@NonNull PreFillType.Builder... bitmapAttributeBuilders) {
        if (this.bitmapPreFiller == null) {
            this.bitmapPreFiller = new BitmapPreFiller(this.memoryCache, this.bitmapPool, (DecodeFormat) this.defaultRequestOptionsFactory.build().getOptions().get(Downsampler.DECODE_FORMAT));
        }
        this.bitmapPreFiller.preFill(bitmapAttributeBuilders);
    }

    public void clearMemory() {
        Util.assertMainThread();
        this.memoryCache.clearMemory();
        this.bitmapPool.clearMemory();
        this.arrayPool.clearMemory();
    }

    public void trimMemory(int level) {
        Util.assertMainThread();
        this.memoryCache.trimMemory(level);
        this.bitmapPool.trimMemory(level);
        this.arrayPool.trimMemory(level);
    }

    public void clearDiskCache() {
        Util.assertBackgroundThread();
        this.engine.clearDiskCache();
    }

    @NonNull
    public RequestManagerRetriever getRequestManagerRetriever() {
        return this.requestManagerRetriever;
    }

    @NonNull
    public MemoryCategory setMemoryCategory(@NonNull MemoryCategory memoryCategory2) {
        Util.assertMainThread();
        this.memoryCache.setSizeMultiplier(memoryCategory2.getMultiplier());
        this.bitmapPool.setSizeMultiplier(memoryCategory2.getMultiplier());
        MemoryCategory oldCategory = this.memoryCategory;
        this.memoryCategory = memoryCategory2;
        return oldCategory;
    }

    @NonNull
    private static RequestManagerRetriever getRetriever(@Nullable Context context) {
        Preconditions.checkNotNull(context, "You cannot start a load on a not yet attached View or a Fragment where getActivity() returns null (which usually occurs when getActivity() is called before the Fragment is attached or after the Fragment is destroyed).");
        return get(context).getRequestManagerRetriever();
    }

    @NonNull
    public static RequestManager with(@NonNull Context context) {
        return getRetriever(context).get(context);
    }

    @NonNull
    public static RequestManager with(@NonNull Activity activity) {
        return getRetriever(activity).get(activity);
    }

    @NonNull
    public static RequestManager with(@NonNull FragmentActivity activity) {
        return getRetriever(activity).get(activity);
    }

    @NonNull
    public static RequestManager with(@NonNull Fragment fragment) {
        return getRetriever(fragment.getActivity()).get(fragment);
    }

    @Deprecated
    @NonNull
    public static RequestManager with(@NonNull android.app.Fragment fragment) {
        return getRetriever(fragment.getActivity()).get(fragment);
    }

    @NonNull
    public static RequestManager with(@NonNull View view) {
        return getRetriever(view.getContext()).get(view);
    }

    @NonNull
    public Registry getRegistry() {
        return this.registry;
    }

    /* access modifiers changed from: package-private */
    public boolean removeFromManagers(@NonNull Target<?> target) {
        synchronized (this.managers) {
            for (RequestManager requestManager : this.managers) {
                if (requestManager.untrack(target)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void registerRequestManager(RequestManager requestManager) {
        synchronized (this.managers) {
            if (!this.managers.contains(requestManager)) {
                this.managers.add(requestManager);
            } else {
                throw new IllegalStateException("Cannot register already registered manager");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void unregisterRequestManager(RequestManager requestManager) {
        synchronized (this.managers) {
            if (this.managers.contains(requestManager)) {
                this.managers.remove(requestManager);
            } else {
                throw new IllegalStateException("Cannot unregister not yet registered manager");
            }
        }
    }

    public void onTrimMemory(int level) {
        trimMemory(level);
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onLowMemory() {
        clearMemory();
    }
}
