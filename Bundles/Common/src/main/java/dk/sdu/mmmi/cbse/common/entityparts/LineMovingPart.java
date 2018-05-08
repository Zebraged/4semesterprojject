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
    private float jumpVelocity, jumpHeight, gravityAcceleration, jumpTime;
    private boolean isGrounded;
    private boolean up, falling;
    private float lastY;

    private CollisionPart col;

    public LineMovingPart(float speed, float jumpHeight, float jumpLength) {

        this.speed = speed;

        jumpVelocity = 2 * jumpHeight * speed / jumpLength;

        gravityAcceleration = 2 * jumpHeight * speed * speed / jumpLength / jumpLength;

    }

    @Override
    public void process(GameData gameData, Entity entity) {
        col = entity.getPart(CollisionPart.class);
        float minX = 0;
        float maxX = 0;
        float minY = 0;
        float maxY = 0;

        if (col != null) {
            maxX = col.getMaxX();
            minY = col.getMinY();
            minX = col.getMinX();
            maxY = col.getMaxY();
        }

        if (pos == null) {
            pos = entity.getPart(PositionPart.class);
        }
        if (goalX != -1 || goalY != -1) {
            float dt = gameData.getDelta();

            //Move in the direction of x
            if (Math.abs(pos.getX() - goalX) > 3) {
                float x;
                if (pos.getX() > goalX) {
                    x = pos.getX() - speed * dt;
                } else {
                    x = pos.getX() + speed * dt;
                }

                if (maxX > 1 && x > maxX) {
                    x = maxX;
                }

                if (minX > 1 && x < minX) {
                    x = minX;
                }
                pos.setX(x);
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
                    y += -gravityAcceleration * jumpTime * jumpTime / 2 + jumpVelocity * dt;
                } else if (!isGrounded) {
                    if (y > goalY) {
                        jumpTime += dt;
                        y += -gravityAcceleration * jumpTime * jumpTime / 2;
                    }
                }

                if (minY > 1 && y < minY) {
                    y = minY;
                    jumpTime = 0;
                }

                if (maxY > 1 && y > maxY) {
                    y = maxY;

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
