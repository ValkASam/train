package singlton.singltonDSL;

/**
 * Created by Valk on 10.03.16.
 */
public class SingletonDLS {

    public static void main(String[] args) {
        System.out.println(Singleton.getInstance());
        System.out.println(Singleton.getInstance());
    }
}

class Singleton {
    private static volatile Singleton instance;

    public static Singleton getInstance() {
        Singleton localInstance = instance;
        if (localInstance == null) {
            synchronized (Singleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Singleton();
                }
            }
        }
        return localInstance;
    }

    private Singleton(){};

    public static void main(String[] args) {
        System.out.println(Singleton.getInstance());
        System.out.println(new Singleton());
        System.out.println(Singleton.getInstance());
    }
}
