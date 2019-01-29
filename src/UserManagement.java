public class UserManagement {

    private UsersListFileManagement usersListFileManager;
    private UsersList usersListManager;

    public void changeLogin(String oldLogin, String newLogin, User user) {

        usersListFileManager = new UsersListFileManagement();
        usersListManager = new UsersList();

        if(!(usersListManager.isUserExist(newLogin))) {

                user.setLogin(newLogin);
                System.out.println("Login changed.");
                usersListFileManager.saveNewLogin(oldLogin, newLogin);

        }
        else System.out.println("This login is not available.");
    }

    public void changePassword(String newPassword, User user) {

        usersListFileManager = new UsersListFileManagement();

        user.setPassword(newPassword);
        System.out.println("Password changed.");
        usersListFileManager.saveUser(user);
    }

    public void removeAccount(String login) {

        usersListFileManager = new UsersListFileManagement();
        usersListManager = new UsersList();

            usersListManager.removeUser(login);
            usersListFileManager.deleteUserFromUserIndex(login);
            usersListFileManager.createRemovalFile(login);
            usersListFileManager.deleteUserFile(login);
            System.out.println("Account successfully removed.");
    }

    public void register(String login, String password) {

        usersListManager = new UsersList();
        User user;

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
