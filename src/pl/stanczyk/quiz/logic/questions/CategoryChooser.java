package pl.stanczyk.quiz.logic.questions;

public class CategoryChooser {

    public static QuestionCategory chooseCategory(String choice) {

        QuestionCategory questionCategory = null;

        if (choice.equals("1") || choice.equalsIgnoreCase("music")) {
            questionCategory = new Music();
        } else if (choice.equals("2") || choice.equalsIgnoreCase("science")) {
            questionCategory = new Science();
        } else if (choice.equals("3") || choice.equalsIgnoreCase("movies")) {
            questionCategory = new Movies();
        }
        return questionCategory;
    }
}
