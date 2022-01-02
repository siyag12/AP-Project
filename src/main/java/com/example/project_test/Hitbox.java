package com.example.project_test;
import java.io.Serializable;

public class Hitbox implements Serializable
{
    private double x;
    private double y;
    private double height;
    private double width;
    private static final long serialVersionUID=3L;

    public Hitbox(double x ,double y, double width, double height)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getHeight() {
        return this.height;
    }
    public double getWidth() {
        return this.width;
    }
    public void setX(double x) {
        this.x+= x;
    }
    public void setY(double y) {
        this.y+= y;
    }
}
