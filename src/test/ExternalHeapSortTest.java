//package test;
//
//import static org.junit.Assert.*;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import java.io.File;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.nio.ByteBuffer;
//import java.util.Random;
//
//public class ExternalHeapSortTest {
//
//    private BufferPool bufferPool;
//    private ExternalHeapSort heapSort;
//    private File testFile;
//
//    @Before
//    public void setUp() throws IOException {
//        testFile = new File("test_sort.bin");
//        RandomAccessFile file = new RandomAccessFile(testFile, "rw");
//        Random random = new Random();
//        file.setLength(4096 * 3); // Set file size to 3 blocks
//        for (int i = 0; i < 1024; i++) {
//            short key = (short) random.nextInt(30000);
//            short value = (short) random.nextInt(30000);
//            file.writeShort(key);
//            file.writeShort(value);
//        }
//        file.close();
//        bufferPool = new BufferPool(testFile, 3);
//        heapSort = new ExternalHeapSort(bufferPool);
//    }
//
//    @After
//    public void tearDown() throws IOException {
//        bufferPool.flush();
//        testFile.delete();
//    }
//
//    @Test
//    public void testSort() throws IOException {
//        int totalRecords = 1024;
//        heapSort.sort(totalRecords);
//
//        short previousKey = Short.MIN_VALUE;
//        for (int i = 0; i < totalRecords; i++) {
//            byte[] recordBytes = bufferPool.getRec(i * 4);
//            short key = ByteBuffer.wrap(recordBytes).getShort();
//            assertTrue(previousKey <= key);
//            previousKey = key;
//        }
//    }
//}
