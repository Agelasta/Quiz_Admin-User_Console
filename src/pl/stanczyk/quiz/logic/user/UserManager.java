package pl.stanczyk.quiz.logic.user;

import pl.stanczyk.quiz.data.User;

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
        String oldLogin = user.getLogin();
        System.out.println("Please enter new login:");
        String newLogin = readLoginFromUser(bufferedReader);

        if (!(usersList.isUserExist(newLogin))) {
            user.setLogin(newLogin);
            usersList.getUsersList().put(newLogin, user);
            usersList.getUsersList().remove(oldLogin);
            usersList.saveUsers();
            System.out.println("Login changed.");
        } else {
            System.out.println("This login is not available.");
        }
    }

    private String readLoginFromUser(BufferedReader bufferedReader) {

        String newLogin = null;

        do {
            System.out.println("(login must contain at least 1 letter, not contain any whitespace " +
                    "and have at least 4 characters length)");
            try {
                newLogin = bufferedReader.readLine();
            } catch (IOException e) {
                System.err.println("Error while reading input");
            }
        }
        while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", newLogin)) || newLogin.length() < 4);
        return newLogin;
    }


    public void changePassword(User user, BufferedReader bufferedReader) {

        System.out.println("\n<< CHANGING PASSWORD >>\n");
        System.out.println("Please enter new password:");
        String newPassword = readPasswordFromUser(bufferedReader);

        user.setPassword(newPassword);
        usersList.saveUsers();
        System.out.println("Password changed.");
    }

    private String readPasswordFromUser(BufferedReader bufferedReader) {

        String newPassword = null;

        do {
            System.out.println("(password must contain at least 1 letter, not contain any whitespace " +
                    "and have at least 4 characters length)");
            try {
                newPassword = bufferedReader.readLine();
            } catch (IOException e) {
                System.err.println("Error while reading input");
            }
        }
        while (!(Pattern.matches("[^\\s]*[a-zA-Z]+[^\\s]*", newPassword)) || newPassword.length() < 4);

        return newPassword;
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

        System.out.println("<< REGISTERING >>\n");

                System.out.println("Please create login:");
                String login = readLoginFromUser(bufferedReader);
                System.out.println("Please create password:");
                String password = readPasswordFromUser(bufferedReader);

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

    public User validateUser(BufferedReader bufferedReader) {

        String login = null;
        String password = null;

        try {
            System.out.println("Please enter your login:");
            login = bufferedReader.readLine();
            System.out.println("Please enter your password:");
            password = bufferedReader.readLine();
        }
        catch(IOException e) {
            System.err.println("Error while reading input");
        }
        User user = logIn(login, password);
        return user;
    }
}
