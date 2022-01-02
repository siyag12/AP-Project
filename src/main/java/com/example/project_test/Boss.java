package com.example.project_test;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Boss extends Enemy
{
    public Boss(double x, double y, int health, String color) throws Exception
    {
        super(x, y, 186, 195, health, color);
        Image image=new Image(new FileInputStream("images\\boss.jpg"));
        BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(186, 195, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(186, 195);
        this.setLayoutX(x-93);
        this.setLayoutY(y-97.5);
        KeyFrame frame=new KeyFrame(Duration.millis(1), new Handler());
        rightmover=new Timeline(frame);
        rightmover.setCycleCount(100);
        u=0;
        KeyFrame fallingFrame=new KeyFrame(Duration.millis(16), new GravityHandler());
        falling=new Timeline(fallingFrame);
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
        public void handle(ActionEvent e) {
            moveUp();
            ArrayList<Integer> curr=currentlyColliding();
            for(int i=0;i<curr.size();i++)
            {
                collision(Game.list.get(curr.get(i)));
            }
        }
    }

    public void moveRight()
    {
        hitbox.setX(0.3);
        double leftbound=hitbox.getX()-75;
        setLayoutX(leftbound+0.3);
    }

    public void moveUp()
    {
        hitbox.setY(u);
        double bottombound=hitbox.getY()-25.5;
        setLayoutY(bottombound+u);
        u+=0.3;
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
            if(hitbox.getY()>h.hitbox.getY())
            {
                u=-12;
            }
        }
    }

    public void collideWith(Platform p)
    {
        u=-12;
    }
}
