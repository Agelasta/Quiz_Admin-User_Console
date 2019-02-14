import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class UserManager {

    private UsersList usersList;

    public UserManager() {
        usersList = new UsersList();
    }

    public void changeLogin(User user, BufferedReader bufferedReader) {

        System.out.println("\n<< CHANGING LOGIN >>\n");
        String newLogin = null;
        String oldLogin = user.getLogin();

        do {
            System.out.println("Please enter new login:");
            System.out.println("(login must contain at least 1 letter, not contain any whitespace " +
                    "and have at least 4 characters length)");
            try {
                newLogin = bufferedReader.readLine();
            } catch (IOException e) {
                System.err.println("Error");
            }
        }
        while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", newLogin)) || newLogin.length() < 4);


        if (!(usersList.isUserExist(newLogin))) {
            user.setLogin(newLogin);
            UsersList.getUsersList().put(newLogin, user);
            UsersList.getUsersList().remove(oldLogin);
            usersList.saveUsers();
            System.out.println("Login changed.");
        } else {
            System.out.println("This login is not available.");
        }
    }


    public void changePassword(User user, BufferedReader bufferedReader) {

        System.out.println("\n<< CHANGING PASSWORD >>\n");
        String newPassword = null;

        do {
            System.out.println("Please enter new password:");
            System.out.println("(password must contain at least 1 letter, not contain any whitespace " +
                    "and have at least 4 characters length)");
            try {
                newPassword = bufferedReader.readLine();
            } catch (IOException e) {
                System.err.println("Error");
            }
        }
        while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", newPassword)) || newPassword.length() < 4);

        user.setPassword(newPassword);
        usersList.saveUsers();
        System.out.println("Password changed.");
    }

    public void removeAccount(User user, BufferedReader bufferedReader) {

        System.out.println("\n<< REMOVING ACCOUNT >>\n");

        if (user == null) {
            System.out.println(" Try again.");
        } else {

            System.out.println("Are you sure to remove your account?");
            System.out.println("Y - YES       N - NO");
            String confirmation = null;
            try {
                confirmation = bufferedReader.readLine();
            } catch (IOException e) {
                System.err.println("Error while reading input");
            }

            if (confirmation.equalsIgnoreCase("y")) {
                usersList.removeUser(user.getLogin());
                System.out.println("Account successfully removed.");
            } else System.out.println("Your account remains active.");
        }
    }

    public void showStatistics(User user) {

        System.out.println("\n<< STATISTICS >>");
        int bestScore = user.getBestScore();

        if (bestScore == 1) {
            System.out.println("\nYour best score is " + bestScore + " point.");
        } else {
            System.out.println("\nYour best score is " + bestScore + " points.");
        }
        System.out.printf("Your best efficiency is %.2f", user.getBestEfficiency());
        System.out.println(" %.");
    }

    public void register(BufferedReader bufferedReader) {

        String login = null;
        String password = null;

        System.out.println("<< REGISTERING >>\n");

        try {
            do {
                System.out.println("Please create login:");
                System.out.println("(login must contain at least 1 letter, not contain any whitespace " +
                        "and have at least 4 characters length)");

                login = bufferedReader.readLine();
            }
            while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", login)) || login.length() < 4);

            do {
                System.out.println("Please create password:");
                System.out.println("(password must contain at least 1 letter, not contain any whitespace " +
                        "and have at least 4 characters length)");
                password = bufferedReader.readLine();
            }
            while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", password)) || password.length() < 4);
        } catch (IOException e) {
            System.err.println("Error");
        }

        if (!(usersList.isUserExist(login))) {

            User user = new User(login, password);
            usersList.addUser(user);
        } else {
            System.out.println("User " + login + " already exists.");
        }
    }


    public User logIn(String login, String password) {

        User user = null;

        if (usersList.isUserExist(login)) {

            if (usersList.getUser(login).getPassword().equals(password)) {
                user = usersList.getUser(login);
                System.out.println("You are logged in, " + login + ".");
            } else System.out.print("Password is not correct.");

        } else System.out.print("User not found.");

        return user;
    }

    public User validateUser(BufferedReader bufferedReader) throws IOException {

        System.out.println("Please enter your login:");
        String login = bufferedReader.readLine();
        System.out.println("Please enter your password:");
        String password = bufferedReader.readLine();
        User user = logIn(login, password);
        return user;
    }
}
