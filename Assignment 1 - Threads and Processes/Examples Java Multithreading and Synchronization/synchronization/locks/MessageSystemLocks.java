package oss1.synchronization.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSystemLocks {
    public static void main(String[] args) {
        MessageQueue q = new MessageQueue(10);

        ExecutorService es = Executors.newCachedThreadPool();

        es.execute(new Producer(q));
        es.execute(new Producer(q));
        es.execute(new Producer(q));

        es.execute(new Consumer(q));
        es.execute(new Consumer(q));
        es.execute(new Consumer(q));

        es.shutdown();
    }
}
