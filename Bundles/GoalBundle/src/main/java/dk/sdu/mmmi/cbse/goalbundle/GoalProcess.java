/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.goalbundle;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.awt.Rectangle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import dk.sdu.mmmi.cbse.common.services.IPlayerInfoService;

/**
 *
 * @author Marcg
 */
public class GoalProcess implements IEntityProcessingService{

    public void process(GameData gameData, World world) {
        BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        ServiceReference reference = context.getServiceReference(IPlayerInfoService.class);
        
        //Checks if the player is within the invisible rectangle of the player.
        //If so, make the player win the level.
        for(Entity ent : world.getEntities(Goal.class)){
            PositionPart pos = ent.getPart(PositionPart.class);
            SizePart size = ent.getPart(SizePart.class);
            Rectangle rect = new Rectangle((int)pos.getX(), (int)pos.getY(), size.getWidth(), size.getHeight());
            if(reference != null){
                IPlayerInfoService playerPosition = (IPlayerInfoService) context.getService(reference);
                if(rect.contains(playerPosition.getX(), playerPosition.getY())){
                    gameData.setGameWon(true);
                }
            }
        }
        
    }
    
}
