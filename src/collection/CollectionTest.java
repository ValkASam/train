package collection;

import com.sun.deploy.util.ArrayUtil;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Valk on 10.02.16.
 */
public class CollectionTest {


    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>(){{
            add(10);
            add(30);
            add(20);
            add(40);
            add(9);
            add(8);
            add(7);
            add(6);
            add(5);
        }};
        System.out.println(leastN(list, 4));
        //
        System.out.println(list);

        list.removeIf(e -> e == 9);

        list.removeIf(new Predicate<Integer>(){
            @Override
            public boolean test(Integer integer) {
                return integer==7;
            }
        });

        list.removeIf(e -> new Predicate() {
            @Override
            public boolean test(Object o) {
                return false;
            }

            public boolean test(Integer integer, Integer etalon) {
                return integer == etalon;
            }
        }.test(e, 5));

        System.out.println(list);
        //
        Integer[] arr = list.toArray(new Integer[list.size()]);
        Object[] arr1 = list.toArray();
        System.out.println(arr);
        System.out.println(arr1);
        //
        ValueClass valueClass = new ValueClass(20, "20");
        Map<ValueClass, Integer> map = new HashMap<ValueClass, Integer>(){{
            put(new ValueClass(10, "10"), 1);
            put(new ValueClass(30, "30"), 3);
            put(valueClass, 2);
            put(new ValueClass(40, "40"), 4);
        }};
        System.out.println(map.entrySet());
        System.out.println(map.get(valueClass));
        valueClass.ii = 50;
        System.out.println(map.entrySet());
        System.out.println(map.get(valueClass));
        System.out.println(map.get(new ValueClass(20, "20")));
        //
        TreeSet treeSet = new TreeSet(){{
            add(2);
            add(5);
            add(4);
            add(3);
        }};
        System.out.println(treeSet.last());
    }

    public static <T extends Comparable<T>> List<T> leastN(Collection<T> input, int n) {
        assert n > 0;
        PriorityQueue<T> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (T t : input) {
            if (pq.size() < n) {
                pq.add(t);
            } else if (pq.peek().compareTo(t) > 0) {
                pq.poll();
                pq.add(t);
            }
        }
        List<T> list = new ArrayList<>(pq);
        Collections.sort(list);
        return list;
    }
}

class ValueClass{
    public Integer ii;
    public String ss;

    public ValueClass(Integer ii, String ss) {
        this.ii = ii;
        this.ss = ss;
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public int hashCode() {
        int result = ii != null ? ii.hashCode() : 0;
        result = 31 * result + (ss != null ? ss.hashCode() : 0);
        return result;
    }
}
