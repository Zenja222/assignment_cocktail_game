package com.ridango.game.repository;

import com.ridango.game.model.GameScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//Repository interface for acessing data from the database.
@Repository
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {

    @Query("SELECT MAX(gs.score) FROM GameScore gs")
    Integer findMaxScore();
}
