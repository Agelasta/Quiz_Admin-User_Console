import java.util.Scanner;

public class QuickPlay {

    public static void playQuick() {

        final int NUMBER_OF_QUESTIONS = 3;

        QuestionCategory music = new Music();
        QuestionCategory science = new Science();
        QuestionCategory movies = new Movies();

        science.getQuestionsList().clear();

        music.addQuestionsFromFile();
        science.addQuestionsFromFile();
        movies.addQuestionsFromFile();

        int score = 0;

        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {

            science.randomQuestion();
            String answer = science.getAnswer();
            science.remove();

            String input;

            do {
                System.out.println("\nEnter 1, 2 or 3:");
                Scanner sc = new Scanner(System.in);
                input = sc.nextLine();
            }
            while (!(input.equals("1")) && !(input.equals("2")) && !(input.equals("3")));

            if (input.equals(answer)) {
                System.out.println("Congratulations! You have scored 1 point.\n");
                score++;
            } else {
                System.out.println("Wrong answer!\n");
            }
        }
        System.out.println("You scored in total " + score + " points.");

        science.getQuestionsList().clear();
    }
}
