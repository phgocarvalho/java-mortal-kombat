package com.codechallenge.mortalkombat.repository;

import com.codechallenge.mortalkombat.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository repository;

    private Player entity;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        Player player = new Player("Sub zero", "expert");

        entity = repository.save(player);
    }

    @Test
    public void save() {
        Player player = repository.findById(entity.getId()).get();

        assertNotNull(player.getId());
        assertEquals("Sub zero", player.getName());
        assertEquals("expert", player.getType());
    }

    @Test
    public void findAll() {
        assertEquals(1, repository.count());
    }
}