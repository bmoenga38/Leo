import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Buffer class to manage a block of data.
 */
public class Buffer {

    private byte[] buffer;
    private int block;
    private boolean dirty;
    private RandomAccessFile file;

    public Buffer(RandomAccessFile file) {
        this.file = file;
        buffer = new byte[4096];
        block = -1;
        dirty = false;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public boolean getDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public byte[] getRec(int pos) {
        byte[] rec = new byte[4];
        System.arraycopy(buffer, pos, rec, 0, 4);
        return rec;
    }

    public void setRecord(int pos, byte[] record) {
        System.arraycopy(record, 0, buffer, pos, 4);
        dirty = true;
    }

    public void writeBack() throws IOException {
        if (dirty && block != -1) {
            file.seek(block * 4096);
            file.write(buffer);
            Stat.diskWrites++;
            dirty = false;
        }
    }

    @Override
    public String toString() {
        return "Block: " + block + ", Dirty: " + dirty;
    }
}
