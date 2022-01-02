package com.example.project_test;

import javafx.animation.Timeline;
import java.io.Serializable;

public abstract class Weapon extends GameObject implements Serializable
{
    protected double damage;
    protected int level;
    protected transient Timeline rightmover;
    private static final long serialVersionUID=4L;
    public Weapon(double x, double y, double width, double height, double damage, int level)
    {
        super(x,y,width,height);
        this.damage=damage;
        this.level=level;
    }

    public double getDamage(){return damage;}
    public void increaseLevel(){
        this.level++;
        this.damage+=(this.damage)*0.5;
    }
    @Override
    public void stopMovement(){}

}
