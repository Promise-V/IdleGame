import java.util.HashMap;
import java.util.Map;

/**
 * Represents a player in the idle game.
 * Handles player stats, balance, XP, and owned investments.
 */
public class Player {

    private String accountID;
    protected final XPManager xpManager;
    public final StatTracker statTracker; // Can keep public if only reading, but better to use getter
    private double balance;

    private final Map<InvestmentType, Investment> ownedInvestments;

    public Player() {
        this.balance = 200.0;
        this.xpManager = new XPManager();
        this.statTracker = new StatTracker();
        this.ownedInvestments = new HashMap<>();
    }

    // XP Methods
    public void gainXP(int amount) {
        xpManager.addXP(amount);
    }

    public int getPlayerLevel() {
        return xpManager.getLevel();
    }

    public int getXP() {
        return xpManager.getXP();
    }

    public int getXPToNextLevel() {
        return xpManager.getXpToNextLevel();
    }

    public void setXP(int xp) {
        xpManager.setXP(xp);
    }

    public void setXPToNextLevel(int next) {
        xpManager.setXPToNextLevel(next);
    }

    // Balance Methods
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addBalance(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    // Investment Handling
    public boolean buy(Investment investment) {
        InvestmentType type = investment.getType();

        if (ownedInvestments.containsKey(type)) {
            System.out.println("You already own this investment.");
            return false;
        }

        if (balance >= investment.getPriceofInvestment()) {
            balance -= investment.getPriceofInvestment();
            ownedInvestments.put(type, investment);
            gainXP(25);
            statTracker.incrementTotalInvestments();
            return true;
        }

        System.out.println("Not enough money.");
        return false;
    }

    public boolean upgrade(Investment inv) {
        double upgradeCost = inv.getUpgradeCost();

        if (balance >= upgradeCost) {
            balance -= upgradeCost;
            inv.incrementLevel();
            gainXP(10);
            statTracker.incrementTotalUpgrades();
            return true;
        } else {
            System.out.println("Not enough money to upgrade.");
            return false;
        }
    }

    // Getter for owned investments (read-only reference)
    public Map<InvestmentType, Investment> getOwnedInvestments() {
        return ownedInvestments;
    }

    // Optional: Getter/Setter for account ID if needed externally
    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
}