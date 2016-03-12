import com.sun.deploy.util.ArrayUtil;
import interfaceClass2.A;
import interfaceClass2.B;
import interfaceClass2.C;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Valk on 22.11.15.
 */
public class Main {
    static List<Integer> o = new ArrayList<Integer>() {{
        add(1);
    }};

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, ClassNotFoundException {
        System.out.println(o);
        //
        char[] ch = {'a', 'b'};
        Constructor constructor = String.class.getDeclaredConstructor(char[].class, boolean.class);
        constructor.setAccessible(true);
        String str = (String) constructor.newInstance(ch, true);
        ch[0] = 'X';
        A(str);
        //
        Double d1 = 0 / 1d;
        Double d2 = 0 / -1d;
        //в обоих случаях сравнене будет корректным 0 != -0
        System.out.println(d1 == d2);
        System.out.println(Double.compare(d1, d2) == 0);
        //
        double f1 = 0 / 1f;
        double f2 = 0 / -1f;
        //для примитивных типов в первом случае 0 == -0
        System.out.println(f1 == f2);
        System.out.println(Double.compare(f1, f2) == 0);
        //
        String s = "фыва";
        String ss = new String(s.getBytes("UTF-8"), "Windows-1251");
        System.out.println(ss);
        s = new String(ss.getBytes("Windows-1251"), "UTF-8");
        System.out.println(s);
        //
        System.out.println(Thread.currentThread().getThreadGroup().getParent());
        //
        if (Double.isInfinite(1 / 0d)) System.out.println(1 / 0d);
        if (Double.isInfinite(-1 / 0d)) System.out.println(-1 / 0d);
        //
        String sss = "asdf";
        System.out.println(new StringBuilder(sss).reverse().toString());
        //
        System.out.println(new Main().new Inner());
        System.out.println(new Main.Nested());
        //
        new ArrayList<String>().iterator();
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> pair : map.entrySet()) {
        }
        List<String> lst = new LinkedList<String>() {{
            add("a");
            add("s");
            add("f");
        }};
        lst.add(1, "aa");
        System.out.println(lst);
        {
            //
            int i = 5000;
            Integer j = 5000;
            Integer k = 5000;
            System.out.println(i == j); //true
            System.out.println(j == k); //false
            //
            int ii = 50;
            Integer jj = 50;
            Integer kk = 50;
            System.out.println(ii == jj); //true
            System.out.println(jj == kk); //true
        }
        {
            Integer j = null;
            //Integer res = true ? j : 1; //будет NPE
        }
        {
            Integer j = null;
            //Integer res = false ? 1 : j; //будет NPE
        }
        {
            Integer i = 300;
            Integer j = 300;
            System.out.println(i==j); // ---> false, т.к не будет autoboxing к int - будут сравнивться объекты (ссылки)
        }
        {
            int i = 300;
            Integer j = 300;
            System.out.println(i==j); // ---> true, т.к тут будет autoboxing к int
        }

}

    public static void A(String str) {
        System.out.println(str);
    }

static class Nested {
    static int i = 0;
}

static class Nested1 {
    static int ii = 1;
}

class Inner {

}

interface Intrface {

}

static class AAA extends Nested {
    static class sAAA extends Nested1 {
    }

    void method() {
        System.out.println(i + sAAA.ii);
    }
}

}

class BB implements Main.Intrface {

}


