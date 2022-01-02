package com.example.project_test;

import javafx.scene.layout.Background;

public class Abyss extends GameObject
{
    public Abyss(double x, double y)
    {
        super(x, y, 100000, 20);
        this.setBackground(Background.EMPTY);
    }

    @Override
    public void collision(GameObject g){}
    @Override
    public void movement(){}
    @Override
    public void stopMovement(){}
}
