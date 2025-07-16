public class XPManager {
    private int currentXP;
    private int level;
    private int xpToNextLevel;
    private int totalXP;
    public XPManager(){
        level = 1;
        currentXP = 0;
        xpToNextLevel = calculateXPForLevel(level);
    }
    public void addXP(int amount){
        currentXP += amount;
        this.totalXP +=amount;
        while( currentXP >= xpToNextLevel){
            currentXP -= xpToNextLevel;
            level ++;
            xpToNextLevel = calculateXPForLevel(level);
            System.out.println("Leveled up! Now Level "+ level);
        }
    }
    private int calculateXPForLevel(int level){
        return 50 * level * level;
    }
    public int getXP(){
        return currentXP;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
    public int getXpToNextLevel() {
        return xpToNextLevel;
    }
    public int getTotalXP(){
        return totalXP;
    }
    public double getProgressToNextLevel() {
        return calculateXPForLevel(level+1);
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }
}
