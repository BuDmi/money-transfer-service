package ru.netology.moneytransfer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferInfoApplicationTests {
    private final static int PORT = 5500;
    @Autowired
    TestRestTemplate restTemplate;

    private final static GenericContainer<?> testApp = new GenericContainer<>("app:latest")
        .withExposedPorts(PORT);

    @BeforeAll
    public static void setUp() {
        testApp.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> response =
            restTemplate.getForEntity("http://localhost:" + testApp.getMappedPort(PORT), String.class);
        System.out.println(response.getBody());
    }

    @Test
    void testSave() {
        String cardFromNumber = "1234567812345678";
        String cardFromValidTill = "12/25";
        String cardFromCVV = "123";
        String cardToNumber = "1111111111111111";
        int value = 100;
        String currency = "RUB";

        String jsonString = String.format("{\"cardFromNumber\": \"%s\", " +
                "\"cardFromValidTill\": \"%s\", " +
                "\"cardFromCVV\": \"%s\", " +
                "\"cardToNumber\":  \"%s\", " +
                "\"amount\": {\"value\": \"%d\", \"currency\": \"%s\" }}",
            cardFromNumber, cardFromValidTill, cardFromCVV, cardToNumber, value, currency);
        final String baseUrl = String.format("http://localhost:%d/transfer", testApp.getMappedPort(PORT));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, String.class);
        assertEquals(response.getStatusCode(), (HttpStatus.OK));
        String operationId = response.getBody();

        assertNotNull(operationId);
        assertNotEquals(operationId, "");
    }

    @Test
    void testConfirm() {
        String id = "12345678";
        String code = "0000";
        String jsonString = String.format("{\"operationId\": \"%s\", \"code\": \"%s\"}",
            id, code);
        final String baseUrl = String.format("http://localhost:%d/confirmOperation", testApp.getMappedPort(PORT));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, String.class);

        assertEquals((HttpStatus.OK), response.getStatusCode());
        String operationId = response.getBody();

        assertNotNull(operationId);
        assertNotEquals(operationId, "");
    }
}
