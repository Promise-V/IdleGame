/**
 * Represents a Cookie Cart investment.
 * A starter investment with low cost and short reward intervals.
 */
public class CookieCart extends Investment {
    /**
     * Constructs a new CookieCart with base values.
     */
    public CookieCart(){
        this.description = "A little cart selling overpriced cookies outside a school.";
        this.level = 1;
        this.priceofInvestment = 300;
        this.rewardTime = 4;
        this.returnofInvestment = 25;
    }
    /**
     * Returns the display name of the investment.
     * @return the investment's name
     */
    @Override
    public String getName() {
        return "Cookie Cart";
    }
    /**
     * Returns the type of this investment.
     * @return the InvestmentType enum value
     */
    @Override
    public InvestmentType getType() {
        return InvestmentType.COOKIE_CART;
    }
}
