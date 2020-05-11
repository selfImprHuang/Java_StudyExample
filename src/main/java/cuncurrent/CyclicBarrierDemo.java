package cuncurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {


    public static void main(String[] args) throws InterruptedException {
        basePurporse();
        // resetPurpose();

    }

    public static void basePurporse(){
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("最后一个计算线程");
            }
        });
        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    System.out.println("计算线程");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }


    public static void resetPurpose() throws InterruptedException {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("等待计算");
                        Thread.sleep(3000);
                        cyclicBarrier.await();
                        System.out.println("我计算结束了");
                    } catch (InterruptedException e) {
                    } catch (BrokenBarrierException e) {
                    }
                }
            }.start();
        }

        Thread.sleep(2000);
        //重置不再支持
        cyclicBarrier.reset();
        System.out.println("不再等待");
        System.out.println("我去做别的了");
    }

}
