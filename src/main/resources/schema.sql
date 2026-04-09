-- 1. Identity Domain
CREATE DATABASE e_commerce

CREATE TABLE IF NOT EXISTS roles(
    role_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);



CREATE TABLE IF NOT EXISTS users (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_first_name VARCHAR(100) NOT NULL,
    user_last_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(150) UNIQUE NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    is_enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS user_role(
    user_id INT NOT NULL REFERENCES users(user_id),
    role_id INT NOT NULL REFERENCES roles(role_id)
);

CREATE TABLE IF NOT EXISTS customer(
    cust_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT UNIQUE NOT NULL REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS customer_address (
    address_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cust_id INT NOT NULL References customer(cust_id),
    label VARCHAR(20) NOT NULL,
    city VARCHAR(20) NOT NULL,
    street VARCHAR(100) NOT NULL,
    notes VARCHAR(250)
);
    CREATE INDEX idx_customer_address_cust_id ON customer_address(cust_id);
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
    is_available BOOLEAN DEFAULT TRUE,
    cat_id INT NOT NULL References category(cat_id)
);

    CREATE INDEX idx_product_cat_id ON product(cat_id);
-- 3. Cart & Order Domain

CREATE TABLE IF NOT EXISTS cart(
    cart_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cust_id INT NOT NULL  References customer(cust_id),
    is_locked BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_cart_cust_id ON cart(cust_id);

CREATE TABLE IF NOT EXISTS cart_item (
    cart_item_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    prod_id INT NOT NULL  References product(prod_id),
    qty INT DEFAULT 1 CHECK(qty>0),
    cart_id INT NOT NULL REFERENCES cart(cart_id)
);
    CREATE INDEX idx_cart_item_prod_id ON cart_item(prod_id);

CREATE TABLE IF NOT EXISTS orders (
    order_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cust_id INT NOT NULL References customer(cust_id),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    ord_note VARCHAR(500),
    address_id INT NOT NULL REFERENCES customer_address(address_id)
);
    CREATE INDEX idx_cust_id ON Orders(cust_id);

CREATE TABLE IF NOT EXISTS order_item (
    order_item_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id INT NOT NULL References  orders(order_id),
    prod_id INT NOT NULL References  product(prod_id),
    qty INT DEFAULT 1 CHECK(qty>0),
    price DECIMAL(10, 2) NOT NULL, -- Snapshot price at time of order,
    subtotal DECIMAL(10,2) NOT NULL
);
    CREATE INDEX idx_order_item_order_id ON order_item(order_id);