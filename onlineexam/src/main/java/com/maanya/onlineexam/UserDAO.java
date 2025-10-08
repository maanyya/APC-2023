package com.maanya.onlineexam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String user;
    
    @Value("${spring.datasource.password}")
    private String pass;

    public boolean validateUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error validating user: " + e.getMessage());
            return false;
        }
    }
    
    public boolean userExists(String username) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE username=?"
            );
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error checking user existence: " + e.getMessage());
            return false;
        }
    }
    
    public boolean registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO users (id, username, password) VALUES (users_seq.NEXTVAL, ?, ?)"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }
}
