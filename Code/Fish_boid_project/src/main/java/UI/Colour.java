/**
 * Colour.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package UI;

import javafx.scene.paint.Color;

/**
 * This is a colour class, an enum of the different colours.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public enum Colour {
    //extra information stored for my enum constants.
    RED(Color.RED),
    GREEN(Color.GREEN),
    PURPLE(Color.PURPLE),
    YELLOW(Color.YELLOW);

    private final Color fxColor;

    /**
     * Constructor for a colour object.
     *
     * @param fxColor Colour fx value.
     */
    Colour(Color fxColor) {
        this.fxColor = fxColor;
    }

    /**
     * Gets the fx colour.
     *
     * @return fx colour.
     */
    public Color getFxColour() {
        return fxColor;
    }
}
