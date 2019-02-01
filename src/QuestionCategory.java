import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public abstract class QuestionCategory {

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

    public void showQuestions() {

        var properties = new Properties();
        int counter = 1;

        try (var reader = new FileReader(this.getClass().getName().toLowerCase() + "Questions")) {
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

    public void addQuestionsFromFile() {

        var property = new Properties();
        int number = 1;

        try (var reader = new FileReader(this.getClass().getName().toLowerCase() + "Questions")) {
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

    public void saveQuestion(Question question) {

        var properties = new Properties();

        try (var reader = new FileReader(this.getClass().getName().toLowerCase() + "Questions")) {
            properties.load(reader);
            index = Integer.valueOf(properties.getProperty("index"));
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        properties.setProperty(String.valueOf(index), question.getQuestion());
        properties.setProperty(String.valueOf(++index), question.getOption1());
        properties.setProperty(String.valueOf(++index), question.getOption2());
        properties.setProperty(String.valueOf(++index), question.getOption3());
        properties.setProperty(String.valueOf(++index), String.valueOf(question.getPointer()));

        properties.setProperty("index", String.valueOf(++index));

        try (var writer = new FileWriter(this.getClass().getName().toLowerCase() + "Questions")) {
            properties.store(writer, this.getClass().getName().toLowerCase());
        } catch (IOException e) {
            System.out.println("Question not saved");
        }

        System.out.println("Question saved.");
    }

    public void showQuestionRemovalList() {

        var properties = new Properties();

        try (var reader = new FileReader(this.getClass().getName().toLowerCase() + "Questions")) {
            properties.load(reader);
            index = Integer.valueOf(properties.getProperty("index"));
        } catch (IOException e) {
            System.out.println("Error while reading file");
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

    public void removeQuestionFromFile(int number) {

        var properties = new Properties();

        try (var reader = new FileReader(this.getClass().getName().toLowerCase() + "Questions")) {
            properties.load(reader);
            index = Integer.valueOf(properties.getProperty("index"));
        } catch (IOException e) {
            System.out.println("Error while reading file");
        }

        if (number >= index || number <= 0) {
            System.out.println("Incorrect input. Try again.");
        }
        else {

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

            try (var writer = new FileWriter(this.getClass().getName().toLowerCase() + "Questions")) {
                properties.store(writer, "Music");
            } catch (IOException e) {
                System.out.println("Error while removing question");
            }

            System.out.println("Question removed.");
        }
    }
}
