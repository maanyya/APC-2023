package com.maanya.onlineexam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDAO {
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String user;
    
    @Value("${spring.datasource.password}")
    private String pass;

    public void addQuestionsManually() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            Statement checkStmt = conn.createStatement();
            ResultSet countRs = checkStmt.executeQuery("SELECT COUNT(*) as count FROM questions");
            countRs.next();
            int questionCount = countRs.getInt("count");
            
            if (questionCount >= 15) {
                System.out.println("Database has " + questionCount + " questions. Ready for exam!");
                return;
            }
            
            System.out.println("Database has " + questionCount + " questions. Adding more to reach 15...");
            
            String[][] additionalQuestions = {
                {"Which programming language is platform independent?", "C", "Java", "Python", "JavaScript", "B"},
                {"What does HTML stand for?", "Hyper Text Markup Language", "High Tech Modern Language", "Home Tool Markup Language", "Hyperlink and Text Markup Language", "A"}
            };
            
            String insertSQL = "INSERT INTO questions (id, text, optionA, optionB, optionC, optionD, correctAnswer) VALUES (questions_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            
            int questionsToAdd = Math.min(additionalQuestions.length, 15 - questionCount);
            
            for (int i = 0; i < questionsToAdd; i++) {
                String[] question = additionalQuestions[i];
                pstmt.setString(1, question[0]);
                pstmt.setString(2, question[1]);
                pstmt.setString(3, question[2]);
                pstmt.setString(4, question[3]);
                pstmt.setString(5, question[4]);
                pstmt.setString(6, question[5]);
                pstmt.executeUpdate();
            }
            
            System.out.println("Successfully added " + questionsToAdd + " questions!");
        } catch (Exception e) {
            System.out.println("Error manually adding questions: " + e.getMessage());
        }
    }

    public void startExam(Scanner sc, String username) {
        int score = 0;
        int totalQuestions = 0;
        final int MAX_QUESTIONS = 15;
        
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM (SELECT * FROM questions ORDER BY id) WHERE ROWNUM <= " + MAX_QUESTIONS);

            System.out.println("\n" + "=".repeat(50));
            System.out.println("           ONLINE EXAM STARTED");
            System.out.println("           Welcome " + username + "!");
            System.out.println("=".repeat(50));
            
            while (rs.next() && totalQuestions < MAX_QUESTIONS) {
                totalQuestions++;
                System.out.println("\nQuestion " + totalQuestions + "/" + MAX_QUESTIONS + ":");
                System.out.println("Q: " + rs.getString("text"));
                System.out.println("A) " + rs.getString("optionA"));
                System.out.println("B) " + rs.getString("optionB"));
                System.out.println("C) " + rs.getString("optionC"));
                System.out.println("D) " + rs.getString("optionD"));
                System.out.print("Your answer (A/B/C/D): ");
                String ans = sc.nextLine();

                if (ans.equalsIgnoreCase(rs.getString("correctAnswer"))) {
                    score++;
                    System.out.println("Correct!");
                } else {
                    System.out.println("Wrong! Correct answer was: " + rs.getString("correctAnswer"));
                }
            }
            
            double percentage = (score * 100.0) / totalQuestions;
            
            saveScore(conn, username, score, totalQuestions, percentage);
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           EXAM FINISHED!");
            System.out.println("=".repeat(50));
            System.out.println("Student: " + username);
            System.out.println("Your Score: " + score + " out of " + totalQuestions);
            System.out.println("Percentage: " + String.format("%.1f", percentage) + "%");
            
            if (score == totalQuestions) {
                System.out.println("EXCELLENT! Perfect Score!");
            } else if (score >= totalQuestions * 0.8) {
                System.out.println("GREAT JOB! Well done!");
            } else if (score >= totalQuestions * 0.6) {
                System.out.println("GOOD! Keep practicing!");
            } else {
                System.out.println("Need more practice. Try again!");
            }
            System.out.println("=".repeat(50));
            
        } catch (Exception e) {
            System.out.println("Error during exam: " + e.getMessage());
        }
    }
    
    private void saveScore(Connection conn, String username, int score, int totalQuestions, double percentage) {
        try {
            String insertScore = "INSERT INTO scores (id, username, score, total_questions, percentage) VALUES (scores_seq.NEXTVAL, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertScore);
            pstmt.setString(1, username);
            pstmt.setInt(2, score);
            pstmt.setInt(3, totalQuestions);
            pstmt.setDouble(4, percentage);
            pstmt.executeUpdate();
            System.out.println("Score saved to database!");
        } catch (Exception e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }
    
    public void showLeaderboard() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username, score, total_questions, percentage, exam_date FROM scores ORDER BY exam_date DESC");
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("                    LEADERBOARD");
            System.out.println("=".repeat(60));
            System.out.printf("%-15s %-8s %-8s %-12s %-12s%n", "Username", "Score", "Total", "Percentage", "Date");
            System.out.println("-".repeat(60));
            
            while (rs.next()) {
                System.out.printf("%-15s %-8d %-8d %-11.1f%% %-12s%n", 
                    rs.getString("username"),
                    rs.getInt("score"),
                    rs.getInt("total_questions"),
                    rs.getDouble("percentage"),
                    rs.getDate("exam_date").toString()
                );
            }
            System.out.println("=".repeat(60));
            
        } catch (Exception e) {
            System.out.println("Error displaying leaderboard: " + e.getMessage());
        }
    }
}
