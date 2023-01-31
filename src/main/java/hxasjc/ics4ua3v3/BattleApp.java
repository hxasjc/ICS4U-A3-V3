package hxasjc.ics4ua3v3;

import hxasjc.beans.FinalOrNullObjectValue;
import hxasjc.ics4ua3v3.monsters.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * The main application. It is responsible for setting up the UI and starting the battle
 */
public class BattleApp extends Application {
    /**
     * The associated instance of {@link BattleBeginController}
     */
    private final FinalOrNullObjectValue<BattleBeginController> battleBeginController = new FinalOrNullObjectValue<>();

    /**
     * The associated instance of {@link BattleController}
     */
    private final FinalOrNullObjectValue<BattleController> battleController = new FinalOrNullObjectValue<>();

    /**
     * The main stage associated with this app
     */
    public final FinalOrNullObjectValue<Stage> primaryStage = new FinalOrNullObjectValue<>();

    /**
     * The first/only app instance to be created
     */
    public static final FinalOrNullObjectValue<BattleApp> CURRENT_APP = new FinalOrNullObjectValue<>();

    /**
     * Launch the app
     * @see Application#launch(String...)
     * @param args Launch args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start the program and initialize the UI
     * @param stage The application's stage
     */
    @Override
    public void start(Stage stage) {
        try {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> showErrorDialog(e)); //set error handler

            primaryStage.setValue(stage);

            FXMLLoader loader = new FXMLLoader(BattleApp.class.getResource("battle-begin.fxml")); //load monster selection screen from fxml file
            Scene scene = new Scene(loader.load());

            stage.setTitle("DnD Battle Simulator"); //set the title and show the UI
            stage.setScene(scene);
            stage.show();

            CURRENT_APP.setValue(this);

            battleBeginController.setValue(loader.getController());

            getBattleBeginController().initRaceDropdowns(); //populate monster selection dropdowns
            getBattleBeginController().onBeginBattle(this::startBattle);

            pingMonsters();
        } catch (Throwable t) {
            showErrorDialog(t);
        }
    }

    /**
     * This method is called when the player pressed the Begin Battle button. It updates the UI and creates a new battle instance
     * @param playerClass The player's chosen class
     * @param computerClass The computer's chosen class
     */
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

    /**
     * Get the UI controller for the monster selection screen
     * @return The controller for battle-begin.fxml
     */
    public BattleBeginController getBattleBeginController() {
        if (!battleBeginController.containsValue()) {
            throw new NullPointerException("Controller has not been initialized");
        }
        return battleBeginController.getValue();
    }

    /**
     * Gets the UI controller for the battle screen
     * @return The controller for battle.fxml
     */
    public BattleController getBattleController() {
        if (!battleController.containsValue()) {
            throw new NullPointerException("Controller has not been initialized");
        }
        return battleController.getValue();
    }

    /**
     * Show an Alert dialog box with the throwable stack trace
     * @param throwable The throwable to display
     */
    private void showErrorDialog(Throwable throwable) {
        throwable.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, Util.stackTraceToString(throwable));
        alert.setWidth(500);
        alert.showAndWait();
    }

    /**
     * Call a method in all monster classes to ensure they are loaded
     */
    private static void pingMonsters() {
        Zombie.ping();
        OgreZombie.ping();
        BeholderZombie.ping();
        AwakenedShrub.ping();
        Allosaurus.ping();
        Ankylosaurus.ping();
    }
}
