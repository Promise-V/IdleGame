import java.util.Timer;
import java.util.TimerTask;

public class timertest {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask payout = new TimerTask() {
            @Override
            public void run() {
                System.out.println("+$2!");
            }
        };
        timer.scheduleAtFixedRate(payout,2000,10000);
    }
}
