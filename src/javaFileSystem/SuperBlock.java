package javaFileSystem;

public class SuperBlock {
    // number of blocks in the file system.
    int size;
    //public int size;
    // number of index blocks in the file system.
    int iSize;  //索引块数量
    //public int iSize;
    // first block of the free list
    int freeList;   //空闲列表中的第一个块
    //public int freeList;

    public String toString () {
        return
                "SUPERBLOCK:\n"
                        + "Size: " + size
                        + "  Isize: " + iSize
                        + "  freeList: " + freeList;
    }
}
