//package test;
//
//import static org.junit.Assert.*;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import java.io.File;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//
//public class BufferPoolTest {
//
//    private BufferPool bufferPool;
//    private File testFile;
//
//    @Before
//    public void setUp() throws IOException {
//        testFile = new File("testfile.bin");
//        if (!testFile.exists()) {
//            RandomAccessFile raf = new RandomAccessFile(testFile, "rw");
//            raf.setLength(4096); // Set file size to one block
//            raf.close();
//        }
//        bufferPool = new BufferPool(testFile, 3);
//    }
//
//    @After
//    public void tearDown() throws IOException {
//        bufferPool.flush();
//        testFile.delete();
//    }
//
//    @Test
//    public void testGetAndSetRec() throws IOException {
//        byte[] record = {1, 2, 3, 4};
//        bufferPool.setRec(0, record);
//        Assert.assertArrayEquals(record, bufferPool.getRec(0));
//    }
//
//    @Test
//    public void testCacheHits() throws IOException {
//        byte[] record = {1, 2, 3, 4};
//        bufferPool.setRec(0, record);
//        bufferPool.getRec(0); // Should be a cache hit
//        Assert.assertEquals(1, bufferPool.getCacheHits());
//    }
//
//    @Test
//    public void testDiskReadsAndWrites() throws IOException {
//        byte[] record = {1, 2, 3, 4};
//        bufferPool.setRec(0, record);
//        bufferPool.getRec(0);
//        Assert.assertEquals(1, bufferPool.getDiskReads());
//        Assert.assertEquals(1, bufferPool.getDiskWrites());
//    }
//}
