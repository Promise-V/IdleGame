/**
 * Represents a Lemonade Stand investment.
 * A basic starter investment with low cost and short reward intervals.
 */
public class LemonadeStand extends Investment {
    /**
     * Constructs a new Lemonade Stand with base values.
     */
    public LemonadeStand() {
        this.description = "A cozy little lemonade stand.";
        this.level = 1;
        this.priceofInvestment = 100;
        this.rewardTime = 5; // Payout every 5 seconds
        this.returnofInvestment = 20;
    }
    /**
     * Returns the display name of the investment.
     * @return the investment's name
     */
    @Override
    public String getName() {
        return "Lemonade Stand";
    }
    /**
     * Returns the type of this investment.
     * @return the InvestmentType enum value
     */
    @Override
    public InvestmentType getType() {
        return InvestmentType.LEMONADE_STAND;
    }
}