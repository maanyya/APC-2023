package com.maanya.onlineexam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class ExamController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, 
                          @RequestParam String password,
                          RedirectAttributes redirectAttributes) {
        if (userRepository.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("error", "Username already exists!");
            return "redirect:/";
        }
        
        User user = new User(username, password);
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        if (userRepository.findByUsernameAndPassword(username, password).isPresent()) {
            session.setAttribute("username", username);
            return "redirect:/exam";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials!");
            return "redirect:/";
        }
    }



    @GetMapping("/exam")
    public String exam(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/";
        }

        // Get exactly 15 fixed questions (same order every time, no shuffling, no repetition)
        List<Question> allQuestions = questionRepository.findAll();
        // Always use the first 15 questions in database order (no randomization)
        List<Question> examQuestions = allQuestions.size() >= 15 ? 
            allQuestions.subList(0, 15) : allQuestions;
        
        session.setAttribute("questions", examQuestions);
        session.setAttribute("currentQuestionIndex", 0);
        session.setAttribute("score", 0);
        session.setAttribute("answers", new ArrayList<String>());
        session.setAttribute("testStartTime", System.currentTimeMillis());

        model.addAttribute("question", examQuestions.get(0));
        model.addAttribute("questionNumber", 1);
        model.addAttribute("totalQuestions", examQuestions.size());
        
        return "exam";
    }

    @PostMapping("/submit-answer")
    public String submitAnswer(@RequestParam String answer,
                              HttpSession session,
                              Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/";
        }

        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");
        Integer score = (Integer) session.getAttribute("score");
        @SuppressWarnings("unchecked")
        List<String> answers = (List<String>) session.getAttribute("answers");

        if (questions == null || currentIndex == null) {
            return "redirect:/exam";
        }

        Question currentQuestion = questions.get(currentIndex);
        answers.add(answer);
        
        if (answer.equalsIgnoreCase(currentQuestion.getCorrectAnswer())) {
            score++;
            session.setAttribute("score", score);
        }

        currentIndex++;
        session.setAttribute("currentQuestionIndex", currentIndex);
        session.setAttribute("answers", answers);

        if (currentIndex >= questions.size()) {
            double percentage = (score * 100.0) / questions.size();
            Score examScore = new Score(username, score, questions.size(), percentage);
            scoreRepository.save(examScore);
            
            // Get leaderboard data for result page
            List<Score> leaderboardScores = scoreRepository.findAllByOrderByScoreDescExamDateAsc();
            
            model.addAttribute("username", username);
            model.addAttribute("score", score);
            model.addAttribute("totalQuestions", questions.size());
            model.addAttribute("percentage", percentage);
            model.addAttribute("percentageFormatted", String.format("%.1f", percentage));
            model.addAttribute("scores", leaderboardScores);
            
            session.removeAttribute("questions");
            session.removeAttribute("currentQuestionIndex");
            session.removeAttribute("score");
            session.removeAttribute("answers");
            session.removeAttribute("testStartTime");
            
            return "result";
        }
        
        model.addAttribute("question", questions.get(currentIndex));
        model.addAttribute("questionNumber", currentIndex + 1);
        model.addAttribute("totalQuestions", questions.size());
        
        return "exam";
    }

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        List<Score> scores = scoreRepository.findAllByOrderByScoreDescExamDateAsc();
        model.addAttribute("scores", scores);
        return "leaderboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}