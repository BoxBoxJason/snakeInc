package org.snakeinc.snake.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Snake {

    private final ArrayList<Tile> body;
    private Race race;

    public static enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private static enum Race {
        PYTHON(Color.GREEN),
        ANACONDA(Color.GRAY),
        BOA(Color.BLUE);

        private final Color color;

        Race(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public static Race getRandomRace() {
            Race[] races = values();
            return races[new Random().nextInt(races.length)];
        }
    }

    public Snake() {
        body = new ArrayList<>();
        body.add(new Tile(5, 5)); // La tête du serpent
        this.race = Race.getRandomRace();
    }

    public ArrayList<Tile> getBody() {
        return body;
    }

    public Tile getHead() {
        return body.getFirst();
    }

    public int eat(Food food) {
        int sizeIncrease = 1;
        switch (race) {
            case PYTHON:
                if (food.getType() == Food.FoodType.BROCOLI) {
                    sizeIncrease = -2;
                }
            case ANACONDA:
                if (food.getType() == Food.FoodType.APPLE) {
                    sizeIncrease = 2;
                } else if (food.getType() == Food.FoodType.BROCOLI) {
                    sizeIncrease = -1;
                }
            case BOA:
                if (food.getType() == Food.FoodType.APPLE) {
                    sizeIncrease = 3;
                } else if (food.getType() == Food.FoodType.BROCOLI) {
                    sizeIncrease = 0;
                }
        }
        if (sizeIncrease > 0) {
            for (int i = 0; i < sizeIncrease; i++) {
                body.add(body.get(body.size() - 1).copy());
            }
        } else {
            for (int i = 0; i < -sizeIncrease; i++) {
                body.remove(body.size() - 1);
            }
        }
        return sizeIncrease;
    }

    public void move(Snake.Direction direction) {
        Tile newHead = getHead().copy();

        switch (direction) {
            case Snake.Direction.UP:
                newHead.setY(newHead.getY() - 1);
                break;
            case Snake.Direction.DOWN:
                newHead.setY(newHead.getY() + 1);
                break;
            case Snake.Direction.LEFT:
                newHead.setX(newHead.getX() - 1);
                break;
            case Snake.Direction.RIGHT:
                newHead.setX(newHead.getX() + 1);
                break;
        }

        body.addFirst(newHead);
        body.removeLast(); // Supprime le dernier segment pour simuler le déplacement
    }

    public void draw(Graphics g) {
        for (Tile t : body) {
            g.setColor(race.getColor());
            t.drawRectangle(g);
        }
    }

    public boolean checkSelfCollision() {
        for (int i = 1; i < body.size(); i++) {
            if (getHead().equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWallCollision() {
        return !getHead().isInsideGame();
    }

}
