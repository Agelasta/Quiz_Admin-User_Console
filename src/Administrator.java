import java.io.*;

public class Administrator {

    private String administratorPassword;
    private UsersList usersList;
    private UsersBackup usersBackup;

    public Administrator() {

        usersList = new UsersList();
        usersBackup = new UsersBackup();
    }

    public String getPassword() {return administratorPassword;}

    public void setPassword(String password) {administratorPassword = password;}

    public void fetchPassword() {

        String password;

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

        usersList.addUser(new User(login, password));
    }

    public void removeUser(String login) {

        if (usersList.isUserExist(login)) {
            usersList.removeUser(login);
            usersBackup.deleteUserFromUserIndex(login);
            usersBackup.deleteUserFile(login);
            System.out.println("User " + login + " removed.");
        } else System.out.println("User does not exist.");
    }

    public void showUsers() {

        if (usersList.getSize() > 0)
            usersList.showList();
        else System.out.println("There is no user registered.");
    }
}
