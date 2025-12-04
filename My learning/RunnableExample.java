// Define an action: a description of what should happen later when this task is executed
class MyRunnable implements Runnable {
    @Override
    public void run() {
        // Code that will run in the new thread - what the thread will do when it runs.
        System.out.println("The thread is running.");
    }
}

// Defining the main program
public class RunnableExample {
    // The code inside main() runs first
    public static void main(String[] args) {
        // create a new thread and pass in the MyRunnable object which tells the 
        // thread what to do
        Thread thread = new Thread(new MyRunnable());
        thread.start(); // Start the thread
    }
}

// This method is particularly useful when your class needs to extend another class or 
// implement multiple interfaces, as it separates the thread’s task from the Thread 
// class itself, providing greater flexibility and reusability.