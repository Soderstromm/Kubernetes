package com.example.demo;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class ApplicationIntegrationTest {

    @Container
    private static final GenericContainer<?> devContainer = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    @Container
    private static final GenericContainer<?> prodContainer = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    private static final TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeAll
    public static void setUp() {
        devContainer.start();
        prodContainer.start();
    }

    @Test
    void devProfileReturnsOk() {
        int port = devContainer.getMappedPort(8080);
        String url = "http://localhost:" + port + "/authorize?user=admin&password=123";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(200, response.getStatusCodeValue());
        System.out.println("Dev profile response: " + response.getBody());
    }

    @Test
    void prodProfileReturnsOk() {
        int port = prodContainer.getMappedPort(8081);
        String url = "http://localhost:" + port + "/authorize?user=admin&password=123";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(200, response.getStatusCodeValue());
        System.out.println("Prod profile response: " + response.getBody());
    }
}
