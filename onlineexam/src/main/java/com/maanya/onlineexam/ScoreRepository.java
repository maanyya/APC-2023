package com.maanya.onlineexam;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    
    List<Score> findAllByOrderByScoreDescExamDateAsc();
    
    List<Score> findByUsernameOrderByExamDateDesc(String username);
}