public class InvestmentFactory {
    public static Investment create(InvestmentType type){
        return switch (type) {
            case LEMONADE_STAND -> new LemonadeStand();
            case COMMERCIAL_AIRLINE -> null;
            case TECH_STARTUP -> null;
            default -> throw new IllegalArgumentException("Unknown Investment Type: " + type);
        };
    }
}
