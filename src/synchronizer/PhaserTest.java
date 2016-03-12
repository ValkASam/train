package synchronizer;
/**
 * http://habrahabr.ru/post/117185/
 */

import java.util.concurrent.Phaser;

/**
 * Created by Valk on 14.05.15.
 */
public class PhaserTest {
    static int lines = 10;
    static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    static StringBuffer randomAlphabet = new StringBuffer();

    public static void main(String[] args) {

        final Phaser phaser = new Phaser() {
            protected boolean onAdvance(int phase, int parties) {
                System.out.println(randomAlphabet);
                //
                randomAlphabet = new StringBuffer();

                return phase >= lines; //loop count managing here
            }
        };

        // everyone have to wait for the main thread
        phaser.register();

        for (int i = 0; i < alphabet.length(); i++) {

            final char next = alphabet.charAt(i);
            phaser.register(); // ticket for the following thread

            new Thread() {
                public void run() {
                    do {
                        //randomAlphabet.append(next);

                        StringBuilder str = new StringBuilder().append(phaser.getPhase()).append(" (").append(phaser.getRegisteredParties()).append(") ");
                        randomAlphabet.append(str);

                        phaser.arriveAndAwaitAdvance(); // checkout
                    } while ( !phaser.isTerminated() );
                }
            }.start();
        }

        try {Thread.sleep(50);} catch (InterruptedException e){}
        System.out.println("Write this by hand and carry in the pocket:");
        // release
        phaser.arriveAndDeregister();

        while ( !phaser.isTerminated() ){}
        System.out.println("END");
    }
}
