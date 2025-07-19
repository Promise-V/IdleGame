import java.util.Timer;
import java.util.TimerTask;
/**
 * GameEngine manages the core loop that ticks every second,
 * awarding income from investments and tracking earnings.
 */
public class GameEngine {
    private Timer timer;
    private final Player player;
    /**
     * Initializes the GameEngine with the current player.
     * @param player the player instance
     */
    public GameEngine(Player player) {
        this.player = player;
    }
    /**
     * Starts the game loop, ticking every 1 second.
     */
    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Investment investment : player.getOwnedInvestments().values()) {
                    double earned = investment.tick();
                    player.addBalance(earned);
                    player.statTracker.addToTotalEarnings(earned);
                }
            }
        }, 0, 1000);
    }
    /**
     * Stops the game loop.
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
