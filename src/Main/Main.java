package Main;

import Admin.Administrator;
import Question.Question;
import Question.QuestionGenerator;
import QuestionBase.MusicCategory;
import QuestionBase.Questions;
import QuestionBase.ScienceCategory;
import User.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        final String QUICK_PLAY = "1";
        final String PLAY = "2";
        final String REGISTER = "3";
        final String STATISTICS = "4";
        final String CHANGE_LOGIN = "5";
        final String CHANGE_PASSWORD = "6";
        final String REMOVE_ACCOUNT = "7";
        final String ADD_QUESTION = "8";
        final String REMOVE_QUESTION = "9";
        final String SHOW_QUESTIONS = "10";
        final String ADD_USER = "11";
        final String REMOVE_USER = "12";
        final String SHOW_USERS = "13";
        final String CHANGE_ADMIN_PASSWORD = "14";

        ScienceCategory scienceCategory = new ScienceCategory();
        MusicCategory musicCategory = new MusicCategory();
        CategoryChooser categoryChooser = new CategoryChooser();
        UsersList usersList = new UsersList();
        Administrator admin = new Administrator();
        UserManagement userManager = new UserManagement();
        User user = new User("kamil", "nowak");
        User user2 = new User();
        User user4 = new User();

        usersList.readRemovalFile();
        usersList.fetchUsers();
        admin.fetchPassword();

//---------------------------------------------------------------------------------------------------------------------

        Question m1 = new Question.Builder()
                .question("How many strings does a classical guitar have?")
                .option1("11")
                .option2("4")
                .option3("6")
                .pointer(3)
                .build();
        Question m2 = new Question.Builder()
                .question("What was the second name of Wolfgang Mozart?")
                .option1("Stefan")
                .option2("Amadeus")
                .option3("Peter")
                .pointer(2)
                .build();
        Question m3 = new Question.Builder()
                .question("How can you play hang?")
                .option1("with hands")
                .option2("with bow")
                .option3("with drumstick")
                .pointer(1)
                .build();
        Question m4 = new Question.Builder()
                .question("What is a note in the first line in G clef?")
                .option1("e")
                .option2("g")
                .option3("h")
                .pointer(1)
                .build();
        Question m5 = new Question.Builder()
                .question("Who recorded song \"Idioglossia\"?")
                .option1("Metallica")
                .option2("Queen")
                .option3("Pain of Salvation")
                .pointer(3)
                .build();

        /*musicCategory.saveQuestion(m1);
        musicCategory.saveQuestion(m2);
        musicCategory.saveQuestion(m3);
        musicCategory.saveQuestion(m4);
        musicCategory.saveQuestion(m5);*/

        /*Question m1 = new Question.Builder()
                .question("How long the light from the sun is running to the Earth?")
                .option1("8 minutes")
                .option2("74 days")
                .option3("half a year")
                .pointer(1)
                .build();
        Question m2 = new Question.Builder()
                .question("What was the first name of Mr Bell, the inventor of telephone?")
                .option1("Graham")
                .option2("Federico")
                .option3("Alexander")
                .pointer(3)
                .build();
        Question m3 = new Question.Builder()
                .question("In which year Pluto ceased to be a planet?")
                .option1("2002")
                .option2("2006")
                .option3("2010")
                .pointer(2)
                .build();
        Question m4 = new Question.Builder()
                .question("Which of following is not a name of a quark?")
                .option1("justice")
                .option2("beauty")
                .option3("true")
                .pointer(1)
                .build();
        Question m5 = new Question.Builder()
                .question("How many zeros does a centillion have?")
                .option1("100")
                .option2("600")
                .option3("10000")
                .pointer(2)
                .build();
*/

