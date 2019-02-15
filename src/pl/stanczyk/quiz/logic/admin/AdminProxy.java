package pl.stanczyk.quiz.logic.admin;

import pl.stanczyk.quiz.interfaces.AdminInterface;

import java.io.BufferedReader;

public class AdminProxy implements AdminInterface {

    @Override
    public boolean validateAdmin(BufferedReader bufferedReader) {
        Admin admin = new Admin();
        admin.fetchPassword();
        return admin.validateAdmin(bufferedReader);
    }
}
