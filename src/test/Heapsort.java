//package test;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.StandardOpenOption;
//
//public class Heapsort {
//
//    public static void main(String[] args) {
//        if (args.length != 3) {
//            System.out.println("Usage: java Heapsort <data-file-name> <num-buffers> <stat-file-name>");
//            return;
//        }
//
//        String dataFileName = args[0];
//        int numBuffers = Integer.parseInt(args[1]);
//        String statFileName = args[2];
//
//        try {
//            BufferPool bufferPool = new BufferPool(new File(dataFileName), numBuffers);
//            ExternalHeapSort heapSort = new ExternalHeapSort(bufferPool);
//
//            long startTime = System.currentTimeMillis();
//            int totalRecords = (int) (new File(dataFileName).length() / 4);
//            heapSort.sort(totalRecords);
//            long endTime = System.currentTimeMillis();
//
//            bufferPool.flush();
//
//            long executionTime = endTime - startTime;
//
//            appendStatistics(statFileName, dataFileName, bufferPool, executionTime);
//
//            System.out.println("HeapSort execution time: " + executionTime + " ms");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void appendStatistics(String statFileName, String dataFileName, BufferPool bufferPool, long executionTime) throws IOException {
//        String stats = String.format(
//                "Data file: %s\nCache hits: %d\nDisk reads: %d\nDisk writes: %d\nExecution time: %d ms\n",
//                dataFileName, bufferPool.getCacheHits(), bufferPool.getDiskReads(), bufferPool.getDiskWrites(), executionTime
//        );
//
//        Files.write(new File(statFileName).toPath(), stats.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//    }
//}
