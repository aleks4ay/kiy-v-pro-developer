package org.aleks4ay.developer.tools;

public class DateTool {

    public static void main(String[] args) {
        System.out.println(getCorrectYearAsString("34"));
        System.out.println(getCorrectYearAsString("2034"));
        System.out.println(getCorrectYearAsString("-4"));
        System.out.println(getCorrectYearAsString("4"));
        System.out.println(getCorrectYearAsString("yrty34"));
        System.out.println(getCorrectYearAsString("2020"));
    }

    public static String getCorrectYearAsString(String yearString) {
        int year;
        try {
            year = Integer.parseInt(yearString);
            if (year < 20) {
                return "";
            }
        } catch (NumberFormatException ne) {
            return "";
        }
        return year < 100 ? "20" + year : String.valueOf(year);
    }
}
