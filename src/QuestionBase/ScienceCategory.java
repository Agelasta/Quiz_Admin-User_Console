package QuestionBase;

import Question.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScienceCategory implements Questions {

    public static List<Question> scienceQuestions = new ArrayList<>();
    private int number;
    private static int index;


    public void remove() {
        scienceQuestions.remove(number);

    }

    public void getQuestion(int index) {

        System.out.println(scienceQuestions.get(index).getQuestion());
        System.out.println("1) " + scienceQuestions.get(index).getOption1());
        System.out.println("2) " + scienceQuestions.get(index).getOption2());
        System.out.println("3) " + scienceQuestions.get(index).getOption3());

    }

    public void randomQuestion() {
        var random = new Random();
        number = random.nextInt(scienceQuestions.size());
        getQuestion(number);
    }

    public String getAnswer() {
        return scienceQuestions.get(number).getAnswer();
    }

    public void showQuestions() {

        var properties = new Properties();
        readIndex();

        System.out.println("Number of questions: " + index/5 + "\n");

        try ( var reader = new FileReader("scienceQuestions"))
        {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file");        }

        List<Integer> questionNumbers = Stream.iterate(1, x -> x + 5).limit(index/5).collect(Collectors.toList());

        Set<Map.Entry<Object, Object>> questions = properties.entrySet();

        for (Map.Entry<Object, Object> object : questions) {

            String key = (String) object.getKey();
            String value = (String) object.getValue();

            if (questionNumbers.contains(Integer.valueOf(key))) {
                System.out.println(key + ": " + value);
                System.out.println("1) " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 1)));
                System.out.println("2) " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 2)));
                System.out.println("3) " + properties.getProperty(String.valueOf(Integer.valueOf(key) + 3)));
                System.out.println("");
            }
        }
    }

    public void addFromFile() {
        var property = new Properties();

        int number = 1;

        try ( var reader = new FileReader("scienceQuestions"))
        {
            property.load(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        for (int i = 0; i < property.size() / 5; i++) {
            scienceQuestions.add(new Question.Builder()
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

        try ( var writer = new FileWriter("scienceQuestions", true))
        {
            property.store(writer, "Science");
        } catch (IOException e) {
            System.out.println("Question not saved");
        }

        System.out.println("Question saved");
    }


    public int readIndex() {

        try ( var bufferedReader = new BufferedReader(new FileReader("scienceIndex.txt")))
        {
            index = Integer.valueOf(bufferedReader.readLine());
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        return index;
    }

    public void saveIndex() {

        try (var writer = new FileWriter("scienceIndex.txt"))
        {
            writer.write(String.valueOf(index));
        } catch (IOException e) {
            System.err.println("Error - index not saved");
        }
    }

    public void showRemoveList() {

        var properties = new Properties();

        try ( var bufferedReader = new BufferedReader(new FileReader("scienceIndex.txt")))
        {
            index = Integer.valueOf(bufferedReader.readLine());
        } catch (IOException e) {
            System.out.println("Error while reading file");
        }

        List<Integer> questionNumbers = Stream.iterate(1, x -> x + 5).limit(index / 5).collect(Collectors.toList());

        try ( var reader = new FileReader("scienceQuestions"))
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

        try ( var reader = new FileReader("scienceQuestions"))
        {
            properties.load(reader);
        } catch (IOException e) {
            System.out.println("Error while reading file");
        }

        for (int i = number; i < number + 5; i++) {
            properties.remove(String.valueOf(i));
        }

        Set<Map.Entry<Object, Object>> questions = properties.entrySet();

        for (Map.Entry<Object, Object> object2 : questions) {
            String key = (String) object2.getKey();
            String value = (String) object2.getValue();

            if (Integer.valueOf(key) > number) {
                properties.setProperty(String.valueOf(Integer.valueOf(key) - 5), value);
                properties.remove(key);
            }
        }

        try ( var writer = new FileWriter("scienceQuestions"))
        {
            properties.store(writer, "Science");
        } catch (IOException e) {
            System.out.println("Error while removing question");
        }

        System.out.println("Question removed");

        changeIndexInFile();
    }

    public void changeIndexInFile() {

        try ( var bufferedReader = new BufferedReader(new FileReader("scienceIndex.txt")))

        {
            index = Integer.valueOf(bufferedReader.readLine());

        } catch (IOException e) {
            System.out.println("Error");
        }

        index -= 5;

        try ( var writer = new FileWriter("scienceIndex.txt"))
        {
            writer.write(String.valueOf(index));
        } catch (IOException e) {
            System.out.println("Error - index not changed");
        }
    }
}

