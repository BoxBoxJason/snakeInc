package org.snakeinc.server.repository;

import org.snakeinc.server.model.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {
    List<ScoreEntity> findBySnake(String snake);
}
