package com.dave.demo.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EurekaApplicationTests {

    @Test
    void contextLoads() {
        int i = 6;
        i += i - 1;
        System.out.println(i);
    }
}
