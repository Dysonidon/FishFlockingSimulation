/**
 * PhysicsHandler.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package Main;

/**
 * This is a physics engine class, a blueprint for all physics interactions.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public class PhysicsHandler {
    /**
     * Changes the acceleration based off of some external force being added.
     * Change could be caused by anything from other fish to boundaries to sharks.
     *
     * @param force external force
     */
    public void applyForce(SeaCreature creature, Vector force) {
        creature.getAcceleration().add(force);
    }

    /**
     * The update function which changes the position of the entity based of how far and where velocity makes it go.
     * velocity is changed and set by acceleration (acceleration has been decided based of multiple external forces).
     * Only place where velocity and position are actually changed at the end of a loop although are referenced.
     */
    public void physicsUpdate(SeaCreature creature, double MAX_SPEED) {
        creature.getVelocity().add(creature.getAcceleration());//Velocity changes based of acceleration(speed changes by external force)
        creature.getVelocity().limitMagnitude(MAX_SPEED);//velocity limited to max speed
        creature.getPosition().add(creature.getVelocity());//position updated based of current velocity (how far in what direction)
        creature.getAcceleration().multiply(0);//acceleration set back to 0
    }
}
