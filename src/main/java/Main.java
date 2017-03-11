import io.docbot.hash.ConsistentHashing;

import java.lang.Integer;import java.lang.String;import java.lang.System;import java.util.*;
import java.util.Arrays;import java.util.HashMap;import java.util.HashSet;import java.util.Map;import java.util.Set;import java.util.concurrent.ThreadLocalRandom;

/**
 * Created with IntelliJ IDEA.
 * User: bgzxz1987@gmail.com
 * Date: 17/3/2
 * Time: 下午1:53
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        String[] servers = {"192.168.1.1:111", "192.168.1.2:111", "192.168.1.3:111"};
        ConsistentHashing m1 = new ConsistentHashing(Arrays.asList(servers));
        ConsistentHashing m2 = new ConsistentHashing(Arrays.asList(servers),100);
        Set<String> s = new HashSet<>();
        while (s.size() <100000){
            byte[] b = new byte[100];
            ThreadLocalRandom.current().nextBytes(b);
            s.add(new String(b));
        }
        Map<String,Integer> r = new HashMap<>();
        Map<String,Integer> rVNode = new HashMap<>();
        for(String k:s){
            String server1 = m1.getServer(k);
            String server2 = m2.getServer(k);
            Integer n1 = r.get(server1);
            Integer n2 = rVNode.get(server2);
            if(n1 == null){
                r.put(server1,1);
            } else{
                n1++;
                r.put(server1,n1);
            }
            if(n2 == null){
                rVNode.put(server2,1);
            } else{
                n2++;
                rVNode.put(server2,n2);
            }
        }
        System.out.println("不带虚拟节点："+r);
        System.out.println("带虚拟节点："+rVNode);
    }
}
