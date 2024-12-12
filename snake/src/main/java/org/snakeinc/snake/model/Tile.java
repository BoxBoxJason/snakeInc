package org.snakeinc.snake.model;

import java.awt.Graphics;
import java.util.Objects;
import java.util.Random;

import lombok.Getter;
import java.util.HashSet;
import org.snakeinc.snake.GamePanel;

@Getter
public class Tile {

    @Getter
    private int x;

    @Getter
    private int y;

    private int upperPixelX;
    private int upperPixelY;

    public static HashSet<String> existingTiles = new HashSet<String>();

    public Tile(int x, int y) {
        setX(x);
        setY(y);
        existingTiles.add(getKey(x, y));
    }

    private static String getKey(int x, int y) {
        return x + "," + y;
    }

    public static Tile getRandomTile() {
        Random random = new Random();
        int x = random.nextInt(0, (GamePanel.GAME_WIDTH / GamePanel.TILE_SIZE) - 1);
        int y = random.nextInt(0, (GamePanel.GAME_HEIGHT / GamePanel.TILE_SIZE) - 1);
        String randomKey = getKey(x, y);
        while (existingTiles.contains(randomKey)) {
            x = random.nextInt(0, (GamePanel.GAME_WIDTH / GamePanel.TILE_SIZE) - 1);
            y = random.nextInt(0, (GamePanel.GAME_HEIGHT / GamePanel.TILE_SIZE) - 1);
            randomKey = getKey(x, y);
        }
        return new Tile(x, y);
    }

    public Tile copy() {
        return new Tile(this.x, this.y);
    }

    public void setX(int X) {
        existingTiles.remove(getKey(x, y));
        this.x = X;
        upperPixelX = X * GamePanel.TILE_SIZE;
        existingTiles.add(getKey(x, y));
    }

    public void setY(int Y) {
        existingTiles.remove(getKey(x, y));
        this.y = Y;
        upperPixelY = Y * GamePanel.TILE_SIZE;
        existingTiles.add(getKey(x, y));
    }

    public void drawRectangle(Graphics g) {
        g.fillRect(upperPixelX, upperPixelY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }

    public void drawOval(Graphics g) {
        g.fillOval(upperPixelX, upperPixelY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }

    public boolean isInsideGame() {
        return (((x >= 0) && (x < GamePanel.N_TILES_X)) && ((y >= 0) && (y < GamePanel.N_TILES_Y)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tile tile = (Tile) o;
        return x == tile.x && y == tile.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
