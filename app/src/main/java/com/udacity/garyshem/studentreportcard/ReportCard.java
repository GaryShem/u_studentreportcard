package com.udacity.garyshem.studentreportcard;


import java.util.ArrayList;
import java.util.Locale;

public class ReportCard {
    // Since we contain grades as percentage,
    // we can assign conversion via value thresholds
    // These 2 arrays allow us to tune thresholds and corresponding characters
    // By making them static we apparently only create them once,
    // instead of with every report card.
    private static int[] mGradeThresholds = {85, 70, 60, 50, 0};
    private static char[] mGradeCharacters = {'A', 'B', 'C', 'D', 'F'};

    private ArrayList<Grade> mGrades = new ArrayList<>();
    private int mYear;

    public ReportCard(int year) {
        // Any validation check required here?
        mYear = year;
    }

    /**
     * Getter for year
     */
    public int getYear() {
        return mYear;
    }

    /**
     * Grade setter for percentage-based systems
     */
    public void setGrade(String subjectName, int grade) {

        // I am not sure what's the correct way of checking for errors
        // I know the basics of the exception mechanism,
        // but not sure if it is appropriate in android
        // It'd be awesome to get some insights regarding this
        if (grade < 0 || grade > 100) {
            // error resolving code goes here
            // throwing an exception at least provides info that an error has occurred
            throw new RuntimeException("invalid grade, should be between 0 and 100");
        }

        for (int i = 0; i < mGrades.size(); i++) {
            Grade currentGrade = mGrades.get(i);
            if (subjectName.equals(currentGrade.subjectName)) {
                currentGrade.grade = grade;
                return;
            }
        }

        // We will get to this point if a desired subject doesn't exist
        // in the student's card yet
        // In this case we want to create a new grade record and add it to the card
        mGrades.add(new Grade(subjectName, grade));
    }

    /**
     * Grade getter for percentage-based systems
     */
    public int getGradePercentage(String subjectName) {
        for (int i = 0; i < mGrades.size(); i++) {
            Grade currentGrade = mGrades.get(i);
            if (subjectName.equals(currentGrade.subjectName)) {
                return currentGrade.grade;
            }
        }
        // If we don't find the required grade, return an error
        // Once again, I am not sure of the appropriate way to handle error here
        throw new RuntimeException("Requested subject isn't on the student card");
    }

    /**
     * Grade setter for character-based systems
     */
    public void setGrade(String subjectName, char gradeCharacter) {
        // This method actually assigns an integer value to the grade
        // based on the thresholds we've declared
        for (int i = 0; i < mGradeCharacters.length; i++) {
            if (gradeCharacter == mGradeCharacters[i]) {
                // Call the other setGrade method, providing required integer number
                setGrade(subjectName, mGradeThresholds[i]);
            }
        }
    }

    /**
     * Grade getter for percentage-based systems
     */
    public char getGradeCharacter(String subjectName) {
        // This method essentially gets the grade percentage
        // and then converts it to character
        // If a card does not have the grade yet, getGradePercentage will throw an error
        // so we don't need to check for errors separately here
        int gradePercentage = getGradePercentage(subjectName);
        return convertGradeToChar(gradePercentage);
    }

    /**
     * Method for converting integer grades into characters
     */
    private char convertGradeToChar(int grade) {

        for (int i = 0; i < mGradeThresholds.length; i++) {
            if (grade >= mGradeThresholds[i]) {
                return mGradeCharacters[i];
            }
        }
        // We should never get here if we've set thresholds properly
        throw new RuntimeException("Grading get/set mechanism is set up improperly");
    }

    /**
     * Method for printing grade percentages in readable form
     */
    public String getPercentageGradeString() {
        StringBuilder result = new StringBuilder();
        result.append(getYear() + System.getProperty("line.separator"));
        for (int i = 0; i < mGrades.size(); i++) {
            Grade currentGrade = mGrades.get(i);
            String subjectName = currentGrade.subjectName;
            int grade = currentGrade.grade;
            result.append(String.format(Locale.getDefault(), "%s: %d%s",
                    subjectName, grade, System.getProperty("line.separator")));
        }
        return result.toString();
    }

    /**
     * Method for printing grade characters in readable form
     */
    public String getCharacterGradeString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format(Locale.getDefault(), "%s: %d%s",
                "Year", getYear(), System.getProperty("line.separator")));
        for (int i = 0; i < mGrades.size(); i++) {
            Grade currentGrade = mGrades.get(i);
            String subjectName = currentGrade.subjectName;
            char grade = convertGradeToChar(currentGrade.grade);
            result.append(String.format(Locale.getDefault(), "%s: %c%s",
                    subjectName, grade, System.getProperty("line.separator")));
        }
        return result.toString();
    }

    /**
     * Returns grades in character form
     */
    @Override
    public String toString() {
        return getCharacterGradeString();
    }

    /**
     * Class for storing grades
     * <p/>
     * This is simply a storage, all validation checks and conversions
     * are done by the RecordCard class
     */
    private class Grade {
        private String subjectName;
        private int grade;

        // Constructor for number-based systems
        // Conversion to characters is done by ReportCard class
        public Grade(String subjectName, int grade) {
            this.subjectName = subjectName;
            this.grade = grade;
        }
    }
}
