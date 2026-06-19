package com.danikula.videocache;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

/**
 * Simple memory based {@link Cache} implementation.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 * <p>
 * Modified by LKY-Lockee on 2026/6/22
 */
public class ByteArrayCache implements Cache {

    private volatile byte[] data;
    private volatile boolean completed;

    public ByteArrayCache() {
        this(new byte[0]);
    }

    public ByteArrayCache(byte[] data) {
        this.data = Preconditions.checkNotNull(data);
    }

    @Override
    public int read(byte[] buffer, long offset, int length) {
        if (offset >= data.length) {
            return -1;
        }
        return new ByteArrayInputStream(data).read(buffer, (int) offset, length);
    }

    @Override
    public long available() {
        return data.length;
    }

    @Override
    public void append(byte[] newData, int length) {
        Preconditions.checkNotNull(data);
        Preconditions.checkArgument(length >= 0 && length <= newData.length);

        byte[] appendedData = Arrays.copyOf(data, data.length + length);
        System.arraycopy(newData, 0, appendedData, data.length, length);
        data = appendedData;
    }

    @Override
    public void close() {
    }

    @Override
    public void complete() {
        completed = true;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }
}
