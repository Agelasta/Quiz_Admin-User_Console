import java.util.Scanner;

public class QuickPlay {

    public static void playQuick() {

        QuestionCategory music = new Music();
        QuestionCategory science = new Science();
        QuestionCategory movies = new Movies();

        music.addFromFile();
        science.addFromFile();
        movies.addFromFile();

        int score = 0;

        for (int i = 0; i < 5; i++) {

            science.randomQuestion();
            String answer = science.getAnswer();
            science.remove();
            String userInput;

            do {
                System.out.println("\nEnter 1, 2 or 3:");
                Scanner sc = new Scanner(System.in);
                userInput = sc.nextLine();
            }
            while (!(userInput.equals("1")) && !(userInput.equals("2")) && !(userInput.equals("3")));

            if (userInput.equals(answer)) {
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
