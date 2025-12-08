package oss1.synchronization.semaphore;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public class ProducerConsumerSemaphore {
    private static final int BUFFER_SIZE = 10;
    private static final int MAX_VALUE = 10000;
    private final Stack<Integer> buffer = new Stack<>();
    private final Semaphore writePermits = new Semaphore(BUFFER_SIZE);
    private final Semaphore readPermits = new Semaphore(0);
    private final Random random = new Random();

    private class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                writePermits.acquireUninterruptibly();
                buffer.push(random.nextInt(MAX_VALUE));
                readPermits.release();
            }
        }
    }

    private class Consumer implements Runnable {
        private String name;

        public Consumer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                readPermits.acquireUninterruptibly();
                System.out.println(name + " " + buffer.pop());
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writePermits.release();
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerSemaphore obj = new ProducerConsumerSemaphore();
        Producer p1 = obj.new Producer();
        Producer p2 = obj.new Producer();
        Producer p3 = obj.new Producer();
        Consumer c1 = obj.new Consumer("C1");
        Consumer c2 = obj.new Consumer("C2");
        Consumer c3 = obj.new Consumer("C3");
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t3 = new Thread(p3);
        Thread t4 = new Thread(c1);
        Thread t5 = new Thread(c2);
        Thread t6 = new Thread(c3);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}