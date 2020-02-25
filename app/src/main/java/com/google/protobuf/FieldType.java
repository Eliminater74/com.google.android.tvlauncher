package com.google.protobuf;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

public enum FieldType {
    DOUBLE(0, Collection.SCALAR, JavaType.DOUBLE),
    FLOAT(1, Collection.SCALAR, JavaType.FLOAT),
    INT64(2, Collection.SCALAR, JavaType.LONG),
    UINT64(3, Collection.SCALAR, JavaType.LONG),
    INT32(4, Collection.SCALAR, JavaType.INT),
    FIXED64(5, Collection.SCALAR, JavaType.LONG),
    FIXED32(6, Collection.SCALAR, JavaType.INT),
    BOOL(7, Collection.SCALAR, JavaType.BOOLEAN),
    STRING(8, Collection.SCALAR, JavaType.STRING),
    MESSAGE(9, Collection.SCALAR, JavaType.MESSAGE),
    BYTES(10, Collection.SCALAR, JavaType.BYTE_STRING),
    UINT32(11, Collection.SCALAR, JavaType.INT),
    ENUM(12, Collection.SCALAR, JavaType.ENUM),
    SFIXED32(13, Collection.SCALAR, JavaType.INT),
    SFIXED64(14, Collection.SCALAR, JavaType.LONG),
    SINT32(15, Collection.SCALAR, JavaType.INT),
    SINT64(16, Collection.SCALAR, JavaType.LONG),
    GROUP(17, Collection.SCALAR, JavaType.MESSAGE),
    DOUBLE_LIST(18, Collection.VECTOR, JavaType.DOUBLE),
    FLOAT_LIST(19, Collection.VECTOR, JavaType.FLOAT),
    INT64_LIST(20, Collection.VECTOR, JavaType.LONG),
    UINT64_LIST(21, Collection.VECTOR, JavaType.LONG),
    INT32_LIST(22, Collection.VECTOR, JavaType.INT),
    FIXED64_LIST(23, Collection.VECTOR, JavaType.LONG),
    FIXED32_LIST(24, Collection.VECTOR, JavaType.INT),
    BOOL_LIST(25, Collection.VECTOR, JavaType.BOOLEAN),
    STRING_LIST(26, Collection.VECTOR, JavaType.STRING),
    MESSAGE_LIST(27, Collection.VECTOR, JavaType.MESSAGE),
    BYTES_LIST(28, Collection.VECTOR, JavaType.BYTE_STRING),
    UINT32_LIST(29, Collection.VECTOR, JavaType.INT),
    ENUM_LIST(30, Collection.VECTOR, JavaType.ENUM),
    SFIXED32_LIST(31, Collection.VECTOR, JavaType.INT),
    SFIXED64_LIST(32, Collection.VECTOR, JavaType.LONG),
    SINT32_LIST(33, Collection.VECTOR, JavaType.INT),
    SINT64_LIST(34, Collection.VECTOR, JavaType.LONG),
    DOUBLE_LIST_PACKED(35, Collection.PACKED_VECTOR, JavaType.DOUBLE),
    FLOAT_LIST_PACKED(36, Collection.PACKED_VECTOR, JavaType.FLOAT),
    INT64_LIST_PACKED(37, Collection.PACKED_VECTOR, JavaType.LONG),
    UINT64_LIST_PACKED(38, Collection.PACKED_VECTOR, JavaType.LONG),
    INT32_LIST_PACKED(39, Collection.PACKED_VECTOR, JavaType.INT),
    FIXED64_LIST_PACKED(40, Collection.PACKED_VECTOR, JavaType.LONG),
    FIXED32_LIST_PACKED(41, Collection.PACKED_VECTOR, JavaType.INT),
    BOOL_LIST_PACKED(42, Collection.PACKED_VECTOR, JavaType.BOOLEAN),
    UINT32_LIST_PACKED(43, Collection.PACKED_VECTOR, JavaType.INT),
    ENUM_LIST_PACKED(44, Collection.PACKED_VECTOR, JavaType.ENUM),
    SFIXED32_LIST_PACKED(45, Collection.PACKED_VECTOR, JavaType.INT),
    SFIXED64_LIST_PACKED(46, Collection.PACKED_VECTOR, JavaType.LONG),
    SINT32_LIST_PACKED(47, Collection.PACKED_VECTOR, JavaType.INT),
    SINT64_LIST_PACKED(48, Collection.PACKED_VECTOR, JavaType.LONG),
    GROUP_LIST(49, Collection.VECTOR, JavaType.MESSAGE),
    MAP(50, Collection.MAP, JavaType.VOID);

    private static final Type[] EMPTY_TYPES = new Type[0];
    private static final FieldType[] VALUES;

    static {
        FieldType[] values = values();
        VALUES = new FieldType[values.length];
        for (FieldType type : values) {
            VALUES[type.f184id] = type;
        }
    }

    private final Collection collection;
    private final Class<?> elementType;
    /* renamed from: id */
    private final int f184id;
    private final JavaType javaType;
    private final boolean primitiveScalar;

