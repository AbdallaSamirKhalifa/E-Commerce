-- 1. Identity Domain
CREATE TABLE IF NOT EXISTS Users (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_first_name VARCHAR(100),
    user_last_name VARCHAR(100),
    user_email VARCHAR(150) UNIQUE NOT NULL,
    user_password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS customer(
    cust_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS customer_addresse (
    address_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cust_id INT NOT NULL, -- References customer(cust_id),
    label VARCHAR(20) NOT NULL,
    city VARCHAR(20) NOT NULL,
    street VARCHAR(100) NOT NULL,
    notes VARCHAR(250) NOT NULL
);

-- 2. Catalog Domain
CREATE TABLE IF NOT EXISTS category (
    cat_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cat_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
    prod_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    prod_name VARCHAR(150) NOT NULL,
    prod_description VARCHAR(500),
    prod_price DECIMAL(10, 2),
    is_available BIT DEFAULT CAST(1 AS BIT),
    cat_id INT -- Reference categories(cat_id)
);

-- 3. Cart & Order Domain

CREATE TABLE IF NOT EXISTS cart(
    cart_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cust_id INT NOT NULL,    -- Reference customer(cust_id)
    is_locked BIT DEFAULT CAST(1 AS BIT)
);
CREATE TABLE IF NOT EXISTS cart_items (
    cart_item_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    prod_id INT NOT NULL, -- Reference Product(prod_id)
    qty INT DEFAULT 1 CHECK(qty>0),
    cart_id INT UNIQUE NOT NULL -- REFERENCES car(cart_id)
);

CREATE TABLE IF NOT EXISTS Orders (
    Order_ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cust_id INT NOT NULL,    -- Reference to customer(cust_id)
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    ord_note VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS Order_Items (
    Order_Item_ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ord_id INT NOT NULL,   -- Reference to Orders.Order_ID
    prod_id INT NOT NULL, -- Reference to Products.Product_ID
    qty INT DEFAULT 1 CHECK(qty>0),
    price DECIMAL(10, 2) NOT NULL -- Snapshot price at time of order
);