/**
 * UiController.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package UI;

import Main.Applications;
import Main.Fish;
import Main.SeaCreature;
import Main.Shark;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.Arrays;
import java.util.List;

/**
 * This is a UI view class for displaying the UI.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public class UiController {
    public ToggleButton redBtn, greenBtn, purpleBtn, yellowBtn, blackBtn;
    public Button clearBtn;
    public Pane simulationBoard;
    private List<ToggleButton> buttonSelections;
    private Applications app;
    private Colour colour;

    /**
     * Initialises the UI controller.
     */
    @FXML
    public void initialize() {
        //Setting button data using colour enum
        redBtn.setUserData(Colour.RED);
        greenBtn.setUserData(Colour.GREEN);
        purpleBtn.setUserData(Colour.PURPLE);
        yellowBtn.setUserData(Colour.YELLOW);
        buttonSelections = Arrays.asList(redBtn, greenBtn, purpleBtn, yellowBtn, blackBtn);
        simulationBoard.setOnMouseClicked(this::spawnEntity);
        clearBtn.setOnAction(_ -> clearAll());
        //button selection logic
        for (ToggleButton button : buttonSelections) {
            button.setOnAction(_ -> {
                if (button.isSelected()) {
                    resetButtons();
                    selectBtn(button);
                    setHighlight(button, true);
                } else {
                    resetButtons();
                }
            });
        }
    }

    /**
     * Clear all group entities from the board.
     */
    private void clearAll() {
        app.clearAll();
        simulationBoard.getChildren().removeIf(node -> node instanceof Group);
        resetButtons();
    }

    /**
     * Spawns the entity in the main program.
     *
     * @param mouseEvent where the mouse is clicked to spawn the entity.
     */
    private void spawnEntity(MouseEvent mouseEvent) {
        double xPos = mouseEvent.getX(), yPos = mouseEvent.getY();
        if (app.checkEntityDistances(xPos, yPos) && isWithinBoard(xPos, yPos)) {//in the board not on another entity
            if (fishButtonSelected()) {
                app.spawnEntity("fish", xPos, yPos, colour);
            } else {
                app.spawnEntity("shark", xPos, yPos, colour);
            }
        }
    }

    /**
     * Checks mouse position is within board boundaries.
     *
     * @param x position.
     * @param y position.
     * @return if is within board dimensions.
     */
    private boolean isWithinBoard(double x, double y) {
        return x > 0 && x < simulationBoard.getWidth() - 1 && y > 0 && y < simulationBoard.getHeight() - 1;
    }

    /**
     * Checks if a fish button has been selected.
     *
     * @return if one is selected.
     */
    private boolean fishButtonSelected() {
        boolean oneIsSelected = false;
        for (ToggleButton button : buttonSelections) {
            if (button.isSelected() && button != blackBtn) {
                oneIsSelected = true;
                colour = (Colour) button.getUserData();//sets the colour to be given
            }
        }
        return oneIsSelected;
    }

    /**
     * Sets the creature UI and adds it to the simulation board.
     *
     * @param creature Creature to set UI for.
     * @param colour of fish UI.
     */
    public void addCreatureToBoard(SeaCreature creature, Colour colour) {
        CreatureUiController entityUI = null;
        if (creature instanceof Fish) {
            entityUI = new FishUiController(colour);
        } else if (creature instanceof Shark) {
            entityUI = new SharkUiController();
        }
        creature.setEntityView(entityUI);//sets the creatures UI
        simulationBoard.getChildren().add(creature.getEntityView().getView());//adds the set UI to the board
    }

    /**
     * Resets all the button selection and highlight on the control panel
     */
    private void resetButtons() {
        buttonSelections.forEach(button -> {
            button.setSelected(false);
            setHighlight(button, false);
        });
    }

    /**
     * Sets button FX based on if its highlighted or not.
     *
     * @param button button to highlight.
     * @param highlight if its highlighted or not.
     */
    private void setHighlight(ToggleButton button, boolean highlight) {
        String style = button.getStyle();
        if (highlight) {
            style = style.replace("-fx-border-color: black;", "-fx-border-color: #63f7b4;")
                    .replace("-fx-border-width: 2.25;", "-fx-border-width: 5;")
                    .replace("-fx-border-radius: 6;", "-fx-border-radius: 1;");
        } else {
            style = style.replace("-fx-border-color: #63f7b4;", "-fx-border-color: black;")
                    .replace("-fx-border-width: 5;", "-fx-border-width: 2.25;")
                    .replace("-fx-border-radius: 1;", "-fx-border-radius: 6;");
        }
        button.setStyle(style);
    }

    /**
     * Selects the given button.
     *
     * @param button to be selected.
     */
    private void selectBtn(ToggleButton button) {
        button.setSelected(true);
    }

    /**
     * Sets the application.
     *
     * @param app application.
     */
    public void setApp(Applications app) {
        this.app = app;
    }
}
