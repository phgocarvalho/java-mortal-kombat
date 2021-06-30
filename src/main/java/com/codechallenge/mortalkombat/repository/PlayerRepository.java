package com.codechallenge.mortalkombat.repository;

import com.codechallenge.mortalkombat.entity.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
}
