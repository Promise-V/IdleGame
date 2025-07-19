import javafx.scene.control.Label;

/**
 * Abstract base class representing an investment in the idle game.
 * Each investment has a payout interval, upgradeable levels, and ROI.
 */
public abstract class Investment {

    // === Fields ===
    protected String description;
    protected int level;
    protected double priceofInvestment;
    protected int rewardTime; // seconds
    protected double returnofInvestment;
    protected int secondsSinceLastPayout;
    protected double upgradeCost;
    protected Label attachedLabel;

    // === Public Methods ===

    /**
     * Increments the investment level and updates related values like cost and reward interval.
     */
    public void incrementLevel() {
        this.priceofInvestment = Math.round(priceofInvestment * 1.15);
        this.level++;
        rewardTime = Math.max(1, rewardTime - 1);
        this.returnofInvestment = Math.round(returnofInvestment * 1.15);
    }

    /**
     * @return The cost required to upgrade this investment.
     */
    public double getUpgradeCost() {
        return Math.round(priceofInvestment * level * 1.5);
    }

    /**
     * @return The display name of the investment.
     */
    public abstract String getName();

    /**
     * @return The price to buy the investment.
     */
    public double getPriceofInvestment() {
        return priceofInvestment;
    }

    /**
     * @return The current level of the investment.
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return Reward interval in seconds.
     */
    public int getRewardTime() {
        return rewardTime;
    }

    /**
     * @return Return of investment per payout.
     */
    public double getProfit() {
        return returnofInvestment;
    }

    /**
     * Resets the payout timer.
     * @param seconds time since last payout (used for offline tracking).
     */
    public void setSecondsSinceLastPayout(int seconds) {
        this.secondsSinceLastPayout = seconds;
    }

    /**
     * Called every second to calculate whether the investment should pay out.
     * @return earnings generated from this tick.
     */
    public double tick() {
        secondsSinceLastPayout++;
        if (secondsSinceLastPayout >= rewardTime) {
            secondsSinceLastPayout = 0;
            return returnofInvestment;
        }
        return 0.0;
    }
    /**
     * @return The investment type enum value.
     */
    public abstract InvestmentType getType();
    /**
     * Updates the attached UI label with new investment info.
     */
    public void updateLabel() {
        if (attachedLabel != null) {
            attachedLabel.setText(toString());
        }
    }
    /**
     * @return Basic string description for tooltip/UI.
     */
    public String investmentInfo() {
        return " $" + returnofInvestment + " every " + rewardTime + " seconds. " + "@ Level " + level;
    }
}
