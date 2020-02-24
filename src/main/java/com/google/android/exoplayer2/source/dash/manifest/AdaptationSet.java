package com.google.android.exoplayer2.source.dash.manifest;

import java.util.Collections;
import java.util.List;

public class AdaptationSet {
    public static final int ID_UNSET = -1;
    public final List<Descriptor> accessibilityDescriptors;

    /* renamed from: id */
    public final int f95id;
    public final List<Representation> representations;
    public final List<Descriptor> supplementalProperties;
    public final int type;

    public AdaptationSet(int id, int type2, List<Representation> representations2, List<Descriptor> accessibilityDescriptors2, List<Descriptor> supplementalProperties2) {
        List<Descriptor> list;
        List<Descriptor> list2;
        this.f95id = id;
        this.type = type2;
        this.representations = Collections.unmodifiableList(representations2);
        if (accessibilityDescriptors2 == null) {
            list = Collections.emptyList();
        } else {
            list = Collections.unmodifiableList(accessibilityDescriptors2);
        }
        this.accessibilityDescriptors = list;
        if (supplementalProperties2 == null) {
            list2 = Collections.emptyList();
        } else {
            list2 = Collections.unmodifiableList(supplementalProperties2);
        }
        this.supplementalProperties = list2;
    }
}
