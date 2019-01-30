public class UserManager {

    private UsersBackup usersBackup;
    private UsersList usersList;

    public UserManager() {
        usersBackup = new UsersBackup();
        usersList = new UsersList();
    }

    public void changeLogin(String oldLogin, String newLogin, User user) {

        if(!(usersList.isUserExist(newLogin))) {

                user.setLogin(newLogin);
                System.out.println("Login changed.");
                usersBackup.saveNewLogin(oldLogin, newLogin);

        }
        else System.out.println("This login is not available.");
    }

    public void changePassword(String newPassword, User user) {

        user.setPassword(newPassword);
        System.out.println("Password changed.");
        usersBackup.saveUser(user);
    }

    public void removeAccount(String login) {

            usersList.removeUser(login);
            usersBackup.deleteUserFromUserIndex(login);
            usersBackup.deleteUserFile(login);
            System.out.println("Account successfully removed.");
    }

    public void register(String login, String password) {

        User user;

        if (!(usersList.isUserExist(login))) {

            user = new User(login, password);
            usersList.addUser(user);

        } else System.out.println("User " + login + " already exists.");
    }

    public User logIn(String login, String password) {

        User user = null;

        if (usersList.isUserExist(login)) {

            if (usersList.getUser(login).getPassword().equals(password)) {
                user = usersList.getUser(login);
                System.out.println("You are logged in, " + login + ".");
            }
            else System.out.print("Password is not correct.");

        }
        else System.out.print("User not found.");
        return user;
    }
}
