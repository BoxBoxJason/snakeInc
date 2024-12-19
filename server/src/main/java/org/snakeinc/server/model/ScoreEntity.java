package org.snakeinc.server.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "scores")
public class ScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String snake;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private LocalDate date = LocalDate.now();
}
