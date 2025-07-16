import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    private Timer timer;
    private Player player;
    public GameEngine(Player player){
        this.player = player;
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Investment i : player.ownedInvestments.values()) {
                    double earned = i.tick();
                    player.addBalance(earned);
                    player.statTracker.addToTotalEarnings(earned);
                }
                // optional: payout summary, UI updates, etc.
            }
        }, 0, 1000);
    }

    public void stop(){
        timer.cancel();
    }
}
