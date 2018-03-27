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
    private int Heuristic;
    
    public Node(String type, int x, int y){
        this.xPos = x;
        this.yPos = y;
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

    public int getHeuristic() {
        return Heuristic;
    }

    public void setHeuristic(int Heuristic) {
        this.Heuristic = Heuristic;
    }
    
   
}
