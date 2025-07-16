import java.io.*;
import java.util.List;

public class SaveManager {

    private static final String SAVE_FILE = "saves/save.txt";
    private static long lastSavedTime;
    //  Save the game state
    public static void save(Player player) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {

            //Save the player's balance
            writer.write("BALANCE=" + player.getBalance());
            writer.newLine(); // moves to the next line

            // Save each owned investment
            for (Investment inv : player.ownedInvestments.values()) {
                writer.write("INVESTMENT=" + inv.getType().name() + "," + inv.getLevel() +","+ inv.secondsSinceLastPayout);
                writer.newLine();
            }
            //Save time at close in milliseconds
            writer.write("LAST_SAVED_TIME=" + System.currentTimeMillis());
            writer.newLine();
            // Save Player Level
            writer.write("PLAYER_LEVEL="+player.getPlayerLevel());
            writer.newLine();
            // Save Total Play Time
            writer.write("TOTAL_PLAY_TIME=" + player.statTracker.getTotalPlayTime());
            writer.newLine();
            // Save Total Earning throughout Game Cycle
            writer.write("TOTAL_EARNINGS=" + player.statTracker.getTotalEarnings());
            writer.newLine();
            // Save Total Investments
            writer.write("TOTAL_INVESTMENTS_PURCHASED=" + player.statTracker.getTotalInvestmentsPurchased());
            writer.newLine();
            // Save Total Upgrades Made.
            writer.write("TOTAL_UPGRADES_MADE=" + player.statTracker.getTotalUpgradesMade());
            writer.newLine();
            writer.write("TOTAL_XP=" + player.xpManager.getTotalXP());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //️ Load the game state
    public static void load(Player player) {
        System.out.println("Reading save file...");
        File file = new File(SAVE_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("BALANCE=")) {
                    double balance = Double.parseDouble(line.split("=")[1]);
                    player.setBalance(balance); // assume this exists
                }

                else if (line.startsWith("INVESTMENT=")) {
                    String[] parts = line.split("=")[1].split(",");
                    InvestmentType type = InvestmentType.valueOf(parts[0]);
                    int level = Integer.parseInt(parts[1]);

                    Investment inv = InvestmentFactory.create(type);
                    for (int i = 1; i < level; i++) {
                        inv.incrementLevel(); // upgrades it to the correct level
                    }
                    int seconds = 0;
                    if (parts.length >= 3) {
                        seconds = Integer.parseInt(parts[2]);
                    }
                    inv.setSecondsSinceLastPayout(seconds);
                    player.ownedInvestments.put(inv.getType(), inv);

                    System.out.println("Loaded investment: " + inv.getType() + " Level: " + inv.getLevel());
                }
                else if (line.startsWith("LAST_SAVED_TIME=")) {
                lastSavedTime = Long.parseLong(line.split("=")[1]);
                System.out.println("Loaded lastSavedTime: " + lastSavedTime);
            }
                else if(line.startsWith("PLAYER_LEVEL=")){
                    int level = Integer.parseInt(line.split("=")[1].trim());
                    player.xpManager.setLevel(level);
                }
                else if (line.startsWith("TOTAL_PLAY_TIME=")) {
                    long playTime = Long.parseLong(line.split("=")[1].trim());
                    player.statTracker.setTotalPlayTime(playTime);
                }
                else if (line.startsWith("TOTAL_EARNINGS=")) {
                    double earnings = Double.parseDouble(line.split("=")[1].trim());
                    player.statTracker.setTotalEarnings(earnings);
                }
                else if (line.startsWith("TOTAL_INVESTMENTS_PURCHASED=")){
                    int totalinvestments = Integer.parseInt(line.split("=")[1].trim());
                    player.statTracker.setTotalNumberOfInvestments(totalinvestments);
                }
                else if(line.startsWith("TOTAL_UPGRADES_MADE=")){
                    int totalupgrades = Integer.parseInt(line.split("=")[1].trim());
                    player.statTracker.setTotalUpgrades(totalupgrades);
                }
                else if (line.startsWith("TOTAL_XP=")) {
                    int totalXP = Integer.parseInt(line.split("=")[1].trim());
                    player.xpManager.setTotalXP(totalXP);
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    public static long getLastSavedTime(){
        return lastSavedTime;
    }
}