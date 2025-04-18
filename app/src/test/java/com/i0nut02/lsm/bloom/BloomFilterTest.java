package com.i0nut02.lsm.bloom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BloomFilterTest {

    private BloomFilter bloomFilter;

    @BeforeEach
    public void setup() {
        bloomFilter = new BloomFilter();
    }

    @Test
    public void testAddAndLookup_ExistingKey_ReturnsTrue() {
        byte[] key = "key".getBytes();
        
        // Add the key to the BloomFilter
        bloomFilter.add(key);
        
        // Verify that the key is considered as "possibly present"
        assertTrue(bloomFilter.lookup(key), "The key should be found after being added.");
    }

    @Test
    public void testAddAndLookup_NonExistentKey_ReturnsFalse() {
        byte[] key1 = "key1".getBytes();
        byte[] key2 = "key2".getBytes();

        // Add one key and lookup another
        bloomFilter.add(key1);
        
        // Lookup a key that was not added
        assertFalse(bloomFilter.lookup(key2), "The key should not be found if not added.");
    }
}
