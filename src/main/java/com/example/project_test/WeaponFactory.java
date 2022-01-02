package com.example.project_test;
public class WeaponFactory
{
    public WeaponFactory(){}
    public Weapon createWeapon(double x, double y, Weapon w) throws Exception {
        if(w==null) {
            return null;
        }
        if(w instanceof Knife) {
            return new Knife(x, y, w.level);
        }
        if(w instanceof Spear) {
            return new Spear(x, y, w.level);
        }
        return null;
    }
}
