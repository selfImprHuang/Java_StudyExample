package cuncurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {
        System.out.println(2&2);
        System.out.println(1&2);
        System.out.println(0&2);
        System.out.println(0|2);
        System.out.println(1|2);
        System.out.println(2|2);
        retry:
        for(;;){
            System.out.println(123);
            if(true){
                break retry;
            }
        }

        System.out.println("(结束了");


        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getId() + "工作结束了");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                }
            }.start();
        }
    }


}
