package com.example.project_test;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Ork extends Enemy
{
    public Ork(double x, double y, int health, String color) throws Exception
    {
        super(x, y, 50, 51, health, color);
        u=0;
        if(color.equals("Green"))
        {
            Image image=new Image(new FileInputStream("images\\Orc1.png"));
            BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(50, 51, false, false, true, false));
            this.setBackground(new Background(bkg));
        }
        else
        {
            Image image=new Image(new FileInputStream("images\\RedOrc1.png"));
            BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(50, 51, false, false, true, false));
            this.setBackground(new Background(bkg));
        }

        this.setPrefSize(50, 51);
        this.setLayoutX(x-25);
        this.setLayoutY(y-25.5);
        KeyFrame frame=new KeyFrame(Duration.millis(1), new Handler());
        rightmover=new Timeline(frame);
        rightmover.setCycleCount(100);

        KeyFrame upFrame=new KeyFrame(Duration.millis(16), new GravityHandler());
        falling=new Timeline(upFrame);
        falling.setCycleCount(Animation.INDEFINITE);

    }

    public class Handler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            moveRight();
        }
    }

    public class GravityHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent e)
        {
            moveUp();
            ArrayList<Integer> curr=currentlyColliding();
            try {
                for(int i=0;i<curr.size();i++)
                {
                    collision(Game.list.get(curr.get(i)));
                }
            }
            catch(IndexOutOfBoundsException exception){}
        }
    }




    public void moveUp()
    {
        hitbox.setY(u);
        double bottombound=hitbox.getY()-25.5;
        setLayoutY(bottombound+u);
        u+=0.3;
    }

    public void moveRight()
    {
        hitbox.setX(1.5);
        double leftbound=hitbox.getX()-25;
        setLayoutX(leftbound+1.5);
    }

    @Override
    public void movement()
    {
        falling.play();
    }

    @Override
    public void collision(GameObject g){
        if(g instanceof Hero) collideWith((Hero)g);
        if(g instanceof Platform) collideWith((Platform)g);
        if(g instanceof Abyss) collideWith((Abyss)g);
        if(g instanceof Weapon) collideWith((Weapon)g);
    }

    public void collideWith(Hero h)
    {
        double distx=Math.abs(h.getHitbox().getX()-hitbox.getX());
        double disty=Math.abs(h.getHitbox().getY()-hitbox.getY());
        double theta=Math.atan(disty/distx);
        double delta=Math.atan(hitbox.getHeight()/hitbox.getWidth());
        if(theta<=delta)
        {
            rightmover.play();
        }
        else
        {
            if(hitbox.getY()<h.hitbox.getY())
            {
                u=-8;
            }
        }
    }

    public void collideWith(Platform p)
    {
        u=-(7+(Math.random()*4)) ;
    }

    public void collideWith(Abyss a)
    {
        rightmover.stop();
        dieAbyss();
    }
}
