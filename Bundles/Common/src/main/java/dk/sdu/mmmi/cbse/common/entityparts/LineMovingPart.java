/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.other.ExpandedMath;

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
    private boolean failed;
    private float lastY;
    private int collisionFrames;

    private CollisionPart col;

    public LineMovingPart(float speed, float jumpHeight, float jumpLength) {
        this.speed = speed * 25;

        jumpVelocity = jumpHeight;

        gravity = 2;

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
        float dt = gameData.getDelta();
        if (goalX != -1 || goalY != -1) {

            //Move in the direction of x
            if (Math.abs(pos.getX() - goalX) > 1 && (pos.getY() >= goalY - 5)) {
                float x;
                if (pos.getX() > goalX) {
                    x = pos.getX() - speed * dt;
                } else {
                    x = pos.getX() + speed * dt;
                }

                if (maxX > 1 && x > maxX) {
                    collisionFrames++;
                    x = maxX;
                }

                if (minX > 1 && x < minX) {
                    collisionFrames++;
                    x = minX;
                }
                pos.setX(x);
            } else {
                if (Math.abs(goalX - pos.getX()) < 32) {
                    int dir = (goalX > pos.getX()) ? -1 : 1;
                    pos.setX(pos.getX() + ((speed / 10) * dt) * dir);
                }
            }

            if (goalY != -1) {
                float y = pos.getY();
                lastY = y;

                if (goalY > y + 5) {
                    up = true;
                } else if (goalY + 32 < y) { //if position is 100 pixels over goalY
                    up = false;
                }

            }

            //set to done if goal reached
            if (Math.abs(pos.getX() - goalX) <= 1 /*&& pos.getY() >= goalY*/) {
                goalX = -1;
                goalY = -1;
            }
        }

        float y = pos.getY();
        if (up) {
            jumpTime += dt * 200;
            float fallingDir = ExpandedMath.clamp((jumpVelocity - gravity - jumpTime) * dt, -30, 30);
            y += fallingDir;
        } else if (!isGrounded) {
            jumpTime += dt * 200;
            float fallingDir = ExpandedMath.clamp((-gravity - jumpTime) * dt, -30, 30);
            y += fallingDir;
        }
        if (minY > 1 && y < minY) {
            y = minY;
            jumpTime = 0;
        }

        if (maxY > 1 && y > maxY) {
            y = maxY;
            collisionFrames++;
        }

        pos.setY(y);

        //If the position part is colliding beyond repair
        if (collisionFrames > 60) {
            goalX = -1;
            goalY = -1;
        }
    }

    public boolean reachedGoal() {
        return (goalX == -1 && goalY == -1);
    }

    public void setGoal(float x, float y) {
        collisionFrames = 0;
        y = (y < 33) ? 33 : y;
        this.goalX = x;
        this.goalY = y;
    }
}
