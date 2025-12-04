package oss1.synchronization.intrinsic;

public class MessageSystemIntrinsic {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(5);
        new Producer(queue).start();
        new Producer(queue).start();
        new Producer(queue).start();
        new Consumer(queue).start();
        new Consumer(queue).start();
        new Consumer(queue).start();
    }
}