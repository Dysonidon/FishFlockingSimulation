/**
 * Shark.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package Main;

import javafx.scene.layout.Pane;
import java.util.List;

/**
 * This is a shark class, a blueprint for all shark objects.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public class Shark extends SeaCreature {
    private final List<Fish> fishesReference;

    /**
     * Constructor for a shark object.
     * @param xPos x axis position.
     * @param yPos y axis position.
     * @param boardReference board object reference.
     * @param fishesReference fishes list reference.
     */
    public Shark(double xPos, double yPos, Pane boardReference, List<Fish> fishesReference) {
        super(boardReference);
        this.position = new Vector(xPos, yPos);
        this.fishesReference = fishesReference;
        this.MAX_SPEED = 2.8;
        this.BORDER_OFFSET = 100;
    }

    /**
     * Applies a seek force for the calculated predicted position of the target fish.
     *
     * @param target fish.
     */
    public void pursue(Fish target) {
        Vector targetPosition = target.getPosition().copy();
        Vector predictedPosition = target.getVelocity().copy().multiply(10);//prediction of 10 pixels ahead
        targetPosition.add(predictedPosition);
        physicsHandler.applyForce(this, seek(targetPosition));
    }

    /**
     * Removes fish if within the target distance for a shark to eat it.
     *
     * @param target fish.
     */
    public void eatFish(Fish target) {
        double distance = getDifference(this.getPosition(), target.getPosition()).getMagnitude();
        if (distance < 20) {
            fishesReference.remove(target);
            boardReference.getChildren().remove(target.getEntityView().getView());
        }
    }

    /**
     * Finds the closest fish on the board to the shark.
     *
     * @return closest fish.
     */
    public Fish getTarget() {
        Fish closestFish = null;
        double closestDistance = 10000;
        for (Fish fish : fishesReference) {
            double distance = getDifference(this.getPosition(), fish.getPosition()).getMagnitude();
            if (distance < closestDistance) {
                closestDistance = distance;
                closestFish = fish;
            }
        }
        return closestFish;
    }
}