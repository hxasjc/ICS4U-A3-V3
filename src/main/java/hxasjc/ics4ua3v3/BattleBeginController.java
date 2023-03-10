package hxasjc.ics4ua3v3;

import hxasjc.ics4ua3v3.monsters.Monster;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * This class controls the UI for the monster selection screen
 */
public class BattleBeginController {
    /**
     * Dropdown to choose the player's monster
     */
    @FXML
    private ChoiceBox<String> playerRaceChoice;

    /**
     * Dropdown to choose the computer's monster
     */
    @FXML
    private ChoiceBox<String> computerRaceChoice;

    /**
     * The button to begin the battle
     */
    @FXML
    private Button beginBattle;

    /**
     * A list containing the pretty names of each available class. Updated by {@link BattleBeginController#updateRaceList()}
     */
    private final ObservableList<String> raceChoiceList = FXCollections.observableArrayList();

    /**
     * A HashMap that stores a pretty name and the class for each monster available. It allows for easy retrieving of classes chosen by the dropdowns
     */
    private final HashMap<String, Class<? extends Monster>> raceNameMap = new HashMap<>();

    /**
     * The class that will be used to spawn the player's monster
     */
    private Class<? extends Monster> playerMonster;

    /**
     * The class that will be used to spawn the computer's monster
     */
    private Class<? extends Monster> computerMonster;

    /**
     * BiConsumer that is called when the button to begin the battle is called
     */
    private BiConsumer<Class<? extends Monster>, Class<? extends Monster>> beginBattleConsumer = (playerClass, computerClass) -> {};

    /**
     * Add a new monster class to {@link BattleBeginController#raceNameMap}.
     * @param monsterClass Class to add to the map
     */
    public void addMonster(Class<? extends Monster> monsterClass) {
        raceNameMap.put(Util.getNiceName(monsterClass), monsterClass);
        updateRaceList();
    }

    /**
     * Set the options for the monster choosing dropdowns to the list of monsters updated by {@link BattleBeginController#updateRaceList()}.
     */
    public void initRaceDropdowns() {
        playerRaceChoice.setItems(raceChoiceList);
        computerRaceChoice.setItems(raceChoiceList);
    }

    /**
     * Update the list of possible monsters from {@link BattleBeginController#raceNameMap} and sort them in alphabetical order
     */
    private void updateRaceList() {
        raceChoiceList.clear();
        raceChoiceList.add("Random");
        //raceChoiceList.addAll(raceNameMap.keySet());
        raceNameMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    Monster monster;
                    try {
                        monster = entry.getValue().getDeclaredConstructor().newInstance();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                    String cr = Monster.getChallengeRatingString(monster);
                    raceChoiceList.add(entry.getKey() + " (" + cr + ")");
                });
    }

    /**
     * Method that is called when the button to begin the battle is pressed. It passes the player's class and the computer's class to {@link BattleBeginController#beginBattleConsumer}
     * @param event Event generated by JavaFX
     */
    @FXML
    public void beginBattleAction(ActionEvent event) {
        beginBattleConsumer.accept(playerMonster, computerMonster);
    }

    /**
     * Method that is called when the value for the player monster dropdown is updated. It updates the variable
     * for the player's class, getting a random one if needed, and checks if the button to begin the battle should be enabled.
     * @param event Event generated by JavaFX
     */
    @FXML
    public void playerChooseAction(ActionEvent event) {
        String choice = playerRaceChoice.getValue();
        if (choice.equals("Random")) {
            playerMonster = getRandomMonster();
        } else {
            choice = choice.substring(0, choice.indexOf("(")).strip();
            playerMonster = raceNameMap.get(choice);
        }
        updateButtonVisibility();
    }

    /**
     * Method that is called whenever the value for the computer monster dropdown is updated. It updates the variable
     * for the computer's class, getting a random one if needed, and checks if the button to begin the battle should be enabled.
     * @param event Event generated by JavaFX
     */
    @FXML
    public void computerChooseAction(ActionEvent event) {
        String choice = computerRaceChoice.getValue();
        if (choice.equals("Random")) {
            computerMonster = getRandomMonster();
        } else {
            choice = choice.substring(0, choice.indexOf("(")).strip();
            computerMonster = raceNameMap.get(choice);
        }
        updateButtonVisibility();
    }

    /**
     * Checks if the button to begin the battle should be enabled. It will only be enabled if both dropdowns have a value selected
     */
    private void updateButtonVisibility() {
        beginBattle.setDisable(!(playerMonster != null && computerMonster != null));
    }

    /**
     * Gets a random monster class from the list of available classes
     * @return A random monster from {@link BattleBeginController#raceChoiceList}
     */
    private Class<? extends Monster> getRandomMonster() {
        List<Class<? extends Monster>> classList = raceNameMap.values().stream().toList();
        return classList.get(new Random().nextInt(classList.size()));
    }

    /**
     * Sets a BiConsumer that is called when a new battle is begun
     * @param consumer Consumer that will be called when a new battle is begun
     */
    public void onBeginBattle(BiConsumer<Class<? extends Monster>, Class<? extends Monster>> consumer) {
        beginBattleConsumer = consumer;
    }
}
