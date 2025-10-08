package com.maanya.onlineexam;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scores_seq")
    @SequenceGenerator(name = "scores_seq", sequenceName = "scores_seq", allocationSize = 1)
    private int id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private int score;
    
    @Column(name = "total_questions", nullable = false)
    private int totalQuestions;
    
    @Column(nullable = false)
    private double percentage;
    
    @Column(name = "exam_date", nullable = false)
    private LocalDateTime examDate;

    // Default constructor for JPA
    public Score() {
        this.examDate = LocalDateTime.now();
    }
    
    // Constructor for convenience
    public Score(String username, int score, int totalQuestions, double percentage) {
        this.username = username;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.examDate = LocalDateTime.now();
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    public LocalDateTime getExamDate() { return examDate; }
    public void setExamDate(LocalDateTime examDate) { this.examDate = examDate; }
}