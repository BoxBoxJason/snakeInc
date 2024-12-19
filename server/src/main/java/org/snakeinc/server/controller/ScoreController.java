package org.snakeinc.server.controller;

import org.snakeinc.server.model.ScoreEntity;
import org.snakeinc.server.dto.SnakeStats;
import org.snakeinc.server.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    private static final List<String> VALID_SNAKES = List.of("python", "anaconda", "boaConstrictor");

    // POST /api/v1/score
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

    // GET /api/v1/scores
    @GetMapping("/scores")
    public ScoresResponse getScores(@RequestParam(value = "snake", required = false) String snake) {
        List<ScoreEntity> scores = scoreService.getScoresBySnake(snake);

        return new ScoresResponse(
            scores.stream().map(score -> new ScoreDTO(
                score.getSnake(),
                score.getScore(),
                score.getDate().format(DateTimeFormatter.ISO_DATE)
            )).collect(Collectors.toList())
        );
    }

    // GET /api/v1/scores/stats
    @GetMapping("/scores/stats")
    public StatsResponse getSnakeStatistics() {
        List<SnakeStats> stats = scoreService.getSnakeStatistics();
        return new StatsResponse(stats);
    }

    // Inner class for the POST request body
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

    // Inner class for the GET /api/v1/scores response
    public static class ScoresResponse {
        private List<ScoreDTO> scores;

        public ScoresResponse(List<ScoreDTO> scores) {
            this.scores = scores;
        }

        public List<ScoreDTO> getScores() {
            return scores;
        }

        public void setScores(List<ScoreDTO> scores) {
            this.scores = scores;
        }
    }

    public static class ScoreDTO {
        private String snake;
        private int score;
        private String date;

        public ScoreDTO(String snake, int score, String date) {
            this.snake = snake;
            this.score = score;
            this.date = date;
        }

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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    // Inner class for the GET /api/v1/scores/stats response
    public static class StatsResponse {
        private List<SnakeStats> stats;

        public StatsResponse(List<SnakeStats> stats) {
            this.stats = stats;
        }

        public List<SnakeStats> getStats() {
            return stats;
        }

        public void setStats(List<SnakeStats> stats) {
            this.stats = stats;
        }
    }
}
