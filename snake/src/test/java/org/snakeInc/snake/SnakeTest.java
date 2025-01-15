package org.snakeInc.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.snakeinc.snake.model.Food;
import org.snakeinc.snake.model.Snake;
import org.snakeinc.snake.model.Tile;

public class SnakeTest {

    @Test
    public void snakeEatApples_NoException() {
        Snake snake = new Snake();
        Food food = new Food();
        Assertions.assertDoesNotThrow(() -> snake.eat(food));
    }

    void testMoveDirection(Snake.Direction direction, int x, int y) {
        Snake snake = new Snake();
        Tile head = snake.getHead();
        int startX = head.getX();
        int startY = head.getY();
        snake.move(direction);
        Assertions.assertEquals(startX + x, snake.getHead().getX());
        Assertions.assertEquals(startY + y, snake.getHead().getY());
    }

    @Test
    void snakeMovesDown() {
        testMoveDirection(Snake.Direction.DOWN, 0, 1);
    }

    @Test
    void snakeMovesLeft() {
        testMoveDirection(Snake.Direction.LEFT, -1, 0);
    }

    @Test
    void snakeMovesUp() {
        testMoveDirection(Snake.Direction.UP, 0, -1);
    }

    @Test
    void snakeMovesRight() {
        testMoveDirection(Snake.Direction.RIGHT, 1, 0);
    }

}
