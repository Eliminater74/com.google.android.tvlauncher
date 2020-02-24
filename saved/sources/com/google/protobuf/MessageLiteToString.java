package com.google.protobuf;

import com.google.android.gms.people.PeopleConstants;
import com.google.protobuf.GeneratedMessageLite;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

final class MessageLiteToString {
    private static final String BUILDER_LIST_SUFFIX = "OrBuilderList";
    private static final String BYTES_SUFFIX = "Bytes";
    private static final String LIST_SUFFIX = "List";
    private static final String MAP_SUFFIX = "Map";

    MessageLiteToString() {
    }

    static String toString(MessageLite messageLite, String commentString) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("# ");
        buffer.append(commentString);
        reflectivePrintWithIndent(messageLite, buffer, 0);
        return buffer.toString();
    }

    private static void reflectivePrintWithIndent(MessageLite messageLite, StringBuilder buffer, int indent) {
        Map<String, Method> nameToNoArgMethod;
        boolean hasValue;
        MessageLite messageLite2 = messageLite;
        StringBuilder sb = buffer;
        int i = indent;
        Map<String, Method> hashMap = new HashMap<>();
        Map<String, Method> nameToMethod = new HashMap<>();
        Set<String> getters = new TreeSet<>();
        int i2 = 0;
        for (Method method : messageLite.getClass().getDeclaredMethods()) {
            nameToMethod.put(method.getName(), method);
            if (method.getParameterTypes().length == 0) {
                hashMap.put(method.getName(), method);
                if (method.getName().startsWith(PeopleConstants.Endpoints.ENDPOINT_GET)) {
                    getters.add(method.getName());
                }
            }
        }
        for (String getter : getters) {
            String suffix = getter.replaceFirst(PeopleConstants.Endpoints.ENDPOINT_GET, "");
            if (suffix.endsWith(LIST_SUFFIX) && !suffix.endsWith(BUILDER_LIST_SUFFIX) && !suffix.equals(LIST_SUFFIX)) {
                String valueOf = String.valueOf(suffix.substring(i2, 1).toLowerCase());
                String valueOf2 = String.valueOf(suffix.substring(1, suffix.length() - LIST_SUFFIX.length()));
                String camelCase = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                Method listMethod = (Method) hashMap.get(getter);
                if (listMethod != null && listMethod.getReturnType().equals(List.class)) {
                    printField(sb, i, camelCaseToSnakeCase(camelCase), GeneratedMessageLite.invokeOrDie(listMethod, messageLite2, new Object[i2]));
                }
            }
            if (suffix.endsWith(MAP_SUFFIX) && !suffix.equals(MAP_SUFFIX)) {
                String valueOf3 = String.valueOf(suffix.substring(i2, 1).toLowerCase());
                String valueOf4 = String.valueOf(suffix.substring(1, suffix.length() - MAP_SUFFIX.length()));
                String camelCase2 = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
                Method mapMethod = (Method) hashMap.get(getter);
                if (mapMethod != null && mapMethod.getReturnType().equals(Map.class) && !mapMethod.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(mapMethod.getModifiers())) {
                    printField(sb, i, camelCaseToSnakeCase(camelCase2), GeneratedMessageLite.invokeOrDie(mapMethod, messageLite2, new Object[i2]));
                }
            }
            String valueOf5 = String.valueOf(suffix);
            if (((Method) nameToMethod.get(valueOf5.length() != 0 ? "set".concat(valueOf5) : new String("set"))) != null) {
                if (suffix.endsWith(BYTES_SUFFIX)) {
                    String valueOf6 = String.valueOf(suffix.substring(i2, suffix.length() - BYTES_SUFFIX.length()));
                    if (hashMap.containsKey(valueOf6.length() != 0 ? PeopleConstants.Endpoints.ENDPOINT_GET.concat(valueOf6) : new String(PeopleConstants.Endpoints.ENDPOINT_GET))) {
                    }
                }
                String valueOf7 = String.valueOf(suffix.substring(i2, 1).toLowerCase());
                String valueOf8 = String.valueOf(suffix.substring(1));
                String camelCase3 = valueOf8.length() != 0 ? valueOf7.concat(valueOf8) : new String(valueOf7);
                String valueOf9 = String.valueOf(suffix);
                Method getMethod = (Method) hashMap.get(valueOf9.length() != 0 ? PeopleConstants.Endpoints.ENDPOINT_GET.concat(valueOf9) : new String(PeopleConstants.Endpoints.ENDPOINT_GET));
                String valueOf10 = String.valueOf(suffix);
                Method hasMethod = (Method) hashMap.get(valueOf10.length() != 0 ? "has".concat(valueOf10) : new String("has"));
                if (getMethod != null) {
                    Object value = GeneratedMessageLite.invokeOrDie(getMethod, messageLite2, new Object[i2]);
                    if (hasMethod == null) {
                        nameToNoArgMethod = hashMap;
                        hasValue = !isDefaultValue(value);
                    } else {
                        nameToNoArgMethod = hashMap;
                        hasValue = ((Boolean) GeneratedMessageLite.invokeOrDie(hasMethod, messageLite2, new Object[i2])).booleanValue();
                    }
                    if (hasValue) {
                        printField(sb, i, camelCaseToSnakeCase(camelCase3), value);
                        hashMap = nameToNoArgMethod;
                        i2 = 0;
                    } else {
                        hashMap = nameToNoArgMethod;
                        i2 = 0;
                    }
                } else {
                    i2 = 0;
                }
            }
        }
        if (messageLite2 instanceof GeneratedMessageLite.ExtendableMessage) {
            Iterator<Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object>> iter = ((GeneratedMessageLite.ExtendableMessage) messageLite2).extensions.iterator();
            while (iter.hasNext()) {
                Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object> entry = iter.next();
                int number = ((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).getNumber();
                StringBuilder sb2 = new StringBuilder(13);
                sb2.append("[");
                sb2.append(number);
                sb2.append("]");
                printField(sb, i, sb2.toString(), entry.getValue());
            }
        }
        if (((GeneratedMessageLite) messageLite2).unknownFields != null) {
            ((GeneratedMessageLite) messageLite2).unknownFields.printWithIndent(sb, i);
        }
    }

    private static boolean isDefaultValue(Object o) {
        if (o instanceof Boolean) {
            return !((Boolean) o).booleanValue();
        }
        if (o instanceof Integer) {
            if (((Integer) o).intValue() == 0) {
                return true;
            }
            return false;
        } else if (o instanceof Float) {
            if (((Float) o).floatValue() == 0.0f) {
                return true;
            }
            return false;
        } else if (o instanceof Double) {
            if (((Double) o).doubleValue() == 0.0d) {
                return true;
            }
            return false;
        } else if (o instanceof String) {
            return o.equals("");
        } else {
            if (o instanceof ByteString) {
                return o.equals(ByteString.EMPTY);
            }
            if (o instanceof MessageLite) {
                if (o == ((MessageLite) o).getDefaultInstanceForType()) {
                    return true;
                }
                return false;
            } else if (!(o instanceof Enum) || ((Enum) o).ordinal() != 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    static final void printField(StringBuilder buffer, int indent, String name, Object object) {
        if (object instanceof List) {
            for (Object entry : (List) object) {
                printField(buffer, indent, name, entry);
            }
        } else if (object instanceof Map) {
            for (Map.Entry<?, ?> entry2 : ((Map) object).entrySet()) {
                printField(buffer, indent, name, entry2);
            }
        } else {
            buffer.append(10);
            for (int i = 0; i < indent; i++) {
                buffer.append(' ');
            }
            buffer.append(name);
            if (object instanceof String) {
                buffer.append(": \"");
                buffer.append(TextFormatEscaper.escapeText((String) object));
                buffer.append('\"');
            } else if (object instanceof ByteString) {
                buffer.append(": \"");
                buffer.append(TextFormatEscaper.escapeBytes((ByteString) object));
                buffer.append('\"');
            } else if (object instanceof GeneratedMessageLite) {
                buffer.append(" {");
                reflectivePrintWithIndent((GeneratedMessageLite) object, buffer, indent + 2);
                buffer.append("\n");
                for (int i2 = 0; i2 < indent; i2++) {
                    buffer.append(' ');
                }
                buffer.append("}");
            } else if (object instanceof Map.Entry) {
                buffer.append(" {");
                Map.Entry<?, ?> entry3 = (Map.Entry) object;
                printField(buffer, indent + 2, "key", entry3.getKey());
                printField(buffer, indent + 2, "value", entry3.getValue());
                buffer.append("\n");
                for (int i3 = 0; i3 < indent; i3++) {
                    buffer.append(' ');
                }
                buffer.append("}");
            } else {
                buffer.append(": ");
                buffer.append(object.toString());
            }
        }
    }

    private static final String camelCaseToSnakeCase(String camelCase) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char ch = camelCase.charAt(i);
            if (Character.isUpperCase(ch)) {
                builder.append("_");
            }
            builder.append(Character.toLowerCase(ch));
        }
        return builder.toString();
    }
}
