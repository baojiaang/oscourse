package ConditionVar;

import java.util.ArrayList;

public class ReadWriteSimulate {
    public static void main(String[] args) {
        int READER_COUNT = 10;
        int WRITER_COUNT = 3;

        RWLock rwLock = new RWLock();
        ArrayList<Thread> readers = new ArrayList();
        ArrayList<Thread> writers = new ArrayList();

        for(int i = 0; i<READER_COUNT; ++i) {
            Thread reader = new Thread(new Reader(rwLock, i));
            readers.add(reader);
            reader.start();
        }

        for(int i = 0; i<WRITER_COUNT; ++i) {
            Thread writer = new Thread(new Writer(rwLock, i));
            readers.add(writer);
            writer.start();
        }

        while(true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
