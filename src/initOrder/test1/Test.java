package initOrder.test1;

/**
 * Created by Valk on 21.11.15.
 */
public class Test {
    class A {
        String str;
        {
            str = "ab";                                             // 2
        }

        A() {
            printLength();                                             // 3
        }

        void printLength() {
            System.out.println(str.length());
        }
    }

    class B extends A {
        String str;
        {
            str = "abc";                                                // (*)
        }

        void printLength() {
            System.out.println(str.length());                                             // 4, где str из (*)
        }
    }

    public static void main(String[] args) {
        new Test().new B();                                             // 1
    }
}
