package com.example.project_test;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;

public class WeaponChest extends Chest
{
    private Weapon w;
    public WeaponChest(double x, double y, Weapon w) throws Exception
    {
        super(x, y, 72.1, 51);
        this.open=false;
        Image image1=new Image(new FileInputStream("images\\chest_closed.png"));
        this.close_img=new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(72.1, 51, false, false, true, false));
        this.setBackground(new Background(this.close_img));
        this.setPrefSize(72.1, 51);
        this.setLayoutX(x-36.05);
        this.setLayoutY(y-25.5);

        Image image2=new Image(new FileInputStream("images\\chest_open.png"));
        this.open_img=new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(72.1, 51, false, false, true, false));

        this.w=w;
    }
    @Override
    public void collision(GameObject g) {
        if(g instanceof Hero) collideWith((Hero)g);

    }

    public void collideWith(Hero hero) {
        if(!open)
        {
            hero.getWeapon(w);
            open=true;
            this.setBackground(new Background(this.open_img));
        }
    }
}
