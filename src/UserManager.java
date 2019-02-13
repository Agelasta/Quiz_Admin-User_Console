public class UserManager {

    private UsersList usersList;

    public UserManager() {
        usersList = new UsersList();
    }

    public void changeLogin(String oldLogin, String newLogin, User user) {

        if(!(usersList.isUserExist(newLogin))) {
                user.setLogin(newLogin);
                UsersList.getUsersList().put(newLogin, user);
                UsersList.getUsersList().remove(oldLogin);
                usersList.saveUsers();
                System.out.println("Login changed.");
        }
        else System.out.println("This login is not available.");
    }

    public void changePassword(String newPassword, User user) {

        user.setPassword(newPassword);
        usersList.saveUsers();
        System.out.println("Password changed.");
    }

    public void removeAccount(String login) {

            usersList.removeUser(login);
            usersList.saveUsers();
            System.out.println("Account successfully removed.");
    }

    public void register(String login, String password) {

        if (!(usersList.isUserExist(login))) {

            User user = new User(login, password);
            usersList.addUser(user);
            usersList.saveUsers();
        }
        else System.out.println("User " + login + " already exists.");
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
