package singlton.singltonODH;

/**
 * Created by Valk on 10.03.16.
 */
public class SingltonODH {
    public static void main(String[] args) {
        System.out.println("------");
        System.out.println(Singleton.getInstance());
        System.out.println(Singleton.getInstance());
    }
}

class Singleton {

    public static class SingletonHolder {
        //static class SingletonHolder не будет создан и не будет создан HOLDER_INSTANCE до первого обращения к tatic class SingletonHolder
        public static final Singleton HOLDER_INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private Singleton(){
        System.out.println("Singleton is created");
    }
}
