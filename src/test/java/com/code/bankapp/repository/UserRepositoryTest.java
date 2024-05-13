package com.code.bankapp.repository;

import com.code.bankapp.model.Role;
import com.code.bankapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstName("jafeth")
                .lastName("adet")
                .email("jafethadet2021@gmail.com")
                .password("password")
                .build();
        entityManager.persist(user);
    }

    @Test
    @DisplayName("Junit test for findByEmail method")
    void whenEmailFound_returnUser() {
        String email = "jafethadet2021@gmail.com";
        Optional<User> found = userRepository.findByEmail(email);
        assertEquals(found.get().getEmail(),email);
    }

}