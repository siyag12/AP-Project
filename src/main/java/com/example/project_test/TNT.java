package com.example.project_test;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.ConcurrentModificationException;

public class TNT extends GameObject {
    private Timeline timer;
    private Timeline rightmover;
    Circle c;

    public TNT(double x, double y) throws Exception {
        super(x, y, 50, 51);
        Image image = new Image(new FileInputStream("images\\TNT.png"));
        BackgroundImage bkg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(50, 51, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(50, 51);
        this.setLayoutX(x - 25);
        this.setLayoutY(y - 25.5);
        c = new Circle();
        c.setCenterX(x);
        c.setCenterY(y);
        c.setRadius(100);
        c.setFill(Color.TRANSPARENT);
        KeyFrame timerFrame = new KeyFrame(Duration.seconds(1), e -> {
            explode();
        });
        timer = new Timeline(timerFrame);
        timer.setCycleCount(1);
        KeyFrame rightFrame = new KeyFrame(Duration.millis(1), e -> { moveRight();});
        rightmover = new Timeline(rightFrame);
        rightmover.setCycleCount(40);
    }

    @Override
    public void collision(GameObject g) {
        if (g instanceof Hero) collideWith((Hero) g);
        if (g instanceof Weapon) collideWith((Weapon) g);
    }

    @Override
    public void movement() { }

    @Override
    public void stopMovement() { }

    public void moveRight() {
        hitbox.setX(3);
        double leftbound = hitbox.getX() - 25;
        setLayoutX(leftbound + 3);
        c.setCenterX(c.getCenterX() + 3);
    }

    public void collideWith(Hero h) {
        timer.play();
        rightmover.play();
    }

    public void collideWith(Weapon w) {
        timer.play();
    }

    public void explode() {
        AnchorPane curr = (AnchorPane) Game.gameScreen.getRoot();
        curr.getChildren().add(this.c);
        explodeTNT();
        Game.list.remove(this);
        curr.getChildren().remove(this);
        try {
            for (GameObject g : Game.list) {
                if (g instanceof Hero || g instanceof Enemy) {
                    double distx = Math.abs(g.getHitbox().getX() - hitbox.getX());
                    double disty = Math.abs(g.getHitbox().getY() - hitbox.getY());
                    double distance = Math.sqrt(Math.pow(distx, 2) + Math.pow(disty, 2));

                    if (distance < 100) {
                        if (g instanceof Hero) ((Hero) g).die();
                        if (g instanceof Enemy) ((Enemy) g).dieAbyss();
                    }
                }
            }
        } catch (ConcurrentModificationException e) {
            // do nothing
        }
    }

    public void explodeTNT() {
        FillTransition fill = new FillTransition();
        fill.setCycleCount(1);
        fill.setDuration(Duration.seconds(2));
        fill.setFromValue(Color.RED);
        fill.setToValue(Color.TRANSPARENT);
        fill.setShape(this.c);
        fill.play();
    }

}
