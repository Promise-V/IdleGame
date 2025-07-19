import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.DecimalFormat;

/**
 * Main UI class for the Idle Game. Handles all JavaFX components,
 * layout initialization, and live UI updates.
 */
public class IdleGameUI extends Application {

    private Player player;
    private GameEngine gameEngine;
    private VBox investmentList;
    private Label balanceLabel;
    private Label levelLabel;
    private ProgressBar xpProgress;
    private VBox statsContent;
    private Label totalPlayTimeLabel;
    private String formatted;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        player = new Player();
        SaveManager.load(player);
        player.statTracker.startSession();

        if (SaveManager.getLastSavedTime() > 0) {
            double offlineEarnings = OfflineManager.calculateOfflineEarnings(player, SaveManager.getLastSavedTime());
            player.addBalance(offlineEarnings);
            player.statTracker.addToTotalEarnings(offlineEarnings);
            System.out.println("Offline earnings: " + offlineEarnings);
        }

        gameEngine = new GameEngine(player);
        gameEngine.start();

        setupUI(primaryStage);
        startUIUpdater();
    }

    /**
     * Initializes and lays out the UI components
     */
    private void setupUI(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_CENTER);

        // Header elements
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);

        balanceLabel = new Label();
        balanceLabel.setFont(new Font(16));

        levelLabel = new Label();
        levelLabel.setFont(new Font(16));

        xpProgress = new ProgressBar();
        xpProgress.setPrefWidth(120);

        header.getChildren().addAll(balanceLabel, levelLabel, xpProgress);

        // Stats panel
        statsContent = new VBox(5);
        statsContent.getChildren().addAll(
                new Label(), // Total Earnings placeholder
                new Label(), // Total Investments
                new Label(), // Total Upgrades
                totalPlayTimeLabel = new Label()
        );

        TitledPane statsPane = new TitledPane("Stats", statsContent);

        // Investment list
        investmentList = new VBox(10);
        ScrollPane investmentScroll = new ScrollPane(investmentList);
        investmentScroll.setFitToWidth(true);
        investmentScroll.setPrefHeight(400);

        renderInvestments();

        root.getChildren().addAll(header, statsPane, investmentScroll);

        Scene scene = new Scene(root, 300, 600);
        stage.setScene(scene);
        stage.setTitle("Idle Game");
        stage.setOnCloseRequest(e -> {
            player.statTracker.endSession();
            SaveManager.save(player);
            gameEngine.stop();
        });
        stage.show();

        // Initial update
        updateHeader();
    }

    /**
     * Renders owned and purchasable investments into the UI
     */
    private void renderInvestments() {
        investmentList.getChildren().clear();

        for (Investment inv : player.getOwnedInvestments().values()) {
            VBox box = new VBox(5);
            box.setStyle("-fx-border-color: black; -fx-padding: 10;");

            Label nameLabel = new Label(inv.getName());
            Label descLabel = new Label(inv.investmentInfo());

            Button actionButton = new Button("Upgrade ($" + inv.getUpgradeCost() + ")");
            actionButton.setOnAction(e -> {
                if (player.upgrade(inv)) {
                    inv.updateLabel();
                    renderInvestments();
                }
            });

            box.getChildren().addAll(nameLabel, descLabel, actionButton);
            investmentList.getChildren().add(box);
        }

        for (InvestmentType type : InvestmentType.values()) {
            if (!player.getOwnedInvestments().containsKey(type)) {
                Investment inv = InvestmentFactory.create(type);
                if (inv == null) continue;

                VBox box = new VBox(5);
                box.setStyle("-fx-border-color: gray; -fx-padding: 10;");

                Label nameLabel = new Label(inv.getName());
                Label descLabel = new Label(inv.investmentInfo());

                Button buyButton = new Button("Buy ($" + inv.getPriceofInvestment() + ")");
                buyButton.setOnAction(e -> {
                    if (player.buy(inv)) {
                        renderInvestments();
                    }
                });

                box.getChildren().addAll(nameLabel, descLabel, buyButton);
                investmentList.getChildren().add(box);
            }
        }
    }

    /**
     * Updates all UI fields in real time (every second)
     */
    private void startUIUpdater() {
        Thread uiThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                javafx.application.Platform.runLater(this::updateHeader);
            }
        });
        uiThread.setDaemon(true);
        uiThread.start();
    }

    /**
     * Updates header stats like balance, level, XP bar, etc.
     */
    private void updateHeader() {
        DecimalFormat df = new DecimalFormat("#.00");

        balanceLabel.setText("Balance: $" + (int) player.getBalance());
        levelLabel.setText("Lvl " + player.getPlayerLevel());
        xpProgress.setProgress((double) player.getXP() / player.getXPToNextLevel());

        ((Label) statsContent.getChildren().get(0)).setText("Earnings: $" + df.format(player.statTracker.getTotalEarnings()));
        ((Label) statsContent.getChildren().get(1)).setText("Investments: " + player.statTracker.getTotalInvestmentsPurchased());
        ((Label) statsContent.getChildren().get(2)).setText("Upgrades: " + player.statTracker.getTotalUpgradesMade());

        long totalSeconds = player.statTracker.getLivePlayTime();
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        totalPlayTimeLabel.setText("Play Time: " + minutes + "m " + seconds + "s");
    }
}
