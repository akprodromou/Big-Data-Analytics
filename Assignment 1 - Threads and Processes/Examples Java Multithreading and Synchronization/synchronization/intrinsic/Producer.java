package oss1.synchronization.intrinsic;

public class Producer extends Thread {
    private final MessageQueue queue;

    public Producer(MessageQueue queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            queue.put("MSG#" + i);
        }
    }
}