package edu.uga.cs.project4countryquiz;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GenerateQuiz {

    String q;
    String a1;
    String a2;
    String a3;
    String ccc;
    String cA;

    public GenerateQuiz() {

    }
    public GenerateQuiz(GenerateQuestion[] q_ca, List<String[]> answerChoices)
    {
        for(int i = 0; i < q_ca.length; i++){
            String[] answer = new String[3];
            for(int j= 0; i < 2; j++){
                answer[j] = answer[j];
            }
            answer[2] = q_ca[i].ccc;
            shuffleArray(answer);
            this.q = q_ca[i].q;
            this.a1 = answer[0];
            this.a2 = answer[1];
            this.a3 = answer[2];
            this.ccc = q_ca[i].ccc;
            Log.d("FILLED: ", "QUESTION: "+q);
        }
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
