/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oss1.synchronization.blockingqueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSystemBlockingQueue {
    public static void main(String[] args) {
        MessageQueue q = new MessageQueue(10);

        ExecutorService es = Executors.newCachedThreadPool();

        es.execute(new Producer(q, "P1"));
        es.execute(new Producer(q, "P2"));
        es.execute(new Producer(q, "P3"));

        es.execute(new Consumer(q, "C1"));
        es.execute(new Consumer(q, "C2"));
        es.execute(new Consumer(q, "C3"));

        es.shutdown();
    }
}
