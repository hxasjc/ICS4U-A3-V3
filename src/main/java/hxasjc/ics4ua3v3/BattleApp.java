package hxasjc.ics4ua3v3;

import hxasjc.beans.FinalOrNullObjectValue;
import hxasjc.ics4ua3v3.monsters.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

            stage.setTitle("DnD Battle Simulator");
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
