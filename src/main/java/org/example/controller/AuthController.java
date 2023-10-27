package org.example.controller;

import org.example.Models.Role;
import org.example.Models.User;
import org.example.controller.impl.IAuthController;
import org.example.utils.AppException;
import org.example.utils.StringUtils;
import org.example.view.AuthPage;
import org.example.view.LoginPage;
import org.example.view.RegisterPage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.example.utils.AppInput.enterNumber;
import static org.example.utils.AppInput.enterString;
import static org.example.utils.FileUtil.getCredentialFile;
import static org.example.utils.UserUtils.setLoggedInUser;
import static org.example.utils.Utils.println;

public class AuthController implements IAuthController {

    private final HomeController homeController;
    private final LoginPage loginPage;
    private final RegisterPage registerPage;
    private final AuthPage authPage;

    public AuthController() {
        authPage = new AuthPage();
        loginPage = new LoginPage();
        registerPage = new RegisterPage();
        homeController = new HomeController(this);

    }

    @Override
    public void authMenu() {
        authPage.printAuthMenu();
        int choice;
        try {
            choice = enterNumber(StringUtils.ENTER_CHOICE);
            if (choice == 99) {
                authPage.printThankYou();
                System.exit(0);
            } else {
                if (choice == 1) {
                    login();
                } else if (choice == 2) {
                    register();
                } else {
                    invalidChoice(new AppException(StringUtils.INVALID_CHOICE));
                }
            }
        } catch (AppException appException) {
            invalidChoice(appException);
        }
    }

    @Override
    public void login() {
        String email, password;
        email = enterString(StringUtils.ENTER_EMAIL);
        password = enterString(StringUtils.ENTER_PASSWORD);

        User user = validateUser(email,password);
        if (user!= null) {
            setLoggedInUser(user);
            homeController.printMenu();
        } else {
            loginPage.printInvalid();
            authMenu();
        }
    }

    private User validateUser(String email, String password) {
        try{
            Scanner sc = new Scanner(getCredentialFile());
            while (sc.hasNext()) {
                String value = sc.next().trim();
                if (!value.startsWith("id")) {
                    String[] userArray = value.split(",");
                    if (userArray[2].equals(email) && userArray[3].equals(password)) {
                        User user = new User();
                        user.setId(Integer.parseInt(userArray[0]));
                        user.setName(userArray[1]);
                        user.setEmail(userArray[2]);
                        user.setPassword(userArray[3]);
                        if (user.getEmail().equals("admin@kumaran.com")){
                            user.setRole(Role.ADMIN);
                        } else {
                            user.setRole(Role.USER);
                        }
                        return user;
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            invalidChoice(new AppException(StringUtils.FILE_NOT_FOUND));
        }
        return null;
    }

    @Override
    public void register() {
        String name, email, password, c_password;
        name = enterString(StringUtils.ENTER_NAME);
        email = enterString(StringUtils.ENTER_EMAIL);
        password = enterString(StringUtils.ENTER_PASSWORD);
        c_password = enterString(StringUtils.ENTER_PASSWORD_AGAIN);

        if (password.equals(c_password)) {
            try {
                FileWriter csvWriter = new FileWriter(getCredentialFile(), true);
                int id = (int) (Math.random() * 100);
                csvWriter.append("\n");
                csvWriter.append(id + "," + name + "," + email + "," + password);
                csvWriter.flush();
                csvWriter.close();
                registerPage.printRegistrationSuccessful();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            registerPage.passwordMisMatch();
        }
        authMenu();
    }

    private void invalidChoice(AppException appException) {
        println(appException.getMessage());
        authMenu();
    }

}
