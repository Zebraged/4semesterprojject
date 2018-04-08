/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Jesper
 */
public class CollisionPart implements EntityPart {

    private boolean leftAllowed = true;
    private boolean rightAllowed = true;
    private boolean gravityAllowed = true;
    private static CollisionPart instance = null;
    private CollisionPart(){
        
    }
    
    public static CollisionPart getInstance() {
      if(instance == null) {
         instance = new CollisionPart();
      }
      return instance;
   }
    
    @Override
    public void process(GameData gameData, Entity entity) {
         
    }

    public boolean isLeftAllowed() {
        return leftAllowed;
    }

    public boolean isRightAllowed() {
        return rightAllowed;
    }

    public void setLeftAllowed(boolean leftAllowed) {
        this.leftAllowed = leftAllowed;
    }

    public void setRightAllowed(boolean rightAllowed) {
        this.rightAllowed = rightAllowed;
    }

    public boolean isGravityAllowed() {
        return gravityAllowed;
    }

    public void setGravityAllowed(boolean gravityAllowed) {
        this.gravityAllowed = gravityAllowed;
    }

    

    
    
}
