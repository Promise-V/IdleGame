/**
 * Represents a Food Truck investment.
 * A middle class investment with moderate costs and short reward intervals.
 */
public class FoodTruck extends Investment {
    public FoodTruck(){
        this.description = "Taco Tuesdays just became profitable";
        this.level = 1;
        this.priceofInvestment = 750;
        this.rewardTime = 3;
        this.returnofInvestment = 35;
    }
    /**
     * Returns the display name of the investment.
     * @return the investment's name
     */
    @Override
    public String getName() {
        return "Food Truck";
    }
    /**
     * Returns the type of this investment.
     * @return the InvestmentType enum value
     */
    @Override
    public InvestmentType getType() {
        return InvestmentType.FOOD_TRUCK;
    }
}
