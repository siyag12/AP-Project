package com.example.project_test;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;

public class CoinChest extends Chest {
    private int coins;

    public CoinChest(double x, double y) throws Exception {
        super(x, y, 75.67, 61);
        this.open = false;
        Image image1 = new Image(new FileInputStream("images\\coin_chest_closed.png"));
        this.close_img = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(75.67, 61, false, false, true, false));
        this.setBackground(new Background(this.close_img));
        this.setPrefSize(75.67, 61);
        this.setLayoutX(x - 37.385);
        this.setLayoutY(y - 30.5);

        Image image2 = new Image(new FileInputStream("images\\coin_chest_open.png"));
        this.open_img = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(67.67, 61, false, false, true, false));
        this.coins = 5;
    }

    @Override
    public void collision(GameObject g) {
        if (g instanceof Hero) {
            collideWith((Hero) g);
        }
    }

    public void collideWith(Hero h) {
        if (!open) {
            h.addCoin(coins);
            Game.coinScore.setText("Coins collected: " + h.coins);
            open = true;
            this.setBackground(new Background(this.open_img));
        }
    }
}
