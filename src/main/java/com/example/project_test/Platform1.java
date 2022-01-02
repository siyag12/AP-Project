package com.example.project_test;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class Platform1 extends Platform {
    public Platform1(double x, double y) throws Exception
    {
        super(x, y, 289.5, 45);
        Image image=new Image(new FileInputStream("images\\platform1.png"));
        BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(289.5, 222.5, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(289.5,222.5);
        this.setLayoutX(x-144.75);
        this.setLayoutY(y-22.5);
    }

    @Override
    public void movement(){super.movement();}
    @Override
    public void collision(GameObject g){}
}
