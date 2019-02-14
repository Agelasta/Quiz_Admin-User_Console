import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class UsersList implements Serializable {

    private static Map<String, User> usersList = new TreeMap<>();

    public static Map<String, User> getUsersList() {
        return usersList;
    }

    public void addUser(User user) {

        if (isUserExist(user.getLogin())) {
            System.out.println("User " + user.getLogin() + " already exists.");
        }
        else {
            usersList.put(user.getLogin(), user);
            saveUsers();
            System.out.println("Account created successfully.");
        }
    }

    public void removeUser(String login) {

            usersList.remove(login);
            saveUsers();
    }

    public User getUser(String login) {

        return usersList.get(login);
    }

    public int getSize() {
        return usersList.size();
    }

    public boolean isUserExist(String login) {

        boolean result = false;

        if (usersList.size() == 0) {
            return result;
        }
        else {
            if(usersList.containsKey(login)) {
                result = true;
            }
        }
        return result;
    }

    public void showList() {

        int index = 1;

        Iterator<Map.Entry<String, User>> userIterator = usersList.entrySet().iterator();
        while(userIterator.hasNext()) {
            String login = userIterator.next().getKey();
            System.out.println("USER " + index + ": " + login + " (" +
                    usersList.get(login).getPassword() + ")");
            index++;
        }
    }

    public void saveUsers() {

        try (var oos = new ObjectOutputStream(new FileOutputStream("usersList.obj"))) {
            oos.writeObject(usersList);
        } catch (IOException e) {
            System.err.println("Error - user not saved");
        }
    }

    public void fetchUsers() {

        try (var ois = new ObjectInputStream(new FileInputStream("usersList.obj"))) {
            Map<String, User> users = (Map<String, User>) ois.readObject();
            usersList.putAll(users);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error - user not fetched");
        }
    }
}
