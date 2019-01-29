import java.io.*;

public class Administrator {

    private String administratorPassword;
    private UsersList usersListManager;
    private UsersListFileManagement usersListFileManager;

    public Administrator() {

        usersListManager = new UsersList();
        usersListFileManager = new UsersListFileManagement();
    }

    public String getPassword() {return administratorPassword;}

    public void setPassword(String password) {administratorPassword = password;}

    public void fetchPassword() {

        String password = null;

        try ( var ois = new ObjectInputStream(new FileInputStream("password.obj")))
        {
            password = (String) ois.readObject();
            setPassword(password);
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while reading file");
        }

    }

    public void changePassword(String newPassword) {

        setPassword(newPassword);

        try  ( var oos = new ObjectOutputStream(new FileOutputStream("password.obj")))
        {
            oos.writeObject(newPassword);
            System.out.println("Password changed.");
        }
        catch (IOException e) {
            System.err.println("Error - password not changed");
        }
    }

    public void addUser(String login, String password) {

        usersListManager = new UsersList();
        usersListManager.addUser(new User(login, password));

    }

    public void removeUser(String login) {

        usersListManager = new UsersList();
        usersListFileManager = new UsersListFileManagement();

        if (usersListManager.isUserExist(login)) {
            usersListManager.removeUser(login);
            usersListFileManager.deleteUserFromUserIndex(login);
            usersListFileManager.createRemovalFile(login);
            usersListFileManager.deleteUserFile(login);
        } else System.out.println("User does not exist.");
    }

    public void showUsers() {

        usersListManager = new UsersList();

        if (usersListManager.getSize() > 0)
            usersListManager.showList();
        else System.out.println("There is no user registered.");
    }
}
