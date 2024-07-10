import java.io.IOException;
import java.nio.ByteBuffer;

public class ExternalHeapSort {
    private BufferPool bufferPool;
    private static final int RECORD_SIZE = 4; // 4 bytes per record
    private static final int BLOCK_SIZE = 4096; // 4096 bytes per block
    private static final int RECORDS_PER_BLOCK = BLOCK_SIZE / RECORD_SIZE;

    public ExternalHeapSort(BufferPool bufferPool) {
        this.bufferPool = bufferPool;
    }

    public void sort(int totalRecords) throws IOException {
        buildHeap(totalRecords);
        for (int end = totalRecords - 1; end > 0; end--) {
            swap(0, end);
            heapify(0, end);
        }
    }

    private void buildHeap(int totalRecords) throws IOException {
        for (int i = totalRecords / 2 - 1; i >= 0; i--) {
            heapify(i, totalRecords);
        }
    }

    private void heapify(int index, int heapSize) throws IOException {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heapSize && compare(left, largest) > 0) {
            largest = left;
        }

        if (right < heapSize && compare(right, largest) > 0) {
            largest = right;
        }

        if (largest != index) {
            swap(index, largest);
            heapify(largest, heapSize);
        }
    }

    private int compare(int index1, int index2) throws IOException {
        byte[] rec1 = bufferPool.getRec(index1 * RECORD_SIZE);
        byte[] rec2 = bufferPool.getRec(index2 * RECORD_SIZE);
        return ByteBuffer.wrap(rec1).getShort() - ByteBuffer.wrap(rec2).getShort();
    }

    private void swap(int index1, int index2) throws IOException {
        byte[] rec1 = bufferPool.getRec(index1 * RECORD_SIZE);
        byte[] rec2 = bufferPool.getRec(index2 * RECORD_SIZE);
        bufferPool.setRec(index1 * RECORD_SIZE, rec2);
        bufferPool.setRec(index2 * RECORD_SIZE, rec1);
    }
}
