package io.colorfulcode.wordsearchapi.domain;

import java.util.List;

public class Grid {
    List<String> words;
    char[][] contents;
    int size;
    List<String> omittedWords;

    public Grid( List<String> words, char[][] contents, int size, List<String> omittedWords) {
        this.words = words;
        this.contents = contents;
        this.size = size;
        this.omittedWords = omittedWords;
    }

    public char[][] getContents() {
        return contents;
    }

    public List<String> getOmittedWords() {
        return omittedWords;
    }
}
