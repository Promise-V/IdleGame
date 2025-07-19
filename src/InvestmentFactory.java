/**
 * Factory class to create Investment instances based on their InvestmentType.
 */
public class InvestmentFactory {
    /**
     * Creates an instance of the specified InvestmentType.
     *
     * @param type The type of investment to create.
     * @return An instance of the corresponding Investment subclass, or null if not yet implemented.
     * @throws IllegalArgumentException if the investment type is unknown.
     */
    public static Investment create(InvestmentType type) {
        return switch (type) {
            case LEMONADE_STAND -> new LemonadeStand();
            case COMMERCIAL_AIRLINE -> null; // Future implementation
            case TECH_STARTUP -> null;        // Future implementation
            default -> throw new IllegalArgumentException("Unknown Investment Type: " + type);
        };
    }
}