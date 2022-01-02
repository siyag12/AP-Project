package com.example.project_test;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class Platform3 extends Platform {
    public Platform3(double x, double y) throws Exception
    {
        super(x, y, 423, 32.5);
        Image image=new Image(new FileInputStream("images\\platform3.png"));
        BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(423, 178.5, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(423, 178.5);
        this.setLayoutX(x-211.5);
        this.setLayoutY(y-16.25);
    }

    @Override
    public void movement(){super.movement();}
    @Override
    public void collision(GameObject g){}
}
