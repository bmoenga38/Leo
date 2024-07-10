import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * BufferPool class that manages buffers and interacts with a file.
 * @author sheril George
 * @version 1.0
 */
public class BufferPool {

    private RandomAccessFile disk;
    private Buffer[] pool;
    private int totalBlocksInFile;
    private final static int BLOCK_SIZE = 4096;
    private final static int RECORD_SIZE = 4;
    private int numberOfBuffers;
    private int[] lruCounters;
    private int lruCounter;

    /**
     * Constructor to initialize BufferPool.
     *
     * @param file      the file to interact with
     * @param numBuffer the number of buffers
     */
    public BufferPool(File file, int numBuffer) throws IOException {
        this.numberOfBuffers = numBuffer;
        this.disk = new RandomAccessFile(file, "rw");
        this.totalBlocksInFile = (int) ((disk.length() + BLOCK_SIZE - 1) / BLOCK_SIZE);
        this.pool = new Buffer[numBuffer];
        this.lruCounters = new int[numBuffer];
        this.lruCounter = 0;

        for (int i = 0; i < numBuffer; i++) {
            pool[i] = new Buffer(disk);
            lruCounters[i] = -1;
        }
    }

    /**
     * Get a record from the buffer pool.
     *
     * @param index the index of the record
     * @return the record as a byte array
     * @throws IOException if an I/O error occurs
     */
    public byte[] getRec(int index) throws IOException {
        int block = index / BLOCK_SIZE;
        int pos = index % BLOCK_SIZE;

        for (int i = 0; i < numberOfBuffers; i++) {
            if (pool[i].getBlock() == block) {
                if (i != 0) {
                    shift(i);
                }
                Stat.cacheHits++;
                return pool[0].getRec(pos);
            }
        }

        leastRecent();
        int readIndex = block * BLOCK_SIZE;
        disk.seek(readIndex);
        disk.readFully(pool[0].getBuffer());
        pool[0].setBlock(block);
        Stat.diskReads++;
        return pool[0].getRec(pos);
    }

    private void leastRecent() throws IOException {
        Buffer lru = pool[numberOfBuffers - 1];
        if (lru.getDirty()) {
            lru.writeBack();
            Stat.diskWrites++;
        }
        System.arraycopy(pool, 0, pool, 1, numberOfBuffers - 1);
        pool[0] = lru;
    }

    private void shift(int i) {
        Buffer target = pool[i];
        for (int j = i; j > 0; j--) {
            pool[j] = pool[j - 1];
            lruCounters[j] = lruCounters[j - 1];
        }
        pool[0] = target;
        lruCounters[0] = lruCounter++;
    }

    public void flush() throws IOException {
        for (int i = 0; i < numberOfBuffers; i++) {
            if (pool[i].getDirty()) {
                pool[i].writeBack();
                Stat.diskWrites++;
            }
        }
        disk.close();
    }

    public int numBlocks() {
        return this.totalBlocksInFile;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BufferPool state:\n");
        for (int i = 0; i < pool.length; i++) {
            sb.append("Buffer ").append(i).append(": ").append(pool[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public void setRec(int index, byte[] record) throws IOException {
        int block = index / BLOCK_SIZE;
        int pos = index % BLOCK_SIZE;

        for (int i = 0; i < pool.length; i++) {
            if (pool[i].getBlock() == block) {
                if (i != 0) {
                    shift(i);
                }
                pool[0].setRecord(pos, record);
                pool[0].setDirty(true);
                Stat.cacheHits++;
                return;
            }
        }

        leastRecent();
        int readIndex = block * BLOCK_SIZE;
        disk.seek(readIndex);
        disk.readFully(pool[0].getBuffer());
        pool[0].setBlock(block);
        pool[0].setRecord(pos, record);
        pool[0].setDirty(true);
        Stat.diskReads++;
    }

    public int getCacheHits() {
        return Stat.cacheHits;
    }

    public int getDiskReads() {
        return Stat.diskReads;
    }

    public int getDiskWrites() {
        return Stat.diskWrites;
    }
}
