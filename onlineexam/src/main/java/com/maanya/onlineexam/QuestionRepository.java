package com.maanya.onlineexam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    
    // Use the inherited findAll() method and randomize in the service layer
}