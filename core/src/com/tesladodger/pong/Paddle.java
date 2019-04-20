package com.tesladodger.pong;

import com.badlogic.gdx.Gdx;

class Paddle {
    int x;
    int y = Gdx.graphics.getHeight() / 2 - height / 2;
    private int speed;

    static int thickness = 10;
    static int height = 60;

    Paddle(int x) {
        this.x = x;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    void update() {
        if ( (y > 0 && speed < 0) || (y < Gdx.graphics.getHeight() - height && speed > 0) ) {
            y += speed;
        }
    }

    /**
     * Method to move the left paddle in single player mode.
     * @param angle angle of the ball;
     * @param ballY y coordinate of the ball;
     */
    void moveAuto(double angle, float ballY) {
        // Ball is moving away, move to the center.
        if (Math.abs(angle / (2*Math.PI)) % 1 < 0.25 || Math.abs(angle / (2*Math.PI)) % 1 > 0.75) {
            if (this.y > Gdx.graphics.getHeight() / 2) {
                this.setSpeed(-6);
            }
            else if (this.y < Gdx.graphics.getHeight() / 2 - height) {
                this.setSpeed(6);
            }
        }
        // Otherwise, move to the ball.
        else {
            if ((int) ballY < this.y + (height / 4)) {
                this.setSpeed(-7);
            } else if ((int) ballY > this.y + ((height * 3) / 4)) {
                this.setSpeed(7);
            }
        }
    }

}
