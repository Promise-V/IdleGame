import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
    public String accountID;
    public XPManager xpManager;
    public StatTracker statTracker;
    public double balance;
    public Map<InvestmentType, Investment> ownedInvestments = new HashMap<>();
    public Player(){
        this.balance = 200.0;
        xpManager = new XPManager();
        statTracker = new StatTracker();
    }
    public void gainXP(int amount){
        xpManager.addXP(amount);
    }
    public int getPlayerLevel(){
        return xpManager.getLevel();
    }
    public int getXP(){
        return xpManager.getXP();
    }
    public int getXPToNextLevel(){
        return xpManager.getXpToNextLevel();
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getBalance() {
        return balance;
    }
    public void addBalance(double amount){
         if(amount > 0 ){
             balance += amount;
         }
    }
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
            statTracker.incrementTotalNumberOfInvestments();
            return true;
        }
        System.out.println("Not enough money.");
        return false;
    }
    public boolean upgrade(Investment inv) {
        double upgradeCost = inv.getUpgradeCost();
        if (balance >= upgradeCost) {
            balance -= upgradeCost;
            inv.incrementLevel();  // now we know they can afford it
            System.out.println("Level Upgraded");
            gainXP(10);
            statTracker.incrementTotalUpgrades();
            return true;
        } else {
            System.out.println("Not enough money to upgrade.");
            return false;
        }
    }
}
