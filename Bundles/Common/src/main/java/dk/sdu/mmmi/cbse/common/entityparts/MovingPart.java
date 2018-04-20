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
 * @author Alexander
 */
public class MovingPart implements EntityPart {

    private float speed;
    private float gravity, jumpVelocity;
    private boolean left, right, up;
    private boolean isGrounded;
    private float jumpTime;
    private float fallspeed;

    /**
     * Initializes MovingPart and calculates gravity and initial jump-velocity
     * based on desired jump height and jump length.
     *
     * @param speed MovingPart's horizontal speed
     * @param jumpHeight MovingPart's maximum jump hight
     * @param jumpLength MovingPart's maximum jump length
     */
    public MovingPart(float speed, float jumpHeight, float jumpLength) {
        this.speed = speed;

        jumpVelocity = 2 * jumpHeight * speed / jumpLength;

        gravity = 2 * jumpHeight * speed * speed / jumpLength / jumpLength;
        
        fallspeed = gravity - (gravity/7);
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
