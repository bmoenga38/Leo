//package test;
//
//import static org.junit.Assert.*;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import java.io.File;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.nio.Buffer;
//
//public class BufferTest {
//
//    private Buffer buffer;
//    private RandomAccessFile file;
//    private File testFile;
//
//    @Before
//    public void setUp() throws IOException {
//        testFile = new File("testfile.bin");
//        file = new RandomAccessFile(testFile, "rw");
//        buffer = new Buffer(file);
//    }
//
//    @After
//    public void tearDown() throws IOException {
//        file.close();
//        testFile.delete();
//    }
//
//    @Test
//    public void testSetAndGetRecord() {
//        byte[] record = {1, 2, 3, 4};
//        buffer.setRecord(0, record);
//        assertArrayEquals(record, buffer.getRec(0));
//    }
//
//    @Test
//    public void testSetAndGetBlock() {
//        buffer.setBlock(5);
//        assertEquals(5, buffer.getBlock());
//    }
//
//    @Test
//    public void testSetAndGetDirty() {
//        buffer.setDirty(true);
//        assertTrue(buffer.getDirty());
//        buffer.setDirty(false);
//        assertFalse(buffer.getDirty());
//    }
//
//    @Test
//    public void testWriteBack() throws IOException {
//        byte[] record = {1, 2, 3, 4};
//        buffer.setBlock(0);
//        buffer.setRecord(0, record);
//        buffer.writeBack();
//
//        byte[] readBuffer = new byte[4];
//        file.seek(0);
//        file.read(readBuffer);
//        assertArrayEquals(record, readBuffer);
//    }
//}
