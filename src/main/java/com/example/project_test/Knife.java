package com.example.project_test;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Knife extends Weapon
{
    transient Timeline knifestopper;
    private static final long serialVersionUID=5L;
    public Knife(double x, double y, int level) throws FileNotFoundException {
        super(x,y,34.5,8,50+(0.5*level), level);
        Image image=new Image(new FileInputStream("images\\knife1.png"));
        BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(34.5, 8, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(34.5, 8);
        this.setLayoutX(x-17.25);
        this.setLayoutY(y-4);
        KeyFrame rightframe=new KeyFrame(Duration.millis(1), new RightHandler());
        KeyFrame stopFrame=new KeyFrame(Duration.millis(300), new StopHandler());
        rightmover=new Timeline(rightframe);
        rightmover.setCycleCount(Animation.INDEFINITE);
        knifestopper=new Timeline(stopFrame);
        knifestopper.setCycleCount(1);
    }
    public class RightHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
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

    public class StopHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent e)
        {
            remove();
        }

    }
    public void moveRight()
    {
        hitbox.setX(3);
        double leftbound=hitbox.getX()-8.625;
        setLayoutX(leftbound+3);
    }

    @Override
    public void movement()
    {
        Game.list.add(this);
        AnchorPane curr=(AnchorPane) Game.gameScreen.getRoot();
        curr.getChildren().add(this);
        this.knifestopper.play();
        this.rightmover.play();

    }
    @Override
    public void collision(GameObject g)
    {
        if(g instanceof Enemy) collideWith((Enemy)g);
        if(g instanceof Platform) collideWith((Platform)g);
        if(g instanceof TNT) collideWith((TNT)g);
    }

    public void collideWith(Enemy e)
    {
        this.rightmover.stop();
        e.collideWith(this);
        Game.list.remove(this);
        AnchorPane curr=(AnchorPane) Game.gameScreen.getRoot();
        curr.getChildren().remove(this);
    }

    public void collideWith(Platform p)
    {
        this.rightmover.pause();
        remove();
    }

    public void collideWith(TNT t)
    {
        t.collideWith(this);
        remove();
    }

    public void remove()
    {
        this.rightmover.pause();
        Game.list.remove(this);
        AnchorPane curr=(AnchorPane) Game.gameScreen.getRoot();
        curr.getChildren().remove(this);
    }

}
