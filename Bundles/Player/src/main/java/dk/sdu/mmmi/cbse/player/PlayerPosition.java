/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPlayerInfoService;

/**
 *
 * @author Marcg
 */
public class PlayerPosition implements IPlayerInfoService {

    private PositionPart part;
    private LifePart life;

    public PlayerPosition() {
    }

    public void addPositionPart(PositionPart part) {
        this.part = part;
    }

    public void setLifePart(LifePart life) {
        this.life = life;
    }

    public float getX() {
        if (part == null) {
            return 0;
        }
        return part.getX();
    }

    public float getY() {
        if (part == null) {
            return 0;
        }
        return part.getY();
    }

    public int getLife() {
        return life.getLife();
    }

    public void updateLife(int life) {
        this.life.updateLife(life);
    }

}
