-- Insert data into accounts table
INSERT INTO accounts (id_account, login, password, email) 
VALUES (1, 'user1', 'pass123', 'user1@example.com');

INSERT INTO accounts (id_account, login, password, email) 
VALUES (2, 'user2', 'pass456', 'user2@example.com');

INSERT INTO accounts (id_account, login, password, email) 
VALUES (3, 'user3', 'pass789', 'user3@example.com');

-- Insert data into orders table
INSERT INTO orders (id_order, id_account, price, order_date) 
VALUES (101, 1, 49.99, TO_DATE('2024-01-01', 'YYYY-MM-DD'));

INSERT INTO orders (id_order, id_account, price, order_date) 
VALUES (102, 2, 19.99, TO_DATE('2024-01-02', 'YYYY-MM-DD'));

INSERT INTO orders (id_order, id_account, price, order_date) 
VALUES (103, 1, 79.99, TO_DATE('2024-01-03', 'YYYY-MM-DD'));

INSERT INTO orders (id_order, id_account, price, order_date) 
VALUES (104, 3, 29.99, TO_DATE('2024-01-04', 'YYYY-MM-DD'));
