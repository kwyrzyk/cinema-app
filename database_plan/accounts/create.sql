CREATE TABLE accounts (
    id_account INT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) -- Added phone_number column
);

CREATE TABLE orders (
    id_order INT PRIMARY KEY,
    id_account INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    order_date DATE NOT NULL,
    FOREIGN KEY (id_account) REFERENCES accounts(id_account) ON DELETE CASCADE
);
