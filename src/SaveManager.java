import java.io.*;
import java.util.Map;

/**
 * Manages saving and loading of game state to a file.
 * Ensures persistence of player progress, including XP, investments, stats, and time.
 */
public class SaveManager {

    private static final String SAVE_FILE = "saves/save.txt";
    private static long lastSavedTime;

    /**
     * Saves all player data to the save file.
     * @param player the current Player object to serialize.
     */
    public static void save(Player player) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {

            // Basic info
            writer.write("BALANCE=" + player.getBalance());
            writer.newLine();

            // Investments
            for (Investment inv : player.getOwnedInvestments().values()) {
                writer.write("INVESTMENT=" + inv.getType().name() + "," + inv.getLevel() + "," + inv.secondsSinceLastPayout);
                writer.newLine();
            }

            // Session metadata
            writer.write("LAST_SAVED_TIME=" + System.currentTimeMillis());
            writer.newLine();

            // XP & Level
            writer.write("PLAYER_LEVEL=" + player.getPlayerLevel());
            writer.newLine();
            writer.write("TOTAL_XP=" + player.xpManager.getTotalXP());
            writer.newLine();

            // Stats
            writer.write("TOTAL_PLAY_TIME=" + player.statTracker.getTotalPlayTime());
            writer.newLine();
            writer.write("TOTAL_EARNINGS=" + player.statTracker.getTotalEarnings());
            writer.newLine();
            writer.write("TOTAL_INVESTMENTS_PURCHASED=" + player.statTracker.getTotalInvestmentsPurchased());
            writer.newLine();
            writer.write("TOTAL_UPGRADES_MADE=" + player.statTracker.getTotalUpgradesMade());
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Failed to save player data: " + e.getMessage());
        }
    }

    /**
     * Loads player data from the save file into the provided player instance.
     * @param player the Player instance to populate with loaded data.
     */
    public static void load(Player player) {
        System.out.println("Reading save file...");
        File file = new File(SAVE_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("BALANCE=")) {
                    double balance = Double.parseDouble(line.split("=")[1]);
                    player.setBalance(balance);
                }

                else if (line.startsWith("INVESTMENT=")) {
                    String[] parts = line.split("=")[1].split(",");
                    InvestmentType type = InvestmentType.valueOf(parts[0]);
                    int level = Integer.parseInt(parts[1]);
                    int seconds = (parts.length >= 3) ? Integer.parseInt(parts[2]) : 0;

                    Investment inv = InvestmentFactory.create(type);
                    for (int i = 1; i < level; i++) {
                        inv.incrementLevel();
                    }
                    inv.setSecondsSinceLastPayout(seconds);
                    player.getOwnedInvestments().put(type, inv);
                }

                else if (line.startsWith("LAST_SAVED_TIME=")) {
                    lastSavedTime = Long.parseLong(line.split("=")[1]);
                }

                else if (line.startsWith("PLAYER_LEVEL=")) {
                    int level = Integer.parseInt(line.split("=")[1]);
                    player.xpManager.setLevel(level);
                }

                else if (line.startsWith("TOTAL_XP=")) {
                    int totalXP = Integer.parseInt(line.split("=")[1]);
                    player.xpManager.setTotalXP(totalXP);
                    player.xpManager.rebuildFromTotalXP(); // Rebuild level from XP
                }

                else if (line.startsWith("TOTAL_PLAY_TIME=")) {
                    long time = Long.parseLong(line.split("=")[1]);
                    player.statTracker.setTotalPlayTime(time);
                }

                else if (line.startsWith("TOTAL_EARNINGS=")) {
                    double earnings = Double.parseDouble(line.split("=")[1]);
                    player.statTracker.setTotalEarnings(earnings);
                }

                else if (line.startsWith("TOTAL_INVESTMENTS_PURCHASED=")) {
                    int count = Integer.parseInt(line.split("=")[1]);
                    player.statTracker.setTotalNumberOfInvestments(count);
                }

                else if (line.startsWith("TOTAL_UPGRADES_MADE=")) {
                    int upgrades = Integer.parseInt(line.split("=")[1]);
                    player.statTracker.setTotalUpgrades(upgrades);
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Failed to load save data: " + e.getMessage());
        }
    }

    /**
     * Returns the timestamp of the last saved time for offline earnings.
     */
    public static long getLastSavedTime() {
        return lastSavedTime;
    }
}