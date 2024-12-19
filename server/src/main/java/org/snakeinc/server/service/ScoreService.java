package org.snakeinc.server.service;

import org.snakeinc.server.model.ScoreEntity;
import org.snakeinc.server.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public void saveScore(String snake, int score) {
        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setSnake(snake);
        scoreEntity.setScore(score);
        scoreRepository.save(scoreEntity);
    }
}
