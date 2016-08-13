package com.javarush.test.level25.lesson16.big01;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The main game class - Space
 */
public class Space
{
    //The width and height of the playing field
    private int width;
    private int height;

    //Spaceship
    private SpaceShip ship;
    //UFO List
    private ArrayList<Ufo> ufos = new ArrayList<Ufo>();
    //Bomb list
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    //Rocket list
    private ArrayList<Rocket> rockets = new ArrayList<Rocket>();

    public Space(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    /**
     *  The main program loop.
      * It's all important actions occur
     */
    public void run()
    {
        //Create a canvas for drawing.
        Canvas canvas = new Canvas(width, height);

        //Create an object "observer of the keyboard" and we start it.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //The game runs until the ship alive.
        while (ship.isAlive())
        {
            //"Observer" contains events keystrokes?
            if (keyboardObserver.hasKeyEvents())
            {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //If the "left arrow" - move the figure to the left
                System.out.print(event.getKeyCode());
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    ship.moveLeft();
                //If the "right arrow" - move the figure to the right
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    ship.moveRight();
                //If the "gap" - run the ball
                else if (event.getKeyCode() == KeyEvent.VK_SPACE)
                    ship.fire();
            }

            //move objects games
            moveAllItems();

            //collision check
            checkBombs();
            checkRockets();
            //remove dead objects from the lists
            removeDead();

            //Create UFOs (1 every 10 turns)
            createUfo();

            //We describe all the objects on the canvas canvas display
            canvas.clear();
            draw(canvas);
            canvas.print();

            //pause 300 ms
            Space.sleep(300);
        }

        //writing message "Game Over"
        System.out.println("Game Over!");
    }

    /**
     * Move objects games
     */
    public void moveAllItems()
    {
        for (BaseObject object : getAllItems())
        {
            object.move();
        }
    }

    /**
     * The method returns a generic list that contains all the objects of the game
     */
    public List<BaseObject> getAllItems()
    {
        ArrayList<BaseObject> list = new ArrayList<BaseObject>(ufos);
        list.add(ship);
        list.addAll(bombs);
        list.addAll(rockets);
        return list;
    }

    /**
     * Create a new UFO. 1 time for 10 calls.
     */
    public void createUfo()
    {
        if (ufos.size() > 0) return;

        int random10 = (int) (Math.random() * 10);
        if (random10 == 0)
        {
            double x = Math.random() * 20;
            double y = Math.random() * 10;
            ufos.add(new Ufo(x, y));
        }
    }

    /**
     * Check bombs.
      * A) collision with the ship (the ship and bomb  die)
      * B) fall below the edge of the playing field (the bomb dies)
     */
    public void checkBombs()
    {
        for (Bomb bomb : bombs)
        {
            if (ship.isIntersec(bomb))
            {
                ship.die();
                bomb.die();
            }

            if (bomb.getY() >= height)
                bomb.die();
        }
    }

    /**
     * Check the rocket.
      * A) collision with a UFO (UFO Rocket and die)
      * B) fly above the edge of the playing field (the rocket dies)
     */
    public void checkRockets()
    {
        for (Rocket rocket : rockets)
        {
            for (Ufo ufo : ufos)
            {
                if (ufo.isIntersec(rocket))
                {
                    ufo.die();
                    rocket.die();
                }
            }

            if (rocket.getY() <= 0)
                rocket.die();
        }
    }

    /**
     * Remove the dead objects (bombs, rocket, UFO) from the list
     */
    public void removeDead()
    {
        for (BaseObject object : new ArrayList<BaseObject>(bombs))
        {
            if (!object.isAlive())
                bombs.remove(object);
        }

        for (BaseObject object : new ArrayList<BaseObject>(rockets))
        {
            if (!object.isAlive())
                rockets.remove(object);
        }

        for (BaseObject object : new ArrayList<BaseObject>(ufos))
        {
            if (!object.isAlive())
                ufos.remove(object);
        }
    }

    /**
     * Drawing of all objects of the game: 
     * * a) to fill the entire canvas points. 
     * * B) draw the objects on the canvas.
     */
    public void draw(Canvas canvas)
    {
        //draw game
        for (int i = 0; i < width + 2; i++)
        {
            for (int j = 0; j < height + 2; j++)
            {
                canvas.setPoint(i, j, '.');
            }
        }

        for (int i = 0; i < width + 2; i++)
        {
            canvas.setPoint(i, 0, '-');
            canvas.setPoint(i, height + 1, '-');
        }

        for (int i = 0; i < height + 2; i++)
        {
            canvas.setPoint(0, i, '|');
            canvas.setPoint(width + 1, i, '|');
        }

        for (BaseObject object : getAllItems())
        {
            object.draw(canvas);
        }
    }


    public SpaceShip getShip()
    {
        return ship;
    }

    public void setShip(SpaceShip ship)
    {
        this.ship = ship;
    }

    public ArrayList<Ufo> getUfos()
    {
        return ufos;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public ArrayList<Bomb> getBombs()
    {
        return bombs;
    }

    public ArrayList<Rocket> getRockets()
    {
        return rockets;
    }

    public static Space game;

    public static void main(String[] args) throws Exception
    {
        game = new Space(20, 20);
        game.setShip(new SpaceShip(10, 16));
        game.run();
    }

    /**
     * Method pauses long delay milliseconds.)
     */
    public static void sleep(int delay)
    {
        try
        {
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
        }
    }
}
