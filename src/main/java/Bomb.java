/**
 * Class for Bomb
 */
public class Bomb extends BaseObject
{
    public Bomb(double x, double y)
    {
        super(x, y, 1);
    }

    /**
     * Describes himself on the canvas.
     */
    @Override
    public void draw(Canvas canvas)
    {
        canvas.setPoint(x,y,'B');
    }

    /**
     * Move yourself down to one move.
     */
    @Override
    public void move()
    {
        y++;
    }
}
