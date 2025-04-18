package com.i0nut02.lsm.memtable;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.i0nut02.lsm.types.ByteArrayPair;

public class MemSetTest {

    @Test
    public void testPutAndGet() {
        MemSet memSet = new MemSet();

        byte[] key = "key1".getBytes();
        byte[] value = "value1".getBytes();

        memSet.put(new ByteArrayPair(key, value));
        byte[] result = memSet.get(key);
        assertTrue(Arrays.equals(value, result));
    }

    @Test
    public void testDelete() {
        MemSet memSet = new MemSet();
        byte[] key = "key2".getBytes();
        byte[] value = "value2".getBytes();

        memSet.put(new ByteArrayPair(key, value));
        memSet.delete(key);

        assertNull(memSet.get(key));
    }

    @Test
    public void testFlush() {
        MemSet memSet = new MemSet();
        byte[] key = "key3".getBytes();
        byte[] value = "value3".getBytes();

        memSet.put(new ByteArrayPair(key, value));
        List<ByteArrayPair> flushed = memSet.flush();

        assertEquals(1, flushed.size());
        assertTrue(Arrays.equals(flushed.get(0).key(), key));
        assertTrue(Arrays.equals(flushed.get(0).value(), value));
    }

    @Test
    public void testSize() {
        MemSet memSet = new MemSet();
        assertEquals(0, memSet.size());

        memSet.put(new ByteArrayPair("k".getBytes(), "v".getBytes()));
        assertEquals(1, memSet.size());
    }
}
