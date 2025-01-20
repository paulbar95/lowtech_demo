# lowtech_demo
Demo workshop for lowtech GmbH - CloudComputing WiSe2425

## Setup the database
### Download and Install
1. Download the latest version of [pg-Admin](https://www.pgadmin.org/download/pgadmin-4-windows/).
2. Download the postgreSQL *server* [version 14.15](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads).
3. Install the postgreSQL *server* and choose **postgres** as *superuser* (if not selected by default) and **root** as *password*.
4. Setup the *server* to run on **port 5432** (if not selected by default).

### Database and schema
1. Create a new *database* named **db_lowtech**. Select all default settings (assigned user: **postgres**, etc.).
2. Create a new *schema* for **db_lowtech** called **sc_webshop**.

## Setup the backend
1. Open the folder `webshop` in Intellij (or any other suitable IDE).
2. The IDE should automatically run the `build.gradle` file to load required dependencies. (If not run the `build.gradle` manually or `rebuild` the project.)
3. Start the **backend server** by executing the `WebshopApplication.java`. (Your IDE should automatically detect a run-configuration pointing to the `WebshopApplication.java` - if not, configure it manually.)
4. Your **backend server** is now running on **http://localhost:8080**.

## Setup the frontend
1. Open the folder `webshop_frontend` in Intellij (or any other suitable IDE).
2. Open a **terminal** (terminal is prefered - not powershell). Run the comman `npm install` to install the required frontend-dependencies.
3. Run the command `npm start` to start the **frontend server** on **http://localhost:5173**.

## WebshopApplication
Use the web application's **Add Product** tab the *frontend* provides to manage **products** with **CRUD**-Operations and view the products in a **Product Catalog**.
To enable adding an item to your **Shopping Cart**, first add an corresponding inventory entry and select a desired **quantity** for your product.
Once the inventory is set you can add items to your **Shopping Cart**. Open your **Shopping Cart** and select the amount of products you want to buy. Then proceed to **Checkout ** to finalize your order.
Orders can be managed and viewed in the **Order Management** tab.