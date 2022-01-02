package com.example.project_test;
import javafx.scene.layout.*;

public abstract class Chest extends GameObject
{
    protected boolean open;
    protected BackgroundImage open_img;
    protected BackgroundImage close_img;

    public Chest(double x, double y, double width, double height) throws Exception
    {
        super(x, y, width, height);
    }

    @Override
    public void movement(){}
    @Override
    public void stopMovement(){}
}
