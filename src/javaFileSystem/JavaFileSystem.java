package javaFileSystem;

import java.io.File;
import java.util.LinkedList;

public class JavaFileSystem implements FileSystem {
    // Set up the constants for the whence field in seek
    public static final int SEEK_SET    = 0;
    public static final int SEEK_CUR    = 1;
    public static final int SEEK_END    = 2;
   // public int IBLOCK_SIZE = 8;  //索引块大小
    public int iNumber_conter=0;
    private Disk disk;
    private SuperBlock superBlock;
    private LinkedList<Integer> freelist;
    private FileTable fileTable;
    private int SINGLE_INDIRECTBLOCK_SIZE = disk.BLOCK_SIZE*disk.POINTER_PER_BLOCK;
    private int DOUBLE_INDIRECTBLOCK_SIZE=SINGLE_INDIRECTBLOCK_SIZE*disk.POINTER_PER_BLOCK;
    private int TRIPLE_INDIRECTBLOCK_SIZE=DOUBLE_INDIRECTBLOCK_SIZE*disk.POINTER_PER_BLOCK;



    JavaFileSystem() {
        //throw new RuntimeException("not implemented");
        disk=new Disk();
        fileTable=new FileTable();
    }

    // Initialize the the disk to a state representing an empty file-system.
    // Fill in the super block, mark all inodes as "unused", and link
    // all data blocks into the free list.
    public int formatDisk(int size, int iSize) {
        if(size<iSize||size==0||iSize==0)
            return -1;
            superBlock=new SuperBlock();  // initialzie the superblock
            superBlock.size = size;
            superBlock.iSize=iSize;
            disk.write(0,superBlock);
            for(int i=1;i<iSize;i++){
                InodeBlock inodeBlock=new InodeBlock();
                for(int j=0;j<inodeBlock.node.length;j++)
                {
                    inodeBlock.node[j].flags=0;   //make evey node is unused
                }
                disk.write(i,inodeBlock);
            }
            freelist=new LinkedList<>();
            for(int i=iSize+1;i<size;i++){
                freelist.add(i);
            }
                return 0;

    } // formatDisk

    // Close all files and shut down the simulated disk.
    public int shutdown() {
        for(int i=0;i<fileTable.fdArray.length-1;i++){
             if(fileTable.isValid(i))
                close(i);
            }

            superBlock.freeList=this.freelist.size();
            disk.stop();
            return  0;

        }
     // shutdown

    // Create a new (empty) file and return a file descriptor.
    public int create() {
        for(int i=1;i<superBlock.size;i++){
            InodeBlock inodeBlock=new InodeBlock();
            disk.read(i,inodeBlock);
            for(int j=1;j<inodeBlock.node.length-1;j++){
                Inode inode=inodeBlock.node[j];
                if(inode.flags==0){
                    inode.flags=1;
                    inode.fileSize=0;
                    inode.owner=0;
                    int fd=fileTable.allocate();
                    if(fd!=-1){
                        fileTable.add(inode,iNumber_conter,fd);
                        iNumber_conter++;
                        return  fd;

                    }
                }
            }
        }
            return -1;
    } // create

    // Return the inumber of an open file
    public int inumber(int fd) {
        if(!fileTable.isValid(fd))
            return -1;
        return fileTable.getInumber(fd)+1;

    }

    // Open an existing file identified by its inumber
    public int open(int iNumber) {
        for(int i=0;i<fileTable.fdArray.length;i++){
            if(fileTable.fdArray[i]!=null&&fileTable.fdArray[i].getInumber()==iNumber)
                return  -1;  //  check the file whether is opened
        }
        InodeBlock inodeBlock=new InodeBlock();
        for(int i=1;i<superBlock.iSize;i++) {
            disk.read(i,inodeBlock);
            for(int j=0;j<inodeBlock.node.length;j++){
                Inode inode=inodeBlock.node[j];
                if(inode.flags!=0&&inode.owner==iNumber)
                {
                    int index=fileTable.allocate();
                    if(index>=0){
                        FileDescriptor fileDescriptor=new FileDescriptor(inode,iNumber);
                            fileTable.fdArray[index]=fileDescriptor;
                                return index;
                    }
                    return  -1;   //  no sapce in filetable
                }
            }
        }
        return -1;   //  this means the file does not exists
    } // open


    public int open1(int iNumber){
        InodeBlock inodeBlock=new InodeBlock();
        int blockNumber=iNumber/inodeBlock.node.length+1;
        disk.read(blockNumber,inodeBlock);
        Inode inode=inodeBlock.node[iNumber%inodeBlock.node.length];
        int fd=fileTable.allocate();
        if(fd>=0)
            return fd;
        return -1;
    }

    // Read up to buffer.length bytes from the open file indicated by fd,
    // starting at the current seek pointer, and update the seek pointer.
    // Return the number of bytes read, which may be less than buffer.length
    // if the seek pointer is near the current end of the file.
    // In particular, return 0 if the seek pointer is greater than or
    // equal to the size of the file.
    public int read(int fd, byte[] buffer) {
        if(!fileTable.isValid(fd))
            return -1;
        FileDescriptor fileDescriptor=fileTable.fdArray[fd];
        Inode inode=fileDescriptor.getInode();
        int pointer=0;
        byte[] buf=new byte[disk.BLOCK_SIZE];
        disk.read(inode.pointer[pointer],buf);
        for(int i=fileDescriptor.getSeekPointer();i<buffer.length;i++)
        {
            buffer[i]=buf[i];
        }
        return buffer.length;

    } // read

    // Transfer buffer.length bytes from the buffer to the file, starting
    // at the current seek pointer, and add buffer.length to the seek pointer.
    public int write(int fd, byte[] buffer) {
        throw new RuntimeException("not implemented");
    } // write

    // Update the seek pointer by offset, according to whence.
    // Return the new value of the seek pointer.
    // If the new seek pointer would be negative, leave it unchanged
    // and return -1.
    public int seek(int fd, int offset, int whence) {
        throw new RuntimeException("not implemented");
    } // seek

    // Write the inode back to disk and free the file table entry
    public int close(int fd) {
        if(!fileTable.isValid(fd))
            return -1;
        FileDescriptor fileDescriptor=fileTable.fdArray[fd];
        InodeBlock inodeBlock=new InodeBlock();
        int iNumber=fileTable.getInumber(fd);
        int blockNumber=(iNumber/inodeBlock.node.length)+1;
        disk.read(blockNumber,inodeBlock);
        inodeBlock.node[iNumber%inodeBlock.node.length]=fileDescriptor.getInode();
        disk.write(blockNumber,inodeBlock);
        fileTable.free(fd);
        return 0;
    } // close




    // Delete the file with the given inumber, freeing all of its blocks.
    public int delete(int iNumber) {
        throw new RuntimeException("not implemented");
    } // delete

    public String toString() {
        throw new RuntimeException("not implemented");
    }
}
