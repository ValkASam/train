package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Valk on 22.11.15.
 */
public class Livelock {
    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();
    static String str1 = new String();
    static String str2 = new String();
    //
    public static void main(String[] args) {
        Thread thread1 = new Thread(new A(lock1, lock2));
        Thread thread2 = new Thread(new A(lock2, lock1));
        thread1.start();
        thread2.start();
    }
    //
    static class A implements Runnable{
        private Lock lock1;
        private Lock lock2;

        public A(Lock lock1, Lock lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
                        Thread.sleep(300);
                        try {
                            if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + " " + "захватил два лока");
                                } finally {
                                    lock2.unlock();
                                }
                            } else {
                                System.out.println("    " + Thread.currentThread().getName() + " " + "не получил lock2");
                            }
                        } finally {
                            lock1.unlock();
                        }
                    } else {
                        System.out.println("    " + Thread.currentThread().getName() + " " + "не получил lock1");
                    }
                    Thread.sleep(100);
                }
            } catch (Exception e) {}
        }
    }
}
