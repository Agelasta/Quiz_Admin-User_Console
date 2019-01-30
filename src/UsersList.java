import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsersList implements Serializable {

    private static List<User> usersList = new ArrayList<>();

    public static List<User> getUsersList() {
        return usersList;
    }

    public void addUser(User user) {

        UsersBackup usersBackup = new UsersBackup();

        if (isUserExist(user.getLogin())) {
            System.out.println("User " + user.getLogin() + " already exists.");
        }
        else {
            usersList.add(user);
            usersBackup.saveUser(user);
            usersBackup.saveToUserIndex(user);
            System.out.println("Account created successfully.");
        }
    }

    public void removeUser(String login) {

            usersList.removeIf(user -> user.getLogin().equals(login));
    }

    public int getSize() {
        return usersList.size();
    }

    public User getUser(String login) {

        User user = new User();

        for (int i = 0; i < usersList.size(); i++)
            if ((usersList.get(i).getLogin().equals(login)))
                user = usersList.get(i);

        return user;
    }

    public boolean isUserExist(String login) {

        boolean result = false;

        if (usersList.size() == 0) {
            return result;
        }
        else {
            for (int i = 0; i < usersList.size(); i++) {
                if (login.equals(usersList.get(i).getLogin())) {
                    result = true;
                }
            }
        }
        return result;
    }

    public void showList() {
        for (int i = 0; i < usersList.size(); i++) {
            System.out.print("USER " + (i+1) + ": ");
            System.out.println(usersList.get(i).getLogin() + " (" + usersList.get(i).getPassword() + ")");
        }
    }
}
