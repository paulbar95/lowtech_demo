
# lowtech_demo
Demo workshop for lowtech GmbH - CloudComputing WiSe2425


## Setup with Docker
### All-In-One Instance
1: Navigate to the root directory of the project (`lowtech_demo`)
2: Run with default parameters :
```bash
docker compose up -d
```
#### Or run with custom parameters:
##### Example Commands:


Build:
```bash
API_BASE_URL="http://backend:8080/api" docker-compose build
```
Build for Windows cmd:
```bash
set API_BASE_URL=http://backend:8080/api && docker-compose build
```
Run:
```bash
DB_URL="postgres_db:5432" DB_USER="postgres" DB_PASS="root" CORS_ALLOWED_ORIGINS="http://your-frontend-instance-ip" docker-compose up -d
```

Run for Windows cmd:
```bash
set DB_URL=postgres_db:5432
set DB_USER=postgres
set DB_PASS=root
set CORS_ALLOWED_ORIGINS=http://your-frontend-instance-ip
docker-compose up -d
```

### Distributed Instances
Build individual images:
```bash
docker build -t my_postgres ./database
```
```bash
docker build -t my_backend ./webshop
```
```bash
docker build --build-arg API_BASE_URL="http://backend:8080/api" -t my_frontend ./webshop_frontend
```

Run individual images:
```bash
docker run -d --name postgres_db ^
  -p 5432:5432 ^
  my_postgres
```
```bash
docker run -d --name webshop_backend ^
  -p 8080:8080 ^
  -e DB_URL="your-db-instance-ip:5432" ^
  -e DB_USER="postgres" ^
  -e DB_PASS="root" ^
  -e CORS_ALLOWED_ORIGINS="http://your-frontend-instance-ip" ^
  my_backend
```
```bash
docker run -d --name webshop_frontend ^
  -p 80:80 ^
  my_frontend
```

### Parameters Explained


| Variable Name  | Default Value | Description | 
| ------------- | ------------- |------------- |
| `DB_URL`  | `postgres_db:5432`  | The database host and port   |
| `DB_USER`  | `postgres`  | The database username  |
| `DB_PASS`  | `root`  | The database password  |
| `CORS_ALLOWED_ORIGINS`  | `http://localhost,http://localhost:5173`  | The database username  |
| `API_BASE_URL`  | `http://localhost:8080/api`  | The backend API URL that the frontend will communicate with  |

## Manual Setup (for development)

### Setup the database
#### Download and Install
1. Download the latest version of [pg-Admin](https://www.pgadmin.org/download/pgadmin-4-windows/).
2. Download the postgreSQL *server* [version 14.15](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads).
3. Install the postgreSQL *server* and choose **postgres** as *superuser* (if not selected by default) and **root** as *password*.
4. Setup the *server* to run on **port 5432** (if not selected by default).

#### Database and schema
1. Create a new *database* named **db_lowtech**. Select all default settings (assigned user: **postgres**, etc.).
2. Create a new *schema* for **db_lowtech** called **sc_webshop**.

### Setup the backend
1. Open the folder `webshop` in Intellij (or any other suitable IDE).
2. In your IDE select the **project settings** and install the Java SDK `corretto-17.0.13`. --> Set the project's SDK to `corretto-17.0.13`.
3. The IDE should automatically run the `build.gradle` file to load required dependencies. (If not run the `build.gradle` manually or `rebuild` the project.)
4. Start the **backend server** by executing the `WebshopApplication.java`. (Your IDE should automatically detect a run-configuration pointing to the `WebshopApplication.java` - if not, configure it manually.)
5. Your **backend server** is now running on **http://localhost:8080**.

### Setup the frontend
1. Open the folder `webshop_frontend` in Intellij (or any other suitable IDE).
2. Open a **terminal** (terminal is prefered - not powershell). Run the comman `npm install` to install the required frontend-dependencies.
3. Run the command `npm start` to start the **frontend server** on **http://localhost:5173**.

## WebshopApplication
Use the web application's **Add Product** tab the *frontend* provides to manage **products** with **CRUD**-Operations and view the products in a **Product Catalog**.
To enable adding an item to your **Shopping Cart**, first add an corresponding inventory entry and select a desired **quantity** for your product.
Once the inventory is set you can add items to your **Shopping Cart**. Open your **Shopping Cart** and select the amount of products you want to buy. Then proceed to **Checkout ** to finalize your order.
Orders can be managed and viewed in the **Order Management** tab.