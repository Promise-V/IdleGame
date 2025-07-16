public class StatTracker {
    private long sessionStartTime;
    private long totalPlayTime;
    private int totalUpgrades;
    private int totalBuys;
    private double totalMoneyEarned;
    private int totalNumberOfInvestments;
    public void startSession(){
        sessionStartTime = System.currentTimeMillis();
    }
    public void endSession(){
        int sessionTimeInSeconds = (int)((System.currentTimeMillis() - sessionStartTime) / 1000);
        totalPlayTime += sessionTimeInSeconds;
    }
    public long getTotalPlayTime() {
        return totalPlayTime;
    }
    public double getTotalEarnings() {
        return totalMoneyEarned;
    }
    public int getTotalInvestmentsPurchased() {
        return totalNumberOfInvestments;
    }
    public void addToTotalEarnings(double earnings){
        totalMoneyEarned += earnings;
    }
    public int getTotalUpgradesMade() {
        return totalUpgrades;
    }

    public void setTotalPlayTime(long playTime) {
        this.totalPlayTime = playTime;
    }

    public void setTotalEarnings(double earnings) {
        this.totalMoneyEarned = earnings;
    }
    public void setTotalNumberOfInvestments(int numberOfInvestments){
        this.totalNumberOfInvestments = numberOfInvestments;
    }
    public void incrementTotalNumberOfInvestments(){
        totalNumberOfInvestments++;
    }
    public void incrementTotalUpgrades(){
        totalUpgrades++;
    }
    public void setTotalUpgrades(int totalUpgrades){
        this.totalUpgrades = totalUpgrades;
    }
}
