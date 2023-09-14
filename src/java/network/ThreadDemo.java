/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author java4
 */
import java.lang.*;

public class ThreadDemo implements Runnable {

    Thread t;

    public void run() {
        for (int i = 10; i < 13; i++) {
            try {
                Thread.sleep(100);
                System.out.println("Completed");


            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new ThreadDemo());
        t.start();
        Thread t2 = new Thread(new ThreadDemo());
        t2.start();
    }
}

