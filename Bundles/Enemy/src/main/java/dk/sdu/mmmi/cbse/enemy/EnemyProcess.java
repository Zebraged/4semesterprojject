/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.entityparts.LineMovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IScoreService;
import dk.sdu.mmmi.cbse.enemy.enemy_ai_k.Node;
import dk.sdu.mmmi.cbse.enemy.enemy_ai_k.Pathfinder;
import dk.sdu.mmmi.cbse.enemy.type.Enemy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Marcg
 */
public class EnemyProcess implements IEntityProcessingService {

    private HashMap<Entity, LinkedList<Node<PositionPart>>> nodeMap = new HashMap();
    private Pathfinder p;
    private Entity player;
    private Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Enemy.class)) {

            
            LineMovingPart lineMovingPart = entity.getPart(LineMovingPart.class);
            AssetGenerator assetGen = entity.getPart(AssetGenerator.class);
            PositionPart positionPart = entity.getPart(PositionPart.class);
            LifePart life = entity.getPart(LifePart.class);
            LinkedList<Node<PositionPart>> nodes = nodeMap.get(entity);
            
            
            float x = positionPart.getX();
            float y = positionPart.getY();
            
            if(life.getLife() <= 0){
                BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
                ServiceReference ref = context.getServiceReference(IScoreService.class);
                if(ref != null){
                    IScoreService score = (IScoreService)context.getService(ref);
                    score.addScore(200);
                }
                world.removeEntity(entity);
            }
            if(random.nextInt(1000) == 0 && nodes != null) {

                nodes.clear();
            }

            if (nodes == null || nodes.isEmpty()) {
                boolean playerFound = false;
                if (player == null) {
                    for (Entity p : world.getEntities()) {
                        if (p.getAlignment() == 1) {
                            player = p;
                            playerFound = true;
                            break;
                        }
                    }
                } else {
                    playerFound = true;
                }
                if (playerFound) {
                    p = new Pathfinder(positionPart, player.getPart(PositionPart.class), world);
                    nodeMap.clear();
                    nodeMap.put(entity, p.getResult());
                }
            } else {
                if (lineMovingPart.reachedGoal()) {
                    Node<PositionPart> n = nodes.pollFirst();
                    float yExtra = (nodes.isEmpty()) ? -4 : 28;

                    lineMovingPart.setGoal(n.getObject().getX(), n.getObject().getY() + yExtra);

                }
            }
            
            lineMovingPart.process(gameData, entity);
            if (positionPart.getX() < x) {
                assetGen.nextImage("Walk", false);
            } else if (positionPart.getX() > x) {
                assetGen.nextImage("Walk", true);
            } else if (positionPart.getX() == x && positionPart.getY() == y) {
                assetGen.nextImage("Idle", true);
            }
            assetGen.process(gameData, entity);
    }
    }
}

