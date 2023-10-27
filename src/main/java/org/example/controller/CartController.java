package org.example.controller;

import org.example.Models.Cart;
import org.example.Models.CartProduct;
import org.example.Models.Product;
import org.example.Models.User;
import org.example.controller.impl.ICartController;
import org.example.utils.AppException;
import org.example.utils.StringUtils;
import org.example.view.CartPage;

import java.util.ArrayList;

import static org.example.utils.AppInput.enterNumber;
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
        User loggedInUser = getLoggedInUser();
        if (loggedInUser.getUserCart() == null) {
            cartPage.printEmptyCart();
            homeController.printMenu();
        } else {
            ArrayList<CartProduct> cartProducts = loggedInUser.getUserCart().getCartProducts();
            cartPage.printCart(cartProducts);

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
}
