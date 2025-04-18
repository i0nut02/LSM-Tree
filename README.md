# LSM-Tree
A simple implementation of a key-value database.

## How an LSM-Tree works
An **LSM-Tree** (*Log-Structured Merge-Tree*) is a method for implementing the storage layer of a database optimized for **high write throughput**.  
This is achieved by using an **append-only** policy for writes on disk, avoiding costly random disk accesses and instead performing **efficient sequential writes**.  
The efficiency of an LSM-Tree relies on the following components:

- **Memtable**  
  An in-memory data structure that allows efficient retrieval and deletion of data based on keys.  
  Typically, a **Skip List** is used, enabling fast **point queries** and **range queries** (e.g., retrieving all keys between 4 and 10).

- **Write-Ahead Log (WAL)**  
  A file where all **mutating operations** (insertions, deletions, updates) are logged **before** they are applied to the Memtable.  
  **Read operations** (*SELECT* queries) are not logged.  
  The WAL ensures **durability**: in case of a crash, the Memtable can be fully rebuilt from the WAL, preserving consistency.

- **Sorted String Table (SSTable)**  
  An **immutable** file written to disk when the Memtable is flushed.  
  SSTables store **sorted key-value pairs**, allowing efficient retrieval via **binary search** when combined with offset metadata.

- **Compaction Process**  
  Over time, multiple SSTables are created. Searching across many tables would be inefficient, so **compaction** periodically **merges** SSTables, removing duplicates and obsolete entries, and organizing data into fewer, larger, more sorted files across multiple **levels**.

- **Bloom Filters**  
  A **space-efficient** probabilistic data structure that allows fast membership tests.  
  A Bloom filter can quickly tell if a key **definitely does not** exist in an SSTable, or **may exist** (with a small probability of false positives).  
  This significantly reduces unnecessary disk reads.
