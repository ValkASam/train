package interfaceClass;

/**
 * Created by Valk on 21.11.15.
 */
public class Main {
    public static void main(String[] args) {
        C c = new C() {
            @Override
            public void print() {
                System.out.println("C");
            }
        };
        c.print();
        B b = new B();
    }
}



