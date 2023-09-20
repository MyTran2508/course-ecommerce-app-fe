package com.programming.userservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    // JUnit test for
    @Test
    public void given_when_then() {
        // given - preconditions or setup

        // when - action or behaviour that we are going test

        // then - verify the output
    }
}
