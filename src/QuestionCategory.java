import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void showQuestions() {

        var properties = new Properties();
        int counter = 1;
        readIndex();

        try ( var reader = new FileReader(this.getClass().getName().toLowerCase() + "Questions"))
        {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        List<Integer> questionNumbers = Stream.iterate(1, x -> x + SINGLE_QUESTION_VOLUME)
                .limit(index / SINGLE_QUESTION_VOLUME).collect(Collectors.toList());

        Set<Map.Entry<Object, Object>> questions = properties.entrySet();

        for (Map.Entry<Object, Object> object : questions) {
            String key = (String) object.getKey();
            String value = (String) object.getValue();
            if (questionNumbers.contains(Integer.valueOf(key))) {
                System.out.println(counter + ": " + value);
                System.out.println("1) " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 1)));
                System.out.println("2) " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 2)));
                System.out.println("3) " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 3)));
                System.out.println("");
                counter++;
            }
        }
        System.out.println("Number of questions: " + index/5 + "\n");
    }

    public void addFromFile() {

        var property = new Properties();

        int number = 1;

        try ( var reader = new FileReader(this.getClass().getName().toLowerCase() + "Questions"))
        {
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

        var property = new Properties();

        property.setProperty(String.valueOf(readIndex()), question.getQuestion());
        property.setProperty(String.valueOf(++index), question.getOption1());
        property.setProperty(String.valueOf(++index), question.getOption2());
        property.setProperty(String.valueOf(++index), question.getOption3());
        property.setProperty(String.valueOf(++index), String.valueOf(question.getPointer()));

        index++;
        saveIndex();

        try ( var writer = new FileWriter(this.getClass().getName().toLowerCase() + "Questions", true))
        {
            property.store(writer, "Music");
        } catch (IOException e) {
            System.out.println("Question not saved");
        }

        System.out.println("Question saved.");
    }

    public int readIndex() {

        try ( var bufferedReader = new BufferedReader(new FileReader(this.getClass().getName().toLowerCase()
                + "Index")))
        {
            index = Integer.valueOf(bufferedReader.readLine());
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        return index;
    }

    public void saveIndex() {

        try (var writer = new FileWriter(this.getClass().getName().toLowerCase() + "Index"))
        {
            writer.write(String.valueOf(index));
        } catch (IOException e) {
            System.err.println("Error - index not saved");
        }
    }

    public void showQuestionRemovalList() {

        var properties = new Properties();

        try ( var bufferedReader = new BufferedReader(new FileReader(this.getClass().getName().toLowerCase() + "Index")))
        {
            index = Integer.valueOf(bufferedReader.readLine());
        } catch (IOException e) {
            System.out.println("Error while reading file");
        }

        List<Integer> questionNumbers = Stream.iterate(1, x -> x + SINGLE_QUESTION_VOLUME)
                .limit(index / SINGLE_QUESTION_VOLUME).collect(Collectors.toList());

        try ( var reader = new FileReader(this.getClass().getName().toLowerCase() + "Questions"))
        {
            properties.load(reader);
        } catch (IOException e) {
            System.out.println("Error while reading file");
        }

        Set<Map.Entry<Object, Object>> questions = properties.entrySet();

        for (Map.Entry<Object, Object> object : questions) {
            String key = (String) object.getKey();
            String value = (String) object.getValue();
            if (questionNumbers.contains(Integer.valueOf(key))) {
                System.out.println(key + ":" + value);
            }
        }
    }

    public void removeQuestionFromFile(int number) {

        var properties = new Properties();

        try ( var reader = new FileReader(this.getClass().getName().toLowerCase() + "Questions"))
        {
            properties.load(reader);
        } catch (IOException e) {
            System.out.println("Error while reading file");
        }

        for (int i = number; i < number + SINGLE_QUESTION_VOLUME; i++) {
            properties.remove(String.valueOf(i));
        }

        Set<Map.Entry<Object, Object>> questions = properties.entrySet();

        for (Map.Entry<Object, Object> object2 : questions) {
            String key = (String) object2.getKey();
            String value = (String) object2.getValue();

            if (Integer.valueOf(key) > number) {
                properties.setProperty(String.valueOf(Integer.valueOf(key) - SINGLE_QUESTION_VOLUME), value);
                properties.remove(key);
            }
        }

        try ( var writer = new FileWriter(this.getClass().getName().toLowerCase() + "Questions"))
        {
            properties.store(writer, "Music");
        } catch (IOException e) {
            System.out.println("Error while removing question");
        }

        System.out.println("Question removed.");

        changeIndexInFile();
    }

    public void changeIndexInFile() {

        try ( var bufferedReader = new BufferedReader(new FileReader(this.getClass().getName().toLowerCase() + "Index")))

        {
            index = Integer.valueOf(bufferedReader.readLine());

        } catch (IOException e) {
            System.out.println("Error");
        }

        index -= SINGLE_QUESTION_VOLUME;

        try ( var writer = new FileWriter(this.getClass().getName().toLowerCase() + "Index"))
        {
            writer.write(String.valueOf(index));
        } catch (IOException e) {
            System.out.println("Error - index not changed");
        }
    }
}
