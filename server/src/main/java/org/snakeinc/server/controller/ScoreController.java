package org.snakeinc.server.controller;

import org.snakeinc.server.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    private static final List<String> VALID_SNAKES = List.of("python", "anaconda", "boaConstrictor");

    @PostMapping("/score")
    public ResponseEntity<String> saveScore(@RequestBody ScoreRequest request) {
        if (!VALID_SNAKES.contains(request.getSnake())) {
            return ResponseEntity.badRequest().body("Invalid snake name.");
        }

        if (request.getScore() < 0) {
            return ResponseEntity.badRequest().body("Score cannot be negative.");
        }

        scoreService.saveScore(request.getSnake(), request.getScore());
        return ResponseEntity.status(HttpStatus.CREATED).body("Score saved successfully.");
    }

    public static class ScoreRequest {
        private String snake;
        private int score;

        // Getters and setters
        public String getSnake() {
            return snake;
        }

        public void setSnake(String snake) {
            this.snake = snake;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