    private FieldType(int id, Collection collection2, JavaType javaType2) {
        int i;
        this.f184id = id;
        this.collection = collection2;
        this.javaType = javaType2;
        int i2 = C18591.$SwitchMap$com$google$protobuf$FieldType$Collection[collection2.ordinal()];
        if (i2 == 1) {
            this.elementType = javaType2.getBoxedType();
        } else if (i2 != 2) {
            this.elementType = null;
        } else {
            this.elementType = javaType2.getBoxedType();
        }
        boolean primitiveScalar2 = false;
        if (!(collection2 != Collection.SCALAR || (i = C18591.$SwitchMap$com$google$protobuf$JavaType[javaType2.ordinal()]) == 1 || i == 2 || i == 3)) {
            primitiveScalar2 = true;
        }
        this.primitiveScalar = primitiveScalar2;
    }

    public static FieldType forId(int id) {
        if (id < 0) {
            return null;
        }
        FieldType[] fieldTypeArr = VALUES;
        if (id >= fieldTypeArr.length) {
            return null;
        }
        return fieldTypeArr[id];
    }

    private static Type getGenericSuperList(Class<?> clazz) {
        for (Type genericInterface : clazz.getGenericInterfaces()) {
            if (genericInterface instanceof ParameterizedType) {
                if (List.class.isAssignableFrom((Class) ((ParameterizedType) genericInterface).getRawType())) {
                    return genericInterface;
                }
            }
        }
        Type type = clazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        if (List.class.isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
            return type;
        }
        return null;
    }

    private static Type getListParameter(Class<?> clazz, Type[] realTypes) {
        while (true) {
            int i = 0;
            if (clazz != List.class) {
                Type genericType = getGenericSuperList(clazz);
                if (!(genericType instanceof ParameterizedType)) {
                    realTypes = EMPTY_TYPES;
                    Class<?>[] interfaces = clazz.getInterfaces();
                    int length = interfaces.length;
                    while (true) {
                        if (i >= length) {
                            clazz = clazz.getSuperclass();
                            break;
                        }
                        Class<?> iface = interfaces[i];
                        if (List.class.isAssignableFrom(iface)) {
                            clazz = iface;
                            break;
                        }
                        i++;
                    }
                } else {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Type[] superArgs = parameterizedType.getActualTypeArguments();
                    for (int i2 = 0; i2 < superArgs.length; i2++) {
                        Type superArg = superArgs[i2];
                        if (superArg instanceof TypeVariable) {
                            TypeVariable<?>[] clazzParams = clazz.getTypeParameters();
                            if (realTypes.length == clazzParams.length) {
                                boolean foundReplacement = false;
                                int j = 0;
                                while (true) {
                                    if (j >= clazzParams.length) {
                                        break;
                                    } else if (superArg == clazzParams[j]) {
                                        superArgs[i2] = realTypes[j];
                                        foundReplacement = true;
                                        break;
                                    } else {
                                        j++;
                                    }
                                }
                                if (!foundReplacement) {
                                    String valueOf = String.valueOf(superArg);
                                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 31);
                                    sb.append("Unable to find replacement for ");
                                    sb.append(valueOf);
                                    throw new RuntimeException(sb.toString());
                                }
                            } else {
                                throw new RuntimeException("Type array mismatch");
                            }
                        }
                    }
                    realTypes = superArgs;
                    clazz = (Class) parameterizedType.getRawType();
                }
            } else if (realTypes.length == 1) {
                return realTypes[0];
            } else {
                throw new RuntimeException("Unable to identify parameter type for List<T>");
            }
        }
    }

    /* renamed from: id */
    public int mo28304id() {
        return this.f184id;
    }

    public JavaType getJavaType() {
        return this.javaType;
    }

    public boolean isPacked() {
        return Collection.PACKED_VECTOR.equals(this.collection);
    }

    public boolean isPrimitiveScalar() {
        return this.primitiveScalar;
    }

    public boolean isScalar() {
        return this.collection == Collection.SCALAR;
    }

    public boolean isList() {
        return this.collection.isList();
    }

    public boolean isMap() {
        return this.collection == Collection.MAP;
    }

    public boolean isValidForField(Field field) {
        if (Collection.VECTOR.equals(this.collection)) {
            return isValidForList(field);
        }
        return this.javaType.getType().isAssignableFrom(field.getType());
    }

    private boolean isValidForList(Field field) {
        Class<?> clazz = field.getType();
        if (!this.javaType.getType().isAssignableFrom(clazz)) {
            return false;
        }
        Type[] types = EMPTY_TYPES;
        if (field.getGenericType() instanceof ParameterizedType) {
            types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        }
        Type listParameter = getListParameter(clazz, types);
        if (!(listParameter instanceof Class)) {
            return true;
        }
        return this.elementType.isAssignableFrom((Class) listParameter);
    }

    enum Collection {
        SCALAR(false),
        VECTOR(true),
        PACKED_VECTOR(true),
        MAP(false);

        private final boolean isList;

        private Collection(boolean isList2) {
            this.isList = isList2;
        }

        public boolean isList() {
            return this.isList;
        }
    }

    /* renamed from: com.google.protobuf.FieldType$1 */
    static /* synthetic */ class C18591 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$FieldType$Collection = new int[Collection.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$JavaType = new int[JavaType.values().length];

        static {
            try {
                $SwitchMap$com$google$protobuf$JavaType[JavaType.BYTE_STRING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$protobuf$JavaType[JavaType.MESSAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$protobuf$JavaType[JavaType.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$protobuf$FieldType$Collection[Collection.MAP.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$protobuf$FieldType$Collection[Collection.VECTOR.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$protobuf$FieldType$Collection[Collection.SCALAR.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }
}
