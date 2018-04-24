/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Shrek
 */
public class MovingPart implements EntityPart {

    private float speed;
    private float gravity, jumpVelocity;
    private boolean left, right, up;
    private boolean isGrounded;
<<<<<<< HEAD
    private float jumpTime; //used for calculating gravitational acceleration
=======
    private float jumpTime;
    private float fallspeed;
>>>>>>> master
    private CollisionPart col = CollisionPart.getInstance();

    /**
     * Initializes MovingPart and calculates gravity and initial jump-velocity
     * based on desired jump height and jump length.
     *
     * @param speed MovingPart's horizontal speed
     * @param jumpHeight MovingPart's maximum jump hight
     * @param jumpLength MovingPart's maximum jump length
     */
    public MovingPart(float speed, float jumpHeight, float jumpLength) {
        this.speed = speed*25;

<<<<<<< HEAD
        this.jumpVelocity = 2 * jumpHeight * speed / jumpLength;

        this.gravity = 2 * jumpHeight * speed * speed / jumpLength / jumpLength;
    }

    /**
     * Initializes MovingPart without any gravity
     *
     * @param speed MovingPart's horizontal speed
     */
    public MovingPart(float speed) {
        this.speed = speed;
        this.jumpVelocity = 0;
        this.gravity = 0;
=======
        jumpVelocity = 2 * jumpHeight * speed / jumpLength * 10;

        gravity = 2 * jumpHeight * speed * speed / jumpLength / jumpLength * 25;
        
        fallspeed = gravity - (gravity/7)*25;
>>>>>>> master
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setIsGrounded(boolean isGrounded) {
        this.isGrounded = isGrounded;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

        float minX = 0;
        float maxX = 0;
        float minY = 0;
        float maxY = 0;

        if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.player.Player.*")) {
            maxX = col.getMaxX();
            minY = col.getMinY();
            minX = col.getMinX();
            maxY = col.getMaxY();
        }

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float dt = gameData.getDelta();

        if (left) {
            x -= speed * dt;
        }

        if (right) {
            x += speed * dt;
        }

        if (up) {
            if (isGrounded) {
                jumpTime = 0;
            }
            jumpTime += dt;
            isGrounded = false;
            y += -gravity * jumpTime * jumpTime / 2 + jumpVelocity * dt;
        } else if (!isGrounded) {
            jumpTime += dt;
            y += -gravity * jumpTime * jumpTime / 2;
        }

        if (maxX > 1 && x > maxX) {
            x = maxX;
        }

        if (minX > 1 && x < minX) {
            x = minX;
        }

        if (minY > 1 && y < minY) {
            y = minY;
            jumpTime = 0;
        }

        if (maxY > 1 && y > maxY) {
            y = maxY;

        }

        positionPart.setX(x);
        positionPart.setY(y);

    }

    public float getSpeed() {
        return speed;
    }

    public float getGravity() {
        return gravity;
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public float getFallspeed() {
        return fallspeed;
    }
    
    
    
    
}
