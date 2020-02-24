package com.google.protobuf;

import java.lang.reflect.Field;

final class OneofInfo {
    private final Field caseField;

    /* renamed from: id */
    private final int f185id;
    private final Field valueField;

    public OneofInfo(int id, Field caseField2, Field valueField2) {
        this.f185id = id;
        this.caseField = caseField2;
        this.valueField = valueField2;
    }

    public int getId() {
        return this.f185id;
    }

    public Field getCaseField() {
        return this.caseField;
    }

    public Field getValueField() {
        return this.valueField;
    }
}
