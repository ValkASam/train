package cloneTest;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Valk on 17.12.15.
 */
public class CloneTest {
    static class Obj implements Cloneable {
        int i;

        Obj(int i) {
            this.i = i;
        }

        @Override
        public String toString() {
            return String.valueOf(i);
        }

        @Override
        public Obj clone() throws CloneNotSupportedException {
            return (Obj)super.clone();
        }
    }

    public static void main(String[] arr) throws CloneNotSupportedException {
        ArrayList<Obj> list = new ArrayList<Obj>() {{
            add(new Obj(1));
            add(new Obj(2));
            add(new Obj(3));
            add(new Obj(4));
        }};
        System.out.println(list);
        //
        ArrayList<Obj> list2 = (ArrayList<Obj>) list.clone();
        for (Iterator<Obj> iterator = list2.iterator(); iterator.hasNext();) {
            iterator.next().i++;
        }
        System.out.println(list);
        //
        ArrayList<Obj> list3 = (ArrayList<Obj>) list.clone();
        for (Obj o : list3) {
            Obj obj = o;
            obj.i++;
        }
        System.out.println(list);
        //
        ArrayList<Obj> list4 = (ArrayList<Obj>) list.clone();
        for (int i = 0; i<list4.size(); i++) {
            Obj obj = list4.get(i).clone();
            obj.i++;
        }
        System.out.println(list);
    }

}
