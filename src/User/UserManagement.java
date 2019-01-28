package User;

public class UserManagement {

    private UsersList usersListManager;

    public void changeLogin(String oldLogin, String newLogin, String password, User user) {

        usersListManager = new UsersList();

        if(!(usersListManager.isUserExist(newLogin))) {

                user.setLogin(newLogin);
                System.out.println("Login changed.");
                usersListManager.saveNewLogin(oldLogin, newLogin);

        }
        else System.out.println("This login is not available.");
    }

    public void changePassword(String newPassword, User user) {

            user.setPassword(newPassword );
            System.out.println("Password changed.");
    }

    public void removeAccount(String login) {

        usersListManager = new UsersList();

            usersListManager.removeUser(login);
            usersListManager.deleteUserFromUserIndex(login);
            usersListManager.createRemovalFile(login);
            usersListManager.deleteUserFile(login);
            System.out.println("Account successfully removed.");
    }

    public void register(String login, String password) {

        User user;
        usersListManager = new UsersList();

        if (!(usersListManager.isUserExist(login))) {

            user = new User(login, password);
            usersListManager.addUser(user);
            System.out.println("Account created successfully.");

        } else System.out.println("User " + login + " already exists.");

    }

    public User logIn(String login, String password) {

        User user = null;
        usersListManager = new UsersList();

        if (usersListManager.isUserExist(login)) {

            if (usersListManager.getUser(login).getPassword().equals(password)) {
                user = usersListManager.getUser(login);
                System.out.println("You are logged in, " + login + ".");
            }
            else System.out.print("Password is not correct.");

        }
        else System.out.print("User not found.");
        return user;
    }

}
