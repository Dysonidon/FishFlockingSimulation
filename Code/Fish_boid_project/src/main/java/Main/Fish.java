/**
 * Fish.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package Main;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a fish class, a blueprint for all fish objects.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public class Fish extends SeaCreature {
    private final List<Fish> fishesReference;
    private final List<Shark> sharksReference;

    /**
     * Constructor for a fish object.
     *
     * @param xPos x axis position.
     * @param yPos y axis position.
     * @param boardReference board object reference.
     * @param fishesReference fishes list reference.
     * @param sharksReference sharks list reference.
     */
    public Fish(double xPos, double yPos, Pane boardReference, ArrayList<Fish> fishesReference, List<Shark> sharksReference) {
        super(boardReference);
        this.position = new Vector(xPos, yPos);
        this.fishesReference = fishesReference;
        this.sharksReference = sharksReference;
        this.MAX_SPEED = 2.5;
        this.BORDER_OFFSET = 80;
    }

    /**
     * Fish flocking principles to influence the acceleration to flock with other fish.
     */
    public void flockingBehaviour() {
        Vector separationForce = calculateSeparationForce(fishesReference);
        Vector alignmentForce = calculateAlignmentForce();
        Vector cohereForce = calculateCohereForce();
        //Scale forces
        separationForce.multiply(1.2);
        alignmentForce.multiply(0.8);
        cohereForce.multiply(0.8);
        //Apply forces to acceleration
        physicsHandler.applyForce(this, separationForce);
        physicsHandler.applyForce(this, alignmentForce);
        physicsHandler.applyForce(this, cohereForce);
    }

    /**
     * Gets the closest shark within the detection radius.
     *
     * @return closest shark.
     */
    public Shark getClosebyShark() {
        if (sharksReference.isEmpty()) {
            return null;//no sharks to check for
        }
        double detectionRadius = 150;
        Shark closestShark = null;
        double closestDistance = 10000;
        //searches all sharks within radius for the closest one
        for (Shark shark : sharksReference) {
            double distance = getDifference(this.getPosition(), shark.getPosition()).getMagnitude();
            if (distance < detectionRadius && distance < closestDistance) {
                closestDistance = distance;
                closestShark = shark;
            }
        }
        return closestShark;
    }

    /**
     * Applies force to accelerate in the opposite direction of the shark.
     *
     * @param shark close shark.
     */
    public void evade(Shark shark) {
        Vector evadeForce = seek(shark.getPosition()).multiply(-1);
        physicsHandler.applyForce(this, evadeForce);
    }

    /**
     * Calculates steer force for aligning the fish together.
     *
     * @return steer force.
     */
    public Vector calculateAlignmentForce() {
        double neighborDistance = 50;
        Vector sum = new Vector(0, 0);
        int count = 0;

        for (Fish otherFish : fishesReference) {
            if (otherFish != this && otherFish.getEntityView().getColour() == this.entityView.getColour()) {
                double distance = getDifference(this.getPosition(), otherFish.getPosition()).getMagnitude();
                if (distance > 0 && distance < neighborDistance) {
                    sum.add(otherFish.velocity);
                    count++;
                }
            }
        }

        if (count > 0) {
            sum.divide(count);//average velocity
            sum.normalize();//direction only
            sum.multiply(MAX_SPEED);//scale to fish's max speed
            Vector steerForce = getDifference(sum, this.velocity);
            steerForce.limitMagnitude(MAX_FORCE);//cap steering force
            return steerForce;
        } else {
            return new Vector(0, 0);
        }
    }

    /**
     * Calculates steer force for cohering the fish movements with close by fish.
     *
     * @return steer force.
     */
    public Vector calculateCohereForce() {
        double neighborDistance = 50;
        Vector sum = new Vector(0, 0);
        int count = 0;

        for (Fish otherFish : fishesReference) {
            if (otherFish != this && otherFish.getEntityView().getColour() == this.entityView.getColour()) {
                double distance = getDifference(this.getPosition(), otherFish.getPosition()).getMagnitude();
                if (distance > 0 && distance < neighborDistance) {
                    sum.add(otherFish.getPosition());//add neighbor position
                    count++;
                }
            }
        }

        if (count > 0) {
            sum.divide(count);//average position
            return seek(sum);//steer toward it
        } else {
            return new Vector(0, 0);
        }
    }
}
