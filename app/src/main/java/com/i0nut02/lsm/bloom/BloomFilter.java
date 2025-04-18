package com.i0nut02.lsm.bloom;

import java.util.Arrays;
import java.util.BitSet;
import static java.lang.Math.log;

public class BloomFilter {
    static final int DEFAULT_SIZE = 1 << 20;
    static final double FALSE_POSITIVE_RATE = 0.001;
    
    final int size;
    final int hashFuntions;

    BitSet bitSet;

    /**
     * Create a BloomFilter with default size and deafault false positive rate
     */
    public BloomFilter() {
        this(DEFAULT_SIZE, FALSE_POSITIVE_RATE);
    }

    /**
     * Create a BloomFilter with deafault false positive rate
     *
     * @param expectedInsertions The number of expected insertions.
     */
    public BloomFilter(int expectedInsertions) {
        this(expectedInsertions, FALSE_POSITIVE_RATE);
    }

    /**
     * Create a BloomFilter with defined expected intertions and defined false positive rate
     * 
     * @param expectedInsertions The number of expected insertions.
     * @param falsePositiveRate The rate of false positive.
     */
    public BloomFilter(int expectedInsertions, double falsePositiveRate) {
        size = (int) (-expectedInsertions * log(falsePositiveRate) / (log(2) * log(2)));
        hashFuntions = (int) ((this.size / expectedInsertions) * log(2)); 
        bitSet = new BitSet(this.size);
    }

    /**
     * Add to the BloomFilter insertion of the byte array
     * 
     * @param key byte array to add
     */
    public void add(byte[] key) {
        int hash1 = Arrays.hashCode(key);
        int hash2 = ~hash1;

        for (int i = 0; i < this.hashFuntions; i++) {
            bitSet.set(Math.abs(hash1 + i * hash2) % size);
        }
        return;
    }

    /**
     * Check if the bits of the key have been set
     * 
     * @param key The value to check
     * @return If the bits are set or not 
     */
    public boolean lookup(byte[] key) {
        int hash1 = Arrays.hashCode(key);
        int hash2 = ~hash1;

        for (int i = 0; i < this.hashFuntions; i++) {
            if (!bitSet.get(Math.abs(hash1 + i * hash2) % size)) {
                return false;
            }
        }
        return true;
    }
}
    