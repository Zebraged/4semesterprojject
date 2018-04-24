/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy.EnemyAI.Node;

/**
 *
 * @author Marcg
 */
public class Node {
    
    
    private int x;
    private int y;
    private int jumpHeight;
    private int width = 32;
    private int height = 32;
    private double heuristic = 0;
    private double distance = 0;
    private double cost = 0;
    private Node parent = null;
    
    public Node(int x, int y){
        this.x = x;
        this.y = y;
        this.distance = Double.MAX_VALUE;
    }
    
    public Node(){
        
    }
    
    public void setParent(Node node){
        this.parent = node;
    }
    
    public Node getParent(){
        return this.parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    
    public double getDistance() {
        return distance;
    }

    public double getCost() {
        cost = heuristic + distance;
        return cost;
    }

    public int getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }
    
    
    
   
}
