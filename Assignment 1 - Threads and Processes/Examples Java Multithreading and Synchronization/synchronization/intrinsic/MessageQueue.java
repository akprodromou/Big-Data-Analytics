package oss1.synchronization.intrinsic;

import java.util.ArrayList;
import java.util.List;

public class MessageQueue {
    private final int bufferSize;
    private final List<String> buffer = new ArrayList<>();

    public MessageQueue(int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("Size is illegal.");
        }
        this.bufferSize = bufferSize;
    }

    public synchronized boolean isFull() {
        return buffer.size() == bufferSize;
    }

    public synchronized boolean isEmpty() {
        return buffer.isEmpty();
    }

    public synchronized void put(String message) {
        while (isFull()) {
            System.out.println("Queue is full.");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buffer.add(message);
        System.out.println("Queue receives message '" + message + "'");
        notifyAll();
    }

    public synchronized String get() {
        while (isEmpty()) {
            System.out.println("There is no message in queue.");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String message = buffer.remove(0);
        notifyAll();
        return message;
    }
}