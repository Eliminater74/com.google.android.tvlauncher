package com.google.protobuf.nano;

import com.google.android.gms.people.PeopleConstants;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class MessageNanoPrinter {
    private static final String INDENT = "  ";
    private static final int MAX_STRING_LEN = 200;

    private MessageNanoPrinter() {
    }

    public static <T extends MessageNano> String print(T message) {
        if (message == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        try {
            print(null, message, new StringBuffer(), buf);
            return buf.toString();
        } catch (IllegalAccessException e) {
            String valueOf = String.valueOf(e.getMessage());
            return valueOf.length() != 0 ? "Error printing proto: ".concat(valueOf) : new String("Error printing proto: ");
        } catch (InvocationTargetException e2) {
            String valueOf2 = String.valueOf(e2.getMessage());
            return valueOf2.length() != 0 ? "Error printing proto: ".concat(valueOf2) : new String("Error printing proto: ");
        }
    }

    private static void print(String identifier, Object object, StringBuffer indentBuf, StringBuffer buf) throws IllegalAccessException, InvocationTargetException {
        Field[] fieldArr;
        Object obj = object;
        StringBuffer stringBuffer = indentBuf;
        StringBuffer stringBuffer2 = buf;
        if (obj != null) {
            if (obj instanceof MessageNano) {
                int origIndentBufLength = indentBuf.length();
                if (identifier != null) {
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(deCamelCaseify(identifier));
                    stringBuffer2.append(" <\n");
                    stringBuffer.append(INDENT);
                }
                Class<?> clazz = object.getClass();
                Field[] fields = clazz.getFields();
                int length = fields.length;
                int i = 0;
                while (i < length) {
                    Field field = fields[i];
                    int modifiers = field.getModifiers();
                    String fieldName = field.getName();
                    if ("cachedSize".equals(fieldName)) {
                        fieldArr = fields;
                    } else if ((modifiers & 1) != 1 || (modifiers & 8) == 8) {
                        fieldArr = fields;
                    } else if (fieldName.startsWith("_")) {
                        fieldArr = fields;
                    } else if (!fieldName.endsWith("_")) {
                        Class<?> fieldType = field.getType();
                        Object value = field.get(obj);
                        if (!fieldType.isArray()) {
                            fieldArr = fields;
                            print(fieldName, value, stringBuffer, stringBuffer2);
                        } else if (fieldType.getComponentType() == Byte.TYPE) {
                            print(fieldName, value, stringBuffer, stringBuffer2);
                            fieldArr = fields;
                        } else {
                            int len = value == null ? 0 : Array.getLength(value);
                            int i2 = 0;
                            while (i2 < len) {
                                print(fieldName, Array.get(value, i2), stringBuffer, stringBuffer2);
                                i2++;
                                fields = fields;
                            }
                            fieldArr = fields;
                        }
                    } else {
                        fieldArr = fields;
                    }
                    i++;
                    fields = fieldArr;
                }
                for (Method method : clazz.getMethods()) {
                    String name = method.getName();
                    if (name.startsWith("set")) {
                        String subfieldName = name.substring(3);
                        try {
                            String valueOf = String.valueOf(subfieldName);
                            if (((Boolean) clazz.getMethod(valueOf.length() != 0 ? "has".concat(valueOf) : new String("has"), new Class[0]).invoke(obj, new Object[0])).booleanValue()) {
                                try {
                                    String valueOf2 = String.valueOf(subfieldName);
                                    try {
                                        print(subfieldName, clazz.getMethod(valueOf2.length() != 0 ? PeopleConstants.Endpoints.ENDPOINT_GET.concat(valueOf2) : new String(PeopleConstants.Endpoints.ENDPOINT_GET), new Class[0]).invoke(obj, new Object[0]), stringBuffer, stringBuffer2);
                                    } catch (NoSuchMethodException e) {
                                    }
                                } catch (NoSuchMethodException e2) {
                                }
                            }
                        } catch (NoSuchMethodException e3) {
                        }
                    }
                }
                if (identifier != null) {
                    stringBuffer.setLength(origIndentBufLength);
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(">\n");
                }
            } else {
                String identifier2 = deCamelCaseify(identifier);
                stringBuffer2.append(stringBuffer);
                stringBuffer2.append(identifier2);
                stringBuffer2.append(": ");
                if (obj instanceof String) {
                    String stringMessage = sanitizeString((String) obj);
                    stringBuffer2.append("\"");
                    stringBuffer2.append(stringMessage);
                    stringBuffer2.append("\"");
                } else if (obj instanceof byte[]) {
                    appendQuotedBytes((byte[]) obj, stringBuffer2);
                } else {
                    stringBuffer2.append(obj);
                }
                stringBuffer2.append("\n");
            }
        }
    }

    private static String deCamelCaseify(String identifier) {
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < identifier.length(); i++) {
            char currentChar = identifier.charAt(i);
            if (i == 0) {
                out.append(Character.toLowerCase(currentChar));
            } else if (Character.isUpperCase(currentChar)) {
                out.append('_');
                out.append(Character.toLowerCase(currentChar));
            } else {
                out.append(currentChar);
            }
        }
        return out.toString();
    }

    private static String sanitizeString(String str) {
        if (!str.startsWith("http") && str.length() > 200) {
            str = String.valueOf(str.substring(0, 200)).concat("[...]");
        }
        return escapeString(str);
    }

    private static String escapeString(String str) {
        int strLen = str.length();
        StringBuilder b = new StringBuilder(strLen);
        for (int i = 0; i < strLen; i++) {
            char original = str.charAt(i);
            if (original < ' ' || original > '~' || original == '\"' || original == '\'') {
                b.append(String.format("\\u%04x", Integer.valueOf(original)));
            } else {
                b.append(original);
            }
        }
        return b.toString();
    }

    private static void appendQuotedBytes(byte[] bytes, StringBuffer builder) {
        if (bytes == null) {
            builder.append("\"\"");
            return;
        }
        builder.append('\"');
        for (byte b : bytes) {
            int ch = b & 255;
            if (ch == 92 || ch == 34) {
                builder.append('\\');
                builder.append((char) ch);
            } else if (ch < 32 || ch >= 127) {
                builder.append(String.format("\\%03o", Integer.valueOf(ch)));
            } else {
                builder.append((char) ch);
            }
        }
        builder.append('\"');
    }
}
