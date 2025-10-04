/**
 * Vector.java
 * <p>
 * Copyright (c) 2025 Jacob Broomfield.
 * All rights reserved.
 */
package Main;

/**
 * This is a vector class, a blueprint for all vectors used in the program.
 * A vector is a quantity with a direction (where it points) and a magnitude (length of vector).
 * This is a 2D vector represented by numbers x and y. if x=3 and y=4 it points mostly right and a bit up.
 * ============
 * You can have position which is the x,y position of the object the vector points to its location on screen
 * (where it is)
 * You can have velocity which is the direction the object is moving from one moment to the next.
 * (change in location over time)
 * You can have acceleration which is the change of magnitude or direction of the velocity over time.
 * (change in velocity over time)
 * You can have force which is a vector that causes an object to accelerate.
 *
 * @author Jacob Broomfield
 * @version 1.0
 */
public class Vector {
    //vector properties
    public double x;
    public double y;

    /**
     * Constructor for a Vector object.
     * 
     * @param x how far along x-axis from starting point.
     * @param y How far along y-axis from starting point.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method adds a given vector to this vector before returning it.
     * Adding vectors combines the movements end to end where this vector changes in response to given vector
     * ============
     * Can add velocity to position moving it along velocity(position changes in response to velocity).
     *
     * @param addingVector vector being added.
     * @return result of addition.
     */
    public Vector add(Vector addingVector) {
        this.x += addingVector.x;
        this.y += addingVector.y;
        return this;
    }

    /**
     * This method subtracts a given vector from this vector before returning it.
     * Can find a position of this vector with a relative starting position from the vector given
     * ============
     * Can be used to find direction from this position on the board to the given position which is my location.
     *
     * @param subtractingVector vector being subtracted.
     * @return result of subtraction.
     */
    public Vector subtract(Vector subtractingVector) {
        this.x -= subtractingVector.x;
        this.y -= subtractingVector.y;
        return this;
    }

    /**
     * This method multiplies this vector by the given number essentially scaling it larger or smaller.
     * Any number given less than 1 shrinks the vector by the given scale and vice versa setting the magnitude.
     * ============
     * Difference between different coloured fish is doubled ti push away extra hard when too close.
     *
     * @param scale to scale by.
     * @return scaled vector.
     */
    public Vector multiply(double scale) {
        this.x *= scale;
        this.y *= scale;
        return this;
    }

    /**
     * Divided number by whatever number is given.
     * Used for normalizing too (dividing vector by itself).
     *
     * @param dividingNumber number given.
     */
    public void divide(double dividingNumber) {
        if (dividingNumber != 0) {
            this.x /= dividingNumber;
            this.y /= dividingNumber;
        }
    }

    /**
     * Shrinks vector to a magnitude of 1 whilst maintaining its direction.
     * Always divide it by itself to get 1.
     */
    public void normalize() {
        double magnitude = getMagnitude();
        if (magnitude != 0) {
            divide(magnitude);
        }
    }

    /**
     * If x y cords relative to a starting point are too large sets magnitude within limit given.
     *
     * @param max magnitude limit.
     */
    public void limitMagnitude(double max) {
        if (getMagnitude() > max) setMagnitude(max);
    }

    /**
     * Returns a copy of this vector.
     *
     * @return a copy of this.
     */
    public Vector copy() {
        return new Vector(x, y);
    }

    /**
     * To set a magnitude you need to keep its direction so you set it to 1 and times by given amount.
     * 
     * @param newMagnitude given magnitude.
     */
    public void setMagnitude(double newMagnitude) {
        normalize();
        multiply(newMagnitude);
    }

    /**
     * The magnitude is the length of a vector where the vector is an x,y coordinate relative to a starting point.
     * Can be used say to check if magnitude of velocity is too high then it's going too fast.
     *
     * @return magnitude.
     */
    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);//returns x squared + y squared
    }

    /**
     * Gets the angle from a starting direction based on the x and y
     *
     * @return angle of direction.
     */
    public double getHeading() {
        return Math.atan2(y, x);
    }
}