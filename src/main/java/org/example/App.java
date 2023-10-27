package org.example;

import org.example.controller.AppController;
import org.example.utils.LoadUtils;

public class App {
    public static void main(String[] args) {
        AppController appController = new AppController();
        LoadUtils.load();
        appController.init();
    }
}