package com.example.project_test;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public abstract class Enemy extends GameObject
{
    protected int health;
    protected String color;
    Timeline rightmover;
    Timeline falling;
    double u;
    public Enemy(double x, double y, double width, double height, int health, String color)
    {
        super(x, y, width, height);
        this.health=health;
        this.color=color;
    }
    public void die()
    {
        AnchorPane curr=(AnchorPane) Game.gameScreen.getRoot();
        curr.getChildren().remove(this);
    }

    @Override
    public void movement(){this.falling.play();}
    @Override
    public void stopMovement(){
        falling.stop();
        rightmover.stop();
    }

    public void collideWith(Weapon w)
    {
        this.hurt(w);
    }

    public void hurt(Weapon w)
    {
        this.health-=w.getDamage();
        if(health<0) {
            Game.list.remove(this);
            this.dieAbyss();
        }
    }

    public void dieAbyss()
    {
        KeyFrame deathFrame=new KeyFrame(Duration.millis(800), new DeathHandler());
        Timeline death=new Timeline(deathFrame);
        u=-6;
        death.setCycleCount(1);
        Game.list.remove(this);
        death.play();
    }

    private class DeathHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent e)
        {
            die();
        }
    }



}
