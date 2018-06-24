package javaFileSystem;

public class InodeBlock {
    Inode node[]=new Inode[Disk.BLOCK_SIZE/Inode.SIZE]; //node 数组的长度
    public InodeBlock(){
        for(int i=0;i<Disk.BLOCK_SIZE/Inode.SIZE;i++){
            node[i]=new Inode();
        }
    }


    public String toString() {
        String s="INODEBLOCK:\n";
        for(int i=0;i<node.length;i++)
            s+=node[i]+"\n";
        return s;
    }

    }
