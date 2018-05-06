/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcg
 */
public class LifePart implements EntityPart{

    int Life;
    boolean invulnerable = false;
    int InvurnableTime;
    ExecutorService executor = Executors.newSingleThreadExecutor();

    public LifePart(int Life, int InvurnableTime) {
        this.Life = Life;
        this.InvurnableTime = InvurnableTime;
    }

    public int getLife() {
        return Life;
    }
    
    public void updateLife(int life) {
        if(invulnerable != true){
            this.Life += life;
            setInvulnerable();
        }
    }

    public void setInvurnableTime(int InvurnableTime) {
        this.InvurnableTime = InvurnableTime;
    }

    public boolean isInvurnable() {
        return invulnerable;
    }
    
    private void setInvulnerable(){
        executor.execute(new Runnable(){
            @Override
            public void run() {
                invulnerable = true;
                try {
                    Thread.sleep(InvurnableTime);
                } catch (InterruptedException ex) {
                    System.out.println("invulnerable failed");
                }
                invulnerable = false;
            }
        
        });
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
    
}
