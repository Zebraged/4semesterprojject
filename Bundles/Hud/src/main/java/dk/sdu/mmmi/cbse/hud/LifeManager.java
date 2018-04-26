/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.hud;

/**
 *
 * @author Guest Account
 */
public class LifeManager {

    int life = 3;
    float newLife;

    boolean lostLife;
    boolean gainLife;
    boolean dead;

    public int getLife() {
        return life;
    }

    public boolean isLostLife() {
        return lostLife;
    }

    public void setLostLife(boolean lostLife) {
        this.lostLife = lostLife;
    }

    public boolean isGainLife() {
        return gainLife;
    }

    public void setGainLife(boolean gainLife) {
        this.gainLife = gainLife;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void manageLife(boolean status) {

        if (status == lostLife) {
            life = life--;
        }

        if (status == gainLife) {
            life = life++;
        }

        if (status == dead) {
            life = 0;
        }
    }
}
