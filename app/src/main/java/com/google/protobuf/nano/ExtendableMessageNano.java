package com.google.protobuf.nano;

import com.google.protobuf.nano.ExtendableMessageNano;
import java.io.IOException;

public abstract class ExtendableMessageNano<M extends ExtendableMessageNano<M>> extends MessageNano {
    protected FieldArray unknownFieldData;

    /* access modifiers changed from: protected */
    public int computeSerializedSize() {
        int size = 0;
        if (this.unknownFieldData != null) {
            for (int i = 0; i < this.unknownFieldData.size(); i++) {
                size += this.unknownFieldData.dataAt(i).computeSerializedSize();
            }
        }
        return size;
    }

    /* access modifiers changed from: protected */
    public int computeSerializedSizeAsMessageSet() {
        int size = 0;
        if (this.unknownFieldData != null) {
            for (int i = 0; i < this.unknownFieldData.size(); i++) {
                size += this.unknownFieldData.dataAt(i).computeSerializedSizeAsMessageSet();
            }
        }
        return size;
    }

    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.unknownFieldData != null) {
            for (int i = 0; i < this.unknownFieldData.size(); i++) {
                this.unknownFieldData.dataAt(i).writeTo(output);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeAsMessageSetTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.unknownFieldData != null) {
            for (int i = 0; i < this.unknownFieldData.size(); i++) {
                this.unknownFieldData.dataAt(i).writeAsMessageSetTo(output);
            }
        }
    }

    public final boolean hasExtension(Extension<M, ?> extension) {
        FieldArray fieldArray = this.unknownFieldData;
        if (fieldArray == null || fieldArray.get(WireFormatNano.getTagFieldNumber(extension.tag)) == null) {
            return false;
        }
        return true;
    }

    public final <T> T getExtension(Extension<M, T> extension) {
        FieldData field;
        FieldArray fieldArray = this.unknownFieldData;
        if (fieldArray == null || (field = fieldArray.get(WireFormatNano.getTagFieldNumber(extension.tag))) == null) {
            return null;
        }
        return field.getValue(extension);
    }

    /* JADX INFO: Multiple debug info for r1v0 'this'  com.google.protobuf.nano.ExtendableMessageNano: [D('field' com.google.protobuf.nano.FieldData), D('typedThis' M)] */
    public final <T> M setExtension(Extension<M, T> extension, T value) {
        int fieldNumber = WireFormatNano.getTagFieldNumber(extension.tag);
        if (value == null) {
            FieldArray fieldArray = this.unknownFieldData;
            if (fieldArray != null) {
                fieldArray.remove(fieldNumber);
                if (this.unknownFieldData.isEmpty()) {
                    this.unknownFieldData = null;
                }
            }
        } else {
            FieldData field = null;
            FieldArray fieldArray2 = this.unknownFieldData;
            if (fieldArray2 == null) {
                this.unknownFieldData = new FieldArray();
            } else {
                field = fieldArray2.get(fieldNumber);
            }
            if (field == null) {
                this.unknownFieldData.put(fieldNumber, new FieldData(extension, value));
            } else {
                field.setValue(extension, value);
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public final boolean storeUnknownField(CodedInputByteBufferNano input, int tag) throws IOException {
        int startPos = input.getPosition();
        if (!input.skipField(tag)) {
            return false;
        }
        storeUnknownFieldData(WireFormatNano.getTagFieldNumber(tag), new UnknownFieldData(tag, input.getData(startPos, input.getPosition() - startPos)));
        return true;
    }

    private void storeUnknownFieldData(int fieldNumber, UnknownFieldData unknownField) throws IOException {
        FieldData field = null;
        FieldArray fieldArray = this.unknownFieldData;
        if (fieldArray == null) {
            this.unknownFieldData = new FieldArray();
        } else {
            field = fieldArray.get(fieldNumber);
        }
        if (field == null) {
            field = new FieldData();
            this.unknownFieldData.put(fieldNumber, field);
        }
        field.addUnknownField(unknownField);
    }

    /* access modifiers changed from: protected */
    public final boolean storeUnknownFieldAsMessageSet(CodedInputByteBufferNano input, int maybeMessageSetItemTag) throws IOException {
        if (maybeMessageSetItemTag != WireFormatNano.MESSAGE_SET_ITEM_TAG) {
            return storeUnknownField(input, maybeMessageSetItemTag);
        }
        int typeId = 0;
        byte[] rawBytes = null;
        while (true) {
            int tag = input.readTag();
            if (tag == 0) {
                break;
            } else if (tag == WireFormatNano.MESSAGE_SET_TYPE_ID_TAG) {
                typeId = input.readUInt32();
            } else if (tag == WireFormatNano.MESSAGE_SET_MESSAGE_TAG) {
                int startPos = input.getPosition();
                input.skipField(tag);
                rawBytes = input.getData(startPos, input.getPosition() - startPos);
            } else if (!input.skipField(tag)) {
                break;
            }
        }
        input.checkLastTagWas(WireFormatNano.MESSAGE_SET_ITEM_END_TAG);
        if (rawBytes == null || typeId == 0) {
            return true;
        }
        storeUnknownFieldData(typeId, new UnknownFieldData(typeId, rawBytes));
        return true;
    }

    public M clone() throws CloneNotSupportedException {
        M cloned = (ExtendableMessageNano) super.clone();
        InternalNano.cloneUnknownFieldData(this, cloned);
        return cloned;
    }

    public final FieldArray getUnknownFieldArray() {
        return this.unknownFieldData;
    }
}
