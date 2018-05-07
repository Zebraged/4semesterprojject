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
import dk.sdu.mmmi.cbse.common.entityparts.LineMovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.enemy.enemy_ai_k.Node;
import dk.sdu.mmmi.cbse.enemy.enemy_ai_k.Pathfinder;
import dk.sdu.mmmi.cbse.enemy.type.Enemy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

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
            
            LineMovingPart movingPart = entity.getPart(LineMovingPart.class);
            AssetGenerator assetGen = entity.getPart(AssetGenerator.class);
            PositionPart position = entity.getPart(PositionPart.class);
            LinkedList<Node<PositionPart>> nodes = nodeMap.get(entity);
            
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
                    p = new Pathfinder(position, player.getPart(PositionPart.class), world);
                    nodeMap.clear();
                    nodeMap.put(entity, p.getResult());
                }
                movingPart.setGoal(position.getX(), 0);
            } else {
                if (movingPart.reachedGoal()) {
                    Node<PositionPart> n = nodes.pollFirst();
                    float yExtra = (nodes.isEmpty()) ? 0 : 32;

                    movingPart.setGoal(n.getObject().getX(), n.getObject().getY() + yExtra);

                }
            }
            float x = position.getX();
            float y = position.getY();

            movingPart.process(gameData, entity);
            if (position.getX() < x) {
                assetGen.nextImage("Walk", false);
            } else if (position.getX() > x) {
                assetGen.nextImage("Walk", true);
            } else if (position.getX() == x && position.getY() == y) {
                assetGen.nextImage("Idle", true);
            }
            assetGen.process(gameData, entity);
        }
    }

}
