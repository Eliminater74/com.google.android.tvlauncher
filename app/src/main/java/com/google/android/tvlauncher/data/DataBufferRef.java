package com.google.android.tvlauncher.data;

abstract class DataBufferRef {
    private AbstractDataBuffer mDataBuffer;
    private int mPosition;

    DataBufferRef(AbstractDataBuffer dataBuffer, int position) {
        if (position < 0 || position >= dataBuffer.getCount()) {
            StringBuilder sb = new StringBuilder(56);
            sb.append("Position [");
            sb.append(position);
            sb.append("] is out of bounds [0, ");
            sb.append(dataBuffer.getCount() - 1);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        }
        this.mDataBuffer = dataBuffer;
        this.mPosition = position;
    }

    /* access modifiers changed from: protected */
    public long getLong(int column) {
        return this.mDataBuffer.getLong(this.mPosition, column);
    }

    /* access modifiers changed from: protected */
    public int getInt(int column) {
        return this.mDataBuffer.getInt(this.mPosition, column);
    }

    /* access modifiers changed from: protected */
    public String getString(int column) {
        return this.mDataBuffer.getString(this.mPosition, column);
    }

    /* access modifiers changed from: protected */
    public byte[] getBlob(int column) {
        return this.mDataBuffer.getBlob(this.mPosition, column);
    }
}
