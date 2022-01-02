package com.example.project_test;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;

public class Coin extends GameObject
{
    public Coin(double x, double y) throws Exception
    {
        super(x, y, 30, 30);
        Image image=new Image(new FileInputStream("images\\coin.jpg"));
        BackgroundImage bkg=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(30, 30, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(30, 30);
        this.setLayoutX(x-15);
        this.setLayoutY(y-15);
    }
    @Override
    public void movement(){}
    @Override
    public void stopMovement(){}
    @Override
    public void collision(GameObject g)
    {
        if(g instanceof Hero)
        {
            collideWith((Hero)g);
        }
    }

    public void collideWith(Hero h)
    {
        h.addCoin(1);
        Game.coinScore.setText("Coins collected: "+h.coins);
        AnchorPane curr=(AnchorPane)Game.gameScreen.getRoot();
        curr.getChildren().remove(this);
        Game.list.remove(this);
    }
}
