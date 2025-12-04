package oss1;

class A extends Thread {
    public void run() {
        System.out.println("Thread A started");
        for (int i = 1; i <= 1000; i++) {
            System.out.println("\t From ThreadA: i= " + i);
        }
        System.out.println("Exit from A");
    }
}

class B extends Thread {
    public void run() {
        System.out.println("Thread B started");
        for (int j = 1; j <= 1000; j++) {
            System.out.println("\t From ThreadB: j= " + j);
        }
        System.out.println("Exit from B");
    }
}

class C extends Thread {
    public void run() {
        System.out.println("Thread C started");
        for (int k = 1; k <= 1000; k++) {
            System.out.println("\t From ThreadC: k= " + k);
        }
        System.out.println("Exit from C");
    }
}

public class ThreadPriorityDemo {
    public static void main(String args[]) throws InterruptedException {
        A threadA = new A();
        B threadB = new B();
        C threadC = new C();
        threadA.setPriority(Thread.MIN_PRIORITY);
        threadB.setPriority(threadA.getPriority() + 1);
        threadC.setPriority(Thread.MAX_PRIORITY);
        System.out.println("Started Thread A");
        threadA.start();
        System.out.println("Started Thread B");
        threadB.start();
        System.out.println("Started Thread C");
        threadC.start();
        System.out.println("End of main thread");
    }
}