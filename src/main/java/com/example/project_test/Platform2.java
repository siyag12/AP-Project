package com.example.project_test;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class Platform2 extends Platform {
    public Platform2(double x, double y) throws Exception {
        super(x, y, 157.5, 30);
        Image image=new Image(new FileInputStream("images\\platform2.png"));
        BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(157.5, 155, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(157.5,155);
        this.setLayoutX(x-78.7);
        this.setLayoutY(y-15);
    }

    @Override
    public void movement(){super.movement();}
    @Override
    public void collision(GameObject g){}
}
