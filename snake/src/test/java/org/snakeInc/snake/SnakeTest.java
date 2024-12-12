package org.snakeInc.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.snakeinc.snake.model.Food;
import org.snakeinc.snake.model.Snake;

public class SnakeTest {

    @Test
    public void snakeEatApples_ReturnsCorrectBodySize() {
        Snake snake = new Snake();
        Food food = new Food();
        snake.eat(food);
        Assertions.assertEquals(2, snake.getBody().size());

    }

    @Test void snakeMovesUp_ReturnCorrectHead() {
        Snake snake = new Snake();
        snake.move('U');
        Assertions.assertEquals(5, snake.getHead().getX());
        Assertions.assertEquals(5, snake.getHead().getY());
    }

}
