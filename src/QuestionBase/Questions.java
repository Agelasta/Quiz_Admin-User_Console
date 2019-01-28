package QuestionBase;

import Question.*;

public interface Questions {


         void remove();
         void getQuestion(int index);
         String getAnswer();
         int readIndex();
         void saveIndex();
         void randomQuestion();
         void addFromFile();
         void saveQuestion(Question question);
         void showRemoveList();
         void removeQuestionFromFile(int number);
         void showQuestions();
         void changeIndexInFile();
}
