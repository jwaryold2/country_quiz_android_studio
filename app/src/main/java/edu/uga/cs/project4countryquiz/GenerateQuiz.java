package edu.uga.cs.project4countryquiz;

import android.annotation.SuppressLint;

import java.time.LocalDate;
import java.time.LocalTime;

public class GenerateQuiz {

    GenerateQuestion q1;
    GenerateQuestion q2;
    GenerateQuestion q3;
    GenerateQuestion q4;
    GenerateQuestion q5;
    GenerateQuestion q0;

    //answer holders
    int answered_question = 0;
    public GenerateQuiz() {

    }
    public GenerateQuiz(GenerateQuestion[] questions) {
        this.q0 = questions[0];
        this.q1 = questions[1];
        this.q2 = questions[2];

        //this.q3 = questions[3];
        //this.q4 = questions[4];
        //this.q5 = questions[5];
    }
    //new constructor for partially generated questions;
}
