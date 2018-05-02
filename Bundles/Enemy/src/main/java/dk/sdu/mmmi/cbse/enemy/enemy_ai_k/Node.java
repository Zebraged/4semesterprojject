/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy.enemy_ai_k;

import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mr. Kinder
 */
public class Node<T extends PositionPart> {

    private Node parent;
    private List<Node> successors;
    private float movementCostFromStart;
    private T object;
    private float fValue = -1;

    public Node(T object, Node parent) {
        this.parent = parent;
        this.successors = new ArrayList();
        this.object = object;

        if (parent == null) {
            movementCostFromStart = 0;
        } else {
            movementCostFromStart = getDistanceFromStart();
        }
    }

    public void addSuccessor(Node successor) {
        successors.add(successor);
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @return the movementCostFromStart
     */
    public float getMovementCostFromStart() {
        return movementCostFromStart;
    }

    /**
     * @return the object
     */
    public T getObject() {
        return object;
    }
    
    private float getDistanceFromStart() {
        return (float) Math.sqrt((float) getAOrB());
    }
    
    private float getAOrB() {
        return (float) (Math.pow((double) parent.getObject().getX() - (double) object.getX(), 2) + (Math.pow((double) parent.getObject().getY() - (double) object.getY(), 2)));
    }

    /**
     * @return the fValue
     */
    public float getfValue() {
        return fValue;
    }

    /**
     * @param fValue the fValue to set
     */
    public void setfValue(float fValue) {
        this.fValue = fValue;
    }
}
