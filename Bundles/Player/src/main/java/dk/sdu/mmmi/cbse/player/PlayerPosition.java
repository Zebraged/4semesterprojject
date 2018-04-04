/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;

/**
 *
 * @author Marcg
 */
public class PlayerPosition implements IPlayerPositionService{

    private PositionPart part;
    
    public PlayerPosition(){
    }
    
    public void addPositionPart(PositionPart part){
        this.part = part;
    }
    
    public float getX() {
        if(part == null){
            return 0;
        }
        return part.getX();
    }

    public float getY() {
        if(part == null){
            return 0;
        }
        return part.getY();
    }
    
}
