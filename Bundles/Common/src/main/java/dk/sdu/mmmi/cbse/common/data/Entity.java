package dk.sdu.mmmi.cbse.common.data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();
    
    private ArrayList<Float> shapeX = new ArrayList();
    private ArrayList<Float> shapeY = new ArrayList();
    private float radius;
    private int[] color = new int[4];
    private String spritePath = "";
    
    public Entity() {
    }

    
    public void setColor(int[] color){
        this.color = color;
    }
    
    public int[] getColor(){
        return this.color;
    }
    
    public void setRadius(float r){
        this.radius = r;
    }
    
    public float getRadius(){
        return radius;
    }

    
    public String getID() {
        return ID.toString();
    }

    public ArrayList<Float> getShapeX() {
        return shapeX;
    }

    public void addShapeXpoint(float x) {
        shapeX.add(x);
    }

    public ArrayList<Float> getShapeY() {
        return shapeY;
    }

    public void addShapeYpoint(float y) {
        shapeY.add(y);
    }
    
    public void clearShape(){
        shapeX.clear();
        shapeY.clear();
    }
    
    public void setSprite(String path){
        this.spritePath = path;
    }
    
    public String getSprite(){
        return this.spritePath;
    }
    
    public Entity getSource(){
        return this;
    }
}
