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

        System.out.println("Please enter category:");
        String category = null;

        try {
            category = bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println("Error while reading input");
        }

        QuestionCategory questionCategory = CategoryChooser.chooseCategory(category);

        if (questionCategory == null) {
            System.out.println("Category not found. Try again.");
        } else {

        System.out.println("<< QUESTIONS LIST >>\n");

            var properties = new Properties();
            int counter = 1;

            try (var reader = new FileReader(questionCategory.getClass().getName().toLowerCase()
                    .substring(33) + "Questions")) {
                properties.load(reader);
                index = Integer.valueOf(properties.getProperty("index"));
            } catch (IOException e) {
                System.err.println("Error while reading file");
                e.printStackTrace();
            }

            Set<Map.Entry<Object, Object>> questions = properties.entrySet();

            for (Map.Entry<Object, Object> object : questions) {
                String key = (String) object.getKey();
                String value = (String) object.getValue();
                if (!(key.equals("index")) && (Integer.valueOf(key) % SINGLE_QUESTION_VOLUME == 1)) {
                    System.out.println(counter + ": " + value);
                    System.out.println("1) " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 1)));
                    System.out.println("2) " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 2)));
                    System.out.println("3) " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 3)));
                    System.out.println("ANSWER: " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 4)));
                    System.out.println("");
                    counter++;
                }
            }
            System.out.println("Number of questions: " + index / SINGLE_QUESTION_VOLUME + "\n");
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

        try {

        System.out.println("Enter category:");
        String category = bufferedReader.readLine();

        QuestionCategory questionCategory = CategoryChooser.chooseCategory(category);

        if (questionCategory == null) System.out.println("Category not found. Try again.");

        else {

            System.out.println("Enter Question:");
            String quest = bufferedReader.readLine();
            System.out.println("Enter option1:");
            String option1 = bufferedReader.readLine();
            System.out.println("Enter option2:");
            String option2 = bufferedReader.readLine();
            System.out.println("Enter option3:");
            String option3 = bufferedReader.readLine();

            String pointer;

            do {
                System.out.println("Enter number of option which is an answer:");
                pointer = bufferedReader.readLine();
            }
            while (!(pointer.equals("1")) && !(pointer.equals("2")) && !(pointer.equals("3")));

            Question question = QuestionGenerator.createQuestion(quest, option1, option2, option3, Integer.valueOf(pointer));

            var properties = new Properties();

            try (var reader = new FileReader(questionCategory.getClass().getName().toLowerCase().substring(33) + "Questions")) {
                properties.load(reader);
                index = Integer.valueOf(properties.getProperty("index"));
            }
            catch (IOException e) {
                System.err.println("Error while reading file");
            }

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
        }
        catch (IOException e) {
            System.err.println("Unknown error");
        }
    }

    private void showQuestionRemovalList(QuestionCategory questionCategory) {

        var properties = new Properties();

        try (var reader = new FileReader(questionCategory.getClass().getName().toLowerCase().substring(33) + "Questions")) {
            properties.load(reader);
            index = Integer.valueOf(properties.getProperty("index"));
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        Set<Map.Entry<Object, Object>> questions = properties.entrySet();

        for (Map.Entry<Object, Object> object : questions) {
            String key = (String) object.getKey();
            String value = (String) object.getValue();
            if (!(key.equals("index")) && (Integer.valueOf(key) % SINGLE_QUESTION_VOLUME == 1)) {
                System.out.println(key + ": " + value);
            }
        }
    }

    public void removeQuestionFromFile(BufferedReader bufferedReader) {

        System.out.println("<< REMOVING QUESTION >>\n");

        System.out.println("Enter category:");
        String category = null;

        try {
            category = bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println("Error while reading input");
        }

        QuestionCategory questionCategory = CategoryChooser.chooseCategory(category);

        if (questionCategory == null) {
            System.out.println("Category not found. Try again.");
        } else {

            var properties = new Properties();

            try (var reader = new FileReader(questionCategory.getClass().getName().toLowerCase().substring(33) + "Questions")) {
                properties.load(reader);
                index = Integer.valueOf(properties.getProperty("index"));
            } catch (IOException e) {
                System.err.println("Error while reading file");
            }

            showQuestionRemovalList(questionCategory);

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

            if (number == 0) {
                System.out.println("Incorrect input. Try again.");
            } else {

                for (int i = number; i < number + SINGLE_QUESTION_VOLUME; i++) {
                    properties.remove(String.valueOf(i));
                }

                Set<Map.Entry<Object, Object>> questions = properties.entrySet();

                for (Map.Entry<Object, Object> object2 : questions) {
                    String key = (String) object2.getKey();
                    String value = (String) object2.getValue();

                    if (!(key.equals("index")) && Integer.valueOf(key) > number) {
                        properties.setProperty(String.valueOf(Integer.valueOf(key) - SINGLE_QUESTION_VOLUME), value);
                        properties.remove(key);
                    }
                }

                properties.setProperty("index", String.valueOf(index - SINGLE_QUESTION_VOLUME));

                try (var writer = new FileWriter(questionCategory.getClass().getName().toLowerCase().substring(33) + "Questions")) {
                    properties.store(writer, questionCategory.getClass().getName());
                } catch (IOException e) {
                    System.err.println("Error while removing question");
                }

                System.out.println("Question removed.");
            }
        }
    }
}
