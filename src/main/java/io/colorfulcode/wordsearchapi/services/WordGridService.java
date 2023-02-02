package io.colorfulcode.wordsearchapi.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridService {

    private class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private enum Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_INVERSE,
        VERTICAL_INVERSE,
        DIAGONAL_INVERSE

    }

    public char[][] generateGrid(int gridSize, List<String> words) {
        List<Coordinate> coordinates = new ArrayList<>();
        char[][] contents = new char[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                coordinates.add(new Coordinate(i, j));
                contents[i][j] = '_';
            }
        }

        for(String word: words) {
            Collections.shuffle(coordinates);
            for(Coordinate coordinate : coordinates) {
                int x = coordinate.x;
                int y = coordinate.y;
                Direction selectedDirection = getArbitraryValidDirection(contents, word, coordinate);
                if(selectedDirection != null) {
                    switch (selectedDirection) {
                        case HORIZONTAL -> {
                            for(char c : word.toCharArray()) {
                                contents[y][x++] = c;
                            }
                        }
                        case VERTICAL -> {
                            for(char c : word.toCharArray()) {
                                contents[y++][x] = c;
                            }
                        }
                        case DIAGONAL -> {
                            for(char c : word.toCharArray()) {
                                contents[y++][x++] = c;
                            }
                        }
                        case HORIZONTAL_INVERSE -> {
                            for(char c : word.toCharArray()) {
                                contents[y][x--] = c;
                            }
                        }
                        case VERTICAL_INVERSE -> {
                            for(char c : word.toCharArray()) {
                                contents[y--][x] = c;
                            }
                        }
                        case DIAGONAL_INVERSE -> {
                            for(char c : word.toCharArray()) {
                                contents[y--][x--] = c;
                            }
                        }
                    }
                    break;
                }
            }
        }
        fillWithRandomLetters(contents);
        return contents;
    }

    public void displayGrid(char[][] contents) {
        int gridSize = contents.length;
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                System.out.print(contents[i][j]);
            }
            System.out.println("");
        }
    }

    //Fills any field not taken by a word with a random uppercase letter.
    private void fillWithRandomLetters(char[][] contents) {
        int gridSize = contents.length;
        String uppercaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i=0; i < gridSize; i++) {
            for(int j=0; j < gridSize; j++) {
                if(contents[i][j] == '_') {
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, uppercaseAlphabet.length());
                    contents[i][j] = uppercaseAlphabet.charAt(randomIndex);
                }
            }
        }
    }

    // Gives one of the 6 possible directions for the given word with the given starting coordinate. Returns null if word
    // doesn't fit the grid boundaries in any of the directions.
    private Direction getArbitraryValidDirection(char[][] contents, String word, Coordinate coordinate) {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for(Direction direction : directions) {
            if(doesFit(contents, word, coordinate, direction)) {
                return direction;
            }
        }
        return null;
    }

    // Checks if the given word stays within the grid when starting in the given coordinate and moving in the given direction.
    private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction) {
        int gridSize = contents.length;
        int wordLength = word.length();
        switch (direction) {
            case HORIZONTAL -> {
                if(coordinate.x + wordLength > gridSize) return false;
                for(int i=0; i < wordLength; i++) {
                    char gridLetter = contents[coordinate.y][coordinate.x + i];
                    if(gridLetter != '_' && gridLetter != word.charAt(i) ) {
                        return false;
                    }
                }
            }
            case VERTICAL -> {
                if(coordinate.y + wordLength > gridSize) return false;
                for(int i=0; i < wordLength; i++) {
                    char gridLetter = contents[coordinate.y + i][coordinate.x];
                    if(gridLetter != '_' && gridLetter != word.charAt(i) ) {
                        return false;
                    }
                }
            }
            case DIAGONAL -> {
                if(coordinate.x + wordLength > gridSize || coordinate.y + wordLength > gridSize) return false;
                for(int i=0; i < wordLength; i++) {
                    char gridLetter = contents[coordinate.y + i][coordinate.x + i];
                    if(gridLetter != '_' && gridLetter != word.charAt(i) ) {
                        return false;
                    }
                }
            }
            case HORIZONTAL_INVERSE -> {
                if(coordinate.x < wordLength) return false;
                for(int i=0; i < wordLength; i++) {
                    char gridLetter = contents[coordinate.y][coordinate.x - i];
                    if(gridLetter != '_' && gridLetter != word.charAt(i) ) {
                        return false;
                    }
                }
            }
            case VERTICAL_INVERSE -> {
                if(coordinate.y < wordLength) return false;
                for(int i=0; i < wordLength; i++) {
                    char gridLetter = contents[coordinate.y - i][coordinate.x];
                    if(gridLetter != '_' && gridLetter != word.charAt(i) ) {
                        return false;
                    }
                }
            }
            case DIAGONAL_INVERSE -> {
                if(coordinate.x < wordLength || coordinate.y < wordLength) return false;
                for(int i=0; i < wordLength; i++) {
                    char gridLetter = contents[coordinate.y - i][coordinate.x - i];
                    if(gridLetter != '_' && gridLetter != word.charAt(i) ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
