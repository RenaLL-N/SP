package com.company;

import javax.imageio.IIOException;
import java.io.File;
import java.util.*;

class Pair {
    String first;
    String second;

    Pair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return first.equals(pair.first) &&
                second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}

class Main {
    private final int MAXLENGTH = 30;
    public HashSet<Pair> words = new HashSet<>();

    public void start(File file) throws Exception {
        ReadFile(file);
        PrintWords(GetMaxLen());
    }

    private void ReadFile(File file) throws Exception {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            for (var word:scanner.next().split("[^a-zA-z1-9]+") ) {
                word = (word.length() > MAXLENGTH) ? word.substring(0, MAXLENGTH) : word;
                words.add(new Pair(word, replaceDuplicatesWithTemplate(word)));
            }
        }
    }
    public String replaceDuplicatesWithTemplate(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return inputString;
        }
        int position = 1;
        char[] characters = inputString.toCharArray();
        for (int i = 1,j; i < inputString.length(); i++) {
            for (j = 0; j < position; ++j)
                if (characters[i] == characters[j])
                    break;

            if (j == position) {
                characters[position] = characters[i];
                ++position;
            } else {
                characters[position] = 0;
                ++position;
            }
        }
        return getStringWithoutDuplicates(characters);
    }

    public String getStringWithoutDuplicates(char[] inputChars) {
        StringBuilder stringBuilder = new StringBuilder(inputChars.length);
        for (char character : inputChars) {
            if (character != 0) {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }

    private int GetMaxLen() {
        int res = 0;
        for (Pair p : words)
            res = Math.max(p.second.length(), res);

        return res;
    }

    //виводить слово з максимальною довжиною в форматі "слово_оригінал(слово_без_повторів)
    private void PrintWords(int length) {
        for (Pair p : words)
            if (p.second.length() == length)
                System.out.println(p.first + "(" + p.second + ")");
    }

    public static void main(String[] args) throws IIOException, Exception {
        new Main().start(new File("input1.txt"));
    }
}