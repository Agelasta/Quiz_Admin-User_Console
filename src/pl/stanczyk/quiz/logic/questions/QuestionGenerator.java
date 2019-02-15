package pl.stanczyk.quiz.logic.questions;

import pl.stanczyk.quiz.data.Question;

public class QuestionGenerator {

    public static Question createQuestion(String question, String option1, String option2, String option3, int pointer) {

        Question result = new Question();

        result.setQuestion(question);
        result.setOption1(option1);
        result.setOption2(option2);
        result.setOption3(option3);
        result.setPointer(pointer);

        return result;
    }
}


