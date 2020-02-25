package com.google.android.gms.common.server.response;

import android.util.Base64;
import android.util.Log;

import com.google.android.exoplayer2.C0841C;
import com.google.android.gms.common.internal.Hide;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Hide
public class FastParser<T extends FastJsonResponse> {
    private static final char[] zzf = {'u', 'l', 'l'};
    private static final char[] zzg = {'r', 'u', 'e'};
    private static final char[] zzh = {'r', 'u', 'e', '\"'};
    private static final char[] zzi = {'a', 'l', 's', 'e'};
    private static final char[] zzj = {'a', 'l', 's', 'e', '\"'};
    private static final char[] zzk = {10};
    private static final zza<Integer> zzm = new zza();
    private static final zza<Long> zzn = new zzb();
    private static final zza<Float> zzo = new zzc();
    private static final zza<Double> zzp = new zzd();
    private static final zza<Boolean> zzq = new zze();
    private static final zza<String> zzr = new zzf();
    private static final zza<BigInteger> zzs = new zzg();
    private static final zza<BigDecimal> zzt = new zzh();
    private final char[] zza = new char[1];
    private final char[] zzb = new char[32];
    private final char[] zzc = new char[1024];
    private final StringBuilder zzd = new StringBuilder(32);
    private final StringBuilder zze = new StringBuilder(1024);
    private final Stack<Integer> zzl = new Stack<>();

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0034 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zzb(java.io.BufferedReader r9, char[] r10, java.lang.StringBuilder r11, char[] r12) throws com.google.android.gms.common.server.response.FastParser.ParseException, java.io.IOException {
        /*
            r0 = 0
            r11.setLength(r0)
            int r1 = r10.length
            r9.mark(r1)
            r1 = 0
            r2 = 0
        L_0x000c:
            int r3 = r9.read(r10)
            r4 = -1
            if (r3 == r4) goto L_0x0074
            r4 = r2
            r2 = r1
            r1 = 0
        L_0x0016:
            if (r1 >= r3) goto L_0x006a
            char r5 = r10[r1]
            boolean r6 = java.lang.Character.isISOControl(r5)
            r7 = 1
            if (r6 == 0) goto L_0x003c
            if (r12 == 0) goto L_0x0030
            r6 = 0
        L_0x0024:
            int r8 = r12.length
            if (r6 >= r8) goto L_0x0030
            char r8 = r12[r6]
            if (r8 != r5) goto L_0x002d
            r6 = 1
            goto L_0x0031
        L_0x002d:
            int r6 = r6 + 1
            goto L_0x0024
        L_0x0030:
            r6 = 0
        L_0x0031:
            if (r6 == 0) goto L_0x0034
            goto L_0x003c
        L_0x0034:
            com.google.android.gms.common.server.response.FastParser$ParseException r9 = new com.google.android.gms.common.server.response.FastParser$ParseException
            java.lang.String r10 = "Unexpected control character while reading string"
            r9.<init>(r10)
            throw r9
        L_0x003c:
            r6 = 34
            if (r5 != r6) goto L_0x005d
            if (r2 != 0) goto L_0x005d
            r11.append(r10, r0, r1)
            r9.reset()
            int r1 = r1 + r7
            long r0 = (long) r1
            r9.skip(r0)
            if (r4 == 0) goto L_0x0058
            java.lang.String r9 = r11.toString()
            java.lang.String r9 = com.google.android.gms.common.util.zzn.zza(r9)
            return r9
        L_0x0058:
            java.lang.String r9 = r11.toString()
            return r9
        L_0x005d:
            r6 = 92
            if (r5 != r6) goto L_0x0066
            r2 = r2 ^ 1
            r4 = 1
            goto L_0x0067
        L_0x0066:
            r2 = 0
        L_0x0067:
            int r1 = r1 + 1
            goto L_0x0016
        L_0x006a:
            r11.append(r10, r0, r3)
            int r1 = r10.length
            r9.mark(r1)
            r1 = r2
            r2 = r4
            goto L_0x000c
        L_0x0074:
            com.google.android.gms.common.server.response.FastParser$ParseException r9 = new com.google.android.gms.common.server.response.FastParser$ParseException
            java.lang.String r10 = "Unexpected EOF while parsing string"
            r9.<init>(r10)
            throw r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.FastParser.zzb(java.io.BufferedReader, char[], java.lang.StringBuilder, char[]):java.lang.String");
    }

    public void parse(InputStream inputStream, FastJsonResponse fastJsonResponse) throws ParseException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        try {
            this.zzl.push(0);
            char zzj2 = zzj(bufferedReader);
            if (zzj2 != 0) {
                if (zzj2 == '[') {
                    this.zzl.push(5);
                    Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = fastJsonResponse.getFieldMappings();
                    if (fieldMappings.size() == 1) {
                        FastJsonResponse.Field field = (FastJsonResponse.Field) fieldMappings.entrySet().iterator().next().getValue();
                        fastJsonResponse.addConcreteTypeArrayInternal(field, field.getOutputFieldName(), zza(bufferedReader, field));
                    } else {
                        throw new ParseException("Object array response class must have a single Field");
                    }
                } else if (zzj2 == '{') {
                    this.zzl.push(1);
                    zza(bufferedReader, fastJsonResponse);
                } else {
                    StringBuilder sb = new StringBuilder(19);
                    sb.append("Unexpected token: ");
                    sb.append(zzj2);
                    throw new ParseException(sb.toString());
                }
                zza(0);
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.w("FastParser", "Failed to close reader while parsing.");
                }
            } else {
                throw new ParseException("No data to parse");
            }
        } catch (IOException e2) {
            throw new ParseException(e2);
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (IOException e3) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
            throw th;
        }
    }

    public void parse(String str, FastJsonResponse fastJsonResponse) throws ParseException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        try {
            parse(byteArrayInputStream, fastJsonResponse);
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                Log.w("FastParser", "Failed to close the input stream while parsing.");
            }
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (IOException e2) {
                Log.w("FastParser", "Failed to close the input stream while parsing.");
            }
            throw th;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.server.response.FastParser.zza(java.io.BufferedReader, boolean):boolean
     arg types: [java.io.BufferedReader, int]
     candidates:
      com.google.android.gms.common.server.response.FastParser.zza(com.google.android.gms.common.server.response.FastParser, java.io.BufferedReader):int
      com.google.android.gms.common.server.response.FastParser.zza(java.io.BufferedReader, char[]):int
      com.google.android.gms.common.server.response.FastParser.zza(java.io.BufferedReader, com.google.android.gms.common.server.response.FastJsonResponse$Field<?, ?>):java.util.ArrayList<T>
      com.google.android.gms.common.server.response.FastParser.zza(java.io.BufferedReader, com.google.android.gms.common.server.response.FastParser$zza):java.util.ArrayList<O>
      com.google.android.gms.common.server.response.FastParser.zza(java.io.BufferedReader, com.google.android.gms.common.server.response.FastJsonResponse):boolean
      com.google.android.gms.common.server.response.FastParser.zza(java.io.BufferedReader, boolean):boolean */
    private final boolean zza(BufferedReader bufferedReader, FastJsonResponse fastJsonResponse) throws ParseException, IOException {
        byte[] bArr;
        byte[] bArr2;
        HashMap hashMap;
        BufferedReader bufferedReader2 = bufferedReader;
        FastJsonResponse fastJsonResponse2 = fastJsonResponse;
        Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = fastJsonResponse.getFieldMappings();
        String zza2 = zza(bufferedReader);
        if (zza2 == null) {
            zza(1);
            return false;
        }
        while (zza2 != null) {
            FastJsonResponse.Field field = fieldMappings.get(zza2);
            if (field == null) {
                zza2 = zzb(bufferedReader);
            } else {
                this.zzl.push(4);
                switch (field.getTypeIn()) {
                    case 0:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse2.setInteger(field, zzd(bufferedReader));
                            break;
                        } else {
                            fastJsonResponse2.setIntegers(field, zza(bufferedReader2, zzm));
                            break;
                        }
                    case 1:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse2.setBigInteger(field, zzf(bufferedReader));
                            break;
                        } else {
                            fastJsonResponse2.setBigIntegers(field, zza(bufferedReader2, zzs));
                            break;
                        }
                    case 2:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse2.setLong(field, zze(bufferedReader));
                            break;
                        } else {
                            fastJsonResponse2.setLongs(field, zza(bufferedReader2, zzn));
                            break;
                        }
                    case 3:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse2.setFloat(field, zzg(bufferedReader));
                            break;
                        } else {
                            fastJsonResponse2.setFloats(field, zza(bufferedReader2, zzo));
                            break;
                        }
                    case 4:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse2.setDouble(field, zzh(bufferedReader));
                            break;
                        } else {
                            fastJsonResponse2.setDoubles(field, zza(bufferedReader2, zzp));
                            break;
                        }
                    case 5:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse2.setBigDecimal(field, zzi(bufferedReader));
                            break;
                        } else {
                            fastJsonResponse2.setBigDecimals(field, zza(bufferedReader2, zzt));
                            break;
                        }
                    case 6:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse2.setBoolean(field, zza(bufferedReader2, false));
                            break;
                        } else {
                            fastJsonResponse2.setBooleans(field, zza(bufferedReader2, zzq));
                            break;
                        }
                    case 7:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse2.setString(field, zzc(bufferedReader));
                            break;
                        } else {
                            fastJsonResponse2.setStrings(field, zza(bufferedReader2, zzr));
                            break;
                        }
                    case 8:
                        String zza3 = zza(bufferedReader2, this.zzc, this.zze, zzk);
                        if (zza3 == null) {
                            bArr = null;
                        } else {
                            bArr = Base64.decode(zza3, 0);
                        }
                        fastJsonResponse2.setDecodedBytes(field, bArr);
                        break;
                    case 9:
                        String zza4 = zza(bufferedReader2, this.zzc, this.zze, zzk);
                        if (zza4 == null) {
                            bArr2 = null;
                        } else {
                            bArr2 = Base64.decode(zza4, 10);
                        }
                        fastJsonResponse2.setDecodedBytes(field, bArr2);
                        break;
                    case 10:
                        char zzj2 = zzj(bufferedReader);
                        if (zzj2 == 'n') {
                            zzb(bufferedReader2, zzf);
                            hashMap = null;
                        } else if (zzj2 == '{') {
                            this.zzl.push(1);
                            hashMap = new HashMap();
                            while (true) {
                                char zzj3 = zzj(bufferedReader);
                                if (zzj3 == 0) {
                                    throw new ParseException("Unexpected EOF");
                                } else if (zzj3 == '\"') {
                                    String zzb2 = zzb(bufferedReader2, this.zzb, this.zzd, null);
                                    if (zzj(bufferedReader) != ':') {
                                        String valueOf = String.valueOf(zzb2);
                                        throw new ParseException(valueOf.length() != 0 ? "No map value found for key ".concat(valueOf) : new String("No map value found for key "));
                                    } else if (zzj(bufferedReader) != '\"') {
                                        String valueOf2 = String.valueOf(zzb2);
                                        throw new ParseException(valueOf2.length() != 0 ? "Expected String value for key ".concat(valueOf2) : new String("Expected String value for key "));
                                    } else {
                                        hashMap.put(zzb2, zzb(bufferedReader2, this.zzb, this.zzd, null));
                                        char zzj4 = zzj(bufferedReader);
                                        if (zzj4 != ',') {
                                            if (zzj4 == '}') {
                                                zza(1);
                                            } else {
                                                StringBuilder sb = new StringBuilder(48);
                                                sb.append("Unexpected character while parsing string map: ");
                                                sb.append(zzj4);
                                                throw new ParseException(sb.toString());
                                            }
                                        }
                                    }
                                } else if (zzj3 == '}') {
                                    zza(1);
                                }
                            }
                        } else {
                            throw new ParseException("Expected start of a map object");
                        }
                        fastJsonResponse2.setStringMap(field, hashMap);
                        break;
                    case 11:
                        if (field.isTypeInArray()) {
                            char zzj5 = zzj(bufferedReader);
                            if (zzj5 == 'n') {
                                zzb(bufferedReader2, zzf);
                                fastJsonResponse2.addConcreteTypeArrayInternal(field, field.getOutputFieldName(), null);
                                break;
                            } else {
                                this.zzl.push(5);
                                if (zzj5 == '[') {
                                    fastJsonResponse2.addConcreteTypeArrayInternal(field, field.getOutputFieldName(), zza(bufferedReader2, field));
                                    break;
                                } else {
                                    throw new ParseException("Expected array start");
                                }
                            }
                        } else {
                            char zzj6 = zzj(bufferedReader);
                            if (zzj6 == 'n') {
                                zzb(bufferedReader2, zzf);
                                fastJsonResponse2.addConcreteTypeInternal(field, field.getOutputFieldName(), null);
                                break;
                            } else {
                                this.zzl.push(1);
                                if (zzj6 == '{') {
                                    try {
                                        FastJsonResponse newConcreteTypeInstance = field.newConcreteTypeInstance();
                                        zza(bufferedReader2, newConcreteTypeInstance);
                                        fastJsonResponse2.addConcreteTypeInternal(field, field.getOutputFieldName(), newConcreteTypeInstance);
                                        break;
                                    } catch (InstantiationException e) {
                                        throw new ParseException("Error instantiating inner object", e);
                                    } catch (IllegalAccessException e2) {
                                        throw new ParseException("Error instantiating inner object", e2);
                                    }
                                } else {
                                    throw new ParseException("Expected start of object");
                                }
                            }
                        }
                    default:
                        int typeIn = field.getTypeIn();
                        StringBuilder sb2 = new StringBuilder(30);
                        sb2.append("Invalid field type ");
                        sb2.append(typeIn);
                        throw new ParseException(sb2.toString());
                }
                zza(4);
                zza(2);
                char zzj7 = zzj(bufferedReader);
                if (zzj7 == ',') {
                    zza2 = zza(bufferedReader);
                } else if (zzj7 == '}') {
                    zza2 = null;
                } else {
                    StringBuilder sb3 = new StringBuilder(55);
                    sb3.append("Expected end of object or field separator, but found: ");
                    sb3.append(zzj7);
                    throw new ParseException(sb3.toString());
                }
            }
        }
        zza(1);
        return true;
    }

    private final String zza(BufferedReader bufferedReader) throws ParseException, IOException {
        this.zzl.push(2);
        char zzj2 = zzj(bufferedReader);
        if (zzj2 == '\"') {
            this.zzl.push(3);
            String zzb2 = zzb(bufferedReader, this.zzb, this.zzd, null);
            zza(3);
            if (zzj(bufferedReader) == ':') {
                return zzb2;
            }
            throw new ParseException("Expected key/value separator");
        } else if (zzj2 == ']') {
            zza(2);
            zza(1);
            zza(5);
            return null;
        } else if (zzj2 == '}') {
            zza(2);
            return null;
        } else {
            StringBuilder sb = new StringBuilder(19);
            sb.append("Unexpected token: ");
            sb.append(zzj2);
            throw new ParseException(sb.toString());
        }
    }

    private final String zzb(BufferedReader bufferedReader) throws ParseException, IOException {
        BufferedReader bufferedReader2 = bufferedReader;
        bufferedReader2.mark(1024);
        char zzj2 = zzj(bufferedReader);
        if (zzj2 != '\"') {
            if (zzj2 != ',') {
                int i = 1;
                if (zzj2 == '[') {
                    this.zzl.push(5);
                    bufferedReader2.mark(32);
                    if (zzj(bufferedReader) == ']') {
                        zza(5);
                    } else {
                        bufferedReader.reset();
                        boolean z = false;
                        boolean z2 = false;
                        while (i > 0) {
                            char zzj3 = zzj(bufferedReader);
                            if (zzj3 == 0) {
                                throw new ParseException("Unexpected EOF while parsing array");
                            } else if (!Character.isISOControl(zzj3)) {
                                if (zzj3 == '\"' && !z) {
                                    z2 = !z2;
                                }
                                if (zzj3 == '[' && !z2) {
                                    i++;
                                }
                                if (zzj3 == ']' && !z2) {
                                    i--;
                                }
                                if (zzj3 != '\\' || !z2) {
                                    z = false;
                                } else {
                                    z = !z;
                                }
                            } else {
                                throw new ParseException("Unexpected control character while reading array");
                            }
                        }
                        zza(5);
                    }
                } else if (zzj2 != '{') {
                    bufferedReader.reset();
                    zza(bufferedReader2, this.zzc);
                } else {
                    this.zzl.push(1);
                    bufferedReader2.mark(32);
                    char zzj4 = zzj(bufferedReader);
                    if (zzj4 == '}') {
                        zza(1);
                    } else if (zzj4 == '\"') {
                        bufferedReader.reset();
                        zza(bufferedReader);
                        do {
                        } while (zzb(bufferedReader) != null);
                        zza(1);
                    } else {
                        StringBuilder sb = new StringBuilder(18);
                        sb.append("Unexpected token ");
                        sb.append(zzj4);
                        throw new ParseException(sb.toString());
                    }
                }
            } else {
                throw new ParseException("Missing value");
            }
        } else if (bufferedReader2.read(this.zza) != -1) {
            char c = this.zza[0];
            boolean z3 = false;
            do {
                if (c != '\"' || z3) {
                    if (c == '\\') {
                        z3 = !z3;
                    } else {
                        z3 = false;
                    }
                    if (bufferedReader2.read(this.zza) != -1) {
                        c = this.zza[0];
                    } else {
                        throw new ParseException("Unexpected EOF while parsing string");
                    }
                }
            } while (!Character.isISOControl(c));
            throw new ParseException("Unexpected control character while reading string");
        } else {
            throw new ParseException("Unexpected EOF while parsing string");
        }
        char zzj5 = zzj(bufferedReader);
        if (zzj5 == ',') {
            zza(2);
            return zza(bufferedReader);
        } else if (zzj5 == '}') {
            zza(2);
            return null;
        } else {
            StringBuilder sb2 = new StringBuilder(18);
            sb2.append("Unexpected token ");
            sb2.append(zzj5);
            throw new ParseException(sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public final String zzc(BufferedReader bufferedReader) throws ParseException, IOException {
        return zza(bufferedReader, this.zzb, this.zzd, null);
    }

    private final <O> ArrayList<O> zza(BufferedReader bufferedReader, zza<O> zza2) throws ParseException, IOException {
        char zzj2 = zzj(bufferedReader);
        if (zzj2 == 'n') {
            zzb(bufferedReader, zzf);
            return null;
        } else if (zzj2 == '[') {
            this.zzl.push(5);
            ArrayList<O> arrayList = new ArrayList<>();
            while (true) {
                bufferedReader.mark(1024);
                char zzj3 = zzj(bufferedReader);
                if (zzj3 == 0) {
                    throw new ParseException("Unexpected EOF");
                } else if (zzj3 != ',') {
                    if (zzj3 != ']') {
                        bufferedReader.reset();
                        arrayList.add(zza2.zza(this, bufferedReader));
                    } else {
                        zza(5);
                        return arrayList;
                    }
                }
            }
        } else {
            throw new ParseException("Expected start of array");
        }
    }

    private final String zza(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, char[] cArr2) throws ParseException, IOException {
        char zzj2 = zzj(bufferedReader);
        if (zzj2 == '\"') {
            return zzb(bufferedReader, cArr, sb, cArr2);
        }
        if (zzj2 == 'n') {
            zzb(bufferedReader, zzf);
            return null;
        }
        throw new ParseException("Expected string");
    }

    /* access modifiers changed from: private */
    public final int zzd(BufferedReader bufferedReader) throws ParseException, IOException {
        int i;
        boolean z;
        int i2;
        int i3;
        int i4;
        int zza2 = zza(bufferedReader, this.zzc);
        if (zza2 == 0) {
            return 0;
        }
        char[] cArr = this.zzc;
        if (zza2 > 0) {
            if (cArr[0] == '-') {
                i2 = 1;
                z = true;
                i = Integer.MIN_VALUE;
            } else {
                i2 = 0;
                z = false;
                i = -2147483647;
            }
            if (i2 < zza2) {
                i4 = i2 + 1;
                int digit = Character.digit(cArr[i2], 10);
                if (digit >= 0) {
                    i3 = -digit;
                } else {
                    throw new ParseException("Unexpected non-digit character");
                }
            } else {
                i4 = i2;
                i3 = 0;
            }
            while (i4 < zza2) {
                int i5 = i4 + 1;
                int digit2 = Character.digit(cArr[i4], 10);
                if (digit2 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if (i3 >= -214748364) {
                    int i6 = i3 * 10;
                    if (i6 >= i + digit2) {
                        i3 = i6 - digit2;
                        i4 = i5;
                    } else {
                        throw new ParseException("Number too large");
                    }
                } else {
                    throw new ParseException("Number too large");
                }
            }
            if (!z) {
                return -i3;
            }
            if (i4 > 1) {
                return i3;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    /* access modifiers changed from: private */
    public final long zze(BufferedReader bufferedReader) throws ParseException, IOException {
        boolean z;
        long j;
        long j2;
        int i;
        int zza2 = zza(bufferedReader, this.zzc);
        if (zza2 == 0) {
            return 0;
        }
        char[] cArr = this.zzc;
        if (zza2 > 0) {
            int i2 = 0;
            if (cArr[0] == '-') {
                j = Long.MIN_VALUE;
                i2 = 1;
                z = true;
            } else {
                j = C0841C.TIME_UNSET;
                z = false;
            }
            if (i2 < zza2) {
                i = i2 + 1;
                int digit = Character.digit(cArr[i2], 10);
                if (digit >= 0) {
                    j2 = (long) (-digit);
                } else {
                    throw new ParseException("Unexpected non-digit character");
                }
            } else {
                j2 = 0;
                i = i2;
            }
            while (i < zza2) {
                int i3 = i + 1;
                int digit2 = Character.digit(cArr[i], 10);
                if (digit2 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if (j2 >= -922337203685477580L) {
                    long j3 = j2 * 10;
                    long j4 = (long) digit2;
                    if (j3 >= j + j4) {
                        j2 = j3 - j4;
                        i = i3;
                    } else {
                        throw new ParseException("Number too large");
                    }
                } else {
                    throw new ParseException("Number too large");
                }
            }
            if (!z) {
                return -j2;
            }
            if (i > 1) {
                return j2;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    /* access modifiers changed from: private */
    public final BigInteger zzf(BufferedReader bufferedReader) throws ParseException, IOException {
        int zza2 = zza(bufferedReader, this.zzc);
        if (zza2 == 0) {
            return null;
        }
        return new BigInteger(new String(this.zzc, 0, zza2));
    }

    /* access modifiers changed from: private */
    public final boolean zza(BufferedReader bufferedReader, boolean z) throws ParseException, IOException {
        while (true) {
            char zzj2 = zzj(bufferedReader);
            if (zzj2 != '\"') {
                if (zzj2 == 'f') {
                    zzb(bufferedReader, z ? zzj : zzi);
                    return false;
                } else if (zzj2 == 'n') {
                    zzb(bufferedReader, zzf);
                    return false;
                } else if (zzj2 == 't') {
                    zzb(bufferedReader, z ? zzh : zzg);
                    return true;
                } else {
                    StringBuilder sb = new StringBuilder(19);
                    sb.append("Unexpected token: ");
                    sb.append(zzj2);
                    throw new ParseException(sb.toString());
                }
            } else if (!z) {
                z = true;
            } else {
                throw new ParseException("No boolean value found in string");
            }
        }
    }

    /* access modifiers changed from: private */
    public final float zzg(BufferedReader bufferedReader) throws ParseException, IOException {
        int zza2 = zza(bufferedReader, this.zzc);
        if (zza2 == 0) {
            return 0.0f;
        }
        return Float.parseFloat(new String(this.zzc, 0, zza2));
    }

    /* access modifiers changed from: private */
    public final double zzh(BufferedReader bufferedReader) throws ParseException, IOException {
        int zza2 = zza(bufferedReader, this.zzc);
        if (zza2 == 0) {
            return 0.0d;
        }
        return Double.parseDouble(new String(this.zzc, 0, zza2));
    }

    /* access modifiers changed from: private */
    public final BigDecimal zzi(BufferedReader bufferedReader) throws ParseException, IOException {
        int zza2 = zza(bufferedReader, this.zzc);
        if (zza2 == 0) {
            return null;
        }
        return new BigDecimal(new String(this.zzc, 0, zza2));
    }

    private final <T extends FastJsonResponse> ArrayList<T> zza(BufferedReader bufferedReader, FastJsonResponse.Field<?, ?> field) throws ParseException, IOException {
        ArrayList<T> arrayList = new ArrayList<>();
        char zzj2 = zzj(bufferedReader);
        if (zzj2 == ']') {
            zza(5);
            return arrayList;
        } else if (zzj2 == 'n') {
            zzb(bufferedReader, zzf);
            zza(5);
            return null;
        } else if (zzj2 == '{') {
            this.zzl.push(1);
            while (true) {
                try {
                    FastJsonResponse newConcreteTypeInstance = field.newConcreteTypeInstance();
                    if (!zza(bufferedReader, newConcreteTypeInstance)) {
                        return arrayList;
                    }
                    arrayList.add(newConcreteTypeInstance);
                    char zzj3 = zzj(bufferedReader);
                    if (zzj3 != ',') {
                        if (zzj3 == ']') {
                            zza(5);
                            return arrayList;
                        }
                        StringBuilder sb = new StringBuilder(19);
                        sb.append("Unexpected token: ");
                        sb.append(zzj3);
                        throw new ParseException(sb.toString());
                    } else if (zzj(bufferedReader) == '{') {
                        this.zzl.push(1);
                    } else {
                        throw new ParseException("Expected start of next object in array");
                    }
                } catch (InstantiationException e) {
                    throw new ParseException("Error instantiating inner object", e);
                } catch (IllegalAccessException e2) {
                    throw new ParseException("Error instantiating inner object", e2);
                }
            }
        } else {
            StringBuilder sb2 = new StringBuilder(19);
            sb2.append("Unexpected token: ");
            sb2.append(zzj2);
            throw new ParseException(sb2.toString());
        }
    }

    private final char zzj(BufferedReader bufferedReader) throws ParseException, IOException {
        if (bufferedReader.read(this.zza) == -1) {
            return 0;
        }
        while (Character.isWhitespace(this.zza[0])) {
            if (bufferedReader.read(this.zza) == -1) {
                return 0;
            }
        }
        return this.zza[0];
    }

    private final int zza(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        int i;
        char zzj2 = zzj(bufferedReader);
        if (zzj2 == 0) {
            throw new ParseException("Unexpected EOF");
        } else if (zzj2 == ',') {
            throw new ParseException("Missing value");
        } else if (zzj2 == 'n') {
            zzb(bufferedReader, zzf);
            return 0;
        } else {
            bufferedReader.mark(1024);
            if (zzj2 == '\"') {
                i = 0;
                boolean z = false;
                while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                    char c = cArr[i];
                    if (Character.isISOControl(c)) {
                        throw new ParseException("Unexpected control character while reading string");
                    } else if (c != '\"' || z) {
                        if (c == '\\') {
                            z = !z;
                        } else {
                            z = false;
                        }
                        i++;
                    } else {
                        bufferedReader.reset();
                        bufferedReader.skip((long) (i + 1));
                        return i;
                    }
                }
            } else {
                cArr[0] = zzj2;
                int i2 = 1;
                while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                    if (cArr[i] == '}' || cArr[i] == ',' || Character.isWhitespace(cArr[i]) || cArr[i] == ']') {
                        bufferedReader.reset();
                        bufferedReader.skip((long) (i - 1));
                        cArr[i] = 0;
                        return i;
                    }
                    i2 = i + 1;
                }
            }
            if (i == cArr.length) {
                throw new ParseException("Absurdly long value");
            }
            throw new ParseException("Unexpected EOF");
        }
    }

    private final void zzb(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        int i = 0;
        while (i < cArr.length) {
            int read = bufferedReader.read(this.zzb, 0, cArr.length - i);
            if (read != -1) {
                int i2 = 0;
                while (i2 < read) {
                    if (cArr[i2 + i] == this.zzb[i2]) {
                        i2++;
                    } else {
                        throw new ParseException("Unexpected character");
                    }
                }
                i += read;
            } else {
                throw new ParseException("Unexpected EOF");
            }
        }
    }

    private final void zza(int i) throws ParseException {
        if (!this.zzl.isEmpty()) {
            int intValue = this.zzl.pop().intValue();
            if (intValue != i) {
                StringBuilder sb = new StringBuilder(46);
                sb.append("Expected state ");
                sb.append(i);
                sb.append(" but had ");
                sb.append(intValue);
                throw new ParseException(sb.toString());
            }
            return;
        }
        StringBuilder sb2 = new StringBuilder(46);
        sb2.append("Expected state ");
        sb2.append(i);
        sb2.append(" but had empty stack");
        throw new ParseException(sb2.toString());
    }

    interface zza<O> {
        O zza(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException;
    }

    @Hide
    public static class ParseException extends Exception {
        public ParseException(String str) {
            super(str);
        }

        public ParseException(String str, Throwable th) {
            super(str, th);
        }

        public ParseException(Throwable th) {
            super(th);
        }
    }
}
