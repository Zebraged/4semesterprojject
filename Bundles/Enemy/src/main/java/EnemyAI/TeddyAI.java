/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnemyAI;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;

/**
 *
 * @author Marcg
 */
public class TeddyAI {
    
    private World world;
    private GameData data;
    private IPlayerPositionService playerPosition;
    private BundleContext context;
    
    
    public TeddyAI(World world, GameData data){
        this.world = world;
        this.data = data;
        
        context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        ServiceReference reference = context.getServiceReference(IPlayerPositionService.class);
        playerPosition = (IPlayerPositionService) context.getService(reference);
    }
}
