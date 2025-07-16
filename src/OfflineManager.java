public class OfflineManager {
    public static double calculateOfflineEarnings(Player player, long lastSavedTime){
        long currentTime =  System.currentTimeMillis();
        long secondsPassed = (currentTime - lastSavedTime) / 1000;
        double totalOfflineEarnings = 0;
        for(Investment inv : player.ownedInvestments.values()){
            double reward = inv.getProfit();
            double interval = inv.getRewardTime();
            long payouts = secondsPassed / (long) interval;
            totalOfflineEarnings += payouts * reward;
        }
        return totalOfflineEarnings * 0.33;
    }
}
