public class Counter {
    private int count = 0;

    // When a method is synchronized, only one thread can 
    // execute it at a time for that particular instance of Counter
    // the whole method is synchronized when using "public synchronized void"
    public void increment() {
        synchronized(this) {
            count++;
        }
        System.out.println("Thread " + Thread.currentThread().getName() + "is running..");
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });
        // Both threads start almost at the same time.
        // Thread 1 might enter increment() first — Thread 2 cannot execute increment() 
        // at the same moment, so it waits only for that single increment.
        // Thread 1 then exits increment() → Thread 2 acquires the lock → 
        // increments → releases → and so on.
        t1.start();
        t2.start();

        t1.join(); // Wait for threads to finish
        t2.join();

        System.out.println("Final count: " + counter.getCount());
    }
}
