/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy.EnemyAI;


import EnemyAI.Node.Node;
import EnemyAI.Node.StartNode;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;
import java.util.ArrayList;

/**
 *
 * @author Marcg
 */
public class AI {
    
    private World world;
    private GameData data;
    private IPlayerPositionService playerPosition;
    private BundleContext context;
    private ArrayList<Node> open = new ArrayList();
    private ArrayList<Node> closed = new ArrayList();
    
    
    public AI(World world, GameData data, Entity entity){
        this.world = world;
        this.data = data;
        
        context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        ServiceReference reference = context.getServiceReference(IPlayerPositionService.class);
        playerPosition = (IPlayerPositionService) context.getService(reference);
        PositionPart part = entity.getPart(PositionPart.class);
        
        for(int i = 0; i >= data.getDisplayWidth(); i++){
            for(int j = 0 ; j >= data.getDisplayWidth(); j ++ ){
                if (i == part.getX() && j == part.getY()){
                    open.add(new StartNode(part.getX(), part.getY()));
                }
            }
        }
        
        
    }
    
    
}
