public class Counter extends Thread {

    public void run() {

        int x = 10;
        System.out.print("Left time: ");

        try {
            while (x > -1) {
                System.out.print(x + ".. ");
                x--;
                Thread.sleep(1000);
                if (x == -1) {
                    System.out.println("\n\nToo late... Time's up!\n");
                    System.out.println("Enter 1, 2, or 3 to continue");
                }
                if (Thread.interrupted() || x == -1) break;
            }
        } catch (InterruptedException e) {
            System.out.println("Error");
        }
    }
}
