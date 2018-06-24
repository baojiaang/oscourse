package javaFileSystem;

import java.util.Arrays;

public class Inode {
    public final static int SIZE=64;
    int flags;  //  if  flags==0   the file is not used
    int owner;
    int fileSize;
    int pointer[]=new int[13];


    @Override
    public String toString() {
        return "Inode{" +
                "flags=" + flags +
                ", fileSize=" + fileSize +
                ", pointer=" + Arrays.toString(pointer) +
                '}';
    }
}
