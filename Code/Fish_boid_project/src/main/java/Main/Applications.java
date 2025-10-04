/**
 * Applications.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package Main;

import UI.Colour;
import UI.UiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * This is an applications class for running the program.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public class Applications extends Application {
    private static final double STAGE_WIDTH = 1600, STAGE_HEIGHT = 937, STAGE_POSITION = 100;
    private Stage primaryStage;
    private UiController controller;
    private final ArrayList<Fish> fishes = new ArrayList<>();
    private final ArrayList<Shark> sharks = new ArrayList<>();
    PhysicsHandler physicsHandler = new PhysicsHandler();

    /**
     * Starts the javaFX and displays it to the user.
     *
     * @param stage simulation stage.
     * @throws Exception exception.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("Fish boid simulation");
        ShowUI();
    }

    /**
     * Shows main UI controller using the fxml loader.
     *
     * @throws IOException exception.
     */
    public void ShowUI() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameUI.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setApp(this);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(STAGE_WIDTH);
        primaryStage.setMinHeight(STAGE_HEIGHT);
        primaryStage.setMaxWidth(STAGE_WIDTH);
        primaryStage.setMaxHeight(STAGE_HEIGHT);
        primaryStage.setX(STAGE_POSITION);
        primaryStage.setY(STAGE_POSITION);
        primaryStage.show();
    }

    /**
     * Spawns a newly created creature onto the board and begins its movement.
     *
     * @param type creature type.
     * @param x position.
     * @param y position.
     * @param colour fish colour.
     */
    public void spawnEntity(String type, double x, double y, Colour colour) {
        SeaCreature creature = SeaCreature.create(type, x, y, controller.simulationBoard, fishes, sharks);
        creature.setApp(this);
        if (type.equalsIgnoreCase("fish")) {
            fishes.add((Fish) creature);
        } else if (type.equalsIgnoreCase("shark")) {
            sharks.add((Shark) creature);
        }
        controller.addCreatureToBoard(creature, colour);
        creature.startMoving();
    }

    /**
     * Function for basic movement behaviours for all entities.
     *
     * @param creature creature entity.
     */
    public void moveEntities(SeaCreature creature) {
        Vector steerForce = creature.checkBoundaries();
        if (steerForce != null) {
            physicsHandler.applyForce(creature, steerForce);
        } else {
            if (creature instanceof Fish) {
                moveFishEntity((Fish) creature);
            } else if (creature instanceof Shark) {
                moveSharkEntity((Shark) creature);
            }
        }
        physicsHandler.physicsUpdate(creature, creature.MAX_SPEED);
        creature.showEntityViewUpdate();
    }

    /**
     * Fish specific movement behaviours.
     *
     * @param currentFish fish entity.
     */
    public void moveFishEntity(Fish currentFish) {
        Shark closebyShark = currentFish.getClosebyShark();
        if (closebyShark != null) {
            currentFish.evade(closebyShark);
        } else {
            currentFish.wander();
            currentFish.flockingBehaviour();
        }
    }

    /**
     * Shark specific movement behaviours.
     *
     * @param currentShark shark entity.
     */
    public void moveSharkEntity(Shark currentShark) {
        if (fishes.isEmpty()) {
            currentShark.wander();
        } else {
            Fish target = currentShark.getTarget();
            currentShark.pursue(target);
            currentShark.eatFish(target);
        }
        Vector separationForce = currentShark.calculateSeparationForce(sharks);
        separationForce.multiply(1.2);
        physicsHandler.applyForce(currentShark, separationForce);
    }

    /**
     * Checks the creatures are at a suitable distance from each other.
     *
     * @param x position.
     * @param y position.
     * @return if at a suitable distance.
     */
    public boolean checkEntityDistances(double x, double y) {
        Vector spawnPos = new Vector(x, y);
        for (SeaCreature creature : getAllEntities().toList()) {
            double minDistance = (creature instanceof Fish) ? 30 : 50;//fish and shark separation distances
            double currentDistance = spawnPos.copy().subtract(creature.getPosition()).getMagnitude();
            if (currentDistance < minDistance) {
                return false;
            }
        }
        return true;
    }

    /**
     * Stops all timeline movements and clears fish and shark lists.
     */
    public void clearAll() {
        getAllEntities().forEach(SeaCreature::stopMoving);
        fishes.clear();
        sharks.clear();
    }

    /**
     * Gets a stream of all creatures.
     *
     * @return creature stream.
     */
    private Stream<SeaCreature> getAllEntities() {
        return Stream.concat(fishes.stream(), sharks.stream());
    }
}
