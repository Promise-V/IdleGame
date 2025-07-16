import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class IdleGameUI extends Application {

    private Player player;
    private GameEngine gameEngine;
    private VBox investmentList;
    private Label balanceLabel;
    private Label levelLabel;
    private ProgressBar xpProgress;
    private VBox statsContent;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        player = new Player();
        SaveManager.load(player);
        double offlineEarnings = OfflineManager.calculateOfflineEarnings(player, SaveManager.getLastSavedTime());
        player.addBalance(offlineEarnings);
        player.statTracker.addToTotalEarnings(offlineEarnings);
        gameEngine = new GameEngine(player);
        gameEngine.start();

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_CENTER);

        // Header
        HBox header = new HBox();
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER);

        balanceLabel = new Label("Balance: $" + (int)player.getBalance());
        balanceLabel.setFont(new Font(16));

        levelLabel = new Label("Lvl " + player.getPlayerLevel());
        levelLabel.setFont(new Font(16));

        xpProgress = new ProgressBar();
        xpProgress.setProgress((double) player.getXP() / player.getXPToNextLevel());
        xpProgress.setPrefWidth(120);

        header.getChildren().addAll(balanceLabel, levelLabel, xpProgress);

        // Stats dropdown
        TitledPane statsPane = new TitledPane();
        statsContent = new VBox();
        statsContent.setSpacing(5);
        statsContent.getChildren().addAll(
                new Label("Earnings: $" + (int)player.statTracker.getTotalEarnings()),
                new Label("Investments: " + player.statTracker.getTotalInvestmentsPurchased()),
                new Label("Upgrades: " + player.statTracker.getTotalUpgradesMade()),
                new Label("Play Time: " + (player.statTracker.getTotalPlayTime() / 60) + "m")
        );
        statsPane.setText("Stats");
        statsPane.setContent(statsContent);

        // Investment list
        investmentList = new VBox();
        investmentList.setSpacing(10);
        ScrollPane investmentScroll = new ScrollPane(investmentList);
        investmentScroll.setFitToWidth(true);
        investmentScroll.setPrefHeight(400);

        renderInvestments();

        root.getChildren().addAll(header, statsPane, investmentScroll);

        Scene scene = new Scene(root, 300, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Idle Game");
        primaryStage.setOnCloseRequest(e -> {
            player.statTracker.endSession();
            SaveManager.save(player);
            gameEngine.stop();
        });
        primaryStage.show();

        startUIUpdater();
    }

    private void renderInvestments() {
        investmentList.getChildren().clear();
        for (Investment inv : player.ownedInvestments.values()) {
            VBox box = new VBox();
            box.setSpacing(5);
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
            if (!player.ownedInvestments.containsKey(type)) {
                Investment inv = InvestmentFactory.create(type);
                if (inv == null) continue;

                VBox box = new VBox();
                box.setSpacing(5);
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

    private void startUIUpdater() {
        Thread uiThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                javafx.application.Platform.runLater(() -> {
                    balanceLabel.setText("Balance: $" + (int)player.getBalance());
                    levelLabel.setText("Lvl " + player.getPlayerLevel());
                    xpProgress.setProgress((double) player.getXP() / player.getXPToNextLevel());
                    Label earningsLabel = (Label) statsContent.getChildren().getFirst();
                    DecimalFormat df = new DecimalFormat("#.00");
                    earningsLabel("Total Earnings: $" + df.format(player.statTracker.getTotalEarnings()));
                });
            }
        });
        uiThread.setDaemon(true);
        uiThread.start();
    }

    private void earningsLabel(String s) {
    }
}
