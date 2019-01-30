import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.io.IOException;

public class AppQuiz {

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static QuestionCategory questionCategory = new QuestionCategory();
    private static Administrator admin = new Administrator();
    private static UserManager userManager = new UserManager();
    private static UsersList usersList = new UsersList();
    private static UsersBackup usersBackup = new UsersBackup();
    private static User currentUser;
    private static String input;
    private static String login;
    private static String password;

    private static final String QUICK_PLAY = "1";
    private static final String PLAY = "2";
    private static final String REGISTER = "3";
    private static final String STATISTICS = "4";
    private static final String CHANGE_LOGIN = "5";
    private static final String CHANGE_PASSWORD = "6";
    private static final String REMOVE_ACCOUNT = "7";
    private static final String ADD_QUESTION = "8";
    private static final String REMOVE_QUESTION = "9";
    private static final String SHOW_QUESTIONS = "10";
    private static final String ADD_USER = "11";
    private static final String REMOVE_USER = "12";
    private static final String SHOW_USERS = "13";
    private static final String CHANGE_ADMIN_PASSWORD = "14";

    private static final int CATEGORIES_IN_ROUND = 3;
    private static final double QUESTIONS_IN_CATEGORY = 3;

    public static void main(String[] args) throws IOException {

        admin.fetchPassword();
        usersBackup.fetchUsers();
        questionCategory.getQuestionsList().clear();

        System.out.println("WELCOME TO OUR QUIZ!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("WE HOPE YOU WILL ENJOY PLAYING WITH US");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        do {
            do {

                System.out.print("\n---- PLAYER MENU ----");
                System.out.println("           --- ADMINISTRATOR MENU ---");
                System.out.println("");
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


            switch (input) {

                case QUICK_PLAY:

                    System.out.println("<< QUICK PLAYING >>\n");
                    System.out.println("This is only a demo of our game with no time limit and no score saving.");
                    System.out.println("Register and join our community to gain access to all available features!\n");

                    QuickPlay.playQuick();

                    break;

                case PLAY:

                    if (usersList.getSize() == 0) {
                        System.out.println("This option is available only for registered users. Please register first.");
                    } else {

                        System.out.println("<< PLAYER ROUND >>\n");

                        AppQuiz.validateUser();

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            System.out.println("\nWelcome to the game!");
                            System.out.println("You are to face questions in " + CATEGORIES_IN_ROUND + " categories.");
                            System.out.println("You have 10 seconds to give an answer for each question.");
                            System.out.println("Good luck!");

                            for (int i = 0; i < CATEGORIES_IN_ROUND; i++) {

                                do {
                                    System.out.println("\nChoose question category:");
                                    System.out.println("1 - MUSIC");
                                    System.out.println("2 - SCIENCE");
                                    System.out.println("3 - MOVIES");

                                    try {
                                        input = bufferedReader.readLine();
                                    } catch (IOException e) {
                                        System.err.println("Error");
                                    }
                                }
                                while (!(input.equals("1")) && !(input.equals("2")) && !(input.equals("3")));

                                questionCategory = CategoryChooser.chooseCategory(input);


                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    System.err.println("Error");
                                }
                                System.out.println("Prepare to answering...\n");
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    System.err.println("Error");
                                }

                                questionCategory.addFromFile();

                                for (int j = 0; j < QUESTIONS_IN_CATEGORY; j++) {

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

                                    if (counter.isAlive()) {

                                        counter.setStopCounter(true);

                                        if (input.equals(questionCategory.getAnswer())) {
                                            System.out.println("Congratulations! You have scored 1 point.\n");
                                            currentUser.setScore(currentUser.getScore() + 1);
                                        } else {
                                            System.out.println("Wrong answer!\n");
                                        }
                                    } else System.out.println("Too late... Time's up!\n");

                                    questionCategory.remove();
                                }

                                System.out.println("You scored in total " + currentUser.getScore() + " points.");

                                questionCategory.getQuestionsList().clear();
                            }

                            if (currentUser.getBestScore() < currentUser.getScore()) {
                                currentUser.setBestScore(currentUser.getScore());
                            }

                            if (currentUser.getBestEfficiency() < (currentUser.getScore() / (CATEGORIES_IN_ROUND *
                                    QUESTIONS_IN_CATEGORY)) * 100) {
                                currentUser.setBestEfficiency((currentUser.getScore() / (CATEGORIES_IN_ROUND *
                                        QUESTIONS_IN_CATEGORY)) * 100);
                            }

                            currentUser.setScore(0);
                            usersBackup.saveUser(currentUser);
                        }
                    }

                    break;

                case REGISTER:

                    System.out.println("<< REGISTERING >>\n");

                    do {
                        System.out.println("Login must contain at least 1 letter, not contain any whitespace " +
                                "and have at least 4 characters length.");
                        System.out.println("Please create login:");
                        login = bufferedReader.readLine();
                    }
                    while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", login)) || login.length() < 4);

                    do {
                        System.out.println("Password must contain at least 1 letter, not contain any whitespace " +
                                "and have at least 4 characters length.");
                        System.out.println("Please create password:");
                        password = bufferedReader.readLine();
                    }
                    while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", password)) || password.length() < 4);

                    userManager.register(login, password);

                    break;

                case STATISTICS:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    } else {

                        System.out.println("<< STATISTICS >>\n");

                        AppQuiz.validateUser();

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            int bestScore = currentUser.getBestScore();

                            if (bestScore == 1) {
                                System.out.println("\nYour best score is " + bestScore + " point.");
                            } else {
                                System.out.println("\nYour best score is " + bestScore + " points.");
                            }
                            System.out.printf("Your best efficiency is %.2f", currentUser.getBestEfficiency());
                            System.out.println(" %.");

                        }
                    }
                    break;

                case CHANGE_LOGIN:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    } else {

                        System.out.println("<< CHANGING LOGIN >>\n");

                        AppQuiz.validateUser();

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            String newLogin;

                            do {
                                System.out.println("Login must contain at least 1 letter, not contain any whitespace " +
                                        "and have at least 4 characters length.");
                                System.out.println("Please enter new login:");
                                newLogin = bufferedReader.readLine();
                            }
                            while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", newLogin)) || newLogin.length() < 4);

                            userManager.changeLogin(login, newLogin, currentUser);
                        }
                    }
                    break;

                case CHANGE_PASSWORD:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    } else {

                        System.out.println("<< CHANGING PASSWORD >>\n");

                        AppQuiz.validateUser();

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            String newPassword;

                            do {
                                System.out.println("Password must contain at least 1 letter, not contain any whitespace " +
                                        "and have at least 4 characters length.");
                                System.out.println("Please enter new password:");
                                newPassword = bufferedReader.readLine();
                            }
                            while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", newPassword)) || newPassword.length() < 4);

                            userManager.changePassword(newPassword, currentUser);
                        }
                    }
                    break;

                case REMOVE_ACCOUNT:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered.");
                    } else {

                        System.out.println("<< REMOVING ACCOUNT >>\n");

                        AppQuiz.validateUser();

                        if (currentUser == null) {
                            System.out.println(" Try again.");
                        } else {

                            System.out.println("Are you sure to remove your account?");
                            System.out.println("Y - YES       N - NO");
                            String confirmation = bufferedReader.readLine();
                            if (confirmation.equalsIgnoreCase("y")) {
                                userManager.removeAccount(login);
                            } else System.out.println("Your account remains active.");
                        }
                    }
                    break;

                case ADD_QUESTION:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        System.out.println("<< ADDING QUESTION >>\n");

                        String pointer;

                        System.out.println("Enter category:");
                        String category = bufferedReader.readLine();

                        questionCategory = CategoryChooser.chooseCategory(category);

                        if (questionCategory == null) System.out.println("Category not found. Try again.");

                        else {

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

                            Question quest = QuestionGenerator.createQuestion(question, option1, option2, option3, Integer.valueOf(pointer));

                            questionCategory.saveQuestion(quest);
                        }
                    }
                    break;

                case REMOVE_QUESTION:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        System.out.println("<< REMOVING QUESTION >>\n");

                        System.out.println("Enter category:");
                        String category = bufferedReader.readLine();
                        questionCategory = CategoryChooser.chooseCategory(category);

                        if (questionCategory != null) {

                            questionCategory.showQuestionRemovalList();
                            System.out.println("\nChoose question number:");
                            int number = Integer.valueOf(bufferedReader.readLine());
                            questionCategory.removeQuestionFromFile(number);
                        } else System.out.println("Category not found. Try again.");

                    }
                    break;

                case SHOW_QUESTIONS:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        System.out.println("<< QUESTIONS LIST >>\n");

                        System.out.println("Please enter category:");
                        String category = bufferedReader.readLine();
                        questionCategory = CategoryChooser.chooseCategory(category);

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

                        System.out.println("<< ADDING USER >>\n");

                        System.out.println("Enter login:");
                        login = bufferedReader.readLine();
                        System.out.println("Enter password:");
                        password = bufferedReader.readLine();

                        admin.addUser(login, password);
                    }
                    break;

                case REMOVE_USER:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        if (usersList.getSize() == 0)
                            System.out.println("There is no user registered.");
                        else {

                            System.out.println("<< REMOVING USER >>\n");

                            System.out.println("Enter login:");
                            login = bufferedReader.readLine();
                            admin.removeUser(login);
                        }
                    }
                    break;

                case SHOW_USERS:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        if (usersList.getSize() == 0) {
                            System.out.println("There is no user registered.");
                        } else {

                            System.out.println("<< USERS LIST >>\n");
                            admin.showUsers();
                        }
                    }
                    break;

                case CHANGE_ADMIN_PASSWORD:

                    System.out.println("Please enter administrator password:");

                    if (!(admin.getPassword().equals(bufferedReader.readLine()))) {
                        System.out.println("Password is not correct.");
                    } else {

                        System.out.println("<< CHANGING PASSWORD >>\n");
                        System.out.println("Please enter new password:");
                        String newPassword = bufferedReader.readLine();
                        admin.changePassword(newPassword);
                    }
                    break;
            }

        }
        while (!(input.equals("e")));
    }

    public static void validateUser() throws IOException {

        System.out.println("Please enter your login:");
        login = bufferedReader.readLine();
        System.out.println("Please enter your password:");
        password = bufferedReader.readLine();
        currentUser = userManager.logIn(login, password);
    }
}


