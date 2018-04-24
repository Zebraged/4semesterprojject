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
 * @author Marcg
 */
public class LifePart implements EntityPart{

    int Life;
    boolean invurnable = false;
    int InvurnableTime;

    public LifePart(int Life, int InvurnableTime) {
        this.Life = Life;
        this.InvurnableTime = InvurnableTime;
    }

    public int getLife() {
        return Life;
    }

    public void setLife(int Life) {
        this.Life = Life;
    }

    public void setInvurnableTime(int InvurnableTime) {
        this.InvurnableTime = InvurnableTime;
    }

    public boolean isInvurnable() {
        return invurnable;
    }

    public void setInvurnable(boolean invurnable) {
        this.invurnable = invurnable;
    }
    
    
    
    @Override
    public void process(GameData gameData, Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
