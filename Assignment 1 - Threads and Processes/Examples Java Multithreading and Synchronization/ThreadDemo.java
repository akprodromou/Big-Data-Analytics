package oss1;

class ThreadDemo extends Thread {
    public void run() {
        for (int count = 1, row = 1; row < 200; row++, count++) {
            for (int i = 0; i < count; i++)
                System.out.print('*');
            System.out.print('\n');
        }
    }

    public static void main(String[] args) {
        ThreadDemo t = new ThreadDemo();
        t.start();
        for (int i = 0; i < 50; i++)
            System.out.println("i = " + i + ", i * i = " + i * i);
    }
}