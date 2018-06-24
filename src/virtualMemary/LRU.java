package virtualMemary;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Stack;

public class LRU extends ReplacementAlgorithm {
    private LinkedHashMap<Integer,Integer> map;
    public LRU(int pageFrameCount) {
        super(pageFrameCount);
        map=new LinkedHashMap<Integer, Integer>(pageFrameCount){
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > pageFrameCount;
            }
        };
    }

    @Override
    public void insert(int pageNumber) {
        map.put(pageNumber,pageNumber);
    }

}
