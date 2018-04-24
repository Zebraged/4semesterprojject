/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.platform;

/**
 *
 * @author Chris
 */
public class PlatformObj extends Platform {
    
    private String name;
    private int xpos;
    private int ypos;
    private int zpos;
    
    public PlatformObj(String name, int x, int y, int z){
        this.name = name;
        this.xpos = Grid.gridify(x);
        this.ypos = Grid.gridify(y);
        this.zpos = z;
    }
    public int getxPos(){
        
        return xpos;
    }
    public int getyPos(){
        
        return ypos;
    }
    public int getzPos(){
        return zpos;
    }
}
