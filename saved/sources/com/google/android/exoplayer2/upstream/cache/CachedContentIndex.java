package com.google.android.exoplayer2.upstream.cache;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class CachedContentIndex {
    static final String FILE_NAME_ATOMIC = "cached_content_index.exi";
    private static final int INCREMENTAL_METADATA_READ_LENGTH = 10485760;
    private final SparseArray<String> idToKey;
    private final HashMap<String, CachedContent> keyToContent;
    @Nullable
    private Storage previousStorage;
    private final SparseBooleanArray removedIds;
    private Storage storage;

    private interface Storage {
        void delete() throws IOException;

        boolean exists() throws IOException;

        void initialize(long j);

        void load(HashMap<String, CachedContent> hashMap, SparseArray<String> sparseArray) throws IOException;

        void onRemove(CachedContent cachedContent);

        void onUpdate(CachedContent cachedContent);

        void storeFully(HashMap<String, CachedContent> hashMap) throws IOException;

        void storeIncremental(HashMap<String, CachedContent> hashMap) throws IOException;
    }

    public static final boolean isIndexFile(String fileName) {
        return fileName.startsWith(FILE_NAME_ATOMIC);
    }

    public static void delete(DatabaseProvider databaseProvider, long uid) throws DatabaseIOException {
        DatabaseStorage.delete(databaseProvider, uid);
    }

    public CachedContentIndex(DatabaseProvider databaseProvider) {
        this(databaseProvider, null, null, false, false);
    }

    public CachedContentIndex(@Nullable DatabaseProvider databaseProvider, @Nullable File legacyStorageDir, @Nullable byte[] legacyStorageSecretKey, boolean legacyStorageEncrypt, boolean preferLegacyStorage) {
        Assertions.checkState((databaseProvider == null && legacyStorageDir == null) ? false : true);
        this.keyToContent = new HashMap<>();
        this.idToKey = new SparseArray<>();
        this.removedIds = new SparseBooleanArray();
        Storage legacyStorage = null;
        Storage databaseStorage = databaseProvider != null ? new DatabaseStorage(databaseProvider) : null;
        legacyStorage = legacyStorageDir != null ? new LegacyStorage(new File(legacyStorageDir, FILE_NAME_ATOMIC), legacyStorageSecretKey, legacyStorageEncrypt) : legacyStorage;
        if (databaseStorage == null || (legacyStorage != null && preferLegacyStorage)) {
            this.storage = legacyStorage;
            this.previousStorage = databaseStorage;
            return;
        }
        this.storage = databaseStorage;
        this.previousStorage = legacyStorage;
    }

    public void initialize(long uid) throws IOException {
        Storage storage2;
        this.storage.initialize(uid);
        Storage storage3 = this.previousStorage;
        if (storage3 != null) {
            storage3.initialize(uid);
        }
        if (this.storage.exists() || (storage2 = this.previousStorage) == null || !storage2.exists()) {
            this.storage.load(this.keyToContent, this.idToKey);
        } else {
            this.previousStorage.load(this.keyToContent, this.idToKey);
            this.storage.storeFully(this.keyToContent);
        }
        Storage storage4 = this.previousStorage;
        if (storage4 != null) {
            storage4.delete();
            this.previousStorage = null;
        }
    }

    public void store() throws IOException {
        this.storage.storeIncremental(this.keyToContent);
        int removedIdCount = this.removedIds.size();
        for (int i = 0; i < removedIdCount; i++) {
            this.idToKey.remove(this.removedIds.keyAt(i));
        }
        this.removedIds.clear();
    }

    public CachedContent getOrAdd(String key) {
        CachedContent cachedContent = this.keyToContent.get(key);
        return cachedContent == null ? addNew(key) : cachedContent;
    }

    public CachedContent get(String key) {
        return this.keyToContent.get(key);
    }

    public Collection<CachedContent> getAll() {
        return this.keyToContent.values();
    }

    public int assignIdForKey(String key) {
        return getOrAdd(key).f105id;
    }

    public String getKeyForId(int id) {
        return this.idToKey.get(id);
    }

    public void maybeRemove(String key) {
        CachedContent cachedContent = this.keyToContent.get(key);
        if (cachedContent != null && cachedContent.isEmpty() && !cachedContent.isLocked()) {
            this.keyToContent.remove(key);
            this.storage.onRemove(cachedContent);
            this.idToKey.put(cachedContent.f105id, null);
            this.removedIds.put(cachedContent.f105id, true);
        }
    }

    public void removeEmpty() {
        String[] keys = new String[this.keyToContent.size()];
        this.keyToContent.keySet().toArray(keys);
        for (String key : keys) {
            maybeRemove(key);
        }
    }

    public Set<String> getKeys() {
        return this.keyToContent.keySet();
    }

    public void applyContentMetadataMutations(String key, ContentMetadataMutations mutations) {
        CachedContent cachedContent = getOrAdd(key);
        if (cachedContent.applyMetadataMutations(mutations)) {
            this.storage.onUpdate(cachedContent);
        }
    }

    public ContentMetadata getContentMetadata(String key) {
        CachedContent cachedContent = get(key);
        return cachedContent != null ? cachedContent.getMetadata() : DefaultContentMetadata.EMPTY;
    }

    private CachedContent addNew(String key) {
        CachedContent cachedContent = new CachedContent(getNewId(this.idToKey), key);
        this.keyToContent.put(cachedContent.key, cachedContent);
        this.idToKey.put(cachedContent.f105id, cachedContent.key);
        this.storage.onUpdate(cachedContent);
        return cachedContent;
    }

    /* access modifiers changed from: private */
    @SuppressLint({"GetInstance"})
    public static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (Util.SDK_INT == 18) {
            try {
                return Cipher.getInstance("AES/CBC/PKCS5PADDING", "BC");
            } catch (Throwable th) {
            }
        }
        return Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }

    @VisibleForTesting
    static int getNewId(SparseArray<String> idToKey2) {
        int size = idToKey2.size();
        int id = size == 0 ? 0 : idToKey2.keyAt(size - 1) + 1;
        if (id < 0) {
            int id2 = 0;
            while (id < size && id == idToKey2.keyAt(id)) {
                id2 = id + 1;
            }
        }
        return id;
    }

    /* access modifiers changed from: private */
    public static DefaultContentMetadata readContentMetadata(DataInputStream input) throws IOException {
        int size = input.readInt();
        HashMap<String, byte[]> metadata = new HashMap<>();
        int i = 0;
        while (i < size) {
            String name = input.readUTF();
            int valueSize = input.readInt();
            if (valueSize >= 0) {
                int bytesRead = 0;
                int nextBytesToRead = Math.min(valueSize, (int) INCREMENTAL_METADATA_READ_LENGTH);
                byte[] value = Util.EMPTY_BYTE_ARRAY;
                while (bytesRead != valueSize) {
                    value = Arrays.copyOf(value, bytesRead + nextBytesToRead);
                    input.readFully(value, bytesRead, nextBytesToRead);
                    bytesRead += nextBytesToRead;
                    nextBytesToRead = Math.min(valueSize - bytesRead, (int) INCREMENTAL_METADATA_READ_LENGTH);
                }
                metadata.put(name, value);
                i++;
            } else {
                StringBuilder sb = new StringBuilder(31);
                sb.append("Invalid value size: ");
                sb.append(valueSize);
                throw new IOException(sb.toString());
            }
        }
        return new DefaultContentMetadata(metadata);
    }

    /* access modifiers changed from: private */
    public static void writeContentMetadata(DefaultContentMetadata metadata, DataOutputStream output) throws IOException {
        Set<Map.Entry<String, byte[]>> entrySet = metadata.entrySet();
        output.writeInt(entrySet.size());
        for (Map.Entry<String, byte[]> entry : entrySet) {
            output.writeUTF((String) entry.getKey());
            byte[] value = (byte[]) entry.getValue();
            output.writeInt(value.length);
            output.write(value);
        }
    }

    private static class LegacyStorage implements Storage {
        private static final int FLAG_ENCRYPTED_INDEX = 1;
        private static final int VERSION = 2;
        private static final int VERSION_METADATA_INTRODUCED = 2;
        private final AtomicFile atomicFile;
        @Nullable
        private ReusableBufferedOutputStream bufferedOutputStream;
        private boolean changed;
        @Nullable
        private final Cipher cipher;
        private final boolean encrypt;
        @Nullable
        private final Random random;
        @Nullable
        private final SecretKeySpec secretKeySpec;

        public LegacyStorage(File file, @Nullable byte[] secretKey, boolean encrypt2) {
            Cipher cipher2 = null;
            SecretKeySpec secretKeySpec2 = null;
            if (secretKey != null) {
                Assertions.checkArgument(secretKey.length == 16);
                try {
                    cipher2 = CachedContentIndex.getCipher();
                    secretKeySpec2 = new SecretKeySpec(secretKey, "AES");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                Assertions.checkArgument(!encrypt2);
            }
            this.encrypt = encrypt2;
            this.cipher = cipher2;
            this.secretKeySpec = secretKeySpec2;
            this.random = encrypt2 ? new Random() : null;
            this.atomicFile = new AtomicFile(file);
        }

        public void initialize(long uid) {
        }

        public boolean exists() {
            return this.atomicFile.exists();
        }

        public void delete() {
            this.atomicFile.delete();
        }

        public void load(HashMap<String, CachedContent> content, SparseArray<String> idToKey) {
            Assertions.checkState(!this.changed);
            if (!readFile(content, idToKey)) {
                content.clear();
                idToKey.clear();
                this.atomicFile.delete();
            }
        }

        public void storeFully(HashMap<String, CachedContent> content) throws IOException {
            writeFile(content);
            this.changed = false;
        }

        public void storeIncremental(HashMap<String, CachedContent> content) throws IOException {
            if (this.changed) {
                storeFully(content);
            }
        }

        public void onUpdate(CachedContent cachedContent) {
            this.changed = true;
        }

        public void onRemove(CachedContent cachedContent) {
            this.changed = true;
        }

        /* JADX INFO: Multiple debug info for r8v2 int: [D('i' int), D('fileHashCode' int)] */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x0073 A[Catch:{ IOException -> 0x00b6, all -> 0x00af }, LOOP:0: B:33:0x0071->B:34:0x0073, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x0096  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x0098  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean readFile(java.util.HashMap<java.lang.String, com.google.android.exoplayer2.upstream.cache.CachedContent> r13, android.util.SparseArray<java.lang.String> r14) {
            /*
                r12 = this;
                com.google.android.exoplayer2.util.AtomicFile r0 = r12.atomicFile
                boolean r0 = r0.exists()
                r1 = 1
                if (r0 != 0) goto L_0x000a
                return r1
            L_0x000a:
                r0 = 0
                r2 = 0
                java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                com.google.android.exoplayer2.util.AtomicFile r4 = r12.atomicFile     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                java.io.InputStream r4 = r4.openRead()     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r3.<init>(r4)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                java.io.DataInputStream r4 = new java.io.DataInputStream     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r4.<init>(r3)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r0 = r4
                int r4 = r0.readInt()     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                if (r4 < 0) goto L_0x00a9
                r5 = 2
                if (r4 <= r5) goto L_0x0028
                goto L_0x00a9
            L_0x0028:
                int r6 = r0.readInt()     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r7 = r6 & 1
                if (r7 == 0) goto L_0x0065
                javax.crypto.Cipher r7 = r12.cipher     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                if (r7 != 0) goto L_0x003a
                com.google.android.exoplayer2.util.Util.closeQuietly(r0)
                return r2
            L_0x003a:
                r7 = 16
                byte[] r7 = new byte[r7]     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r0.readFully(r7)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                javax.crypto.spec.IvParameterSpec r8 = new javax.crypto.spec.IvParameterSpec     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r8.<init>(r7)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                javax.crypto.Cipher r9 = r12.cipher     // Catch:{ InvalidKeyException -> 0x005e, InvalidAlgorithmParameterException -> 0x005c }
                javax.crypto.spec.SecretKeySpec r10 = r12.secretKeySpec     // Catch:{ InvalidKeyException -> 0x005e, InvalidAlgorithmParameterException -> 0x005c }
                r9.init(r5, r10, r8)     // Catch:{ InvalidKeyException -> 0x005e, InvalidAlgorithmParameterException -> 0x005c }
                java.io.DataInputStream r5 = new java.io.DataInputStream     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                javax.crypto.CipherInputStream r9 = new javax.crypto.CipherInputStream     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                javax.crypto.Cipher r10 = r12.cipher     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r9.<init>(r3, r10)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r5.<init>(r9)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r0 = r5
            L_0x005b:
                goto L_0x006b
            L_0x005c:
                r1 = move-exception
                goto L_0x005f
            L_0x005e:
                r1 = move-exception
            L_0x005f:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r5.<init>(r1)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                throw r5     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
            L_0x0065:
                boolean r5 = r12.encrypt     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                if (r5 == 0) goto L_0x005b
                r12.changed = r1     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
            L_0x006b:
                int r5 = r0.readInt()     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r7 = 0
                r8 = 0
            L_0x0071:
                if (r8 >= r5) goto L_0x008b
                com.google.android.exoplayer2.upstream.cache.CachedContent r9 = r12.readCachedContent(r4, r0)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                java.lang.String r10 = r9.key     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r13.put(r10, r9)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                int r10 = r9.f105id     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                java.lang.String r11 = r9.key     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r14.put(r10, r11)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                int r10 = r12.hashCachedContent(r9, r4)     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                int r7 = r7 + r10
                int r8 = r8 + 1
                goto L_0x0071
            L_0x008b:
                int r8 = r0.readInt()     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                int r9 = r0.read()     // Catch:{ IOException -> 0x00b6, all -> 0x00af }
                r10 = -1
                if (r9 != r10) goto L_0x0098
                r9 = 1
                goto L_0x0099
            L_0x0098:
                r9 = 0
            L_0x0099:
                if (r8 != r7) goto L_0x00a3
                if (r9 != 0) goto L_0x009e
                goto L_0x00a3
            L_0x009e:
                com.google.android.exoplayer2.util.Util.closeQuietly(r0)
                return r1
            L_0x00a3:
                com.google.android.exoplayer2.util.Util.closeQuietly(r0)
                return r2
            L_0x00a9:
                com.google.android.exoplayer2.util.Util.closeQuietly(r0)
                return r2
            L_0x00af:
                r1 = move-exception
                if (r0 == 0) goto L_0x00b5
                com.google.android.exoplayer2.util.Util.closeQuietly(r0)
            L_0x00b5:
                throw r1
            L_0x00b6:
                r1 = move-exception
                if (r0 == 0) goto L_0x00bd
                com.google.android.exoplayer2.util.Util.closeQuietly(r0)
            L_0x00bd:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedContentIndex.LegacyStorage.readFile(java.util.HashMap, android.util.SparseArray):boolean");
        }

        private void writeFile(HashMap<String, CachedContent> content) throws IOException {
            DataOutputStream output = null;
            try {
                OutputStream outputStream = this.atomicFile.startWrite();
                if (this.bufferedOutputStream == null) {
                    this.bufferedOutputStream = new ReusableBufferedOutputStream(outputStream);
                } else {
                    this.bufferedOutputStream.reset(outputStream);
                }
                output = new DataOutputStream(this.bufferedOutputStream);
                output.writeInt(2);
                output.writeInt(this.encrypt ? 1 : 0);
                if (this.encrypt) {
                    byte[] initializationVector = new byte[16];
                    this.random.nextBytes(initializationVector);
                    output.write(initializationVector);
                    this.cipher.init(1, this.secretKeySpec, new IvParameterSpec(initializationVector));
                    output.flush();
                    output = new DataOutputStream(new CipherOutputStream(this.bufferedOutputStream, this.cipher));
                }
                output.writeInt(content.size());
                int hashCode = 0;
                for (CachedContent cachedContent : content.values()) {
                    writeCachedContent(cachedContent, output);
                    hashCode += hashCachedContent(cachedContent, 2);
                }
                output.writeInt(hashCode);
                this.atomicFile.endWrite(output);
                Util.closeQuietly((Closeable) null);
                return;
            } catch (InvalidKeyException e) {
                e = e;
            } catch (InvalidAlgorithmParameterException e2) {
                e = e2;
            } catch (Throwable th) {
                Util.closeQuietly(output);
                throw th;
            }
            throw new IllegalStateException(e);
        }

        private int hashCachedContent(CachedContent cachedContent, int version) {
            int result = (cachedContent.f105id * 31) + cachedContent.key.hashCode();
            if (version >= 2) {
                return (result * 31) + cachedContent.getMetadata().hashCode();
            }
            long length = ContentMetadata$$CC.getContentLength$$STATIC$$(cachedContent.getMetadata());
            return (result * 31) + ((int) ((length >>> 32) ^ length));
        }

        private CachedContent readCachedContent(int version, DataInputStream input) throws IOException {
            DefaultContentMetadata metadata;
            int id = input.readInt();
            String key = input.readUTF();
            if (version < 2) {
                long length = input.readLong();
                ContentMetadataMutations mutations = new ContentMetadataMutations();
                ContentMetadataMutations.setContentLength(mutations, length);
                metadata = DefaultContentMetadata.EMPTY.copyWithMutationsApplied(mutations);
            } else {
                metadata = CachedContentIndex.readContentMetadata(input);
            }
            return new CachedContent(id, key, metadata);
        }

        private void writeCachedContent(CachedContent cachedContent, DataOutputStream output) throws IOException {
            output.writeInt(cachedContent.f105id);
            output.writeUTF(cachedContent.key);
            CachedContentIndex.writeContentMetadata(cachedContent.getMetadata(), output);
        }
    }

    private static final class DatabaseStorage implements Storage {
        private static final String[] COLUMNS = {"id", "key", "metadata"};
        private static final String COLUMN_ID = "id";
        private static final int COLUMN_INDEX_ID = 0;
        private static final int COLUMN_INDEX_KEY = 1;
        private static final int COLUMN_INDEX_METADATA = 2;
        private static final String COLUMN_KEY = "key";
        private static final String COLUMN_METADATA = "metadata";
        private static final String TABLE_PREFIX = "ExoPlayerCacheIndex";
        private static final String TABLE_SCHEMA = "(id INTEGER PRIMARY KEY NOT NULL,key TEXT NOT NULL,metadata BLOB NOT NULL)";
        private static final int TABLE_VERSION = 1;
        private static final String WHERE_ID_EQUALS = "id = ?";
        private final DatabaseProvider databaseProvider;
        private String hexUid;
        private final SparseArray<CachedContent> pendingUpdates = new SparseArray<>();
        private String tableName;

        public static void delete(DatabaseProvider databaseProvider2, long uid) throws DatabaseIOException {
            delete(databaseProvider2, Long.toHexString(uid));
        }

        public DatabaseStorage(DatabaseProvider databaseProvider2) {
            this.databaseProvider = databaseProvider2;
        }

        public void initialize(long uid) {
            this.hexUid = Long.toHexString(uid);
            this.tableName = getTableName(this.hexUid);
        }

        public boolean exists() throws DatabaseIOException {
            return VersionTable.getVersion(this.databaseProvider.getReadableDatabase(), 1, this.hexUid) != -1;
        }

        public void delete() throws DatabaseIOException {
            delete(this.databaseProvider, this.hexUid);
        }

        /* JADX INFO: Multiple debug info for r3v1 android.database.Cursor: [D('writableDatabase' android.database.sqlite.SQLiteDatabase), D('cursor' android.database.Cursor)] */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0074, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0075, code lost:
            if (r3 != null) goto L_0x0077;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            r3.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x007f, code lost:
            throw r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void load(java.util.HashMap<java.lang.String, com.google.android.exoplayer2.upstream.cache.CachedContent> r14, android.util.SparseArray<java.lang.String> r15) throws java.io.IOException {
            /*
                r13 = this;
                android.util.SparseArray<com.google.android.exoplayer2.upstream.cache.CachedContent> r0 = r13.pendingUpdates
                int r0 = r0.size()
                r1 = 0
                r2 = 1
                if (r0 != 0) goto L_0x000c
                r0 = 1
                goto L_0x000d
            L_0x000c:
                r0 = 0
            L_0x000d:
                com.google.android.exoplayer2.util.Assertions.checkState(r0)
                com.google.android.exoplayer2.database.DatabaseProvider r0 = r13.databaseProvider     // Catch:{ SQLiteException -> 0x0080 }
                android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ SQLiteException -> 0x0080 }
                java.lang.String r3 = r13.hexUid     // Catch:{ SQLiteException -> 0x0080 }
                int r0 = com.google.android.exoplayer2.database.VersionTable.getVersion(r0, r2, r3)     // Catch:{ SQLiteException -> 0x0080 }
                if (r0 == r2) goto L_0x0036
                com.google.android.exoplayer2.database.DatabaseProvider r3 = r13.databaseProvider     // Catch:{ SQLiteException -> 0x0080 }
                android.database.sqlite.SQLiteDatabase r3 = r3.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0080 }
                r3.beginTransaction()     // Catch:{ SQLiteException -> 0x0080 }
                r13.initializeTable(r3)     // Catch:{ all -> 0x0031 }
                r3.setTransactionSuccessful()     // Catch:{ all -> 0x0031 }
                r3.endTransaction()     // Catch:{ SQLiteException -> 0x0080 }
                goto L_0x0036
            L_0x0031:
                r1 = move-exception
                r3.endTransaction()     // Catch:{ SQLiteException -> 0x0080 }
                throw r1     // Catch:{ SQLiteException -> 0x0080 }
            L_0x0036:
                android.database.Cursor r3 = r13.getCursor()     // Catch:{ SQLiteException -> 0x0080 }
            L_0x003a:
                boolean r4 = r3.moveToNext()     // Catch:{ all -> 0x0072 }
                if (r4 == 0) goto L_0x006d
                int r4 = r3.getInt(r1)     // Catch:{ all -> 0x0072 }
                java.lang.String r5 = r3.getString(r2)     // Catch:{ all -> 0x0072 }
                r6 = 2
                byte[] r6 = r3.getBlob(r6)     // Catch:{ all -> 0x0072 }
                java.io.ByteArrayInputStream r7 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0072 }
                r7.<init>(r6)     // Catch:{ all -> 0x0072 }
                java.io.DataInputStream r8 = new java.io.DataInputStream     // Catch:{ all -> 0x0072 }
                r8.<init>(r7)     // Catch:{ all -> 0x0072 }
                com.google.android.exoplayer2.upstream.cache.DefaultContentMetadata r9 = com.google.android.exoplayer2.upstream.cache.CachedContentIndex.readContentMetadata(r8)     // Catch:{ all -> 0x0072 }
                com.google.android.exoplayer2.upstream.cache.CachedContent r10 = new com.google.android.exoplayer2.upstream.cache.CachedContent     // Catch:{ all -> 0x0072 }
                r10.<init>(r4, r5, r9)     // Catch:{ all -> 0x0072 }
                java.lang.String r11 = r10.key     // Catch:{ all -> 0x0072 }
                r14.put(r11, r10)     // Catch:{ all -> 0x0072 }
                int r11 = r10.f105id     // Catch:{ all -> 0x0072 }
                java.lang.String r12 = r10.key     // Catch:{ all -> 0x0072 }
                r15.put(r11, r12)     // Catch:{ all -> 0x0072 }
                goto L_0x003a
            L_0x006d:
                r3.close()     // Catch:{ SQLiteException -> 0x0080 }
                return
            L_0x0072:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x0074 }
            L_0x0074:
                r2 = move-exception
                if (r3 == 0) goto L_0x007f
                r3.close()     // Catch:{ all -> 0x007b }
                goto L_0x007f
            L_0x007b:
                r4 = move-exception
                com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r4)     // Catch:{ SQLiteException -> 0x0080 }
            L_0x007f:
                throw r2     // Catch:{ SQLiteException -> 0x0080 }
            L_0x0080:
                r0 = move-exception
                r14.clear()
                r15.clear()
                com.google.android.exoplayer2.database.DatabaseIOException r1 = new com.google.android.exoplayer2.database.DatabaseIOException
                r1.<init>(r0)
                throw r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedContentIndex.DatabaseStorage.load(java.util.HashMap, android.util.SparseArray):void");
        }

        public void storeFully(HashMap<String, CachedContent> content) throws IOException {
            SQLiteDatabase writableDatabase;
            try {
                writableDatabase = this.databaseProvider.getWritableDatabase();
                writableDatabase.beginTransaction();
                initializeTable(writableDatabase);
                for (CachedContent cachedContent : content.values()) {
                    addOrUpdateRow(writableDatabase, cachedContent);
                }
                writableDatabase.setTransactionSuccessful();
                this.pendingUpdates.clear();
                writableDatabase.endTransaction();
            } catch (SQLException e) {
                throw new DatabaseIOException(e);
            } catch (Throwable th) {
                writableDatabase.endTransaction();
                throw th;
            }
        }

        public void storeIncremental(HashMap<String, CachedContent> hashMap) throws IOException {
            SQLiteDatabase writableDatabase;
            if (this.pendingUpdates.size() != 0) {
                try {
                    writableDatabase = this.databaseProvider.getWritableDatabase();
                    writableDatabase.beginTransaction();
                    for (int i = 0; i < this.pendingUpdates.size(); i++) {
                        CachedContent cachedContent = this.pendingUpdates.valueAt(i);
                        if (cachedContent == null) {
                            deleteRow(writableDatabase, this.pendingUpdates.keyAt(i));
                        } else {
                            addOrUpdateRow(writableDatabase, cachedContent);
                        }
                    }
                    writableDatabase.setTransactionSuccessful();
                    this.pendingUpdates.clear();
                    writableDatabase.endTransaction();
                } catch (SQLException e) {
                    throw new DatabaseIOException(e);
                } catch (Throwable th) {
                    writableDatabase.endTransaction();
                    throw th;
                }
            }
        }

        public void onUpdate(CachedContent cachedContent) {
            this.pendingUpdates.put(cachedContent.f105id, cachedContent);
        }

        public void onRemove(CachedContent cachedContent) {
            this.pendingUpdates.put(cachedContent.f105id, null);
        }

        private Cursor getCursor() {
            return this.databaseProvider.getReadableDatabase().query(this.tableName, COLUMNS, null, null, null, null, null);
        }

        private void initializeTable(SQLiteDatabase writableDatabase) throws DatabaseIOException {
            VersionTable.setVersion(writableDatabase, 1, this.hexUid, 1);
            dropTable(writableDatabase, this.tableName);
            String str = this.tableName;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 88);
            sb.append("CREATE TABLE ");
            sb.append(str);
            sb.append(" ");
            sb.append(TABLE_SCHEMA);
            writableDatabase.execSQL(sb.toString());
        }

        private void deleteRow(SQLiteDatabase writableDatabase, int key) {
            writableDatabase.delete(this.tableName, WHERE_ID_EQUALS, new String[]{Integer.toString(key)});
        }

        private void addOrUpdateRow(SQLiteDatabase writableDatabase, CachedContent cachedContent) throws IOException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CachedContentIndex.writeContentMetadata(cachedContent.getMetadata(), new DataOutputStream(outputStream));
            byte[] data = outputStream.toByteArray();
            ContentValues values = new ContentValues();
            values.put("id", Integer.valueOf(cachedContent.f105id));
            values.put("key", cachedContent.key);
            values.put("metadata", data);
            writableDatabase.replaceOrThrow(this.tableName, null, values);
        }

        private static void delete(DatabaseProvider databaseProvider2, String hexUid2) throws DatabaseIOException {
            SQLiteDatabase writableDatabase;
            try {
                String tableName2 = getTableName(hexUid2);
                writableDatabase = databaseProvider2.getWritableDatabase();
                writableDatabase.beginTransaction();
                VersionTable.removeVersion(writableDatabase, 1, hexUid2);
                dropTable(writableDatabase, tableName2);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            } catch (SQLException e) {
                throw new DatabaseIOException(e);
            } catch (Throwable th) {
                writableDatabase.endTransaction();
                throw th;
            }
        }

        private static void dropTable(SQLiteDatabase writableDatabase, String tableName2) {
            String valueOf = String.valueOf(tableName2);
            writableDatabase.execSQL(valueOf.length() != 0 ? "DROP TABLE IF EXISTS ".concat(valueOf) : new String("DROP TABLE IF EXISTS "));
        }

        private static String getTableName(String hexUid2) {
            String valueOf = String.valueOf(TABLE_PREFIX);
            String valueOf2 = String.valueOf(hexUid2);
            return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        }
    }
}
