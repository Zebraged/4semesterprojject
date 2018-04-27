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
 * @author Mr. Kinder
 */
public class LineMovingPart implements EntityPart {

    private float goalX = -1, goalY = -1;
    private PositionPart pos;
    private float speed;
    private float jumpVelocity, jumpHeight, fallspeed, gravity, jumpTime;
    private boolean isGrounded;
    private boolean up, falling;
    private float lastY;

    public LineMovingPart(float speed, float jumpHeight, float jumpLength) {
        this.speed = speed;

        this.speed = speed * 25;

        jumpVelocity = 2 * jumpHeight * speed / jumpLength * 10;

        gravity = 2 * jumpHeight * speed * speed / jumpLength / jumpLength * 25;

        fallspeed = gravity - (gravity / 7) * 25;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (pos == null) {
            pos = entity.getPart(PositionPart.class);
        }
        if (goalX != -1 || goalY != -1) {
            float dt = gameData.getDelta();

            //Move in the direction of x
            if (Math.abs(pos.getX() - goalX) > 3) {
                if (pos.getX() > goalX) {
                    pos.setX(pos.getX() - speed * dt);
                } else {
                    pos.setX(pos.getX() + speed * dt);
                }
            }

            if (goalY != -1) {
                float y = pos.getY();
                lastY = y;

                if (goalY > y) {
                    up = true;
                } else if (goalY < y) {
                    up = false;
                    isGrounded = false;
                } else {
                    up = false;
                    isGrounded = false;
                }

                if (up) {
                    if (isGrounded) {
                        jumpTime = 0;
                    }
                    jumpTime += dt;
                    isGrounded = false;
                    y += -gravity * jumpTime * jumpTime / 2 + jumpVelocity * dt;
                } else if (!isGrounded) {
                    if (y > goalY) {
                        jumpTime += dt;
                        y += -gravity * jumpTime * jumpTime / 2;
                    }
                }
                pos.setY(y);
            }

            //set to done if goal reached
            if (Math.abs(pos.getX() - goalX) <= 5 && (Math.abs(pos.getY() - goalY) <= 10 || (lastY > pos.getY() && pos.getY() < goalY))) {
                pos.setX(Math.round(goalX));
                pos.setY(Math.round(goalY));
                goalX = -1;
                goalY = -1;
                jumpTime = 0;
                isGrounded = true;
            }
        }
    }

    public boolean reachedGoal() {
        return (goalX == -1 && goalY == -1);
    }

    public void setGoal(float x, float y) {
        this.goalX = x;
        this.goalY = y;
    }
}
