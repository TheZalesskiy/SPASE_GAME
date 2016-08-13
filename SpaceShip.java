package com.javarush.test.level25.lesson16.big01;

/**
 * Class for SpaceShip
 */
public class SpaceShip extends BaseObject
{
    //ship to draw a picture
    private static int[][] matrix = {
            {0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1},
    };

    //motion vector (left -1, + 1 right)
    private double dx = 0;

    public SpaceShip(int x, int y)
    {
        super(x, y, 3);
    }

    /**
     * Set the motion vector to the left
     */
    public void moveLeft()
    {
        dx = -1;
    }

    /**
     * Set the motion vector to the right
     */
    public void moveRight()
    {
        dx = 1;
    }

    /**
     * The method draws your object on the "canvas".
     */
    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawMatrix(x - radius + 1, y, matrix, 'M');
    }

    /**
     * Move yourself in one turn.
      * Check the collision with the boundaries.
     */
    @Override
    public void move()
    {
        x = x + dx;

        checkBorders(radius, Space.game.getWidth() - radius + 1, 1, Space.game.getHeight() + 1);
    }

    /**
     * Shoot.
      * Create two missiles: the left and right side of the ship.
     */
    public void fire()
    {
        Space.game.getRockets().add(new Rocket(x - 2, y));
        Space.game.getRockets().add(new Rocket(x + 2, y));
    }
}
