package com.maanya.onlineexam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_seq")
    @SequenceGenerator(name = "questions_seq", sequenceName = "questions_seq", allocationSize = 1)
    private int id;
    
    @Column(name = "text", nullable = false, length = 500)
    private String text;
    
    @Column(name = "optiona", nullable = false)
    private String optionA;
    
    @Column(name = "optionb", nullable = false)
    private String optionB;
    
    @Column(name = "optionc", nullable = false)
    private String optionC;
    
    @Column(name = "optiond", nullable = false)
    private String optionD;
    
    @Column(name = "correctanswer", nullable = false, length = 10)
    private String correctAnswer;

    // Default constructor for JPA
    public Question() {}
    
    // Constructor for convenience
    public Question(String text, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.text = text;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getOptionA() { return optionA; }
    public void setOptionA(String optionA) { this.optionA = optionA; }
    public String getOptionB() { return optionB; }
    public void setOptionB(String optionB) { this.optionB = optionB; }
    public String getOptionC() { return optionC; }
    public void setOptionC(String optionC) { this.optionC = optionC; }
    public String getOptionD() { return optionD; }
    public void setOptionD(String optionD) { this.optionD = optionD; }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
}
