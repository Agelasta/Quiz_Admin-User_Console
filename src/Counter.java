public class Counter extends Thread {

    private boolean stopCounter = false;

    public void run() {
        while (!stopCounter) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stopCounter = true;
        }
    }

    public void setStopCounter(boolean flag) {
        stopCounter = flag;
    }
}
