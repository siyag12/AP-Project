package com.example.project_test;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Hero extends GameObject implements Serializable {
    int coins;
    int distance;
    transient Timeline rightmover;
    transient Timeline falling;
    Helmet h;
    Weapon currweapon;
    double u;
    boolean revived;
    static transient WeaponFactory factory = new WeaponFactory();
    private static final long serialVersionUID = 2L;

    public Hero(double x, double y) throws Exception {
        super(x, y, 50, 51);
        this.coins = 0;
        this.distance = 0;
        this.u = 0;
        Image image = new Image(new FileInputStream("images\\helmet.jpg"));
        BackgroundImage bkg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(50, 51, false, false, true, false));
        this.setBackground(new Background(bkg));
        this.setPrefSize(50, 51);
        this.setLayoutX(x - 25);
        this.setLayoutY(y - 25.5);
        h = new Helmet();
        currweapon = null;
        KeyFrame rightframe = new KeyFrame(Duration.millis(1), new RightHandler());
        rightmover = new Timeline(rightframe);
        rightmover.setCycleCount(100);

        KeyFrame upFrame = new KeyFrame(Duration.millis(16), new GravityHandler());
        falling = new Timeline(upFrame);
        falling.setCycleCount(Animation.INDEFINITE);
        revived = false;
    }


    public class RightHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            moveRight();
            ArrayList<Integer> curr = currentlyColliding();
            try {
                for (int i = 0; i < curr.size(); i++) {
                    if ((Game.list.get(curr.get(i)) instanceof Hero)) {
                    } else {
                        collision(Game.list.get(curr.get(i)));
                    }

                }
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }

    public class GravityHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            moveUp();
            ArrayList<Integer> curr = currentlyColliding();
            try {
                for (int i = 0; i < curr.size(); i++) {
                    collision(Game.list.get(curr.get(i)));
                }
            } catch (IndexOutOfBoundsException exception) {
            }
        }
    }

    @Override
    public void movement() {
        falling.play();
    }

    @Override
    public void stopMovement() {
        rightmover.stop();
        falling.stop();
    }

    public void moveRight() {
        AnchorPane curr = (AnchorPane) Game.gameScreen.getRoot();
        curr.setLayoutX(curr.getLayoutX() - 1.5);
        hitbox.setX(1.5);
        double leftbound = hitbox.getX() - 25;
        setLayoutX(leftbound + 1.5);
        this.distance += 1.5;
        Game.score.setLayoutX(Game.score.getLayoutX() + 1.5);
        Game.coinScore.setLayoutX(Game.coinScore.getLayoutX() + 1.5);
        int x = distance / 100;
        Game.score.setText("Distance moved: " + x);

        if (hitbox.getX() >= 15640) {
            stopMovement();
            for (int i = 0; i < Game.list.size(); i++) {
                Game.list.get(i).stopMovement();
            }
            Game.toGameWinScreen();
        }
    }

    public void moveUp() {
        hitbox.setY(this.u);
        double bottombound = hitbox.getY() - 25.5;
        setLayoutY(bottombound + this.u);
        this.u += 0.3;
    }

    public void die() {
        AnchorPane curr = (AnchorPane) Game.gameScreen.getRoot();
        falling.stop();
        rightmover.stop();
        curr.getChildren().remove(this);
        Game.list.remove(this);

        for (GameObject g : Game.list) {
            g.stopMovement();
        }
        if (!revived) {
            try {
                Game.toReviveScreen();
            } catch (InsufficientCoinsException e) {
                Game.toGameOverScreen();
            }
        } else {
            Game.toGameOverScreen();
        }

    }

    public void fireWeapon() throws Exception {
        Weapon w = createWeapon();
        if (w != null) {
            w.movement();
        }
    }

    public Weapon createWeapon() throws Exception {
        if (currweapon != null) {

            return factory.createWeapon(hitbox.getX(), hitbox.getY(), currweapon);
        }
        return null;
    }

    public void addCoin(int c) {
        this.coins += c;
    }


    @Override
    public void collision(GameObject g) {
        if (g instanceof Coin) collideWith((Coin) g);
        if (g instanceof Chest) collideWith((Chest) g);
        if (g instanceof Enemy) collideWith((Enemy) g);
        if (g instanceof Platform) collideWith((Platform) g);
        if (g instanceof TNT) collideWith((TNT) g);
        if (g instanceof Abyss) collideWith((Abyss) g);
    }


    public void collideWith(Enemy e) {
        e.collision(this);
        double distx = Math.abs(e.getHitbox().getX() - hitbox.getX());
        double disty = Math.abs(e.getHitbox().getY() - hitbox.getY());
        double theta = Math.atan(disty / distx);
        double delta = Math.atan(hitbox.getHeight() / hitbox.getWidth());
        if (theta <= delta) {
            rightmover.pause();
        } else {
            if (hitbox.getY() < e.hitbox.getY()) {
                u = -8;
            } else {
                dieEnemy();
            }
        }
    }

    public void collideWith(Platform p) {
        u = -8;
    }

    public void collideWith(Coin c) {
        c.collision(this);
    }

    public void collideWith(Chest ch) {
        ch.collision(this);
    }

    public void collideWith(TNT t) {
        rightmover.stop();
        t.collideWith(this);
    }

    public void collideWith(Abyss a) {
        Game.list.remove(this);
        u = -8;
        dieAbyss();
    }

    public void dieAbyss() {
        die();
    }


    public void dieEnemy() {
        Game.list.remove(this);
        this.falling.stop();
        KeyFrame dyingFall = new KeyFrame(Duration.millis(10), new FallWhileDyingHandler());
        Timeline faller = new Timeline(dyingFall);
        faller.setCycleCount(80);
        KeyFrame deathFrame = new KeyFrame(Duration.millis(1000), new EnemyDeathHandler());
        Timeline death = new Timeline(deathFrame);
        death.setCycleCount(1);
        faller.play();
        death.play();
    }

    private class EnemyDeathHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            die();
        }
    }

    private class FallWhileDyingHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            constantFall();
        }
    }

    public void constantFall() {
        hitbox.setY(6);
        double bottonbound = hitbox.getY() - 25.5;
        setLayoutY(bottonbound + 6);
    }

    public void getWeapon(Weapon w) {
        if (w == null) return;

        if (w instanceof Knife) {
            if (h.w1 == null) h.w1 = (Knife) w;
            else h.w1.increaseLevel();

            currweapon = h.w1;
        } else {
            if (h.w2 == null) h.w2 = (Spear) w;
            else h.w2.increaseLevel();

            currweapon = h.w2;
        }
    }

    public void respawn() {
        coins -= 10;
        revived = true;
        int li = -1;
        double ld = Integer.MAX_VALUE;
        for (int i = 0; i < Game.list.size(); i++) {
            if (Game.list.get(i) instanceof Platform) {
                if ((hitbox.getX() - Game.list.get(i).getHitbox().getX() > 0) && hitbox.getX() - Game.list.get(i).getHitbox().getX() < ld) {
                    li = i;
                    ld = hitbox.getX() - Game.list.get(i).getHitbox().getX();
                }
            }
        }
        double spawnpointx = Game.list.get(li).getHitbox().getX();
        double spawnpointy = Game.list.get(li).getHitbox().getY();


        Game.list.add(this);

        hitbox.setX(-ld);
        Game.coinScore.setLayoutX(hitbox.getX() + 925);
        Game.score.setLayoutX(hitbox.getX() + 925);
        hitbox.setY(spawnpointy - hitbox.getY());
        hitbox.setY(-200);
        setLayoutX(hitbox.getX() - 25);
        setLayoutY(hitbox.getY() - 25.5);
        AnchorPane curr = (AnchorPane) Game.gameScreen.getRoot();
        curr.getChildren().add(this);
        curr.setLayoutX(-spawnpointx + 175);
        for (GameObject g : Game.list) {
            g.movement();
        }
    }

}
