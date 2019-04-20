package com.tesladodger.pong;

import com.badlogic.gdx.Gdx;


class Ball {
    float x;
    float y;

    private int speed;
    private double angle;

    static int radius = 10;

    /**
     * Constructor.
     * @param x coordinate;
     * @param y coordinate;
     * @param angle random angle;
     */
    Ball(int x, int y, double angle) {
        this.x = x;
        this.y = y;

        speed = 10;
        this.angle = angle;
    }

    /**
     * Method to reset the position of the ball when a player scores.
     */
    private void reset() {
        x = Gdx.graphics.getWidth() / 2;
        y = Gdx.graphics.getHeight() / 2;
        angle += Math.PI;
    }

    /**
     * Method to change the position of the ball and check wall hits.
     */
    int update() {
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);

        // Wall hit.
        if (y <= radius) {
            angle *= -1;
            y = radius;
        }
        else if (y >= Gdx.graphics.getHeight() - radius) {
            angle *= -1;
            y = Gdx.graphics.getHeight() - radius;
        }

        // Score.
        if (x < 0) {
            reset();
            return 2;
        }
        else if (x > Gdx.graphics.getWidth()) {
            reset();
            return 1;
        }

        return 0;
    }

    /**
     * Method to get the angle given the segment of the paddle that the ball hit.
     * @param seg segment;
     * @return angle;
     */
    private double segment2Angle(int seg) {
        switch (seg) {
            case 0: return angle = - Math.PI / 4;
            case 1: return angle = - Math.PI / 6;
            case 2: return angle = - Math.PI / 12;
            case 3:
            case 4: return angle = 0;
            case 5: return angle = Math.PI / 12;
            case 6: return angle = Math.PI / 6;
            case 7:
            case 8: return angle = Math.PI / 4;
        }
        return 0;
    }

    /**
     * Method to check if the ball hits the paddles and change it's speed accordingly.
     * @param leftY y position of the left paddle;
     * @param rightY y position of the right paddle;
     */
    void checkCollision(int leftY, int rightY) {
        // Left.
        if (x <= 20 + Paddle.thickness + radius &&             // x same as front of paddle,
                x >= 20 + radius &&                            // but not passed the paddle,
                y >= leftY  &&  y <= leftY + Paddle.height) {  // y in the range of the paddle.

            // Size of the paddle segments.
            float segmentSize = Paddle.height / 8;
            // Segment being hit (float).
            float segmentF = (y - leftY) / segmentSize;
            // Round down by subtracting the remainder of the division by 1.
            int segment = (int) (segmentF - (segmentF % 1));

            // Get the angle of return.
            angle = segment2Angle(segment);
            // Translate the ball to avoid bugs.
            x = 20 + Paddle.thickness + radius;
        }

        // Right.
        else if (x >= Gdx.graphics.getWidth() - 20 - Paddle.thickness - radius &&
                x <= Gdx.graphics.getWidth() - 20 - radius &&
                y >= rightY  &&  y <= rightY + Paddle.height) {

            float segmentSize = Paddle.height / 8;
            float segmentF = (y - rightY) / segmentSize;
            int segment = (int) (segmentF - (segmentF % 1));

            angle = Math.PI - segment2Angle(segment);
            x = Gdx.graphics.getWidth() - 20 - Paddle.thickness - radius;
        }
    }

    double getAngle() {
        return angle;
    }
}
