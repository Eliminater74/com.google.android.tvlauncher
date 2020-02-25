package com.google.android.gms.internal;

import com.google.android.gms.people.PeopleConstants;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/* compiled from: MessageLiteToString */
final class zzgpw {
    static String zza(zzgpt zzgpt, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zza(zzgpt, sb, 0);
        return sb.toString();
    }

    private static void zza(zzgpt zzgpt, StringBuilder sb, int i) {
        boolean z;
        zzgpt zzgpt2 = zzgpt;
        StringBuilder sb2 = sb;
        int i2 = i;
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        TreeSet<String> treeSet = new TreeSet<>();
        for (Method method : zzgpt.getClass().getDeclaredMethods()) {
            hashMap2.put(method.getName(), method);
            if (method.getParameterTypes().length == 0) {
                hashMap.put(method.getName(), method);
                if (method.getName().startsWith(PeopleConstants.Endpoints.ENDPOINT_GET)) {
                    treeSet.add(method.getName());
                }
            }
        }
        for (String str : treeSet) {
            String replaceFirst = str.replaceFirst(PeopleConstants.Endpoints.ENDPOINT_GET, "");
            boolean z2 = true;
            if (replaceFirst.endsWith("List") && !replaceFirst.endsWith("OrBuilderList") && !replaceFirst.equals("List")) {
                String valueOf = String.valueOf(replaceFirst.substring(0, 1).toLowerCase());
                String valueOf2 = String.valueOf(replaceFirst.substring(1, replaceFirst.length() - 4));
                String concat = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                Method method2 = (Method) hashMap.get(str);
                if (method2 != null && method2.getReturnType().equals(List.class)) {
                    zza(sb2, i2, zza(concat), zzgoj.zza(method2, zzgpt2, new Object[0]));
                }
            }
            if (replaceFirst.endsWith("Map") && !replaceFirst.equals("Map")) {
                String valueOf3 = String.valueOf(replaceFirst.substring(0, 1).toLowerCase());
                String valueOf4 = String.valueOf(replaceFirst.substring(1, replaceFirst.length() - 3));
                String concat2 = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
                Method method3 = (Method) hashMap.get(str);
                if (method3 != null && method3.getReturnType().equals(Map.class) && !method3.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(method3.getModifiers())) {
                    zza(sb2, i2, zza(concat2), zzgoj.zza(method3, zzgpt2, new Object[0]));
                }
            }
            String valueOf5 = String.valueOf(replaceFirst);
            if (((Method) hashMap2.get(valueOf5.length() != 0 ? "set".concat(valueOf5) : new String("set"))) != null) {
                if (replaceFirst.endsWith("Bytes")) {
                    String valueOf6 = String.valueOf(replaceFirst.substring(0, replaceFirst.length() - 5));
                    if (hashMap.containsKey(valueOf6.length() != 0 ? PeopleConstants.Endpoints.ENDPOINT_GET.concat(valueOf6) : new String(PeopleConstants.Endpoints.ENDPOINT_GET))) {
                    }
                }
                String valueOf7 = String.valueOf(replaceFirst.substring(0, 1).toLowerCase());
                String valueOf8 = String.valueOf(replaceFirst.substring(1));
                String concat3 = valueOf8.length() != 0 ? valueOf7.concat(valueOf8) : new String(valueOf7);
                String valueOf9 = String.valueOf(replaceFirst);
                Method method4 = (Method) hashMap.get(valueOf9.length() != 0 ? PeopleConstants.Endpoints.ENDPOINT_GET.concat(valueOf9) : new String(PeopleConstants.Endpoints.ENDPOINT_GET));
                String valueOf10 = String.valueOf(replaceFirst);
                Method method5 = (Method) hashMap.get(valueOf10.length() != 0 ? "has".concat(valueOf10) : new String("has"));
                if (method4 != null) {
                    Object zza = zzgoj.zza(method4, zzgpt2, new Object[0]);
                    if (method5 == null) {
                        if (zza instanceof Boolean) {
                            z = !((Boolean) zza).booleanValue();
                        } else if (zza instanceof Integer) {
                            z = ((Integer) zza).intValue() == 0;
                        } else if (zza instanceof Float) {
                            z = ((Float) zza).floatValue() == 0.0f;
                        } else if (zza instanceof Double) {
                            z = ((Double) zza).doubleValue() == 0.0d;
                        } else if (zza instanceof String) {
                            z = zza.equals("");
                        } else if (zza instanceof zzgnb) {
                            z = zza.equals(zzgnb.zza);
                        } else if (zza instanceof zzgpt) {
                            z = zza == ((zzgpt) zza).zzr();
                        } else if (zza instanceof Enum) {
                            z = ((Enum) zza).ordinal() == 0;
                        } else {
                            z = false;
                        }
                        if (z) {
                            z2 = false;
                        }
                    } else {
                        z2 = ((Boolean) zzgoj.zza(method5, zzgpt2, new Object[0])).booleanValue();
                    }
                    if (z2) {
                        zza(sb2, i2, zza(concat3), zza);
                    }
                }
            }
        }
        if (zzgpt2 instanceof zzgoj.zzd) {
            Iterator<Map.Entry<zzgoj.zze, Object>> zze = ((zzgoj.zzd) zzgpt2).zzd.zze();
            while (zze.hasNext()) {
                Map.Entry next = zze.next();
                int i3 = ((zzgoj.zze) next.getKey()).zzb;
                StringBuilder sb3 = new StringBuilder(13);
                sb3.append("[");
                sb3.append(i3);
                sb3.append("]");
                zza(sb2, i2, sb3.toString(), next.getValue());
            }
        }
        zzgoj zzgoj = (zzgoj) zzgpt2;
        if (zzgoj.zzb != null) {
            zzgoj.zzb.zza(sb2, i2);
        }
    }

    static final void zza(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            for (Object zza : (List) obj) {
                zza(sb, i, str, zza);
            }
        } else if (obj instanceof Map) {
            for (Map.Entry zza2 : ((Map) obj).entrySet()) {
                zza(sb, i, str, zza2);
            }
        } else {
            sb.append(10);
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                sb.append(' ');
            }
            sb.append(str);
            if (obj instanceof String) {
                sb.append(": \"");
                sb.append(zzgqz.zza(zzgnb.zza((String) obj)));
                sb.append('\"');
            } else if (obj instanceof zzgnb) {
                sb.append(": \"");
                sb.append(zzgqz.zza((zzgnb) obj));
                sb.append('\"');
            } else if (obj instanceof zzgoj) {
                sb.append(" {");
                zza((zzgoj) obj, sb, i + 2);
                sb.append("\n");
                while (i2 < i) {
                    sb.append(' ');
                    i2++;
                }
                sb.append("}");
            } else if (obj instanceof Map.Entry) {
                sb.append(" {");
                Map.Entry entry = (Map.Entry) obj;
                int i4 = i + 2;
                zza(sb, i4, "key", entry.getKey());
                zza(sb, i4, "value", entry.getValue());
                sb.append("\n");
                while (i2 < i) {
                    sb.append(' ');
                    i2++;
                }
                sb.append("}");
            } else {
                sb.append(": ");
                sb.append(obj.toString());
            }
        }
    }

    private static final String zza(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(charAt));
        }
        return sb.toString();
    }
}
