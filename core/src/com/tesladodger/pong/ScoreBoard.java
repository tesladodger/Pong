package com.tesladodger.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


class ScoreBoard {
    private ShapeRenderer shapeRenderer;

    private String leftScore = "0";
    private String rightScore = "0";

    private int[] origin = new int[2];
    private int w = 5;
    private int h = 15;

    /**
     * Method to get the rectangle members of a digit.
     * @param value number;
     * @return int[] with members;
     */
    private int[] getMembers(char value) {
        switch (value) {
            case '0': return new int[] {1, 2, 3,    5, 6, 7,   8, 9, 10, 11, 12, 13};
            case '1': return new int[] {      3,       6,         9,     11,     13};
            case '2': return new int[] {   2, 3, 4, 5,    7,   8, 9, 10, 11, 12, 13};
            case '3': return new int[] {   2, 3, 4,    6, 7,   8, 9, 10, 11, 12, 13};
            case '4': return new int[] {1,    3, 4,    6,      8, 9, 10, 11,     13};
            case '5': return new int[] {1, 2,    4,    6, 7,   8, 9, 10, 11, 12, 13};
            case '6': return new int[] {1, 2,    4, 5, 6, 7,   8, 9, 10, 11, 12, 13};
            case '7': return new int[] {2,    3,       6,      8, 9,     11, 13    };
            case '8': return new int[] {1, 2, 3, 4, 5, 6, 7,   8, 9, 10, 11, 12, 13};
            case '9': return new int[] {1, 2, 3, 4,    6, 7,   8, 9, 10, 11, 12, 13};
        }
        return new int[] {4};
    }

    /**
     * Method to get the coordinates and size of a rectangle, given it's number.
     * @param i number of the rectangle, 1 to 7;
     * @return array with the coordinates and the size;
     */
    private int[] getCoordinates(int i) {
        switch (i) {
            case 1: return new int[]  {origin[0]        , origin[1] + 2*w + h  , w, h};
            case 2: return new int[]  {origin[0] + w    , origin[1] + 2*w + 2*h, h, w};
            case 3: return new int[]  {origin[0] + w + h, origin[1] + 2*w + h  , w, h};
            case 4: return new int[]  {origin[0] + w    , origin[1] + w + h    , h, w};
            case 5: return new int[]  {origin[0]        , origin[1] + w        , w, h};
            case 6: return new int[]  {origin[0] + w + h, origin[1] + w        , w, h};
            case 7: return new int[]  {origin[0] + w    , origin[1]            , h, w};

            case 8: return new int[]  {origin[0]        , origin[1] + 2*h + 2*w, w, w};
            case 9: return new int[]  {origin[0] + w + h, origin[1] + 2*h + 2*w, w, w};
            case 10: return new int[] {origin[0]        , origin[1] + h + w    , w, w};
            case 11: return new int[] {origin[0] + w + h, origin[1] + h + w    , w, w};
            case 12: return new int[] {origin[0]        , origin[1]            , w, w};
            case 13: return new int[] {origin[0] + w + h, origin[1]            , w, w};
        }
        return new int[] {0, 0, w, h};
    }

    /**
     * Method to render a rectangle.
     * @param values coordinates and size;
     */
    private void renderRect(int[] values) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(values[0], values[1], values[2], values[3]);
        shapeRenderer.end();
    }

    /**
     * Method to update the score strings.
     * @param leftScore left player score;
     * @param rightScore right player score;
     */
    void update(int leftScore, int rightScore) {
        this.leftScore = String.valueOf(leftScore);
        this.rightScore = String.valueOf(rightScore);
    }

    /**
     * Main method. Iterates the digits of both scores, gets the members of each digit and calls
     * the render method.
     */
    void display() {
        shapeRenderer = new ShapeRenderer();

        // Origin of the last digit of the left score.
        origin[0] = Gdx.graphics.getWidth() / 2 - 6*w - h;
        origin[1] = Gdx.graphics.getHeight() - 5*w - 2*h;

        // Iterate through the digits of the left score backwards, decreasing the origin.
        for (int i = leftScore.length() - 1; i >= 0; i--) {
            int[] members = getMembers(leftScore.charAt(i));
            for (int member : members) {
                renderRect(getCoordinates(member));
            }
            origin[0] -= 3*w + h;
        }

        // Origin of the first digit of the right score.
        origin[0] = Gdx.graphics.getWidth() / 2 + 4*w;

        // Iterate through the digits of the right score, increasing the origin.
        for (int i = 0; i < rightScore.length(); i++) {
            int[] members = getMembers(rightScore.charAt(i));
            for (int member : members) {
                renderRect(getCoordinates(member));
            }
            origin[0] += 3*w + h;
        }

    }

    void disposeSR() {
        shapeRenderer.dispose();
    }

}
