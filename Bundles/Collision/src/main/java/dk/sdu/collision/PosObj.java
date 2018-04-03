/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

/**
 *
 * @author Jesper
 */
public class PosObj {

    private float x;
    private float y;
    private int sizex;
    private int sizey;

    public PosObj(float x, float y, int sizex, int sizey) {
        this.x = x;
        this.y = y;
        this.sizex = sizex;
        this.sizey = sizey;
    }

    PosObj(float x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
