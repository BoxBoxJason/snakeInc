package org.snakeinc.snake.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import org.snakeinc.snake.GamePanel;

public class Food {

    public enum FoodType {
        APPLE(Color.RED),
        BANANA(Color.YELLOW),
        BROCOLI(Color.GREEN);

        private final Color color;

        FoodType(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public static FoodType getRandomFoodType() {
            List<FoodType> foodTypes = Arrays.asList(values());
            Random random = new Random();
            return foodTypes.get(random.nextInt(foodTypes.size()));
        }
    }

    @Getter
    private Tile position;
    @Getter
    private FoodType type;
    private final Random random;

    public Food() {
        this.type = FoodType.getRandomFoodType();
        random = new Random();
        updateLocation();
    }

    public void updateLocation() {
        type = FoodType.getRandomFoodType();
        position = new Tile(random.nextInt(0, (GamePanel.GAME_WIDTH / GamePanel.TILE_SIZE) - 1),
                random.nextInt(0, (GamePanel.GAME_HEIGHT / GamePanel.TILE_SIZE) - 1));
    }

    public void draw(Graphics g) {
        g.setColor(type.getColor());
        position.drawOval(g);
    }

}
