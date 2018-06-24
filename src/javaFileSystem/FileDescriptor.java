package javaFileSystem;

public class FileDescriptor {  //文件描述符   打开文件表的索引
    private Inode inode;   //定位索引节点
    private int inumber;  //索引号
    private int seekptr;  //当前位置指针

    public FileDescriptor(Inode inode, int inumber){
        this.inode = inode;
        this.inumber = inumber;
        seekptr = 0;
    }

    public FileDescriptor(Inode inode) {
        this.inode = inode;
    }

    public Inode getInode(){
        return inode;
    }

    public int getInumber(){
        return inumber;
    }

    public int getSeekPointer(){
        return seekptr;
    }

    public void setSeekPointer(int i){
        seekptr = i;
    }

    public void setFileSize(int newSize){
        inode.fileSize = newSize;
    }

}
