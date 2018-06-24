package virtualMemary;

import java.util.LinkedList;

public class LRU1 extends ReplacementAlgorithm {
    int count=0;
    LinkedList<Integer> l;
    public LRU1(int pageFrameCount) {
        super(pageFrameCount);
        l=new LinkedList<Integer>();
    }

    @Override
    public void insert(int pageNumber) {
        if(count<pageFrameCount)
            l.add(pageNumber);
        else{
            for(int i=0;i<l.size();i++){
                if(l.get(i)==pageNumber)
                {   l.remove(i);
                    l.add(0,pageNumber);
                    break;
                }
                pageFaultCount++;
            }
            l.remove(l.size()-1);
        }

    }
}
