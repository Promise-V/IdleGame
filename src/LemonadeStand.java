public class LemonadeStand  extends Investment{
    public LemonadeStand(){
        this.description = "A Cozy Ass Lemonade Stand";
        this.level = 1;
        this.priceofInvestment = 100;
        this.rewardTime = 5;
        this.returnofInvestment = 20;
    }
    @Override
    public String getName() {
        return "Lemonade Stand";
    }
    @Override
    public InvestmentType getType() {
        return InvestmentType.LEMONADE_STAND;
    }
}
