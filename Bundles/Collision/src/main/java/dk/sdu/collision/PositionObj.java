/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;

/**
 *
 * @author Jesper
 */
public abstract class PositionObj {

    private float x;
    private float y;
    private int sizex;
    private int sizey;
    private String ID;
    private Entity entity;
    private PositionPart pos;
    private String lastDir = "top";

    public String getID() {
        return ID;
    }

    public PositionObj(Entity e, int sizex, int sizey) {
        entity = e;
        pos = e.getPart(PositionPart.class);
        this.x = pos.getX();
        this.y = pos.getY();
        this.sizex = sizex;
        this.sizey = sizey;
        this.ID = e.getID();
    }

    public void updatePos(Entity e) {
        PositionPart pos = e.getPart(PositionPart.class);
        this.x = pos.getX();
        this.y = pos.getY();

    }

    public float getX1() {
        return x;
    }

    public float getY1() {
        return y;
    }

    public float getX2() {
        return x + sizex;
    }

    public float getY2() {
        return y + sizey;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getLastDir() {
        return lastDir;
    }

    public void setLastDir(String lastDir) {
        this.lastDir = lastDir;
    }

    public float gethysteresis() {
        if (lastDir.equals("top")) {
            return 10;
        } else {
            return 0;
        }
    }

}
