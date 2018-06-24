package virtualMemary;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class FIFO extends ReplacementAlgorithm {
    Queue<Integer> q;
    int count=0;
    public FIFO(int pageFrameCount) {
        super(pageFrameCount);
        q=new LinkedList<Integer>();

    }

    @Override
    public void insert(int pageNumber) {
        count++;
        if(count<pageFrameCount)
            q.offer(pageNumber);
        else{
            if(!q.contains(pageNumber)) {
                q.poll();
                pageFaultCount++;
                q.offer(pageNumber);
            }
        }
    }
}
