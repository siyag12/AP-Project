package com.example.project_test;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class Platform4 extends Platform {
    public Platform4(double x, double y) throws Exception {
        super(x, y, 284, 32.5);
        Image image=new Image(new FileInputStream("images\\platform4.png"));
        BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(284, 170.33, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(284, 170.33);
        this.setLayoutX(x-142);
        this.setLayoutY(y-15.665);
    }

    @Override
    public void movement(){super.movement();}
    @Override
    public void collision(GameObject g){}
}
