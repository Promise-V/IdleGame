import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameUI extends Application {
    private Player player = new Player(); // your player class
    private Label balanceLabel = new Label("Balance: $0.00");
    private Label investmentLabel = new Label("Investments: 0");
    private Label investmentInfo = new Label();
    private Label playerLevel = new Label("Level: ");
    private Button upgrade;

    private Timer gameLoop = new Timer();

    @Override
    public void start(Stage primaryStage) {
        SaveManager.load(player);
        player.statTracker.startSession();
        Button buyButton = new Button("Buy Lemonade Stand ($100)");

        buyButton.setOnAction(e -> {
            boolean bought = player.buy(Objects.requireNonNull(InvestmentFactory.create(InvestmentType.LEMONADE_STAND)));
            if (bought) {
                updateLabels();
            }
        });
        upgrade = new Button("Upgrade Investment");
        upgrade.setOnAction(e -> {
            if (!player.ownedInvestments.isEmpty()) {
                Investment inv = player.ownedInvestments.get(InvestmentType.LEMONADE_STAND);
                boolean upgraded = player.upgrade(inv);
                if (upgraded) updateLabels();
            }
        });
        VBox root = new VBox(10, balanceLabel, investmentLabel, buyButton, investmentInfo, upgrade,playerLevel);
        root.setStyle("-fx-padding: 20;");
        primaryStage.setScene(new Scene(root, 300, 150));
        primaryStage.setTitle("Idle Game");
        primaryStage.show();


        SaveManager.load(player);
        double offlineEarnings = OfflineManager.calculateOfflineEarnings(player, SaveManager.getLastSavedTime());
        player.addBalance(offlineEarnings);
        System.out.println("Offline Earnings: $" + offlineEarnings);
        // 4. Start ticking
        startGameLoop();
        // 5. Set up save on exit (must be LAST)
        primaryStage.setOnCloseRequest(e -> {
            player.statTracker.endSession();  // ⏹️ Stop the session timer
            SaveManager.save(player);         // 💾 Then save everything
        });
    }

    private void startGameLoop() {
        gameLoop.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double income = 0;
                for (Investment inv : player.ownedInvestments.values()) {
                    income += inv.tick();// assuming this method exists
                }
                player.addBalance(income);

                Platform.runLater(() -> updateLabels());
            }
        }, 0, 1000); // every second
    }

    private void updateLabels() {
        balanceLabel.setText("Balance: $" + String.format("%.2f", player.getBalance()));
        investmentLabel.setText("Investments: " + player.ownedInvestments.size());
        playerLevel.setText("Level: " + player.getPlayerLevel());
        if(!player.ownedInvestments.isEmpty()){
            Investment inv = player.ownedInvestments.get(InvestmentType.LEMONADE_STAND);
            investmentInfo.setText(inv.investmentInfo());
            upgrade.setText("Upgrade Investments (" + player.ownedInvestments.get(InvestmentType.LEMONADE_STAND).getUpgradeCost() + "). ");
        }

    }

    @Override
    public void stop() {
        gameLoop.cancel();
    }

    public static void main(String[] args) {
        launch(args);
    }
}