package pl.stanczyk.quiz.logic.questions;

import pl.stanczyk.quiz.data.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class QuestionCategory {

    private static List<Question> questions = new ArrayList<>();
    private int number;
    private int index;
    private final int SINGLE_QUESTION_VOLUME = 5;

    public List<Question> getQuestionsList() {
        return questions;
    }

    public void remove() {
        questions.remove(number);
    }

    public void getQuestion(int index) {

        System.out.println(questions.get(index).getQuestion());
        System.out.println("1) " + questions.get(index).getOption1());
        System.out.println("2) " + questions.get(index).getOption2());
        System.out.println("3) " + questions.get(index).getOption3());
    }

    public void randomQuestion() {
        var random = new Random();
        number = random.nextInt(questions.size());
        getQuestion(number);
    }

    public String getAnswer() {
        return questions.get(number).getAnswer();
    }

    public void showQuestions(BufferedReader bufferedReader) {

        QuestionCategory questionCategory = enterCategory(bufferedReader);

        if (questionCategory == null) {
            System.out.println("Category not found. Try again.");
        } else {

            System.out.println("<< QUESTIONS LIST >>\n");

            var properties = new Properties();
            if((index = readIndex(properties, questionCategory)) == 1) {
                System.out.println("No questions in category.");
            }
            else {
                printQuestions(properties);
                System.out.println("Number of questions: " + index / SINGLE_QUESTION_VOLUME + "\n");
            }
        }
    }

    public void addQuestionsFromFile() {

        var property = new Properties();
        int number = 1;

        try (var reader = new FileReader(this.getClass().getName().toLowerCase().substring(33) + "Questions")) {
            property.load(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        for (int i = 0; i < property.size() / SINGLE_QUESTION_VOLUME; i++) {
            questions.add(new Question.Builder()
                    .question(property.getProperty(String.valueOf(number)))
                    .option1(property.getProperty(String.valueOf(++number)))
                    .option2(property.getProperty(String.valueOf(++number)))
                    .option3(property.getProperty(String.valueOf(++number)))
                    .pointer(Integer.valueOf(property.getProperty(String.valueOf(++number))))
                    .build());
            number++;
        }
    }

    public void saveQuestion(BufferedReader bufferedReader) {

        System.out.println("<< ADDING QUESTION >>\n");

        QuestionCategory questionCategory = enterCategory(bufferedReader);

        if (questionCategory == null) System.out.println("Category not found. Try again.");
        else {
            Question question = takeQuestionFromUser(bufferedReader);
            storeQuestion(questionCategory, question);
        }
    }

    private void showQuestionRemovalList(QuestionCategory questionCategory) {

        var properties = new Properties();
        index = readIndex(properties, questionCategory);

        if((index = readIndex(properties, questionCategory)) == 1) {
            System.out.println("No questions in category.");
        }
        else {
            for (int i = 1; i < index; i = i + SINGLE_QUESTION_VOLUME) {
                System.out.println(i + ": " + properties.getProperty(String.valueOf(i)));
            }
        }
    }

    public void removeQuestion(BufferedReader bufferedReader) {

        System.out.println("<< REMOVING QUESTION >>\n");

        QuestionCategory questionCategory = enterCategory(bufferedReader);

        if (questionCategory == null) {
            System.out.println("Category not found. Try again.");
        } else {

            var properties = new Properties();
            index = readIndex(properties, questionCategory);

            showQuestionRemovalList(questionCategory);

            if(index != 1) {
                number = takeQuestionNumberFromUser(bufferedReader);

                if (number == 0) {
                    System.out.println("Incorrect input. Try again.");
                } else {

                    removeQuestionFromFile(properties);
                    updateQuestionsNumbers(properties);
                    updateFile(properties, questionCategory);
                }
            }
        }
    }

    private void removeQuestionFromFile(Properties properties) {
        for (int i = number; i < number + SINGLE_QUESTION_VOLUME; i++) {
            properties.remove(String.valueOf(i));
        }
    }

    private void updateQuestionsNumbers(Properties properties) {
        for (int i = number + SINGLE_QUESTION_VOLUME; i < index; i++) {
            properties.setProperty(String.valueOf(i - SINGLE_QUESTION_VOLUME), properties.getProperty(String.valueOf(i)));
            properties.remove(String.valueOf(i));
        }
    }

    private void updateFile(Properties properties, QuestionCategory questionCategory) {

        properties.setProperty("index", String.valueOf(index - SINGLE_QUESTION_VOLUME));

        try (var writer = new FileWriter(questionCategory.getClass().getName().toLowerCase().substring(33) + "Questions")) {
            properties.store(writer, questionCategory.getClass().getName().substring(33));
        } catch (IOException e) {
            System.err.println("Error while removing question");
        }
        System.out.println("Question removed.");
    }

    private QuestionCategory enterCategory(BufferedReader bufferedReader) {

        System.out.println("Enter category:");
        String category = null;

        try {
            category = bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println("Error while reading input");
        }
        return CategoryChooser.chooseCategory(category);
    }

    private int readIndex(Properties properties, QuestionCategory questionCategory) {

        try (var reader = new FileReader(questionCategory.getClass().getName().toLowerCase()
                .substring(33) + "Questions")) {
            properties.load(reader);
            index = Integer.valueOf(properties.getProperty("index"));
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }
        return index;
    }

    private void printQuestions(Properties properties) {

        int counter = 1;

        for (int i = 1; i < index; i = i + SINGLE_QUESTION_VOLUME) {
            System.out.println(counter + ": " + properties.getProperty(String.valueOf(i)));
            System.out.println("1) " + properties.getProperty(String.valueOf(i + 1)));
            System.out.println("2) " + properties.getProperty(String.valueOf(i + 2)));
            System.out.println("3) " + properties.getProperty(String.valueOf(i + 3)));
            System.out.println("ANSWER: " + properties.getProperty(String.valueOf(i + 4)));
            System.out.println("");
            counter++;
        }
    }

    private Question takeQuestionFromUser(BufferedReader bufferedReader) {

        String quest = null;
        String option1 = null;
        String option2 = null;
        String option3 = null;
        String pointer = null;

        try {
            System.out.println("Enter question:");
            quest = bufferedReader.readLine();
            System.out.println("Enter option1:");
            option1 = bufferedReader.readLine();
            System.out.println("Enter option2:");
            option2 = bufferedReader.readLine();
            System.out.println("Enter option3:");
            option3 = bufferedReader.readLine();

            do {
                System.out.println("Enter number of option which is an answer:");
                pointer = bufferedReader.readLine();
            }
            while (!(pointer.equals("1")) && !(pointer.equals("2")) && !(pointer.equals("3")));
        } catch (IOException e) {
            System.err.println("Error while reading input");
        }
        return QuestionGenerator.createQuestion(quest, option1, option2, option3, Integer.valueOf(pointer));
    }

    private void storeQuestion(QuestionCategory questionCategory, Question question) {

        var properties = new Properties();
        index = readIndex(properties, questionCategory);

        properties.setProperty(String.valueOf(index), question.getQuestion());
        properties.setProperty(String.valueOf(++index), question.getOption1());
        properties.setProperty(String.valueOf(++index), question.getOption2());
        properties.setProperty(String.valueOf(++index), question.getOption3());
        properties.setProperty(String.valueOf(++index), String.valueOf(question.getPointer()));

        properties.setProperty("index", String.valueOf(++index));

        try (var writer = new FileWriter(questionCategory.getClass().getName().toLowerCase().substring(33) + "Questions")) {
            properties.store(writer, questionCategory.getClass().getName().toLowerCase().substring(33));
        } catch (IOException e) {
            System.err.println("Question not saved");
        }
        System.out.println("Question saved.");
    }

    private int takeQuestionNumberFromUser(BufferedReader bufferedReader) {

        try {
            do {
                System.out.println("\nChoose question number from the list above:");
                try {
                    number = Integer.valueOf(bufferedReader.readLine());
                } catch (IOException e) {
                    System.err.println("Error while reading input");
                }
            }
            while (!(number % 5 == 1) || number <= 0 || number >= index);
        } catch (NumberFormatException e) {
            number = 0;
        }
        return number;
    }
}
