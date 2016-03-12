package synchronizer;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Valk on 05.01.16.
 */
public class ExchangerTest {
    static Integer whoFirst = new Random(new Date().getTime()).nextInt(2) + 1; //1..2
    static Exchanger<String> exchanger = new Exchanger<>();
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            System.out.println("Первым должен быть поток id " + whoFirst);
        }
    });

    public static void main(String[] args) {
        new Thread(new Participant(1)).start();
        new Thread(new Participant(2)).start();
    }

    static class Participant implements Runnable {
        Integer id;

        public Participant(Integer id) {
            this.id = id;
        }

        @Override
        public void run() {
            String objectForSend = "";
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            try {
                if (id != whoFirst) {
                    Thread.sleep(100);
                    System.out.println("Поток id " + id + ": ждет объект");
                } else {
                    objectForSend = "состояние 1";
                    System.out.println("Поток id " + id + ": передает объект: " + objectForSend + " и ждет, пока его получит другой поток");
                }
                //
                String obj = exchanger.exchange(objectForSend); //тот кто передал - ждет, тот кто получает - получил и дальше
                //
                if (id != whoFirst) {
                    Thread.sleep(50); //даем время, чтобы передавший поток успел сообщить, что передал объект
                    System.out.println("Поток id " + id + ": получил объект " + obj);
                    System.out.println("==========================================");
                } else {
                    System.out.println("Поток id " + id + ": передал объект: " + objectForSend);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
