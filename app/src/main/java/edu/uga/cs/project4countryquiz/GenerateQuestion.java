package edu.uga.cs.project4countryquiz;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
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
    int score;
    LocalTime lt;
    LocalDate ld;

    public GenerateQuestion(){

    }

    public GenerateQuestion(Country c) {
        this.q = c.name;
        this.cA = c.continent;
    }


    //generate Score page
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