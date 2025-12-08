package oss1.synchronization.blockingqueue;

public class Consumer extends Thread {
    private final MessageQueue queue;
    private final String name;

    public Consumer(MessageQueue queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + " downloads " + queue.get() + " from the queue.");
        }
    }
}