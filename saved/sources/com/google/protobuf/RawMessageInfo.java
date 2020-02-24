package com.google.protobuf;

final class RawMessageInfo implements MessageInfo {
    private final MessageLite defaultInstance;
    private final int flags;
    private final String info;
    private final Object[] objects;

    /* JADX INFO: Multiple debug info for r0v1 char: [D('value' int), D('position' int)] */
    RawMessageInfo(MessageLite defaultInstance2, String info2, Object[] objects2) {
        this.defaultInstance = defaultInstance2;
        this.info = info2;
        this.objects = objects2;
        int position = 0 + 1;
        int position2 = info2.charAt(0);
        if (position2 < 55296) {
            this.flags = position2;
            return;
        }
        int result = position2 & 8191;
        int shift = 13;
        while (true) {
            int position3 = position + 1;
            int position4 = info2.charAt(position);
            int value = position4;
            if (position4 >= 55296) {
                result |= (value & 8191) << shift;
                shift += 13;
                position = position3;
            } else {
                this.flags = (value << shift) | result;
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public String getStringInfo() {
        return this.info;
    }

    /* access modifiers changed from: package-private */
    public Object[] getObjects() {
        return this.objects;
    }

    public MessageLite getDefaultInstance() {
        return this.defaultInstance;
    }

    public ProtoSyntax getSyntax() {
        return (this.flags & 1) == 1 ? ProtoSyntax.PROTO2 : ProtoSyntax.PROTO3;
    }

    public boolean isMessageSetWireFormat() {
        return (this.flags & 2) == 2;
    }
}
