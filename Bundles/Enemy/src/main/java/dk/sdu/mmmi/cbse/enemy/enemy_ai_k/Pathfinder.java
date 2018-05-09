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
    private boolean[][] addedBefore;
    private LinkedList<Node<PositionPart>> result;
    private PositionPart start, goal;
    private static int TILES_X, TILES_Y;
    private float h;
    private static boolean reachable = true;
    private static float lastGoalY, lastGoalX;
    private final int RIGHT = 1;
    private final int CENTER = 0;
    private final int LEFT = -1;

    public Pathfinder(PositionPart start, PositionPart goal, World world) {
        this.start = start;
        this.goal = new PositionPart(goal.getX(), goal.getY(), 0);
        //If the last was reachable
        //Or the last was unreachable but the Y value has gotten lower
        if (reachable || (!reachable && lastGoalY != goal.getY() && lastGoalX != goal.getX())) {
            lastGoalY = goal.getY();
            lastGoalX = goal.getX();
            open = new ArrayList();
            closed = new ArrayList();
            h = getH();
            if (TILES_X == 0 || TILES_Y == 0) {
                generateNodeMap(world);
            }
            updateMap();
            Node s = new Node(start, null);
            updatedMap[(int) this.goal.getY() / 32][(int) this.goal.getX() / 32] = this.goal;
            open.add(s);
            reachable = false;
            if (Math.abs(goal.getX() - start.getX()) < 32) {
                System.out.println("Making simple route..");
                makeSimpleRoute();
            } else {
                generate();
            }
            //printRoute();

        }

    }

    private void makeSimpleRoute() {
        int dir = ((goal.getX() - start.getX()) < 0) ? -1 : 1;
        Node<PositionPart> end = new Node(new PositionPart(goal.getX() + dir * 32, start.getY(), 0), new Node(start, null));
        makeLinkedList(end);
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
        addedBefore = new boolean[updatedMap.length][updatedMap[1].length];
    }

    private void makeLinkedList(Node<PositionPart> last) {
        reachable = true;
        this.result = new LinkedList();

        Node current = last;

        while (current.getParent() != null) {
            this.result.addFirst(current);
            current = current.getParent();
        }
        if (!this.result.isEmpty()) {
            this.result.remove(0); //Remove the initial position.
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
                closed.add(n);
            }

        }
    }

    private List<Node<PositionPart>> getSuccessors(Node<PositionPart> parent) {
        List<Node<PositionPart>> successors = new ArrayList();

        int x = (int) parent.getObject().getX() / 32;
        int y = (int) parent.getObject().getY() / 32;

        if (parent.getObject() != start) {
            //Negative
            addIfValid(successors, parent, x - 1, y + 1, LEFT);
            addIfValid(successors, parent, x - 1, y + 2, LEFT);
            addIfValid(successors, parent, x - 1, y + 3, LEFT);
            addIfValid(successors, parent, x - 2, y + 1, LEFT);

            //Positive
            addIfValid(successors, parent, x + 1, y + 1, RIGHT);
            addIfValid(successors, parent, x + 1, y + 2, RIGHT);
            addIfValid(successors, parent, x + 1, y + 3, RIGHT);
            addIfValid(successors, parent, x + 2, y + 1, RIGHT);
        }
        addIfValid(successors, parent, x, 0, CENTER);

        addIfValid(successors, parent, x + 1, y - 1, CENTER);
        addIfValid(successors, parent, x + 1, y, CENTER);
        addIfValid(successors, parent, x - 1, y, CENTER);
        addIfValid(successors, parent, x - 1, y, CENTER);
        addIfValid(successors, parent, x, y + 1, CENTER);

        return successors;
    }

    private void addIfValid(List<Node<PositionPart>> list, Node parent, int x, int y, int dir) {
        //If out of array size.
        if (x < 0 || y < 0 || x >= TILES_X || y >= TILES_Y) {
            return;
        }
        //if a platform is directly above it.
        if (updatedMap.length <= y + 1 || updatedMap[y + 1][x] != null && updatedMap[y][x] != goal) {
            return;
        }

        if (dir == LEFT && updatedMap[y][x+1] != null) {
            return;
        }
        if (dir == RIGHT && updatedMap[y][x-1] != null) {
            return;
        }

        PositionPart p = updatedMap[y][x];
        if (p != null) {
            Node<PositionPart> node = new Node(p, parent);
            if (!closed.contains(node) && !addedBefore[y][x]) {
                list.add(node);
                addedBefore[y][x] = true;
            }
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
        if (result != null) {
            for (Node<PositionPart> nod : result) {
                System.out.println("Node: " + nod.getObject().getX() + "   " + nod.getObject().getY());
            }
        } else {
            System.out.println("Impossible route to goal.");
        }
    }

    /**
     * @return the reachable
     */
    public static boolean isReachable() {
        return reachable;
    }
}
