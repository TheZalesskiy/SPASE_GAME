package com.javarush.test.level25.lesson16.big01;

/**
 * Class for Rocket
 */
public class Rocket  extends BaseObject
{

    public Rocket(double x, double y)
    {
        super(x, y, 1);
    }

    /**
     * The method draws your object on the "canvas".
     */
    @Override
    public void draw(Canvas canvas)
    {
        canvas.setPoint(x,y,'R');
    }

    /**
     * Move yourself up to one turn.
     */
    @Override
    public void move()
    {
        y--;
    }
}
