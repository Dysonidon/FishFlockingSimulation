/**
 * FishUiController.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package UI;

import javafx.scene.Group;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;

/**
 * This is a fish view class for displaying the fish objects.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public class FishUiController extends CreatureUiController{
    private final Colour colour;

    /**
     * Constructor for a fish UI object.
     *
     * @param colour fish colour.
     */
    public FishUiController(Colour colour) {
        this.colour = colour;
        view = setView(0.5);
    }

    /**
     * Sets fish view.
     *
     * @param scale size.
     * @return view.
     */
    public Group setView(double scale) {
        //create fishes body
        Ellipse body = new Ellipse(0, 0, 25 * scale, 12 * scale);
        body.setFill(colour.getFxColour());
        //create fishes tail
        Polygon tail = new Polygon(-20 * scale, 0,//tail connects to body
                -40 * scale, -10 * scale,//top corner
                -40 * scale, 10 * scale//bottom corner
        );
        tail.setFill(colour.getFxColour());
        return new Group(tail, body);
    }

    /**
     * Gets fish UI colour.
     *
     * @return colour.
     */
    @Override
    public Colour getColour() {
        return colour;
    }
}
