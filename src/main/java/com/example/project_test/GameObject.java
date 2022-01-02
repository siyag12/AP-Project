package com.example.project_test;

import javafx.scene.layout.Region;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GameObject extends Region implements Serializable {
    protected Hitbox hitbox;
    private static final long serialVersionUID = 1L;
    public GameObject(double x, double y, double width, double height) {
        this.hitbox = new Hitbox(x, y, width, height);
    }

    public abstract void movement();

    public abstract void collision(GameObject g);

    public Hitbox getHitbox() {
        return this.hitbox;
    }

    public boolean detectCollision(GameObject g) {
        double distx = Math.abs(g.getHitbox().getX() - hitbox.getX());
        double disty = Math.abs(g.getHitbox().getY() - hitbox.getY());
        return ((distx < (hitbox.getWidth() + g.getHitbox().getWidth()) / 2) && (disty < (hitbox.getHeight() + g.getHitbox().getHeight()) / 2) && this != g);
    }

    public ArrayList<Integer> currentlyColliding() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < Game.list.size(); i++) {
            try {
                if (detectCollision(Game.list.get(i)) && this != Game.list.get(i)) {
                    list.add(i);
                }
            } catch (NullPointerException e) {
            }
        }
        return list;
    }

    public abstract void stopMovement();
}
