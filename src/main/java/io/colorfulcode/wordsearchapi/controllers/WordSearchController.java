package io.colorfulcode.wordsearchapi.controllers;

import io.colorfulcode.wordsearchapi.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/wordgrid")
@RestController()
public class WordSearchController {

    @Autowired
    WordGridService wordGridService;

    @GetMapping
    public String createWordGrid(@RequestParam int gridSize, @RequestParam List<String> words) {
        char[][] grid = wordGridService.generateGrid(gridSize, words);
        String gridAsString = "";
        for(char[] row : grid) {
            for(char c : row) {
                gridAsString += c + " ";
            }
        }
        return gridAsString;
    }
}
