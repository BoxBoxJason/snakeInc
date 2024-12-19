package org.snakeinc.server.repository;

import org.snakeinc.server.dto.SnakeStats;
import org.snakeinc.server.model.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {

    // Derived query method to find scores by snake name
    List<ScoreEntity> findBySnake(String snake);

    // Custom query for aggregated statistics
    @Query("SELECT new org.snakeinc.server.dto.SnakeStats(s.snake, MIN(s.score), MAX(s.score), AVG(s.score)) " +
           "FROM ScoreEntity s " +
           "GROUP BY s.snake")
    List<SnakeStats> findSnakeStatistics();
}
