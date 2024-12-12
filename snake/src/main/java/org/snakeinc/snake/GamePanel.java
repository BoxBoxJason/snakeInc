package org.snakeinc.snake;

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

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    public static final int TILE_SIZE = 20;
    public static final int N_TILES_X = 25;
    public static final int N_TILES_Y = 25;
    public static final int GAME_WIDTH = TILE_SIZE * N_TILES_X;
    public static final int GAME_HEIGHT = TILE_SIZE * N_TILES_Y;
    private Timer timer;
    private Snake snake;
    private Food food;
    private boolean running = false;
    private char direction = 'R';

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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
    }

    private void checkCollision() {
        // Vérifie si le serpent se mord ou sort de l'écran
        if (snake.checkSelfCollision() || snake.checkWallCollision()) {
            running = false;
            timer.stop();
        }
        // Vérifie si le serpent mange la pomme
        if (snake.getHead().equals(food.getPosition())) {
            snake.eat(food);
            food.updateLocation();
        }
    }

    private void checkZeroLength() {
        if (snake.getBody().size() == 0) {
            running = false;
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move(direction);
            checkCollision();
            checkZeroLength();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
