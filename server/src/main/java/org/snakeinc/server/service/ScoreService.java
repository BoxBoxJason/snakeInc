package org.snakeinc.server.service;

import org.snakeinc.server.dto.SnakeStats;
import org.snakeinc.server.model.ScoreEntity;
import org.snakeinc.server.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    // Save a new score
    public void saveScore(String snake, int score) {
        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setSnake(snake);
        scoreEntity.setScore(score);
        scoreRepository.save(scoreEntity);
    }

    // Fetch scores, filtered by snake if specified
    public List<ScoreEntity> getScoresBySnake(String snake) {
        if (snake == null || snake.isEmpty()) {
            return scoreRepository.findAll();
        }
        return scoreRepository.findBySnake(snake);
    }

    // Fetch aggregated statistics for each snake
    public List<SnakeStats> getSnakeStatistics() {
        // Use the standalone SnakeStats DTO from the dto package
        return scoreRepository.findSnakeStatistics();
    }
}
