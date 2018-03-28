/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnemyAI.Node;

/**
 *
 * @author Marcg
 */
public class Node {
    
    
    private int xPos;
    private int yPos;
    private double heuristic;
    private double distance;
    private double cost;
    
    public Node(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.distance = Double.MAX_VALUE;
    }
    
    public Node(){
        
    }
    
    

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }


    public int getxPos() {
        return xPos;
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
    
    
    
   
}
