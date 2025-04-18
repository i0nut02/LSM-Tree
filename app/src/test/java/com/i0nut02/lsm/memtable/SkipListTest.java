package com.i0nut02.lsm.memtable;

import com.i0nut02.lsm.types.ByteArrayPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SkipListTest {

    private SkipList skipList;

    @BeforeEach
    void setUp() {
        skipList = new SkipList();
    }

    @Test
    void testInsertAndGet() {
        byte[] key = {1, 2, 3};
        byte[] value = {4, 5, 6};
        skipList.put(new ByteArrayPair(key, value));

        byte[] retrieved = skipList.get(key);
        assertArrayEquals(value, retrieved, "The retrieved value should match the inserted value");
    }

    @Test
    void testInsertOverwrite() {
        byte[] key = {1, 2, 3};
        byte[] value1 = {4, 5, 6};
        byte[] value2 = {7, 8, 9};

        skipList.put(new ByteArrayPair(key, value1));
        skipList.put(new ByteArrayPair(key, value2)); // overwrite

        byte[] retrieved = skipList.get(key);
        assertArrayEquals(value2, retrieved, "The value should be overwritten");
    }

    @Test
    void testDelete() {
        byte[] key = {1, 2, 3};
        byte[] value = {4, 5, 6};
        skipList.put(new ByteArrayPair(key, value));
        skipList.delete(key);

        byte[] retrieved = skipList.get(key);
        assertNull(retrieved, "After deletion, the value should be null");
    }

    @Test
    void testSize() {
        assertEquals(0, skipList.size(), "Initial size should be 0");

        skipList.put(new ByteArrayPair(new byte[]{1}, new byte[]{2}));
        skipList.put(new ByteArrayPair(new byte[]{3}, new byte[]{4}));

        assertEquals(2, skipList.size(), "Size should be 2 after inserting two elements");
    }

    @Test
    void testFlush() {
        byte[] key1 = {1};
        byte[] key2 = {2};
        byte[] val1 = {10};
        byte[] val2 = {20};

        skipList.put(new ByteArrayPair(key1, val1));
        skipList.put(new ByteArrayPair(key2, val2));

        List<ByteArrayPair> flushed = skipList.flush();
        assertEquals(2, flushed.size(), "Flush should return 2 elements");
        System.out.println(skipList.toString());
    }
}
