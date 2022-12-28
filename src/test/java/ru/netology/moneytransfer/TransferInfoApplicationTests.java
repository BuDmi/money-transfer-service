package ru.netology.moneytransfer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferInfoApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    private final static GenericContainer<?> devApp = new GenericContainer<>("devapp:latest")
        .withExposedPorts(8080);
    private final static GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest")
        .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> forEntityDevApp =
            restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080), String.class);
        System.out.println(forEntityDevApp.getBody());

        ResponseEntity<String> forEntityProdApp =
            restTemplate.getForEntity("http://localhost:" + prodApp.getMappedPort(8081), String.class);
        System.out.println(forEntityProdApp.getBody());
    }

    @Test
    void checkForDevApp() {
        String expected = "Current profile is dev";
        ResponseEntity<String> forEntity =
            restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080) + "/profile", String.class);

        String actual = forEntity.getBody();

        assertEquals(expected, actual);
    }

    @Test
    void checkForProdApp() {
        String expected = "Current profile is production";
        ResponseEntity<String> forEntity =
            restTemplate.getForEntity("http://localhost:" + prodApp.getMappedPort(8081) + "/profile", String.class);

        String actual = forEntity.getBody();

        assertEquals(expected, actual);
    }

}
