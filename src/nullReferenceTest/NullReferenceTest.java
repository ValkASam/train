package nullReferenceTest;

/**
 * Created by Valk on 21.11.15.
 */
public class NullReferenceTest {
    static String str = "Hello World !";
    static String print (){
        return str;
    }
    public static void main(String[] args) {
        NullReferenceTest nullReferenceTest = null;
        System.out.println(nullReferenceTest.print());
        System.out.println(nullReferenceTest.str);
    }
}
