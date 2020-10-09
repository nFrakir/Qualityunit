package com.gmail.seliverstova.hanna;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main( String[] args ) {
        String fileName = "input.txt";
        readFile(fileName);
    }

    private static void readFile(String fileName) {
        TimelineList timelineList = new TimelineList();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            //ignore the first line
            String line = br.readLine();
            for (; (line = br.readLine()) != null;) {
                parseLine(line, timelineList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseLine(String line, TimelineList timelineList) {
        try {
            if (line.startsWith("C")) {
                timelineList.addTimeline(line);
            }
            if (line.startsWith("D")) {
                System.out.println(timelineList.makeQuery(line));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
