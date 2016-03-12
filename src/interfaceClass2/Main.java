package interfaceClass2;

/**
 * Created by Valk on 21.11.15.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(((A) new C()).text);
        System.out.println(((B) new C()).text);
    }
}
