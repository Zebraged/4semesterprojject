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
    private int yPos;
    private int width = 32;
    private int height = 32;
    private double heuristic;
    private double distance;
    private double cost;
    
    public Node(int x, int y){
        this.x = x;
        this.y = y;
        this.distance = Double.MAX_VALUE;
    }
    
    public Node(){
        
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

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getyPos() {
        return yPos;
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
