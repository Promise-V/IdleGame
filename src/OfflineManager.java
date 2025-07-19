/**
 * Handles the logic for calculating passive earnings while the game is closed.
 */
public class OfflineManager {
    /**
     * Calculates earnings generated during offline time based on investments.
     *
     * @param player The player whose investments are being considered
     * @param lastSavedTime The last time the game was saved (epoch ms)
     * @return The total passive income earned during offline time
     */
    public static double calculateOfflineEarnings(Player player, long lastSavedTime) {
        long currentTime = System.currentTimeMillis();
        long secondsPassed = (currentTime - lastSavedTime) / 1000;
        double totalOfflineEarnings = 0;

        for (Investment inv : player.getOwnedInvestments().values()) {
            double reward = inv.getProfit();
            int interval = inv.getRewardTime();
            long payouts = secondsPassed / interval;
            totalOfflineEarnings += payouts * reward;
        }

        // Only 33% of potential earnings are awarded to keep it balanced
        return totalOfflineEarnings * 0.33;
    }
}