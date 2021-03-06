package pl.stanczyk.quiz.logic.play;

import pl.stanczyk.quiz.data.User;
import pl.stanczyk.quiz.logic.questions.CategoryChooser;
import pl.stanczyk.quiz.logic.questions.QuestionCategory;
import pl.stanczyk.quiz.logic.user.UsersList;

import java.io.BufferedReader;
import java.io.IOException;

public class Play {

    private final int CATEGORIES_IN_ROUND = 3;
    private final int QUESTIONS_IN_CATEGORY = 3;

    public void play(User user, BufferedReader bufferedReader, String input) {

        System.out.println("\n<< PLAYER ROUND >>");

        if (user == null) {
            System.out.println(" Try again.");
        } else {

            System.out.println("\nWelcome to the game!");
            System.out.println("You are to face questions in " + CATEGORIES_IN_ROUND + " categories.");
            System.out.println("You have 10 seconds to give an answer for each question.");
            System.out.println("Good luck!");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("Unknown error");
            }

            playRound(user, bufferedReader, input);

            setBestScore(user);
            setBestEfficiency(user);
            user.setScore(0);

            new UsersList().saveUsers();
        }
    }

    private void setBestScore(User user) {
        if (user.getBestScore() < user.getScore()) {
            user.setBestScore(user.getScore());
        }
    }

    private void setBestEfficiency(User user) {
        if (user.getBestEfficiency() < ((double) user.getScore() / (CATEGORIES_IN_ROUND *
                QUESTIONS_IN_CATEGORY)) * 100) {
            user.setBestEfficiency(((double) user.getScore() / (CATEGORIES_IN_ROUND *
                    QUESTIONS_IN_CATEGORY)) * 100);
        }
    }

    private void playRound(User user, BufferedReader bufferedReader, String input) {

        for (int i = 0; i < CATEGORIES_IN_ROUND; i++) {

            do {
                System.out.println("\nChoose question category:");
                System.out.println("1 - MUSIC");
                System.out.println("2 - SCIENCE");
                System.out.println("3 - MOVIES");

                try {
                    input = bufferedReader.readLine();
                } catch (IOException e) {
                    System.err.println("Error while reading input");
                }
            }
            while (!(input.equals("1")) && !(input.equals("2")) && !(input.equals("3")));

            QuestionCategory questionCategory = CategoryChooser.chooseCategory(input);
            questionCategory.getQuestionsList().clear();
            questionCategory.addQuestionsFromFile();

            try {
                Thread.sleep(500);
                System.out.println("Prepare to answering...\n");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.err.println("Unknown error");
            }

            askQuestions(user, bufferedReader, input, questionCategory);

            System.out.println("You scored in total " + user.getScore() + " points.");

            questionCategory.getQuestionsList().clear();
        }
    }


    private void askQuestions(User user, BufferedReader bufferedReader, String input, QuestionCategory questionCategory) {

        for (int j = 0; j < QUESTIONS_IN_CATEGORY; j++) {

            System.out.println("Question nr " + (j + 1) + "\n");
            questionCategory.randomQuestion();

            Counter counter = new Counter();
            counter.start();

            do {
                System.out.println("\nPlease enter 1, 2 or 3");

                try {
                    input = bufferedReader.readLine();
                } catch (IOException e) {
                    System.err.println("Error while reading input");
                }
            }
            while (!(input.equals("1")) && !(input.equals("2")) && !(input.equals("3")));

            counter.interrupt();

            if (counter.isAlive()) {

                if (input.equals(questionCategory.getAnswer())) {
                    System.out.println("Congratulations! You have scored 1 point.\n");
                    user.setScore(user.getScore() + 1);
                } else {
                    System.out.println("Wrong answer!\n");
                }
            }

            questionCategory.remove();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Unknown error");
            }
        }
    }
}
