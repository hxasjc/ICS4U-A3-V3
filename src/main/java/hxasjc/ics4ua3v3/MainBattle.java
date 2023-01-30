package hxasjc.ics4ua3v3;

import hxasjc.ics4ua3v3.monsters.Monster;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

import java.util.Optional;
import java.util.function.Consumer;

import static hxasjc.ics4ua3v3.DiceRoll.*;

public class MainBattle {
    private final BattleController controller;
    private final Class<? extends Monster> playerClass;
    private final Class<? extends Monster> computerClass;
    private final Monster playerMonster;
    private final Monster computerMonster;
    private boolean playerFirst = false;
    private Consumer<Throwable> errorConsumer = throwable -> {};

    private final BattleTranscript transcript = new BattleTranscript();

    public MainBattle(BattleController controller, Class<? extends Monster> playerClass, Class<? extends Monster> computerClass) throws Throwable {
        this.controller = controller;
        this.playerClass = playerClass;
        this.computerClass = computerClass;

        playerMonster = playerClass.getDeclaredConstructor().newInstance();
        computerMonster = computerClass.getDeclaredConstructor().newInstance();

        /*try {
            this.controller.setPlayerImage(new Image(BattleApp.class.getResource("monsters/" + playerClass.getSimpleName() + ".png").getPath()));
        } catch (NullPointerException ignored) {}

        try {
            this.controller.setComputerImage(new Image(BattleApp.class.getResource("monsters/" + computerClass.getSimpleName() + ".png").getPath()));
        } catch (NullPointerException ignored) {}*/

        controller.setPlayerImage(playerMonster.getMonsterImage());
        controller.setComputerImage(computerMonster.getMonsterImage());

        controller.updateAbilityToolbar(playerMonster);
        controller.setAbilityButtonConsumer(this::playerAbilityButtonHandler);
        controller.updateMonsterLabels(playerMonster, computerMonster);
    }

    public void determineOrder() throws Throwable {
        int playerInit = rollD20();
        int computerInit = rollD20();

        if (playerInit == computerInit) {
            int playerMod = playerMonster.getStat(StatRolls.DEX);
            int computerMod = computerMonster.getStat(StatRolls.DEX);

            if (playerMod == computerMod) {
                if (
                        rollDice(1, 100) >
                                rollDice(1, 100)
                ) {
                    playerFirst = true;
                }
            } else {
                if (playerMod > computerMod) {
                    playerFirst = true;
                }
            }
        } else {
            if (playerInit > computerInit) {
                playerFirst = true;
            }
        }

        controller.setPlayerInitiative(playerInit);
        controller.setComputerInitiative(computerInit);
    }

    public void startBattleLoop() throws Throwable {
        updateHealth();

        if (playerFirst) {
            startPlayerTurn();
        } else {
            computerTurn();
        }
    }

    public void startPlayerTurn() {
        System.out.println("player turn");
        controller.disableAbilityToolbar(false);
    }

    public void playerAbilityButtonHandler(ActionEvent event) {
        try {
            AbilityButton button = (AbilityButton) event.getSource();
            Attack ability = button.getAttack();

            System.out.println("using ability '" + ability.getName() + "'");
            playerMonster.damageWithAttack(computerMonster, ability, transcript);

            controller.disableAbilityToolbar(true);
            if (!isAMonsterDead()) {
                computerTurn();
            } else {
                endGame(true);
            }
        } catch (ClassCastException ignored) {
        } catch (Throwable t) {
            errorConsumer.accept(t);
        }
    }

    public void computerTurn() {
        System.out.println("computer turn");
        computerMonster.damageWithPrimary(playerMonster, transcript);
        updateHealth();
        if (!isAMonsterDead()) {
            startPlayerTurn();
        } else {
            endGame(false);
        }
    }

    public void endGame(boolean playerWin) {
        updateHealth();
        new Alert(
                Alert.AlertType.INFORMATION,
                playerWin ? "You Won!" : "You Lost"
        ).showAndWait();

        //TODO implement this better
        /*Optional<ButtonType> button = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Would you like to save the battle transcript?"
        ).showAndWait();

        if (button.isPresent()) {
            if (button.get().getText().equals("OK")) {
                transcript.save(controller.abilityToolbar.getScene().getWindow());
            }
        }*/
    }

    private void updateHealth() {
        controller.setPlayerHealth(
                playerMonster.getHealth(),
                playerMonster.getMaxHealth()
        );
        controller.setComputerHealth(
                computerMonster.getHealth(),
                computerMonster.getMaxHealth()
        );
    }

    private boolean isAMonsterDead() {
        return playerMonster.getHealth() < 1 || computerMonster.getHealth() < 1;
    }

    public void setErrorHandler(Consumer<Throwable> consumer) {
        errorConsumer = consumer;
    }
}
