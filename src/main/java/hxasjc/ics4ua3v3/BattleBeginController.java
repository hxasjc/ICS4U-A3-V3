package hxasjc.ics4ua3v3;

import hxasjc.ics4ua3v3.monsters.Monster;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.*;
import java.util.function.BiConsumer;

public class BattleBeginController {
    @FXML
    private ChoiceBox<String> playerRaceChoice;

    @FXML
    private ChoiceBox<String> computerRaceChoice;

    @FXML
    private Button beginBattle;

    private ObservableList<String> raceChoiceList = FXCollections.observableArrayList();

    private HashMap<String, Class<? extends Monster>> raceNameMap = new HashMap<>();
    private BiConsumer<Class<? extends Monster>, Boolean> playerMonsterConsumer = (aClass, aBoolean) -> {};
    private BiConsumer<Class<? extends Monster>, Boolean> computerMonsterConsumer = (aClass, aBoolean) -> {};

    private Class<? extends Monster> playerMonster;

    private Class<? extends Monster> computerMonster;
    private BiConsumer<Class<? extends Monster>, Class<? extends Monster>> beginBattleConsumer;

    /*public void addRace(Monster monster) {
        raceNameMap.put(Monster.getNiceName(monster), Monster.class);
        updateRaceList();
    }*/

    public void addMonster(Class<? extends Monster> monsterClass) {
        raceNameMap.put(Util.getNiceName(monsterClass), monsterClass);
        updateRaceList();
    }

    public void initRaceDropdowns() {
        playerRaceChoice.setItems(raceChoiceList);
        computerRaceChoice.setItems(raceChoiceList);
    }

    private void updateRaceList() {
        raceChoiceList.clear();
        raceChoiceList.add("Random");
        //raceChoiceList.addAll(raceNameMap.keySet());
        raceNameMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> raceChoiceList.add(entry.getKey()));
    }

    public void beginBattleAction(ActionEvent event) {
        beginBattleConsumer.accept(playerMonster, computerMonster);
    }

    public void playerChooseAction(ActionEvent event) {
        String choice = playerRaceChoice.getValue();
        if (choice.equals("Random")) {
            playerMonster = getRandomMonster();
            playerMonsterConsumer.accept(playerMonster, true);
        } else {
            playerMonster = raceNameMap.get(choice);
            playerMonsterConsumer.accept(playerMonster, false);
        }
        updateButtonVisibility();
    }

    public void computerChooseAction(ActionEvent event) {
        String choice = computerRaceChoice.getValue();
        if (choice.equals("Random")) {
            computerMonster = getRandomMonster();
            computerMonsterConsumer.accept(computerMonster, true);
        } else {
            computerMonster = raceNameMap.get(choice);
            computerMonsterConsumer.accept(computerMonster, false);
        }
        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        beginBattle.setDisable(!(playerMonster != null && computerMonster != null));
    }

    private Class<? extends Monster> getRandomMonster() {
        List<Class<? extends Monster>> classList = raceNameMap.values().stream().toList();
        return classList.get(new Random().nextInt(classList.size()));
    }

    public void onPlayerMonsterChoose(BiConsumer<Class<? extends Monster>, Boolean> consumer) {
        playerMonsterConsumer = consumer;
    }

    public void onComputerMonsterChoose(BiConsumer<Class<? extends Monster>, Boolean> consumer) {
        computerMonsterConsumer = consumer;
    }

    public void onBeginBattle(BiConsumer<Class<? extends Monster>, Class<? extends Monster>> consumer) {
        beginBattleConsumer = consumer;
    }

    public void printStackTrace() {
        throw new RuntimeException();
    }
}
