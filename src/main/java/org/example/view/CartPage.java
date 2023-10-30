package org.example.view;

import org.example.Models.CartProduct;
import org.example.utils.StringUtils;

import java.util.ArrayList;

import static org.example.utils.Utils.println;

public class CartPage {
    public void printEmptyCart() {
        println(StringUtils.EMPTY_CART);
    }

    public void printCart(ArrayList<String[]> cartProducts) {
        println(StringUtils.CART);
        double total = 0;
        for (String[] cartProduct : cartProducts) {
            total += Integer.parseInt(cartProduct[2]) * Double.parseDouble(cartProduct[1]);
            println(cartProduct[0] + " -- " + cartProduct[1]+ " :: "+cartProduct[2]);
        }
        println(StringUtils.TOTAL_PRICE + total);
    }

    public void printCheckout() {
        println(StringUtils.PRINT_CHECKOUT);
    }

    public void printBack() {
        println(StringUtils.BACK_OPTION);
    }

}
