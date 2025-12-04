package oss1.synchronization.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    private final int bufferSize;
    private final BlockingQueue<String> buffer;

    public MessageQueue(int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("Size is illegal.");
        }
        this.bufferSize = bufferSize;
        this.buffer = new LinkedBlockingQueue<>(bufferSize);
    }

    public void put(String s) {
        try {
            buffer.put(s);
            System.out.println("Queue receives message '" + s + "'");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String get() {
        try {
            return buffer.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
