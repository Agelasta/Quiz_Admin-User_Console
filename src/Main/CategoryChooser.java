package Main;

import QuestionBase.Questions;
import QuestionBase.MusicCategory;
import QuestionBase.ScienceCategory;

public class CategoryChooser {


    public Questions chooseCategory(String choice) {

        Questions questionCategory = null;

        if (choice.equals("1") || choice.equalsIgnoreCase("music")) {
            questionCategory = new MusicCategory();
        } else if (choice.equals("2") || choice.equalsIgnoreCase("science")) {
            questionCategory = new ScienceCategory();
        }

        return questionCategory;
    }

}
