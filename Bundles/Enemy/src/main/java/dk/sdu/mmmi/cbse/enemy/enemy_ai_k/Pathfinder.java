/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy.enemy_ai_k;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mr. Kinder
 */
public class Pathfinder {

    private List<Node<PositionPart>> open, closed; //list of the open and closed map.
    private static PositionPart[][] updatedMap; //list of the tiles etc.
    private static PositionPart[][] staticMap; //list of the tiles etc.
    private LinkedList<Node<PositionPart>> result;
    private PositionPart start, goal;
    private static int TILES_X, TILES_Y;
    private float h;

    public Pathfinder(PositionPart start, PositionPart goal, World world) {
        this.start = start;
        this.goal = goal;
        open = new ArrayList();
        closed = new ArrayList();
        h = getH();
        if (TILES_X == 0 || TILES_Y == 0) {
            generateNodeMap(world);
        }
        updateMap();
        Node s = new Node(start, null);
        updatedMap[(int) goal.getY() / 32][(int) goal.getX() / 32] = goal;
        open.add(s);
        generate();
        //printRoute();
    }

    private void updateMap() {
        TILES_Y = (goal.getY() / 32 + 1 > TILES_Y) ? (int) goal.getY() / 32 + 1 : TILES_Y;
        updatedMap = new PositionPart[TILES_Y][TILES_X];
        for (int y = 0; y < TILES_Y; y++) {
            for (int x = 0; x < TILES_X; x++) {
                if (y < staticMap.length) {
                    updatedMap[y][x] = staticMap[y][x];
                }
            }
        }
    }

    private void makeLinkedList(Node<PositionPart> last) {
        this.result = new LinkedList();

        Node current = last;

        while (current.getParent() != null) {
            this.result.addFirst(current);
            current = current.getParent();
        }
    }

    private void generate() {

        while (!open.isEmpty()) {
            Node n = getLeastF();
            open.remove(n);
            List<Node<PositionPart>> successors = getSuccessors(n);

            for (Node<PositionPart> successor : successors) {
                boolean shouldSkip = false;
                //Stops search if the goal is reached.
                if (successor.getObject() == goal) {
                    makeLinkedList(successor);
                    return;
                }
                //Hasn't been set yet
                if (successor.getfValue() == -1) {
                    successor.setfValue(successor.getMovementCostFromStart() + getH());
                }

                for (Node<PositionPart> node : open) {
                    if (node.getfValue() == -1) {
                        continue;
                    }
                    if (node.getObject() == successor.getObject()) {
                        if (node.getfValue() < successor.getfValue()) {
                            shouldSkip = true;
                        }
                    }
                }
                for (Node<PositionPart> node : closed) {
                    if (node.getObject() == successor.getObject()) {
                        shouldSkip = true;
                    }
                }

                if (!shouldSkip) {
                    open.add(successor);
                }
            }

            closed.add(n);
        }
    }

    private List<Node<PositionPart>> getSuccessors(Node<PositionPart> parent) {
        List<Node<PositionPart>> successors = new ArrayList();

        int x = (int) parent.getObject().getX() / 32;
        int y = (int) parent.getObject().getY() / 32;

        if (parent.getObject() != start) {
            //Negative
            addIfExists(successors, parent, x - 1, y + 1);
            addIfExists(successors, parent, x - 1, y + 2);
            addIfExists(successors, parent, x - 1, y + 3);
            addIfExists(successors, parent, x - 1, y + 4);
            addIfExists(successors, parent, x - 2, y + 1);
            addIfExists(successors, parent, x - 2, y + 2);
            addIfExists(successors, parent, x - 2, y + 3);
            addIfExists(successors, parent, x - 2, y + 4);

            //Positive
            addIfExists(successors, parent, x + 1, y + 1);
            addIfExists(successors, parent, x + 1, y + 2);
            addIfExists(successors, parent, x + 1, y + 3);
            addIfExists(successors, parent, x + 1, y + 4);
            addIfExists(successors, parent, x + 2, y + 1);
            addIfExists(successors, parent, x + 2, y + 2);
            addIfExists(successors, parent, x + 2, y + 3);
            addIfExists(successors, parent, x + 2, y + 4);
        }
        addIfExists(successors, parent, x, y);
        addIfExists(successors, parent, x, 0);

        addIfExists(successors, parent, x + 1, y - 1);
        addIfExists(successors, parent, x + 1, y);
        addIfExists(successors, parent, x - 1, y - 1);
        addIfExists(successors, parent, x - 1, y);
        addIfExists(successors, parent, x, y + 1);

        return successors;
    }

    private void addIfExists(List<Node<PositionPart>> list, Node parent, int x, int y) {
        if (x < 0 || y < 0 || x >= TILES_X || y >= TILES_Y) {
            return;
        }
        PositionPart p = updatedMap[y][x];
        if (p != null) {
            Node<PositionPart> node = new Node(p, parent);
            list.add(node);
        } else {
        }
    }

    private Node<PositionPart> getLeastF() {
        float f = -1;
        Node<PositionPart> leastF = null;
        for (Node<PositionPart> p : open) {
            float nextF = p.getMovementCostFromStart() + getH();
            if (f == -1 || f > nextF) {
                f = nextF;
                leastF = p;
            }
        }
        return leastF;
    }

    private void generateNodeMap(World world) {
        //Sets the size of the map

        Collection<Entity> parts = world.getEntities();

        float heighestX = 0;
        float heighestY = 0;
        for (Entity ent : parts) {
            if (ent.isConstantPosition()) {
                PositionPart p = ((PositionPart) ent.getPart(PositionPart.class));
                if (p.getX() > heighestX) {
                    heighestX = p.getX();
                }
                if (p.getY() > heighestY) {
                    heighestY = p.getY();
                }
            }
        }
        TILES_X = (int) (heighestX / 32) + 1;
        TILES_Y = (int) (heighestY / 32) + 1;
        staticMap = new PositionPart[TILES_Y][TILES_X];

        for (Entity ent : parts) {
            if (ent.isConstantPosition()) {
                PositionPart p = ((PositionPart) ent.getPart(PositionPart.class));
                staticMap[(int) (p.getY() / 32)][(int) (p.getX() / 32)] = p;
            }
        }
    }

    private void printCollisionMap() {
        for (int y = TILES_Y - 1; y >= 0; y--) {
            for (int x = 0; x < TILES_X; x++) {
                System.out.print((updatedMap[y][x] != null) ? "1" : "0");
            }
            System.out.print("\n");
        }
    }

    public LinkedList<Node<PositionPart>> getResult() {
        return result;
    }

    private float getH() {
        return (float) Math.sqrt((float) preH());
    }

    private float preH() {
        return (float) (Math.pow((double) goal.getX() - (double) start.getX(), 2) + (Math.pow((double) goal.getY() - (double) start.getY(), 2)));
    }

    private void printRoute() {
        printCollisionMap();
        System.out.println("---------------------------");
        System.out.println("Start: " + start.getX() + "   " + start.getY());
        for (Node<PositionPart> nod : result) {
            System.out.println("Node: " + nod.getObject().getX() + "   " + nod.getObject().getY());
        }
        System.out.println("End: " + goal.getX() + "   " + goal.getY());
    }
}
