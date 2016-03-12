package synchronizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by Valk on 04.01.16.
 */
public class CyclicBarrerTest {
    public static void main(String args[]) {

        ServiceMan serviceMan = new ServiceMan(3); //3 - порог для барьера

        for (int i = 0; i < 15; i++) {
            new Thread(new Printer(serviceMan, "Printer" + (i + 1))).start();
        }
    }
}

class ServiceMan {
    private CyclicBarrier queue;
    private List<String> inQueue;

    public ServiceMan(int hardWorking) {
        inQueue = new ArrayList<String>();
        Runnable runnable = new Runnable() { //передаем поток, который Не запуститься и будет ждать пока,
            // queue.await() не будет вызван 3 раза
            @Override
            public void run() {
                //запуститься, когда соберется в ожидании у барьера НЕ МЕНЕЕ (может и больше) hardWorking потоков
                System.out.println("Filling " + inQueue);
                inQueue.clear();
                try {
                    Thread.sleep(3000); //чтобы показать, что у барьера может собраться и больше, чем hardWorking потоко
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("готов принимать новые заказы");
            }
        };
        queue = new CyclicBarrier(hardWorking, runnable);
    }

    public void recharge(String name) {
        try {
            inQueue.add(name);
            queue.await();                 //другие потоки, вызывая recharge, вызывают queue.await(),
            // тем самым увеличивая счетчик барьера и сами останавливаясь
        } catch (InterruptedException e) {
        } catch (BrokenBarrierException e) {
            System.out.println("заявка для " + name + " отклонена");
        }
    }

}

class Printer implements Runnable {

    private String name;
    private Random rand;
    private ServiceMan serviceMan;

    public Printer(ServiceMan serviceMan, String name) {
        this.name = name;
        this.serviceMan = serviceMan;
        this.rand = new Random();
    }

    public void run() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(rand.nextInt(10));
                System.out.println(name + " is empty");
                serviceMan.recharge(name); //тут вызов queue.await();
                System.out.println(name + " готов печатать дальше");
                break; //не будем крутить бесконечно
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}