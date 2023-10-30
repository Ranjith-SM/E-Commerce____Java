package org.example.controller;

import org.example.Models.*;
import org.example.controller.impl.ICartController;
import org.example.utils.AppException;
import org.example.utils.StringUtils;
import org.example.view.CartPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.example.utils.AppInput.enterNumber;
import static org.example.utils.FileUtil.getCartFilePath;
import static org.example.utils.FileUtil.getCsvFilePath;
import static org.example.utils.LoadUtils.getProducts;
import static org.example.utils.UserUtils.getLoggedInUser;
import static org.example.utils.UserUtils.setLoggedInUser;
import static org.example.utils.Utils.println;

public class CartController implements ICartController {

    private final HomeController homeController;
    private final OrderController orderController;
    private final CartPage cartPage;

    public CartController(HomeController homeController) {
        this.homeController = homeController;
        orderController = new OrderController(homeController);
        cartPage = new CartPage();
    }

    @Override
    public void addToCart(int productId) {
        User loggedInUser = getLoggedInUser();
        ArrayList<Product> products = getProducts();

        Product userProduct = null;
        for (Product product : products) {
            if (product.getId() == productId) {
                userProduct = product;
                break;
            }
        }

        if (loggedInUser.getUserCart() != null) {
            Cart cart = loggedInUser.getUserCart();

            boolean isFound = false;
            for (CartProduct cartProduct : cart.getCartProducts()) {
                if (cartProduct.getProduct().getId() == productId) {
                    cartProduct.setCount(cartProduct.getCount() + 1);
                    isFound = true;
                }
            }

            if (!isFound) {
                cart.getCartProducts().add(new CartProduct(userProduct, 1));
            }

            loggedInUser.setUserCart(cart);
        } else {
            Cart cart = new Cart();
            ArrayList<CartProduct> cartProducts = new ArrayList<>();
            cartProducts.add(new CartProduct(userProduct, 1));
            cart.setCartProducts(cartProducts);
            loggedInUser.setUserCart(cart);
        }

        setLoggedInUser(loggedInUser);
    }

    private void checkout() {
        orderController.checkout();
    }

    private void invalidChoice(AppException appException) {
        println(appException.getMessage());
        printCart();
    }

    @Override
    public void printCart() {
        createCartFile();
        createCartCsv();
        User loggedInUser = getLoggedInUser();
        if (loggedInUser.getUserCart() == null) {
            cartPage.printEmptyCart();
            homeController.printMenu();
        } else {
            ArrayList<String[]> cartProducts = new ArrayList<String[]>() ;

            File directoryPath = new File(getCsvFilePath());
            //List of all files and directories
            File[] filesList = directoryPath.listFiles();
            for (File file : filesList) {
                if (file.getName().startsWith(String.valueOf(getLoggedInUser().getId()))) {
                    try {
                        Scanner sc = new Scanner(file);
                        while (sc.hasNext()) {
                            String value = sc.next().trim();
                            if (!value.startsWith("title")) {
                                String[] cartparr = value.split(",");
                                String[] cparr = new String[4];
                                for (int i=0;i<cartparr.length;i++) {
                                    cparr[i] = cartparr[i];
                                }
                                cartProducts.add(cparr);
//
//
                            }
                        }
                        sc.close();
                    }catch (FileNotFoundException e) {
                        invalidChoice(new AppException(StringUtils.FILE_NOT_FOUND));
                    }

                }

            }


            cartPage.printCart(cartProducts);
//            createCartFile();
//            createCartCsv();

            cartPage.printCheckout();
            cartPage.printBack();

            try {
                int choice = enterNumber(StringUtils.ENTER_CHOICE);
                if (choice == 88) {
                    checkout();
                } else if (choice == 99) {
                    homeController.printMenu();
                } else {
                    invalidChoice(new AppException(StringUtils.INVALID_CHOICE));
                }
            } catch (AppException appException) {
                invalidChoice(appException);
            }

        }
    }

    public void createCartFile() {
        User loggedInUser = getLoggedInUser();

        try {
            FileWriter fileWriter = new FileWriter(getCartFilePath() + loggedInUser.getId() + "-" + System.currentTimeMillis() + ".txt");
            fileWriter.write("Cart Products are:");
            fileWriter.write("\n");

            double total = 0;
            if (loggedInUser.getUserCart() != null) {
                for (CartProduct cartProduct : loggedInUser.getUserCart().getCartProducts()) {
                    total += cartProduct.getCount() * cartProduct.getProduct().getPrice();
                    fileWriter.write(cartProduct.getProduct().getTitle() + "-" + cartProduct.getProduct().getPrice() + " ::-> " + cartProduct.getCount() + " = Rs. " + cartProduct.getProduct().getPrice() * cartProduct.getCount());
                    fileWriter.write("\n");
                }
                fileWriter.write("Total - Rs. " + total);
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCartCsv() {
        User loggedInUser = getLoggedInUser();

        try {
            FileWriter fileWriter = new FileWriter(getCsvFilePath() + loggedInUser.getId() + "-" + System.currentTimeMillis() + ".csv");
            fileWriter.write("title,price,count,total");
            fileWriter.write("\n");

            double total = 0;
            for (CartProduct cartProduct : loggedInUser.getUserCart().getCartProducts()) {
                total += cartProduct.getCount() * cartProduct.getProduct().getPrice();
                fileWriter.write(cartProduct.getProduct().getTitle() + "," + cartProduct.getProduct().getPrice() + "," + cartProduct.getCount() + "," + cartProduct.getProduct().getPrice() * cartProduct.getCount());
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
