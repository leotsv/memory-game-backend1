package com.memorygame.controller;

import com.memorygame.model.Score;
import com.memorygame.repository.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ScoreController {
    
    private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);
    
    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping("/")
    public String home() {
        logger.info("Accessing home endpoint");
        return "Memory Game Backend is running!";
    }

    @GetMapping("/api/scores")
    public List<Score> getTopScores() {
        logger.info("Fetching top scores");
        try {
            List<Score> scores = scoreRepository.findTop10ByOrderByTurnsAscTimeInSecondsAsc();
            logger.info("Found {} top scores", scores.size());
            return scores;
        } catch (Exception e) {
            logger.error("Error fetching top scores", e);
            throw e;
        }
    }

    @PostMapping("/api/scores")
    public Score saveScore(@RequestBody Score score) {
        logger.info("Saving new score for player: {}", score.getPlayerName());
        try {
            Score savedScore = scoreRepository.save(score);
            logger.info("Successfully saved score with ID: {}", savedScore.getId());
            return savedScore;
        } catch (Exception e) {
            logger.error("Error saving score for player: {}", score.getPlayerName(), e);
            throw e;
        }
    }
} 