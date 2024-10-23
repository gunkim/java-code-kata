package io.github.gunkim.baseball.application;

import java.util.Scanner;

public class DefaultInputScanner implements InputScanner {
    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public String input() {
        return SCANNER.nextLine();
    }
}