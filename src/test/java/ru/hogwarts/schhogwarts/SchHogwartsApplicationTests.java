package ru.hogwarts.schhogwarts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.util.Assert;
import ru.hogwarts.schhogwarts.controllers.StudentController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SchHogwartsApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    void contextLoads(){
        Assertions.assertNotEquals(studentController, null);
    }
}
