package com.gmail.seliverstova.hanna;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timeline {
    private final static String VALIDATION_PATTERN = "\\d{1,2}(\\.\\d{1,2}){0,1} "
            + "\\d{1,2}(\\.\\d{1,2}){0,1}(\\.\\d{1,1}){0,1} "
            + "(P|N)";

    private String serviceDetails;
    private Date date;
    private int time;

    public Timeline(String line, String date_format) throws IllegalArgumentException {
        String[] lines = line.split(" ");
        serviceDetails = lines[1] + " " + lines[2] + " " + lines[3];
        if (!serviceDetails.matches(VALIDATION_PATTERN)) {
            throw new IllegalArgumentException("Error with line  " + line);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(date_format);
        try {
            date = sdf.parse(lines[4]);
            time = Integer.parseInt(lines[5]);
        } catch (NumberFormatException | ParseException e) {
            throw new IllegalArgumentException("Error with line " + line);
        }
    }

    public int getTime() {
        return time;
    }

    public boolean isValid(String pattern, Date dateStart, Date dateEnd) {
        boolean isWithinDateRange = ((date.getTime() >= dateStart.getTime()) &&
                (date.getTime() <= dateEnd.getTime()));
        boolean isValidCategories = serviceDetails.matches(pattern);

        return (isWithinDateRange && isValidCategories);
    }
}
