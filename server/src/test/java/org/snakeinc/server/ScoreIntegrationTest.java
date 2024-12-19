package org.snakeinc.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
