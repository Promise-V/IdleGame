/**
 * Manages the player's XP, levels, and progression.
 */
public class XPManager {

    private int currentXP;
    private int level;
    private int xpToNextLevel;
    private int totalXP;

    public XPManager() {
        this.level = 1;
        this.currentXP = 0;
        this.totalXP = 0;
        this.xpToNextLevel = calculateXPForLevel(level);
    }

    /**
     * Adds XP and handles level up logic if threshold is crossed.
     * @param amount XP to be added
     */
    public void addXP(int amount) {
        if (amount <= 0) return;

        currentXP += amount;
        totalXP += amount;

        while (currentXP >= xpToNextLevel) {
            currentXP -= xpToNextLevel;
            level++;
            xpToNextLevel = calculateXPForLevel(level);
            System.out.println("Leveled up! Now Level " + level);
        }
    }

    /**
     * Rebuilds level and currentXP based on totalXP saved from file.
     */
    public void rebuildFromTotalXP() {
        level = 1;
        currentXP = 0;
        xpToNextLevel = calculateXPForLevel(level);

        int xpRemaining = totalXP;

        while (xpRemaining >= xpToNextLevel) {
            xpRemaining -= xpToNextLevel;
            level++;
            xpToNextLevel = calculateXPForLevel(level);
        }

        currentXP = xpRemaining;
    }

    /**
     * XP formula: increases exponentially with level.
     * @param level the level to calculate XP for
     * @return XP needed to reach the given level
     */
    private int calculateXPForLevel(int level) {
        return 50 * level * level;
    }

    // --- Getters ---

    public int getXP() {
        return currentXP;
    }

    public int getLevel() {
        return level;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public int getTotalXP() {
        return totalXP;
    }

    // --- Setters (for SaveManager use) ---

    public void setXP(int xp) {
        this.totalXP = xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXPToNextLevel(int xpNext) {
        this.xpToNextLevel = xpNext;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }
}