//---------------------------------------------------------------------------------------------------------------------

        //WELCOME

        String input = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("WELCOME TO OUR QUIZ!");
        /*try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("WE HOPE YOU WILL ENJOY PLAYING WITH US");

       /* try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        //START MENU

        do {
            do {

                System.out.print("\n--- PLAYER MENU ---");
                System.out.println("             --- ADMINISTRATOR MENU ---");

                System.out.print("1 - QUICK PLAY");
                System.out.println("                   8 - ADD QUESTION");
                System.out.print("2 - PLAY");
                System.out.println("                         9 - REMOVE QUESTION");
                System.out.print("3 - REGISTER");
                System.out.println("                    10 - SHOW QUESTIONS");
                System.out.print("4 - STATISTICS");
                System.out.println("                  11 - ADD USER");
                System.out.print("5 - CHANGE LOGIN");
                System.out.println("                12 - REMOVE USER");
                System.out.print("6 - CHANGE PASSWORD");
                System.out.println("             13 - SHOW USERS");
                System.out.print("7 - REMOVE ACCOUNT");
                System.out.println("              14 - CHANGE PASSWORD");


                System.out.println("\nEXIT - press 'e'");

                try {
                    input = bufferedReader.readLine();
                } catch (IOException e) {
                    System.err.println("Error");
                }
            }
            while (!(input.equals("1")) && !(input.equals("2")) && !(input.equals("3")) && !(input.equals("4"))
                    && !(input.equals("5")) && !(input.equals("6")) && !(input.equals("7")) && !(input.equals("8"))
                    && !(input.equals("9")) && !(input.equals("10")) && !(input.equals("11")) && !(input.equals("12"))
                    && !(input.equals("13")) && !(input.equals("14")) && !(input.equals("e")));

            //SWITCH --------------------------------------------------------------------------------


            switch (input) {

                case QUICK_PLAY:

                    Main.readQuestions();

                    break;

                case PLAY:

                    if (usersList.getSize() == 0) {
                        System.out.println("This option is available only for registered users. Please register first.");
                    } else {
                        System.out.println("Please enter your login:");
                        String login2 = bufferedReader.readLine();
                        System.out.println("Please enter your password:");
                        String password2 = bufferedReader.readLine();
                        User currentUser = userManager.logIn(login2, password2);

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            for (int i = 0; i < 2; i++) {

                                do {
                                    System.out.println("\nChoose question category:");
                                    System.out.println("1 - MUSIC");
                                    System.out.println("2 - SCIENCE");

                                    try {
                                        input = bufferedReader.readLine();
                                    } catch (IOException e) {
                                        System.err.println("Error");
                                    }
                                }
                                while (!(input.equals("1")) && !(input.equals("2")));

                                Questions questionCategory = categoryChooser.chooseCategory(input);


                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    System.err.println("Error");
                                }
                                System.out.println("Prepare to answering...\n");
                                System.out.println("You have 10 seconds to give an answer for each question.\n");
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    System.err.println("Error");
                                }

                                questionCategory.addFromFile();


                                for (int j = 0; j < 5; j++) {

                                    System.out.println("Question nr " + (j + 1) + "\n");
                                    questionCategory.randomQuestion();

                                    Counter counter = new Counter();
                                    counter.start();

                                    do {
                                        System.out.println("\nPlease enter 1, 2 or 3");

                                        try {
                                            input = bufferedReader.readLine();
                                        } catch (IOException e) {
                                            System.err.println("Error");
                                        }
                                    }
                                    while (!(input.equals("1")) && !(input.equals("2")) && !(input.equals("3")));

                                    if(counter.isAlive()) {

                                        counter.setStopCounter(true);

                                        if (input.equals(questionCategory.getAnswer())) {
                                            System.out.println("Congratulations! You have scored 1 point.\n");
                                            currentUser.setScore(currentUser.getScore() + 1);
                                        } else {
                                            System.out.println("Wrong answer!\n");
                                        }
                                    }
                                    else System.out.println("Too late... Time's up!\n");

                                    questionCategory.remove();
                                }

                                System.out.println("You scored in total " + currentUser.getScore() + " points.");
                            }
                            if (currentUser.getBestScore() < currentUser.getScore()) {
                                currentUser.setBestScore(currentUser.getScore());
                            }

                            if (currentUser.getBestEfficiency() < (currentUser.getScore() / 10.0) * 100) {
                                currentUser.setBestEfficiency((currentUser.getScore() / 10.0) * 100);
                            }

                            currentUser.setScore(0);

                        }
                    }

                    break;

                case REGISTER:

                    String login3;
                    String password3;

                    do {
                        System.out.println("Login must contain at least one letter " +
                                "and have at least 4 characters length.");
                        System.out.println("Please create login:");
                        login3 = bufferedReader.readLine();
                    } while (!(Pattern.matches(".*[a-zA-Z]+.*", login3)) || login3.length() < 4);

                    do {
                        System.out.println("Password must contain of at least one letter " +
                                "and have at least 4 characters length.");
                        System.out.println("Please create password:");
                        password3 = bufferedReader.readLine();
                    }
                    while (!(Pattern.matches(".*[a-zA-Z]+.*", password3)) || password3.length() < 4);

                    userManager.register(login3, password3);

                    break;

                case STATISTICS:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    } else {

                        System.out.println("Please enter your login:");
                        String login4 = bufferedReader.readLine();
                        System.out.println("Please enter your password:");
                        String password4 = bufferedReader.readLine();
                        User currentUser = userManager.logIn(login4, password4);

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            int bestScore = currentUser.getBestScore();
                            if (bestScore == 1) {
                                System.out.println("Your best score is " + bestScore + " point.");
                            } else {
                                System.out.println("Your best score is " + bestScore + " points.");
                            }
                            System.out.println("Your best efficiency is " + currentUser.getBestEfficiency() + " %.");

                        }
                    }
                    break;

                case CHANGE_LOGIN:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    } else {

                        System.out.println("Please enter your login:");
                        String login5 = bufferedReader.readLine();
                        System.out.println("Please enter your password:");
                        String password5 = bufferedReader.readLine();
                        User currentUser = userManager.logIn(login5, password5);

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            String newLogin = null;

                            do {
                                System.out.println("Login must contain of at least one letter " +
                                        "and have at least 4 characters length.");
                                System.out.println("Please enter new login:");
                                newLogin = bufferedReader.readLine();
                            }
                            while (!(Pattern.matches(".*[a-zA-Z]+.*", newLogin)) || newLogin.length() < 4);

                            userManager.changeLogin(login5, newLogin, password5, currentUser);
                        }
                    }
                    break;

                case CHANGE_PASSWORD:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    } else {

                        System.out.println("Please enter your login:");
                        String login6 = bufferedReader.readLine();
                        System.out.println("Please enter your password:");
                        String password6 = bufferedReader.readLine();
                        User currentUser = userManager.logIn(login6, password6);

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            String newPassword;

                            do {
                                System.out.println("Password must contain of at least one letter " +
                                        "and have at least 4 characters length.");
                                System.out.println("Please enter new password:");
                                newPassword = bufferedReader.readLine();
                            }
                            while (!(Pattern.matches(".*[a-zA-Z]+.*", newPassword)) || newPassword.length() < 4);

                            userManager.changePassword(newPassword, currentUser);
                        }
                    }
                    break;

                case REMOVE_ACCOUNT:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    } else {

                        System.out.println("Please enter your login:");
                        String login7 = bufferedReader.readLine();
                        System.out.println("Please enter your password:");
                        String password7 = bufferedReader.readLine();
                        User currentUser = userManager.logIn(login7, password7);

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            System.out.println("Are you sure to remove your account?");
                            System.out.println("Y - YES       N - NO");
                            String confirmation = bufferedReader.readLine();
                            if (confirmation.equalsIgnoreCase("y")) {
                                userManager.removeAccount(login7);
                            }
                        }
                    }
                    break;

                case ADD_QUESTION:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        String pointer;

                        System.out.println("Enter question:");
                        String question = bufferedReader.readLine();
                        System.out.println("Enter option1:");
                        String option1 = bufferedReader.readLine();
                        System.out.println("Enter option2:");
                        String option2 = bufferedReader.readLine();
                        System.out.println("Enter option3:");
                        String option3 = bufferedReader.readLine();

                        do {
                            System.out.println("Enter number of option which is an answer:");
                            pointer = bufferedReader.readLine();
                        }
                        while (!(pointer.equals("1")) && !(pointer.equals("2")) && !(pointer.equals("3")));

                        System.out.println("Enter category:");
                        String category = bufferedReader.readLine();

                        Question quest = QuestionGenerator.createQuestion(question, option1, option2, option3, Integer.valueOf(pointer));

                        Questions questionCategory = categoryChooser.chooseCategory(category);

                        if (questionCategory != null)
                            questionCategory.saveQuestion(quest);
                        else System.out.println("Category not found. Try again.");
                    }

                    break;

                case REMOVE_QUESTION:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        System.out.println("Enter category:");
                        String category = bufferedReader.readLine();
                        Questions questionCategory = categoryChooser.chooseCategory(category);

                        if (questionCategory != null) {
                            questionCategory.showRemoveList();
                            System.out.println("\nChoose question number:");
                            int number = Integer.valueOf(bufferedReader.readLine());
                            questionCategory.removeQuestionFromFile(number);
                        }
                        else System.out.println("Category not found. Try again.");

                    }


                    break;

                case SHOW_QUESTIONS:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        System.out.println("Please enter category:");
                        String category = bufferedReader.readLine();
                        Questions questionCategory = categoryChooser.chooseCategory(category);
                        if (questionCategory != null)
                            questionCategory.showQuestions();
                        else System.out.println("Category not found. Try again.");
                    }

                    break;

                case ADD_USER:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        System.out.println("Enter login:");
                        String login11 = bufferedReader.readLine();
                        System.out.println("Enter password:");
                        String password11 = bufferedReader.readLine();

                        admin.addUser(login11, password11);
                    }

                    break;

                case REMOVE_USER:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        System.out.println("Enter login:");
                        String login12 = bufferedReader.readLine();
                        admin.removeUser(login12);
                    }

                    break;

                case SHOW_USERS:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        admin.showUsers();

                    }
                    break;

                case CHANGE_ADMIN_PASSWORD:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        System.out.println("Please enter new password:");
                        String newPassword = bufferedReader.readLine();
                        admin.changePassword(newPassword);
                    }
                    break;
            }

        }
        while (!(input.equals("e")));

    }


    public static void readQuestions() {

        Properties properties;
        int score = 0;

        List<Integer> numberList = Stream.iterate(1, x -> x + 5).limit(5).collect(Collectors.toList());
        Collections.shuffle(numberList);
        Queue<Integer> numberQueue = new LinkedList<>(numberList);

        for (int i = 0; i < 5; i++) {

            int number = numberQueue.poll();

            properties = new Properties();

            if ((number) % 2 == 0) {

                try {
                    properties.load(new FileReader("musicQuestions"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    properties.load(new FileReader("scienceQuestions"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(properties.getProperty(String.valueOf(number++)));
            System.out.println("1) " + properties.getProperty(String.valueOf(number++)));
            System.out.println("2) " + properties.getProperty(String.valueOf(number++)));
            System.out.println("3) " + properties.getProperty(String.valueOf(number++)));

            String answer = properties.getProperty(String.valueOf(number));

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
    }
}


