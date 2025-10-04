/**
 * SharkUiController.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package UI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;

/**
 * This is a shark view class for displaying the shark objects.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public class SharkUiController extends CreatureUiController{
    /**
     * Constructor for a shark UI object.
     */
    public SharkUiController() {
        view = setView(0.5);
    }

    /**
     * Sets shark view.
     *
     * @param scale size.
     * @return view.
     */
    public Group setView(double scale) {
        //Create sharks body
        Ellipse body = new Ellipse(0, 0, 80 * scale, 20 * scale);
        body.setFill(Color.BLACK);
        //Create sharks tail
        Polygon tail = new Polygon(
                -65 * scale, 0,//attach inside body
                -105 * scale, -18 * scale,//top tip
                -105 * scale, 18 * scale//bottom tip
        );
        tail.setFill(Color.BLACK);
        //Create sharks fin
        Polygon fin = new Polygon(
                -10 * scale, -20 * scale,//base left
                20 * scale, -20 * scale,//base right
                -5 * scale, -45 * scale//tip shifted left for backward tilt
        );
        fin.setFill(Color.BLACK);
        return new Group(tail, body, fin);
    }
}
