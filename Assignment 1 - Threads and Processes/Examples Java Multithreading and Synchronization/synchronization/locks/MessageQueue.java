package oss1.synchronization.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageQueue {
    private final int bufferSize;
    private final List<String> buffer = new ArrayList<>();

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

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

    public void put(String s) {
        lock.lock();
        try {
            while (isFull()) {
                condition.await();
            }
            buffer.add(s);
            System.out.println("Queue receives message '" + s + "'");
            condition.signalAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageQueue.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }

    public String get() {
        lock.lock();
        try {
            while (buffer.isEmpty()) {
                System.out.println("There is no message in queue.");
                condition.await();
            }
            String s = buffer.remove(0);
            condition.signalAll();
            return s;
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageQueue.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            lock.unlock();
        }
    }
}
