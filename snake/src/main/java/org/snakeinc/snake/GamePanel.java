package org.snakeinc.snake;

import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.snakeinc.snake.model.Food;
import org.snakeinc.snake.model.Snake;

import lombok.Getter;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    public static final int TILE_SIZE = 20;
    public static final int N_TILES_X = 25;
    public static final int N_TILES_Y = 25;
    public static final int GAME_WIDTH = TILE_SIZE * N_TILES_X;
    public static final int GAME_HEIGHT = TILE_SIZE * N_TILES_Y;
    private Timer timer;
    // On n'aura jamais plus que 1 Panel de toute façon :)
    @Getter
    private static Snake snake;
    private Food food;
    private boolean running = false;
    private Snake.Direction direction = Snake.Direction.RIGHT;
    private int score = 0;
    @Getter
    private static Difficulty difficulty = Difficulty.MEDIUM;

    public static enum Difficulty {
        EASY,
        MEDIUM,
        HARD,
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        startGame();
    }

    private void startGame() {
        snake = new Snake();
        food = new Food();
        timer = new Timer(100, this);
        timer.start();
        running = true;
        score = 0;

        Random random = new Random();
        int difficultyIndex = random.nextInt(0, Difficulty.values().length);
        GamePanel.difficulty = Difficulty.values()[difficultyIndex];
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + score, (GAME_WIDTH - metrics.stringWidth("Score: " + score)) / 2, 20);

        if (running) {
            food.draw(g);
            snake.draw(g);
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_WIDTH - metrics.stringWidth("Game Over")) / 2, GAME_HEIGHT / 2);
        g.drawString("Press any key to restart", (GAME_WIDTH - metrics.stringWidth("Press any key to restart")) / 2,
                GAME_HEIGHT / 2 + 50);
    }

    private void checkCollision() {
        // Vérifie si le serpent se mord ou sort de l'écran
        if (snake.checkSelfCollision() || snake.checkWallCollision()) {
            running = false;
            timer.stop();
        }
        // Vérifie si le serpent mange la pomme
        if (snake.getHead().equals(food.getPosition())) {
            score += Math.max(snake.eat(food),0) * 5;
            checkZeroLength();
            food.updateLocation();
        }
    }

    private void checkZeroLength() {
        if (snake.getBody().size() == 0 || snake.getHead() == null) {
            running = false;
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move(direction);
            checkCollision();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (running) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != Snake.Direction.RIGHT) {
                        direction = Snake.Direction.LEFT;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Snake.Direction.LEFT) {
                        direction = Snake.Direction.RIGHT;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != Snake.Direction.DOWN) {
                        direction = Snake.Direction.UP;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != Snake.Direction.UP) {
                        direction = Snake.Direction.DOWN;
                    }
                    break;
            }
        } else {
            startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
