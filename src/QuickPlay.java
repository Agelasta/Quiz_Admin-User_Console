import java.util.Scanner;

public class QuickPlay {

    public static void playQuick() {

        final int NUMBER_OF_QUESTIONS = 3;

        QuestionCategory questionCategory = new QuestionCategory();
        QuestionCategory music = new Music();
        QuestionCategory science = new Science();
        QuestionCategory movies = new Movies();

        questionCategory.getQuestionsList().clear();

        music.addQuestionsFromFile();
        science.addQuestionsFromFile();
        movies.addQuestionsFromFile();

        int score = 0;

        System.out.println("<< QUICK PLAYING >>\n");
        System.out.println("This is only a demo of our game with no time limit and no ability to save results.");
        System.out.println("Register and join our community to gain access to all available features!\n");

        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {

            questionCategory.randomQuestion();
            String answer = questionCategory.getAnswer();
            questionCategory.remove();

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

        questionCategory.getQuestionsList().clear();
    }
}
