import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class AppQuiz {

    private final static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static Administrator admin = new Administrator();
    private static UserManager userManager = new UserManager();
    private static UsersList usersList = new UsersList();
    private static Play play = new Play();
    private static User currentUser;
    private static String input;

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

    static {
        admin.fetchPassword();
        usersList.fetchUsers();
    }

    public static void main(String[] args) throws IOException {

        System.out.println("WELCOME TO OUR QUIZ!");

        try {
            Thread.sleep(800);
            System.out.println("WE HOPE YOU WILL ENJOY PLAYING WITH US!");
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        do {
            do {

                System.out.print("\n---- PLAYER MENU ----");
                System.out.println("           -- ADMINISTRATOR MENU --");
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

                    QuickPlay.playQuick();

                    break;

                case PLAY:

                    if (usersList.getSize() == 0) {
                        System.out.println("This option is available only for registered users. Please register first.");
                    } else {
                        if((currentUser = userManager.validateUser(bufferedReader)) == null) {
                            System.out.println(" Try again.");
                        } else {
                            play.play(currentUser, bufferedReader, input);
                        }
                    }
                    break;

                case REGISTER:

                    userManager.register(bufferedReader);

                    break;

                case STATISTICS:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    } else {
                        if((currentUser = userManager.validateUser(bufferedReader)) == null) {
                            System.out.println(" Try again.");
                        } else {
                            userManager.showStatistics(currentUser);
                        }
                    }
                    break;

                case CHANGE_LOGIN:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    }  else {
                        if((currentUser = userManager.validateUser(bufferedReader)) == null) {
                            System.out.println(" Try again.");
                        } else {
                            userManager.changeLogin(currentUser, bufferedReader);
                        }
                    }
                    break;

                case CHANGE_PASSWORD:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered. Please register first.");
                    } else {
                        if((currentUser = userManager.validateUser(bufferedReader)) == null) {
                            System.out.println(" Try again.");
                        } else {
                            userManager.changePassword(currentUser, bufferedReader);
                        }
                    }
                    break;

                case REMOVE_ACCOUNT:

                    if (usersList.getSize() == 0) {
                        System.out.println("There is no user registered.");
                    } else {
                        if((currentUser = userManager.validateUser(bufferedReader)) == null) {
                            System.out.println(" Try again.");
                        } else {
                            userManager.removeAccount(currentUser, bufferedReader);
                        }
                    }
                    break;

                case ADD_QUESTION:

                    if (!admin.validateAdmin(bufferedReader)) {
                        System.out.println("Password is not correct.");
                    } else {
                        admin.addQuestion(bufferedReader);
                    }
                    break;

                case REMOVE_QUESTION:

                    if (!admin.validateAdmin(bufferedReader)) {
                        System.out.println("Password is not correct.");
                    } else {
                        admin.removeQuestion(bufferedReader);
                    }
                    break;

                case SHOW_QUESTIONS:

                    if (!admin.validateAdmin(bufferedReader)) {
                        System.out.println("Password is not correct.");
                    } else {
                        admin.showQuestions(bufferedReader);
                    }
                    break;

                case ADD_USER:

                    if (!admin.validateAdmin(bufferedReader)) {
                        System.out.println("Password is not correct.");
                    } else {
                        admin.addUser(bufferedReader);
                    }
                    break;

                case REMOVE_USER:

                    if (!admin.validateAdmin(bufferedReader)) {
                        System.out.println("Password is not correct.");
                    } else {
                        admin.removeUser(bufferedReader);
                    }
                    break;

                case SHOW_USERS:

                    if (!admin.validateAdmin(bufferedReader)) {
                        System.out.println("Password is not correct.");
                    } else {
                        admin.showUsers();
                    }
                    break;

                case CHANGE_ADMIN_PASSWORD:

                    if (!admin.validateAdmin(bufferedReader)) {
                        System.out.println("Password is not correct.");
                    } else {
                        admin.changePassword(bufferedReader);
                    }
                    break;
            }
        }
        while (!(input.equals("e")));
    }
}


