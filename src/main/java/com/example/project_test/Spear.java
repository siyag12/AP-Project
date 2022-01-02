package com.example.project_test;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.lang.Math.*;

public class Spear extends Weapon
{
    double u;
    private static final long serialVersionUID=6L;
    public Spear(double x, double y, int level) throws Exception
    {
        super(x,y,16,16, 60+(0.5*level), level);
        Image image=new Image(new FileInputStream("images\\spear.png"));
        BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(90.6386, 16, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(90.6386, 16);
        this.setLayoutX(x-82.6386);
        this.setLayoutY(y-8);
        KeyFrame rightframe=new KeyFrame(Duration.millis(16), new RightHandler());
        KeyFrame verticalFrame=new KeyFrame(Duration.millis(16), new GravityHandler());
        rightmover=new Timeline(rightframe, verticalFrame);
        rightmover.setCycleCount(Animation.INDEFINITE);
        u=-4;
    }

    public class RightHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent c)
        {
            moveRight();
            ArrayList<Integer> curr = currentlyColliding();
            try {
                for(int i=0;i<curr.size();i++)
                {
                    collision(Game.list.get(curr.get(i)));
                }
            }
            catch(IndexOutOfBoundsException exception){}
        }
    }

    public class GravityHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent e)
        {
            fall();
            setRotate(Math.toDegrees(Math.atan(u/24)));
        }
    }

    @Override
    public void movement()
    {
        Game.list.add(this);
        AnchorPane curr=(AnchorPane) Game.gameScreen.getRoot();
        curr.getChildren().add(this);
        this.rightmover.play();
    }

    public void moveRight()
    {
        hitbox.setX(24);
        setLayoutX(this.getLayoutX()+24);
    }

    public void fall()
    {
        hitbox.setY(u);
        setLayoutY(getLayoutY()+u);
        u+=0.3;
    }

    @Override
    public void collision(GameObject g)
    {
        if(g instanceof Enemy) collideWith((Enemy) g);
        if(g instanceof Platform) collideWith((Platform) g);
        if(g instanceof TNT) collideWith((TNT)g);
        if(g instanceof Abyss) collideWith((Abyss) g);
    }

    public void collideWith(Enemy e) {
        e.collideWith(this);
        remove();
    }
    public void collideWith(Platform p)
    {
        remove();
    }

    public void collideWith(TNT t)
    {
        t.collision(this);
        remove();
    }
    public void collideWith(Abyss b)
    {
        remove();
    }

    public void remove()
    {
        this.rightmover.stop();
        Game.list.remove(this);
        AnchorPane curr=(AnchorPane) Game.gameScreen.getRoot();
        curr.getChildren().remove(this);
    }
}
