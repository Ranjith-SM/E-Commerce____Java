
<img src= "https://th.bing.com/th/id/OIP.17Z49hdASIEoD7ez2FX_QQHaHa?pid=ImgDet&rs=1 height=200px"> 


# E-commerce using Java

The Project is developed using the Java version-v.17


like other e-commerce , the user can able to register and login and buy products and it will be added to the cart the in cart the user can checkout the products.

    

## Features

 - Register and login
 - View products by category 
 - View products
 - Add to cart
 - Orders
 - Admin
    - CRUD operation in categories 
    - CRUD operation in products 
    - View Registered Users
    - View Orders of the Users
 - Used File Handling Concepts in:
    - Users
    - products
    - categories
    - AddToCart 
    - Orders

## Roadmap

- First created all basic and the required utils , intefaces, classes and controllers(class) for the specific class. 
- then done the authentication part, the user has to register with certain details and login with the same. during this process, i have to store the user to check or to retrieve the user credential to login again.

- After login , the products should be loaded in the page. for that: products are read from the csv file and shown to the user. it is same to the categories.

- 4 Menu options will be shown to the user:
    - Category
    - products
    - Cart 
    - Order
    Then Choosing the option will show the respective details to user.

    when the user doesn't added anything to cart, then it will show empty. else if the user has added some products to cart it will be shown and text file & csv file will be created.
- while reading the products from while by category, i have faced an issue that the category in products can't be accessed. so to rectify this, i get the category from the file and then added the products to that category. if the category is not available the product will not be added.
- then i have faced issue on addtocart file:
    
    when the user loggedout and cameback his/her products should be in the cart. so i have created a file to show the cartproducts to user when they are loggedin and a csv file when they cameback and view the cart.

- then the admin part:

    The Admin can do the CRUD-Operation with the categories file & Products file.

    Admin can also view the registered user.

    Admin can also view the Orders of the specific user.

    - In this part, i have faced problem with edit and delete Operation from file in category and products.

    - to solve this problem, i have referred the stackoverflow and solved the problem.

        At first i have read the file, and stored it in the Arraylist of String array. then modified the content and then updated the original file using the csvwriter.

        while writing there comes an another issue, the elements are written with the double quotes in the file. and to remove that i have added csvwriter's in-built thing. (no_quote_string).
        




## Screenshots



