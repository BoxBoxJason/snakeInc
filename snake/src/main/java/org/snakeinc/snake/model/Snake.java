package org.snakeinc.snake.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Snake {

    private final ArrayList<Tile> body;
    private Race race;

    private enum Race {
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

    public void eat(Food food) {
        body.add(food.getPosition());
    }

    public void move(char direction) {
        Tile newHead = getHead().copy();

        switch (direction) {
            case 'U':
                newHead.setY(newHead.getY() - 1);
                break;
            case 'D':
                newHead.setY(newHead.getY() + 1);
                break;
            case 'L':
                newHead.setX(newHead.getX() - 1);
                break;
            case 'R':
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
