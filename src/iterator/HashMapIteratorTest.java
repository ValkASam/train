package iterator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Valk on 06.01.16.
 */
public class HashMapIteratorTest {
    public static void main(String[] args) {
        Map hm1 = new HashMap();
        Map hm2 = new LinkedHashMap<>();
        System.out.println("------оригинальный порядок -----------");
        for (int i = 0; i<5; i++){
            int k = new Random(i).nextInt();
            System.out.println(k);
            hm1.put(k, i+10);
            hm2.put(k, i + 10);
        }
        System.out.println("------порядок будет изменен-----------");
        for (Iterator iterator = hm1.keySet().iterator(); iterator.hasNext();){
            System.out.println(iterator.next());
        }
        System.out.println("-------порядок будет оригинальный ----------");
        for (Iterator iterator = hm2.keySet().iterator(); iterator.hasNext();){
            System.out.println(iterator.next());
        }
    }
}

