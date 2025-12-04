package oss1.synchronization.blockingqueue;

public class Producer extends Thread {
    private final MessageQueue queue;
    private final String name;

    public Producer(MessageQueue queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            queue.put(name + "-MSG#" + i);
        }
    }
}