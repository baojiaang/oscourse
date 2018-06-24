package javaFileSystem;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Disk {
    public final static int BLOCK_SIZE=512;    //块大小
    public final static int NUM_BLOCKS=1000;   //块数
    public final static int POINTER_PER_BLOCK=(BLOCK_SIZE/4);  // the number of pointers in a disk block to other disk blocks

    private int readCount=0;
    private int writeCount=0;

    // the file representing the simulated  disk
    private File fileName;
    private RandomAccessFile disk;
    public Disk(){
        try {
            fileName=new File("DISK");
            disk=new RandomAccessFile(fileName,"rw");
        }
        catch (IOException e){
            System.err.println("打开磁盘失败了");
            System.exit(1);
        }
    }


    private void seek(int blockNum) throws IOException {
        if(blockNum<0||blockNum>=NUM_BLOCKS)
            throw new RuntimeException("Attempt to read block"  + blockNum + " is out of range");
        disk.seek(blockNum*BLOCK_SIZE);
    }
    public void read(int blocknum, byte[] buffer) {
        if (buffer.length != BLOCK_SIZE)
            throw new RuntimeException(
                    "Read: bad buffer size " + buffer.length);
        try {
            seek(blocknum);
            disk.read(buffer);
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        readCount++;
    }

    public void read(int blocknum, SuperBlock block) {
        try {
            seek(blocknum);
            block.size = disk.readInt();
            block.iSize = disk.readInt();
            block.freeList = disk.readInt();
        }
        catch (EOFException e) {
            if (blocknum != 0) {
                System.err.println(e);
                System.exit(1);
            }
            block.size = block.iSize = block.freeList = 0;
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        readCount++;
    }

    public void read(int blocknum, InodeBlock block) {
        try {
            seek(blocknum);
            for (int i=0; i<block.node.length; i++) {
                block.node[i].flags = disk.readInt();
                block.node[i].owner = disk.readInt();
                //block.node[i].size = disk.readInt();
                block.node[i].fileSize = disk.readInt();
                for (int j=0; j<13; j++)
                    block.node[i].pointer[j] = disk.readInt();
            }
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        readCount++;
    }

    public void read(int blocknum, IndirectBlock block) {
        try {
            seek(blocknum);
            for (int i=0; i<block.pointer.length; i++)
                block.pointer[i] = disk.readInt();
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        readCount++;
    }

    /**
     * Write from the buffer to to blockNum
     */
    public void write(int blocknum, byte[] buffer) {
        if (buffer.length != BLOCK_SIZE)
            throw new RuntimeException(
                    "Write: bad buffer size " + buffer.length);
        try {
            seek(blocknum);
            disk.write(buffer);
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        writeCount++;
    }

    public void write(int blocknum, SuperBlock block) {
        try {
            seek(blocknum);
            disk.writeInt(block.size);
            disk.writeInt(block.iSize);
            disk.writeInt(block.freeList);
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        writeCount++;
    }

    public void write(int blocknum, InodeBlock block) {
        try {
            seek(blocknum);
            for (int i=0; i<block.node.length; i++) {
                disk.writeInt(block.node[i].flags);
                //disk.writeInt(block.node[i].owner);
                //disk.writeInt(block.node[i].size);
                disk.writeInt(block.node[i].fileSize);
                for (int j=0; j<13; j++)
                    disk.writeInt(block.node[i].pointer[j]);
            }
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        writeCount++;
    }

    public void write(int blocknum, IndirectBlock block) {
        try {
            seek(blocknum);
            for (int i=0; i<block.pointer.length; i++)
                disk.writeInt(block.pointer[i]);
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        writeCount++;
    }

    public void stop(boolean removeFile) {
        //System.out.println (toString());
        System.out.println (generateStats());
        if (removeFile)
            fileName.delete();
    }

    public void stop() {
        stop(true);
    }

    /**
     * Outputs statistics from the file system.
     */
    //public String toString() {
    public String generateStats() {
        return ("DISK: Read count: " + readCount + " Write count: " +
                writeCount);
    }

}
