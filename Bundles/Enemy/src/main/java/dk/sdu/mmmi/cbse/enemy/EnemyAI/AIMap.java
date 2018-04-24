/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy.EnemyAI;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.entityparts.SizePart;
import org.osgi.framework.BundleContext;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;
import dk.sdu.mmmi.cbse.enemy.EnemyAI.Node.BlockedNode;
import dk.sdu.mmmi.cbse.enemy.EnemyAI.Node.FreeNode;
import dk.sdu.mmmi.cbse.enemy.EnemyAI.Node.Node;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcg
 */
public class AIMap {

    private int tileSize = 32;
    private World world;
    private GameData data;
    private PositionPart part;
    private MovingPart movePart;
    private IPlayerPositionService playerPosition;
    private BundleContext context;
    private List<Node>    nodeList = new ArrayList();
    private ArrayList<Node> closed = new ArrayList();
    private Entity aIEntity;
    private Rectangle aIRect;

    public AIMap(World world, GameData data, Entity entity) {
        this.world = world;
        this.data = data;
        this.aIEntity = entity;

        movePart = aIEntity.getPart(MovingPart.class);
        part = aIEntity.getPart(PositionPart.class);
        SizePart sizepart = aIEntity.getPart(SizePart.class);

        this.aIRect = new Rectangle();
        this.aIRect.setRect(part.getX(), part.getY(), sizepart.getHeight(), sizepart.getWidth());
        
        generateNodeMap();
    }

    private void generateNodeMap() {
//        context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
//        ServiceReference reference = context.getServiceReference(IPlayerPositionService.class);
//        playerPosition = (IPlayerPositionService) context.getService(reference);
//        PositionPart part = aIEntity.getPart(PositionPart.class);
//        Rectangle entityRect = new Rectangle((int)part.getX(), (int)part.getY(), tileSize, tileSize);
//        Rectangle playerRect = new Rectangle((int)playerPosition.getX(), (int)playerPosition.getY(), tileSize, tileSize);
        int i = 0;
        int j = 0;
        Node node;
        for (int x = 0; x >= data.getDisplayWidth(); x = x + tileSize) {
            for (int y = 0; y >= data.getDisplayHeight(); y = y + tileSize) {
                int location = (y * data.getDisplayWidth()) + x;
                if (isWithinEntity(new Rectangle(x, y, tileSize, tileSize))) {
                    node = new BlockedNode(i, j);
                } else {
                    node = new FreeNode(i, j);
                }
                nodeList.add(node);
                j++;
            }
            i++;
        }
        
        
    }

    private boolean isWithinEntity(Rectangle rectangle) {
        for (Entity entity : world.getEntities()) {
            if (!entity.equals(aIEntity)) {
                PositionPart entityPos = entity.getPart(PositionPart.class);
                SizePart entitySize = entity.getPart(SizePart.class);
                if (entityPos != null && entitySize != null) {
                    Rectangle entityrect = new Rectangle();

                    entityrect.setRect(entityPos.getX(), entityPos.getY(), entitySize.getWidth(), entitySize.getHeight());
                    if (rectangle.intersects(entityrect)) {
                        return true;
                    }

                }

            }

        }
        return false;
    }

    public List<Node> getOpen() {
        return nodeList;
    }

    public List<Node> getClosed() {
        return closed;
    }

    public List<Node> getNavigationMesh(){
        List<Node> navMesh = new ArrayList();
        for(Node node : nodeList){
            //node.
        }
        return navMesh;
    }
}
