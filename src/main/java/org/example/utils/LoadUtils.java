package org.example.utils;

import org.example.Models.Category;
import org.example.Models.Product;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.example.utils.FileUtil.getCategoriesFile;
import static org.example.utils.FileUtil.getProductsFile;

public class LoadUtils {
    private static final ArrayList<Category> categories = new ArrayList<>();
    private static final ArrayList<Product> products = new ArrayList<>();

    public static void load() {
//        Category dressCategory = new Category(1, "Dress");
//        categories.add(dressCategory);
//        Category mobileCategory = new Category(2, "Mobile");
//        categories.add(mobileCategory);

        try {
            Scanner sc = new Scanner(getCategoriesFile());
            while (sc.hasNext()) {
                String value = sc.next().trim();
                if (!value.startsWith("id")) {
                    String[] CategoryArray = value.split(",");
                    Category category = new Category();
//                    System.out.println(CategoryArray[0]);
                    category.setId(Integer.parseInt(CategoryArray[0]));
                    category.setCName(CategoryArray[1]);

                    categories.add(category);
//                    System.out.println(categories);
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            invalidChoice(new AppException(StringUtils.FILE_NOT_FOUND));
        }

        //get products FROM FILE
        try {
            Scanner sc = new Scanner(getProductsFile());
            while (sc.hasNext()) {
                String value = sc.next().trim();
                if (!value.startsWith("id")) {
                    String[] productsArray = value.split(",");
                    Product product = new Product();

                    Category productCategory = null;
                    for (Category category : categories) {
                        if (category.getId() == Integer.parseInt(productsArray[5])) {
                            productCategory = category;
                            break;
                        }
                    }
                    if (productCategory != null) {
                        Product dressProduct = new Product(Integer.parseInt(productsArray[0]), productsArray[1], productsArray[2], Integer.parseInt(productsArray[3]), Integer.parseInt(productsArray[4]), productCategory);
                        products.add(dressProduct);
                    }


//                    System.out.println(productsArray[0]);

//                    product.setId(Integer.parseInt(productsArray[0]));
//                    product.setTitle(productsArray[1]);
//                    product.setDesc(productsArray[2]);
//                    product.setPrice(Double.parseDouble(productsArray[3]));
//                    product.setStocks(Integer.parseInt(productsArray[4]));
//                    Category cname = new Category(productsArray[5]);
//                    product.setCategory(cname);
//                    System.out.println(cname.getCName());
//                    System.out.println(Arrays.toString(CategoryArray));



                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            invalidChoice(new AppException(StringUtils.FILE_NOT_FOUND));
        }


//        Product tShirtProduct = new Product(1, "Tshirt", "Tshirt", 100, 10, dressCategory);
//        Product shirtProduct = new Product(2, "Shirt", "Shirt", 105, 10, dressCategory);
//        products.add(tShirtProduct);
//        products.add(shirtProduct);
//
//        Product iphoneProduct = new Product(3, "iPhone", "iPhone", 1000, 10, mobileCategory);
//        Product samsungProduct = new Product(4, "Samsung", "Samsung", 1005, 10, mobileCategory);
//        products.add(iphoneProduct);
//        products.add(samsungProduct);

    }

    public static void invalidChoice(AppException e) {


    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }

    public static ArrayList<Product> getProducts() {
        return products;
    }


}
