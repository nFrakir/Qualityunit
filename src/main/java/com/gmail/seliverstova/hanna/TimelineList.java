package com.gmail.seliverstova.hanna;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimelineList {
    private final static String DATE_FORMAT = "dd.MM.yyyy";
    private List<Timeline> timelines = new ArrayList<>();

    public String makeQuery(String line) throws IllegalArgumentException {
        String[] lines = line.split(" ");
        String pattern = createPattern(lines);
        String report = "-";

        String[] dateInterval = lines[4].split("-");
        String end = (dateInterval.length > 1) ? dateInterval[1] : dateInterval[0];

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date dateStart = sdf.parse(dateInterval[0]);
            Date dateEnd = sdf.parse(end);
            report = calculate(pattern, dateStart, dateEnd);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error with line " + line);
        }
        return report;
    }

    public void addTimeline(String line) throws IllegalArgumentException {
        timelines.add(new Timeline(line, DATE_FORMAT));
    }

    private String calculate(String pattern, Date dateStart, Date dateEnd) {
        int time = 0;
        int cnt = 0;
        String report = "-";
        for (Timeline timeline : timelines) {
            if (timeline.isValid(pattern, dateStart, dateEnd)) {
                time += timeline.getTime();
                cnt += 1;
            }
        }
        if (cnt > 0) {
            report = Integer.toString(time/cnt);
        }
        return report;
    }

    private String createPattern(String[] lines) {
        String pattern = getFirstPatternNode(lines[1]) + " ";
        pattern += getSecondPatternNode(lines[2]) + " " + lines[3];
        return pattern;
    }

    private String getFirstPatternNode(String line) {
        String[] lines = line.split("\\.");
        StringBuilder sb = new StringBuilder();

        sb.append(line.startsWith("*") ? "\\d{1,2}" : lines[0]);
        sb.append((lines.length == 2) ? ("." + lines[1]) : "(\\.\\d{1,2}){0,1}");

        return sb.toString();
    }

    private String getSecondPatternNode(String line) {
        String[] lines = line.split("\\.");
        StringBuilder sb = new StringBuilder();

        sb.append(line.startsWith("*") ? "\\d{1,2}" : lines[0]);
        sb.append((lines.length > 1) ? ("." + lines[1]) : "(\\.\\d{1,2}){0,1}");
        sb.append((lines.length == 3) ? ("." + lines[2]) : "(\\.\\d{1,1}){0,1}");

        return sb.toString();
    }
}
