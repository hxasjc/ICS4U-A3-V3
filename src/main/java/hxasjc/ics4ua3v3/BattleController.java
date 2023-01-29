package hxasjc.ics4ua3v3;

import hxasjc.ics4ua3v3.monsters.Monster;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Map;
import java.util.function.Consumer;

public class BattleController {
    @FXML
    public ImageView playerImage;
    @FXML
    public ImageView computerImage;
    @FXML
    public ProgressBar playerHealthBar;
    @FXML
    public ProgressBar computerHealthBar;
    @FXML
    public Label playerHealthLabel;
    @FXML
    public Label playerInitiativeLabel;
    @FXML
    public Label computerHealthLabel;
    @FXML
    public Label computerInitiativeLabel;
    @FXML
    public ToolBar abilityToolbar;
    @FXML
    public Label playerMonsterLabel;
    @FXML
    public Label computerMonsterLabel;

    private Consumer<ActionEvent> abilityButtonConsumer = event -> {};

    public void setComputerImage(Image image) {
        computerImage.setImage(image);
    }

    public void setPlayerImage(Image image) {
        playerImage.setImage(image);
    }

    public void setPlayerInitiative(int initiative) {
        playerInitiativeLabel.setText("Initiative: " + initiative);
    }

    public void setComputerInitiative(int initiative) {
        computerInitiativeLabel.setText("Initiative: " + initiative);
    }

    public void setPlayerHealth(int current, int max) {
        playerHealthLabel.setText("Health: " + current + "/" + max);
        try {
            playerHealthBar.setProgress(max / current);
        } catch (ArithmeticException e) {
            playerHealthBar.setProgress(0);
        }
    }

    public void setComputerHealth(int current, int max) {
        computerHealthLabel.setText("Health: " + current + "/" + max);
        try {
            computerHealthBar.setProgress(max / current);
        } catch (ArithmeticException e) {
            computerHealthBar.setProgress(0);
        }
    }

    public void updateAbilityToolbar(Monster monster) {
        ObservableList<Node> list = abilityToolbar.getItems();
        list.clear();
        Attack primary = monster.getPrimaryAttack().get();
        //Button primaryButton = new Button(primary.getName() + " (" + primary.getDamageRoll() + ")");
        AbilityButton primaryButton = new AbilityButton(primary);
        primaryButton.setOnAction(this::playerAbilityButtonAction);
        list.add(primaryButton);
    }

    public void disableAbilityToolbar(boolean disable) {
        abilityToolbar.setDisable(disable);
    }

    public void playerAbilityButtonAction(ActionEvent event) {
        try {
            AbilityButton source = (AbilityButton) event.getSource();
            if (source.getParent().getParent().equals(abilityToolbar)) {
                abilityButtonConsumer.accept(event);
            }
        } catch (ClassCastException ignored) {
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void setAbilityButtonConsumer(Consumer<ActionEvent> abilityButtonConsumer) {
        this.abilityButtonConsumer = abilityButtonConsumer;
    }

    public void updateMonsterLabels(Monster player, Monster computer) {
        playerMonsterLabel.setText(Util.getNiceName(player.getClass()));
        computerMonsterLabel.setText(Util.getNiceName(computer.getClass()));
    }
}
