/**
 * CreatureUiController.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package UI;

import javafx.scene.Group;
import javafx.scene.Node;

/**
 * This is a creature view class for displaying the creature objects.
 * Abstract as there is no creature just fish or shark.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public abstract class CreatureUiController {
    //visible to class and subclasses.
    protected Group view;

    /**
     * Sets creature view.
     *
     * @param scale size.
     * @return view.
     */
    public abstract Group setView(double scale);

    /**
     * Sets creature UI position to given coordinates.
     *
     * @param x cord for new UI position.
     * @param y cord for new UI position.
     */
    public void setPosition(double x, double y) {
        view.setTranslateX(x);
        view.setTranslateY(y);
    }

    /**
     * Sets rotation of creature UI to given angle.
     *
     * @param angle of rotation.
     */
    public void setRotation(double angle) {
        view.setRotate(angle);
    }

    /**
     * Gets the creature view.
     *
     * @return creature view.
     */
    public Node getView() {
        return view;
    }

    /**
     * Gets the creature colour.
     * There is no colour until the fish overrides it.
     *
     * @return null.
     */
    public Colour getColour() {
        return null;
    }
}
