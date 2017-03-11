package io.docbot.hash;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {
    private TreeMap<Long, String> nodes;

    public  ConsistentHashing(List<String> servers){
        nodes = new TreeMap<>();
        for (String s : servers) {
            nodes.put(MurmurHash.hash(s), s);
        }
    }
    public  ConsistentHashing(List<String> servers,int virtualNode){
        nodes = new TreeMap<>();
        for (String s : servers) {
            if(virtualNode > 1){
               for(int i=0;i<virtualNode;i++){
                   nodes.put(MurmurHash.hash(s+"#vNode"+i), s);
               }
            }

        }
    }
    public String getServer(String key){
        SortedMap<Long, String> tail = nodes.tailMap(MurmurHash.hash(key));
        if (tail.isEmpty()) {
            return nodes.get(nodes.firstKey());
        }
        return tail.get(tail.firstKey());
    }

}
