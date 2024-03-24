package edu.uga.cs.project4countryquiz;

import android.annotation.SuppressLint;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
public class GenerateQuestion {
    String a1;
    String a2;
    String a3;

    String q;
    String cA;
    int score;
    LocalTime lt;
    LocalDate ld;

    public GenerateQuestion(){

    }
    public GenerateQuestion(String question, String[] possibleAnswers, String correctA){
            shuffleArray(possibleAnswers);
            this.q = question;
            this.a1 = possibleAnswers[0];
            this.a2 = possibleAnswers[1];
            this.a3 = possibleAnswers[2];
            this.cA = correctA;
    }

    //generate Score page
    public GenerateQuestion(int score, LocalTime lt, LocalDate ld)
    {
        this.score=score;
        this.lt=lt;
        this.ld=ld;
    }
    @Override
    public String toString() {
        return "Country{" +
                "name='" + q + '\'' +
                ", continent='" + cA + '\'' +
                '}';
    }
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
