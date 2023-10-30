//package org.example.view;
//
//public class cateTry {
//    public static ArrayList<Product> getProductsss ( ) throws IOException {
//
//        String line = "";
//        BufferedReader br = new BufferedReader ( new FileReader ( getProductsFile ( ) ) );
//        while ((line = br.readLine ( )) != null) {
//            if (!line.startsWith ( "id" )) {
//                String[] productsArray = line.split ( "," );
////                    System.out.println ( productsArray[0] + " " + productsArray[1] );
////                      Product product=new Product ( Integer.parseInt ( productsArray[0] ),productsArray[1],productsArray[2],Double.parseDouble ( productsArray[3] ),Integer.parseInt ( productsArray[4] ),new Category ( Integer.parseInt ( productsArray[6] ),productsArray[5] ) );
////                      products.add ( product );
//                if (productsArray[6] == "1") {
//                    Category womenFashionCategory = new Category ( Integer.parseInt ( productsArray[6] ), productsArray[5] );
//                    Product womenFashionProduct = new Product ( Integer.parseInt ( productsArray[0] ), productsArray[1], productsArray[2], Double.parseDouble ( productsArray[3] ), Integer.parseInt ( productsArray[4] ), womenFashionCategory );
//                    products.add ( womenFashionProduct );
//                } else if (productsArray[6] == "2") {
//                    Category menFashionCategory = new Category ( Integer.parseInt ( productsArray[6] ), productsArray[5] );
//                    Product menFashionProduct = new Product ( Integer.parseInt ( productsArray[0] ), productsArray[1], productsArray[2], Double.parseDouble ( productsArray[3] ), Integer.parseInt ( productsArray[4] ), menFashionCategory );
//                    products.add ( menFashionProduct );
//                } else if (productsArray[6] == "3") {
//                    Category electronicCategory = new Category ( Integer.parseInt ( productsArray[6] ), productsArray[5] );
//                    Product electronicProduct = new Product ( Integer.parseInt ( productsArray[0] ), productsArray[1], productsArray[2], Double.parseDouble ( productsArray[3] ), Integer.parseInt ( productsArray[4] ), electronicCategory );
//                    products.add ( electronicProduct );
//                } else if (productsArray[6] == "4") {
//                    Category womenAccessoryCategory = new Category ( Integer.parseInt ( productsArray[6] ), productsArray[5] );
//                    Product womenAccessoryProduct = new Product ( Integer.parseInt ( productsArray[0] ), productsArray[1], productsArray[2], Double.parseDouble ( productsArray[3] ), Integer.parseInt ( productsArray[4] ), womenAccessoryCategory );
//                    products.add ( womenAccessoryProduct );
//                } else {
//                    Category menAccessoryCategory = new Category ( Integer.parseInt ( productsArray[6] ), productsArray[5] );
//                    Product menAccessoryProduct = new Product ( Integer.parseInt ( productsArray[0] ), productsArray[1], productsArray[2], Double.parseDouble ( productsArray[3] ), Integer.parseInt ( productsArray[4] ), menAccessoryCategory );
//                    products.add ( menAccessoryProduct );
//                }
//            }
//        }
////
//
//
//        return products;
//    }
//
//}
//}
