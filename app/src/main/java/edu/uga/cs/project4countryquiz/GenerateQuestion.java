package edu.uga.cs.project4countryquiz;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

/**
 * Class to represent a question on the quiz
 */
public class GenerateQuestion {
    //section for second question creation
    String q2;
    String a1;
    String a2;
    String a3;
    String ccc;

    //Section for initial question creation
    String q;
    String cA;

    /**
     * Constructor
     * @param country the country being quizzed
     * @param randAnswers array of the two incorrect continent choices
     * @param correct the correct continent
     */
    public GenerateQuestion(String country,  String [] randAnswers, String correct)
    {
        String[] aaa = new String[3];
        aaa[0] = correct;
        aaa[1] = randAnswers[0];
        aaa[2] = randAnswers[1];
        shuffleArray(aaa);
        this.q2 = country;
        this.ccc = correct;
        this.a1 = aaa[0];
        this.a2 = aaa[1];
        this.a3 = aaa[2];
    }

    /**
     * Create a string representation of the country, mainly for debugging purposes
     * @return string representation of the country name and continent
     */
    @Override
    public String toString() {
        return "Country{" +
                "name='" + q + '\'' +
                ", continent='" + cA + '\'' +
                '}';
    }

    /**
     * Shuffles an array, in this case presents the correct answer always being the same answer choice
     * @param array array to shuffle
     */
    public static void shuffleArray(String[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Swap array[i] with array[index]
            String temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }
}