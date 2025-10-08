package com.maanya.onlineexam;

import java.io.Console;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component 
public class TerminalController {
    private final UserDAO userDAO;
    private final QuestionDAO questionDAO;

    public TerminalController(UserDAO userDAO, QuestionDAO questionDAO) {
        this.userDAO = userDAO;
        this.questionDAO = questionDAO;
    }
    
    private String getPasswordInput(String prompt) {
        Console console = System.console();
        if (console != null) {
            // Use Console for hidden input
            char[] passwordChars = console.readPassword(prompt);
            return new String(passwordChars);
        } else {
            // Fallback for IDEs/environments without console
            System.out.print(prompt + " (Warning: Password will be visible): ");
            Scanner sc = new Scanner(System.in);
            return sc.nextLine().trim();
        }
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("        ONLINE EXAM SYSTEM");
            System.out.println("=".repeat(50));
            System.out.println("1. Register New User");
            System.out.println("2. Take Exam");
            System.out.println("3. View Leaderboard");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    registerUser(sc);
                    break;
                case "2":
                    takeExam(sc);
                    break;
                case "3":
                    questionDAO.showLeaderboard();
                    break;
                case "4":
                    System.out.println("Thank you for using Online Exam System!");
                    return;
                default:
                    System.out.println("Invalid choice! Please select 1, 2, 3, or 4.");
            }
        }
    }
    
    private void registerUser(Scanner sc) {
        System.out.println("\n=== REGISTER NEW USER ===");
        System.out.print("Enter new username: ");
        String username = sc.nextLine().trim();
        
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty!");
            return;
        }
        
       
        if (userDAO.userExists(username)) {
            System.out.println("Username '" + username + "' already exists! Please choose a different username.");
            return;
        }
        
        String password = getPasswordInput("Enter password: ");
        
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty!");
            return;
        }
        
        String confirmPassword = getPasswordInput("Confirm password: ");
        
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }
        
        if (userDAO.registerUser(username, password)) {
            System.out.println("User '" + username + "' registered successfully!");
            System.out.println("You can now login and take the exam.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }
    
    private void takeExam(Scanner sc) {
        System.out.println("\n=== LOGIN TO TAKE EXAM ===");
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();
        String password = getPasswordInput("Enter password: ");

        if (userDAO.validateUser(username, password)) {
            System.out.println(" Login successful! Welcome " + username + "!");
            
            questionDAO.addQuestionsManually();
            
            questionDAO.startExam(sc, username);
        } else {
            System.out.println(" Invalid login credentials!");
            System.out.println(" Hint: Make sure you've registered first, or check your username/password.");
        }
    }
}
