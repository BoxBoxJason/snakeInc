package org.snakeinc.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScoreIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSaveScoreSuccess() {
        Map<String, Object> request = Map.of(
                "snake", "python",
                "score", 125
        );

        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/score", request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Score saved successfully.", response.getBody());
    }

    @Test
    public void testGetScoresWithoutFilter() {
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/scores", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Additional assertions based on expected data
    }

    @Test
    public void testGetScoresWithFilter() {
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/scores?snake=python", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Additional assertions based on expected data
    }

    @Test
    public void testGetSnakeStatistics() {
        // First, save some scores
        restTemplate.postForEntity("/api/v1/score", Map.of("snake", "python", "score", 150), String.class);
        restTemplate.postForEntity("/api/v1/score", Map.of("snake", "python", "score", 100), String.class);
        restTemplate.postForEntity("/api/v1/score", Map.of("snake", "anaconda", "score", 200), String.class);
        restTemplate.postForEntity("/api/v1/score", Map.of("snake", "anaconda", "score", 50), String.class);

        // Then, fetch statistics
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/scores/stats", Map.class);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().get("stats"));

        // Verify the structure and some content
        Map<String, Object> stats = (Map<String, Object>) response.getBody();
        assertNotNull(stats.get("stats"));

        // You can add detailed assertions based on the expected data
    }
}
