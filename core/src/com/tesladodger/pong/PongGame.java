package com.tesladodger.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;


public class PongGame extends ApplicationAdapter {

    private Ball ball;
    private Paddle paddleL;
    private Paddle paddleR;

    private ScoreBoard scoreBoard;

    private int scored;
    private int score1;
    private int score2;

    private boolean inSinglePlayer;

    private ShapeRenderer shapeRenderer;


    private void drawRect(int x, int y, int w, int h) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, w, h);
        shapeRenderer.end();
    }


    @Override
    public void create() {

        Random ran = new Random();
        ball = new Ball(Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() / 2,
                -1 + 2 * ran.nextDouble());

        paddleL = new Paddle(20);
        paddleR = new Paddle(Gdx.graphics.getWidth() - 20 - Paddle.thickness);

        scoreBoard = new ScoreBoard();

        scored = 0;
        score1 = 0;
        score2 = 0;

        inSinglePlayer = true;

        shapeRenderer = new ShapeRenderer();

    }


    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Center lines.
        int y = 3;
        while (y < Gdx.graphics.getHeight()) {
            drawRect(Gdx.graphics.getWidth() / 2 - 1, y, 2, 5);
            y += 10;
        }

        // Ball.
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(ball.x, ball.y, Ball.radius);
        shapeRenderer.end();

        // Paddles.
        drawRect(paddleL.x, paddleL.y, Paddle.thickness, Paddle.height);
        drawRect(paddleR.x, paddleR.y, Paddle.thickness, Paddle.height);


        // Input.
        if      (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) inSinglePlayer = true;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) inSinglePlayer = false;
        // Left.
        if (!inSinglePlayer && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            paddleL.setSpeed(-10);
        }
        else if (!inSinglePlayer && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
           paddleL.setSpeed(10);
        }
        else if (inSinglePlayer) {
            paddleL.moveAuto(ball.getAngle(), ball.y);
        }
        // Right.
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            paddleR.setSpeed(-10);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            paddleR.setSpeed(10);
        }


        // Move the paddles.
        paddleL.update();
        paddleR.update();
        paddleL.setSpeed(0);
        paddleR.setSpeed(0);


        // Move the ball and check for score.
        scored = ball.update();
        if (scored == 1) {
            score1 += 1;
            scoreBoard.update(score1, score2);
        }
        else if (scored == 2) {
            score2 += 1;
            scoreBoard.update(score1, score2);
        }


        // Collisions between ball and paddles.
        ball.checkCollision(paddleL.y, paddleR.y);


        // Show the score board.
        scoreBoard.display();

    }


    @Override
    public void dispose() {
        shapeRenderer.dispose();
        scoreBoard.disposeSR();
    }
}
