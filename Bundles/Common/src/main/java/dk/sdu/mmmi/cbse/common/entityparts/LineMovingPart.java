/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;

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

    private CollisionPart col;

    public LineMovingPart(float speed, float jumpHeight, float jumpLength) {
        this.speed = speed;

        this.speed = speed * 25;

        jumpVelocity = 2 * jumpHeight * speed / jumpLength * 10;

        gravity = 2 * jumpHeight * speed * speed / jumpLength / jumpLength * 25;

        fallspeed = gravity - (gravity / 7) * 25;
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
                } else if (goalY + 100 < y) { //if position is 100 pixels over goalY
                    up = false;
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

                pos.setY(y);
            }

            if (minY > 1 && pos.getY() < minY) {
                pos.setY(minY);
                jumpTime = 0;
                isGrounded = true;
            }

            if (maxY > 1 && pos.getY() > maxY) {
                pos.setY(maxX);
            }

            //set to done if goal reached
            if (Math.abs(pos.getX() - goalX) <= 5 && (Math.abs(pos.getY() - goalY) <= 10 || isGrounded)) {
                goalX = -1;
                goalY = -1;
                jumpTime = 0;
            }
        }
        
        if(gameData.getKeys().isDown(GameKeys.ENTER)) {
            System.out.println("Current Position: "+pos.getX()+"    "+pos.getY());
        }
    }

    public boolean reachedGoal() {
        return (goalX == -1 && goalY == -1);
    }

    public void setGoal(float x, float y) {
        y = (y < 32) ? 32 : y;
        System.out.println("Goal set: " + x + "    " + y);
        this.goalX = x;
        this.goalY = y;
    }
}
