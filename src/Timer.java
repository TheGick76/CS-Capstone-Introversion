public class Timer implements Runnable {

    public int threadNum ;

    public Timer(int i) {
        this.threadNum = i ;
    }

    public void run() {
        int i = 0 ;
        while (true) {
            System.out.println("Thread: " + threadNum + " Time: " + i) ;
            try {
                Thread.sleep(1000) ;
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            i++ ;
        }
    }
}