package org.example.controller;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.example.Models.Category;
import org.example.controller.impl.IAdminController;
import org.example.utils.AppException;
import org.example.utils.StringUtils;
import org.example.view.AdminPage;
import org.example.view.AuthPage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.example.utils.AppInput.enterNumber;
import static org.example.utils.AppInput.enterString;
import static org.example.utils.FileUtil.getCategoriesFile;
import static org.example.utils.FileUtil.getCredentialFile;
import static org.example.utils.LoadUtils.getCategories;
import static org.example.utils.LoadUtils.invalidChoice;
import static org.example.utils.Utils.println;

public class AdminController implements IAdminController {

    private final AdminPage adminPage;
    private final AuthPage authPage;

    public AdminController() {
        adminPage = new AdminPage();
        authPage = new AuthPage();
    }

    @Override
    public void printMenu() {
        adminPage.printMenu();
        int choice;
        try {
            choice = enterNumber(StringUtils.ENTER_CHOICE);
            if (choice == 99) {
                adminPage.printThankYou();
                System.exit(0);
            } else {
                if (choice == 1) {
                    adminPage.categoryMenu();
                    int catChoice = enterNumber(StringUtils.ENTER_CHOICE);
                    if (catChoice == 99) {
                        printMenu();
                    } else if (catChoice == 1) {
                        ArrayList<Category> categories = getCategories();
                        adminPage.printCategories(categories);
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        printMenu();
                    } else if (catChoice == 2) {
                        addCategory();
                        printMenu();
                    } else if (catChoice == 3) {
                        //edit category from file...
                        ArrayList<Category> categories = getCategories();
                        adminPage.printCategories(categories);

                        editCategory();
                        printMenu();
                    } else if (catChoice == 4) {
                        //delete category frm file...
                        ArrayList<Category> categories = getCategories();
                        adminPage.printCategories(categories);

                        deleteCategory();
                        printMenu();


                    } else {
                        println(StringUtils.INVALID_CHOICE);
                    }

                //Products-----------
                } else if (choice == 2) {

                    adminPage.productMenu();
                    int catChoice = enterNumber(StringUtils.ENTER_CHOICE);
                    if (catChoice == 99) {
                        printMenu();
                    } else if (catChoice == 1) {
                        ArrayList<Category> categories = getCategories();
                        adminPage.printCategories(categories);
                        printMenu();
                    } else if (catChoice == 2) {
                        addCategory();
                        printMenu();
                    } else if (catChoice == 3) {
                        //edit Products from file...
                        ArrayList<Category> categories = getCategories();
                        adminPage.printCategories(categories);
                        int id = enterNumber(StringUtils.ENTER_ID);
                        String name = enterString(StringUtils.ENTER_CNAME);
                        try {
                            Scanner sc = new Scanner(getCategoriesFile());
                            while (sc.hasNext()) {
                                String value = sc.next().trim();
                                if (!value.startsWith("id")) {
                                    if (value.startsWith(String.valueOf(id))) {
                                        String[] str = value.split(",");
                                        FileWriter writer = new FileWriter(getCategoriesFile(), true);

                                        writer.write(str[0] + "," + str[1]);
                                        writer.flush();
                                        writer.close();
                                        adminPage.printSuccessful();
                                    }
                                }
                            }
                            printMenu();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (catChoice == 4) {
                        //delete category frm file...
                    } else {
                        println(StringUtils.INVALID_CHOICE);
                    }

                } else if (choice == 3) {
                    ArrayList<String> users = new ArrayList<>();
                    try {
                        Scanner sc = new Scanner(getCredentialFile());
                        while (sc.hasNext()) {
                            String value = sc.next().trim();
                            if (!value.startsWith("id")) {
                                if (!value.startsWith("01")) {
                                    String[] userArray = value.split(",");
//                                    System.out.println(userArray[0]+userArray[1]+userArray[2]);

                                    users.add(Arrays.toString(userArray));
                                }
                            }
                        }
                        adminPage.printUsers(users);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else if (choice == 4) {

                } else {
                    invalidChoice(new AppException(StringUtils.INVALID_CHOICE));
                }
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    private void editCategory() {

        int id = 0;
        String Cname;
        try {
            id = enterNumber(StringUtils.ENTER_ID);
            Cname = enterString(StringUtils.ENTER_CNAME);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

        List<String[]> category = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader("src/main/java/org/example/assets/Categories.csv"));
            category = reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        category.get(id)[1] = Cname;

        try (CSVWriter writer = new CSVWriter(new FileWriter("src/main/java/org/example/assets/Categories.csv" ,false ),CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.RFC4180_LINE_END)) {
            writer.writeAll(category);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void deleteCategory() {

        int id = 0;
        try {
            id = enterNumber(StringUtils.ENTER_ID);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

        List<String[]> records = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader("src/main/java/org/example/assets/Categories.csv"));
            records = reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        List<String[]> recordsToDelete = new ArrayList<>();
        for (String[] record : records) {
            try {
                int categoryIdFromRecord = Integer.parseInt(record[0].trim());
                if (categoryIdFromRecord != id) {
                    recordsToDelete.add(record);
                }
            } catch (NumberFormatException ex) {
            }
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter("src/main/java/org/example/assets/Categories.csv" ,false ),CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.RFC4180_LINE_END)) {
            writer.writeAll(recordsToDelete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void addCategory() throws AppException {
        int id = enterNumber(StringUtils.ENTER_ID);
        String CName = enterString(StringUtils.ENTER_CNAME);

        try {
            FileWriter writer = new FileWriter(getCategoriesFile(), true);
            writer.append(id + "," + CName);
            writer.flush();
            writer.close();
            adminPage.printSuccessful();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}