package hxasjc.ics4ua3v3;

import hxasjc.beans.FinalOrNullObjectValue;
import hxasjc.ics4ua3v3.monsters.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BattleApp extends Application {
    private final FinalOrNullObjectValue<BattleBeginController> battleBeginController = new FinalOrNullObjectValue<>();
    private final FinalOrNullObjectValue<BattleController> battleController = new FinalOrNullObjectValue<>();
    public final FinalOrNullObjectValue<Stage> primaryStage = new FinalOrNullObjectValue<>();

    public static final FinalOrNullObjectValue<BattleApp> CURRENT_APP = new FinalOrNullObjectValue<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> showErrorDialog(e));

            primaryStage.setValue(stage);

            FXMLLoader loader = new FXMLLoader(BattleApp.class.getResource("battle-begin.fxml"));
            Scene scene = new Scene(loader.load());

            stage.setTitle("Dnd Battle Simulator");
            stage.setScene(scene);
            stage.show();

            CURRENT_APP.setValue(this);

            battleBeginController.setValue(loader.getController());

            getBattleBeginController().initRaceDropdowns();
            getBattleBeginController().onBeginBattle(this::startBattle);

            pingMonsters();
        } catch (Throwable t) {
            showErrorDialog(t);
        }
    }

    private void startBattle(Class<? extends Monster> playerClass, Class<? extends Monster> computerClass) {
        /*try {
            System.out.println("player: " + playerClass);
            System.out.println("computer: " + computerClass);

            FXMLLoader loader = new FXMLLoader(BattleApp.class.getResource("battle.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = primaryStage.getValue();
            stage.setScene(scene);

            battleController.setValue(loader.getController());

            BattleController controller = battleController.getValue();

            Monster playerMonster = playerClass.getDeclaredConstructor().newInstance(); //get a monster
            Monster computerMonster = computerClass.getDeclaredConstructor().newInstance();

            try {
                controller.setPlayerImage(new Image(BattleApp.class.getResource("monsters/" + playerClass.getSimpleName() + ".png").getPath()));
            } catch (NullPointerException ignored) {}

            try {
                controller.setComputerImage(new Image(BattleApp.class.getResource("monsters/" + computerClass.getSimpleName() + ".png").getPath()));
            } catch (NullPointerException ignored) {}

            int playerInitiative = rollD20(); //roll for initiative
            int computerInitiative = rollD20();

            boolean playerFirst = false;

            if (playerInitiative == computerInitiative) {
                playerInitiative =+ playerMonster.getStat(StatRolls.DEX); //if there is a tie, attempt to break it by checking DEX modifiers
                computerInitiative =+ computerMonster.getStat(StatRolls.DEX);

                if (playerInitiative == computerInitiative) { //if it's still tied, roll a D100
                    if (
                            rollDice(1, 100) > //player
                                    rollDice(1, 100) //computer
                    ) {
                        playerFirst = true;
                    }
                }
            } else {
                if (playerInitiative > computerInitiative) {
                    playerFirst = true;
                }
            }

            controller.setPlayerInitiative(playerInitiative);
            controller.setComputerInitiative(computerInitiative);

            controller.updateAbilityToolbar(playerMonster);

            while (true) {
                if (playerFirst) {
                    // player turn
                    //playerMonster.damageWithPrimary(computerMonster);

                    System.out.println("player first");

                    controller.disableAbilityToolbar(false);

                    updateHealth(playerMonster, computerMonster);

                    if (isAMonsterDead(playerMonster, computerMonster)) {
                        break;
                    }
                }

                //computer turn
                computerMonster.damageWithPrimary(playerMonster);

                updateHealth(playerMonster, computerMonster);

                if (isAMonsterDead(playerMonster, computerMonster)) {
                    break;
                }

                playerFirst = true; //allow the player to actually take turns
            }
        } catch (Throwable t) {
            showErrorDialog(t);
        }*/

        try {
            //primaryStage.getValue().setScene(new Scene(new FXMLLoader(BattleApp.class.getResource("battle.fxml")).load()));

            FXMLLoader loader = new FXMLLoader(BattleApp.class.getResource("battle.fxml"));
            primaryStage.getValue().setScene(new Scene(loader.load()));

            battleController.setValue(loader.getController());

            MainBattle battle = new MainBattle(
                    battleController.getValue(),
                    playerClass,
                    computerClass
            );

            battle.setErrorHandler(this::showErrorDialog);

            battle.determineOrder();
            battle.startBattleLoop();
        } catch (Throwable t) {
            showErrorDialog(t);
        }
    }

    private void updateHealth(Monster player, Monster computer) {
        int pc = player.getHealth();
        int pm = player.getMaxHealth();
        int cc = computer.getHealth();
        int cm = computer.getMaxHealth();

        BattleController controller = battleController.getValue();

        controller.setPlayerHealth(pc, pm);
        controller.setComputerHealth(cc, cm);
    }

    public BattleBeginController getBattleBeginController() {
        if (!battleBeginController.containsValue()) {
            throw new NullPointerException("Controller has not been initialized");
        }
        return battleBeginController.getValue();
    }

    public BattleController getBattleController() {
        if (!battleController.containsValue()) {
            throw new NullPointerException("Controller has not been initialized");
        }
        return battleController.getValue();
    }

    private void showErrorDialog(Throwable throwable) {
        throwable.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, Util.stackTraceToString(throwable));
        alert.setWidth(500);
        alert.showAndWait();
    }

    private static void pingMonsters() {
        Zombie.ping();
        OgreZombie.ping();
        BeholderZombie.ping();
        AwakenedShrub.ping();
        Allosaurus.ping();
        Ankylosaurus.ping();
    }
}
