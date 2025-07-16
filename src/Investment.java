import javafx.scene.control.Label;

public  abstract class Investment {
    protected String description;
    protected int level;
    protected double priceofInvestment;
    protected int rewardTime;
    protected double returnofInvestment;
    protected int secondsSinceLastPayout;
    protected double upgradeCost;
    protected Label attachedLabel;


    public void incrementLevel(){
        this.priceofInvestment = Math.round(priceofInvestment * 1.15);
        this.level++;
        rewardTime = Math.max(1, rewardTime - 1);
        this.returnofInvestment = Math.round(returnofInvestment * 1.15);
    }
    public double getUpgradeCost(){
        return Math.round(priceofInvestment * level * 1.5);
    }
    public abstract String getName();
    public double getPriceofInvestment(){
        return priceofInvestment;
    }
    public int getLevel(){
        return level;
    }
    public int getRewardTime(){
        return rewardTime;
    }
    public double getProfit(){
        return returnofInvestment;
    }
    public void setSecondsSinceLastPayout(int seconds){
        this.secondsSinceLastPayout = seconds;
    }
    public double tick(){
        secondsSinceLastPayout++;
        if(secondsSinceLastPayout >= rewardTime){
            secondsSinceLastPayout = 0;
            return returnofInvestment;
        }
        return 0.0;
    }
    public abstract InvestmentType getType();
    public void setAttachedLabel(Label label) {
        this.attachedLabel = label;
    }
    public void updateLabel() {
        if (attachedLabel != null) {
            attachedLabel.setText(toString());
        }
    }
    public String investmentInfo(){
        return " $" + returnofInvestment + " every " + rewardTime +"seconds. " + "@ Level " + level;
    }
}
