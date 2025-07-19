/**
 * Tracks gameplay statistics for a player such as play time, upgrades, earnings, and investments.
 */
public class StatTracker {

    private long sessionStartTime;
    private long totalPlayTime; // in seconds
    private boolean sessionStarted = false;

    private int totalUpgrades;
    private int totalInvestments;
    private double totalMoneyEarned;

    /**
     * Starts a new session timer.
     */
    public void startSession() {
        if (!sessionStarted) {
            sessionStartTime = System.currentTimeMillis();
            sessionStarted = true;
        }
    }

    /**
     * Ends the session and adds session duration to total play time.
     */
    public void endSession() {
        if (sessionStarted) {
            long sessionDuration = (System.currentTimeMillis() - sessionStartTime) / 1000;
            totalPlayTime += sessionDuration;
            sessionStarted = false;
        }
    }

    /**
     * @return total time played including current session, in seconds.
     */
    public long getLivePlayTime() {
        if (sessionStarted) {
            long currentSession = (System.currentTimeMillis() - sessionStartTime) / 1000;
            return totalPlayTime + currentSession;
        }
        return totalPlayTime;
    }

    /**
     * Returns total play time in "Xm Ys" format.
     */
    public String getFormattedPlayTime() {
        long totalSeconds = getLivePlayTime();
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return minutes + "m " + seconds + "s";
    }

    // --- Stat Incrementation ---

    public void incrementTotalUpgrades() {
        totalUpgrades++;
    }

    public void incrementTotalInvestments() {
        totalInvestments++;
    }

    public void addToTotalEarnings(double earnings) {
        totalMoneyEarned += earnings;
    }

    // --- Getters ---

    public long getTotalPlayTime() {
        return totalPlayTime;
    }

    public int getTotalUpgradesMade() {
        return totalUpgrades;
    }

    public int getTotalInvestmentsPurchased() {
        return totalInvestments;
    }

    public double getTotalEarnings() {
        return totalMoneyEarned;
    }

    // --- Setters (used by SaveManager) ---

    public void setTotalPlayTime(long playTime) {
        this.totalPlayTime = playTime;
    }

    public void setTotalUpgrades(int upgrades) {
        this.totalUpgrades = upgrades;
    }

    public void setTotalNumberOfInvestments(int investments) {
        this.totalInvestments = investments;
    }

    public void setTotalEarnings(double earnings) {
        this.totalMoneyEarned = earnings;
    }


}