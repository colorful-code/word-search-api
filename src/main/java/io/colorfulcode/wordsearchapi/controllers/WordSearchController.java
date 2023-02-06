package io.colorfulcode.wordsearchapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.colorfulcode.wordsearchapi.domain.Grid;
import io.colorfulcode.wordsearchapi.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/wordgrid")
@RestController()
public class WordSearchController {

    @Autowired
    WordGridService wordGridService;

    @CrossOrigin(origins="*")
    @GetMapping
    public List<String> createWordGrid(@RequestParam int gridSize, @RequestParam List<String> words) throws JsonProcessingException {
        Grid grid = wordGridService.generateGrid(gridSize, words);
        String gridAsString = "";
        for(char[] row : grid.getContents()) {
            for(char c : row) {
                gridAsString += c + " ";
            }
        }
        return Arrays.asList(gridAsString, String.join(",", grid.getOmittedWords()));
    }
}
