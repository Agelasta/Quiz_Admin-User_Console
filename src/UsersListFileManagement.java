import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class UsersListFileManagement {

    public void saveUser(User user) {

        try (var oos = new ObjectOutputStream(new FileOutputStream("users\\" + user.getLogin() + ".obj"))) {
            oos.writeObject(user);
        } catch (IOException e) {
            System.err.println("Error - user not saved");
        }
    }

    public void saveToUserIndex(User user) {

        var properties = new Properties();

        try (var reader = new FileReader("userIndex.txt")) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        String index = properties.getProperty("index");
        String login = user.getLogin();
        String index2 = String.valueOf(Integer.valueOf(index) + 1);

        properties.setProperty(index, login);
        properties.setProperty("index", index2);

        try (var writer = new FileWriter("userIndex.txt");) {
            properties.store(writer, "Users");
        } catch (IOException e) {
            System.err.println("Error - index not saved");
        }
    }

    public void changeLoginInUserIndex(String oldLogin, String newLogin) {

        var properties = new Properties();
        int index = 0;

        try (var reader = new FileReader("userIndex.txt");) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        Set<Map.Entry<Object, Object>> logins = properties.entrySet();

        for (Map.Entry<Object, Object> object : logins)
            if (object.getValue().equals(oldLogin)) {
                index = Integer.valueOf((String) object.getKey());
            }

        properties.setProperty(String.valueOf(index), newLogin);

        try (var writer = new FileWriter("userIndex.txt");) {
            properties.store(writer, "Users");
        } catch (IOException e) {
            System.err.println("Error - login not changed");
        }
    }

    public void deleteUserFile(String login) {

        try {
            Files.delete(Paths.get("users\\" + login + ".obj"));
        } catch (IOException e) {
            System.err.println("Please restart program to complete requested operation.");
        }
    }


    public void createRemovalFile(String login) {

        var properties = new Properties();

        try (var reader = new FileReader("removal.txt");) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        String index = properties.getProperty("index");
        String index2 = String.valueOf(Integer.valueOf(index) + 1);

        properties.setProperty(index, login);
        properties.setProperty("index", index2);

        try (var writer = new FileWriter("removal.txt", true)) {
            properties.store(writer, "Removed");
        } catch (IOException e) {
            System.err.println("Error - file not created");
        }
    }

    public void readRemovalFile() {

        var properties = new Properties();
        String login;
        String key;

        try (var reader = new FileReader("removal.txt")) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        if (properties.size() > 1) {

            Set<Map.Entry<Object, Object>> logins = properties.entrySet();

            for (Map.Entry<Object, Object> object : logins) {
                key = (String) object.getKey();
                login = (String) object.getValue();

                File file = new File("users\\" + login + ".obj");

                if (file.exists()) {
                    file.delete();
                }
                properties.remove(key, login);
            }
        }
        try (var writer = new FileWriter("removal.txt")) {
            properties.setProperty("index", "1");
            properties.store(writer, "Removed");

        } catch (IOException e) {
            System.err.println("Error - file not reseted");
        }
    }


    public void deleteUserFromUserIndex(String login) {

        var properties = new Properties();

        try (var reader = new FileReader("userIndex.txt")) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        int numberRemoved = 0;
        int index = Integer.valueOf(properties.getProperty("index"));
        String newIndex = String.valueOf(index - 1);

        Set<Map.Entry<Object, Object>> logins = properties.entrySet();

        for (Map.Entry<Object, Object> object : logins) {
            String key = (String) object.getKey();
            String value = (String) object.getValue();

            if (value.equals(login)) {
                numberRemoved = Integer.valueOf(key);
                properties.remove(String.valueOf(key), value);
                properties.setProperty("index", newIndex);
            }
        }

        for (Map.Entry<Object, Object> object2 : logins) {
            String key2 = (String) object2.getKey();
            String value2 = (String) object2.getValue();

            if (!(key2.equals("index")) && Integer.valueOf(key2) > numberRemoved) {
                properties.setProperty(String.valueOf(Integer.valueOf(key2) - 1), value2);
                properties.remove(String.valueOf(Integer.valueOf(key2)), value2);
            }
        }

        try (var writer = new FileWriter("userIndex.txt")) {
            properties.store(writer, "Users");
        } catch (IOException e) {
            System.err.println("Error - changes not saved");
        }
    }


    public void saveNewLogin(String oldLogin, String newLogin) {

        User user = null;

        try (var ois = new ObjectInputStream(new FileInputStream("users\\" + oldLogin + ".obj"))) {
            user = (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while reading file");
        }

        user.setLogin(newLogin);

        saveUser(user);

        changeLoginInUserIndex(oldLogin, newLogin);

        deleteUserFile(oldLogin);

    }

    public void fetchUsers() {

        var properties = new Properties();

        try (var reader = new FileReader("userIndex.txt")) {
            properties.load(reader);

        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        if (properties.size() > 1) {

            String[] logins = new String[properties.size() - 1];

            for (int i = 0; i < logins.length; i++) {

                logins[i] = properties.getProperty(String.valueOf(i + 1));

                try (var ois = new ObjectInputStream(new FileInputStream("users\\" + logins[i] + ".obj"))) {
                    UsersList.getUsersList().add((User) ois.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error - user not fetched");
                }
            }
        }
    }
}
