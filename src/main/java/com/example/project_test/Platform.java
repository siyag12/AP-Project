package com.example.project_test;
import javafx.scene.shape.Rectangle;


public abstract class Platform extends GameObject
{
    public Platform(double x, double y, double width, double height)
    {
        super(x, y, width, height);
    }
    @Override
    public void movement(){}
    @Override
    public void stopMovement(){}
}
