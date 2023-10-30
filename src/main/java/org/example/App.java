package org.example;

import org.example.controller.AdminController;
import org.example.controller.AppController;
import org.example.utils.LoadUtils;

public class App {
    public static void main(String[] args) {
        AppController appController = new AppController();
//        AdminController adminController = new AdminController();
        LoadUtils.load();
        appController.init();

    }
}