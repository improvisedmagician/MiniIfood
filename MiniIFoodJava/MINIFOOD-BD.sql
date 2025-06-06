-- Recria o banco de dados
DROP DATABASE IF EXISTS minifood;
CREATE DATABASE minifood;
USE minifood;

-- Tabela Restaurant
CREATE TABLE Restaurant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(150) NOT NULL,
    city VARCHAR(100) NOT NULL
);

-- Tabela Product
CREATE TABLE Product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    restaurant_id INT NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE
);

-- Tabela Client
CREATE TABLE Client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    cep VARCHAR(20) NOT NULL,
    address VARCHAR(150) NOT NULL
);

-- Tabela PaymentMethod
CREATE TABLE PaymentMethod (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(50) NOT NULL
);

-- Tabela Orders
CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    restaurant_id INT NOT NULL,
    order_date DATETIME NOT NULL,
    delivery_time INT NOT NULL,
    payment_method_id INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES Client(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE,
    FOREIGN KEY (payment_method_id) REFERENCES PaymentMethod(id) ON DELETE CASCADE
);

-- Tabela OrderItem
CREATE TABLE OrderItem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Product(id) ON DELETE CASCADE
);

-- Tabela Accompaniment
CREATE TABLE Accompaniment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

-- Tabela de relacionamento entre OrderItem e Accompaniment
CREATE TABLE OrderItemAccompaniment (
    order_item_id INT NOT NULL,
    accompaniment_id INT NOT NULL,
    PRIMARY KEY(order_item_id, accompaniment_id),
    FOREIGN KEY (order_item_id) REFERENCES OrderItem(id) ON DELETE CASCADE,
    FOREIGN KEY (accompaniment_id) REFERENCES Accompaniment(id) ON DELETE CASCADE
);

-- Inserção de métodos de pagamento
INSERT INTO PaymentMethod (description) VALUES 
('Cash'), 
('Credit Card'), 
('Debit Card'), 
('Pix');

-- Inserção de restaurantes
INSERT INTO Restaurant (name, address, city) VALUES
('Pizzaria Central', 'Rua das Flores, 123', 'São Paulo'),
('Burger House', 'Av. Brasil, 456', 'Rio de Janeiro'),
('Sushi Express', 'Praça Central, 789', 'Curitiba');

-- Inserção de produtos
INSERT INTO Product (name, price, restaurant_id) VALUES
('Pizza Margherita', 35.00, 1),
('Pizza Pepperoni', 40.00, 1),
('Hambúrguer Clássico', 25.00, 2),
('Hambúrguer Duplo', 30.00, 2),
('Combo Sushi', 50.00, 3);

-- Inserção de clientes
INSERT INTO Client (name, email, cep, address) VALUES
('Ana Silva', 'ana.silva@email.com', '01001-000', 'Rua A, 100'),
('Carlos Souza', 'carlos.souza@email.com', '02002-000', 'Av. B, 200'),
('Mariana Lima', 'mariana.lima@email.com', '03003-000', 'Rua C, 300'),
('Felipe Rocha', 'felipe.rocha@email.com', '04004-000', 'Av. D, 400');

-- Inserção de acompanhamentos
INSERT INTO Accompaniment (name, price) VALUES
('Batata Frita', 8.00),
('Coca-Cola 350ml', 5.00),
('Molho Especial', 2.50),
('Salada Verde', 7.00),
('Anéis de Cebola', 6.50),
('Suco de Laranja', 6.00);

-- Inserção de pedidos
INSERT INTO Orders (client_id, restaurant_id, order_date, delivery_time, payment_method_id, total_price) VALUES
(1, 1, '2025-05-20 19:00:00', 30, 2, 85.50),
(2, 2, '2025-05-21 20:15:00', 25, 1, 55.00),
(3, 3, '2025-05-22 18:30:00', 40, 4, 65.00),
(4, 1, '2025-05-23 21:00:00', 35, 3, 40.00),
(1, 2, '2025-05-24 19:45:00', 20, 2, 35.00);

-- Inserção de itens do pedido
INSERT INTO OrderItem (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 2, 35.00),
(1, 2, 1, 40.00),
(2, 3, 2, 25.00),
(3, 5, 1, 50.00),
(4, 2, 1, 40.00),
(5, 4, 1, 30.00);

-- Inserção de acompanhamentos por item de pedido
INSERT INTO OrderItemAccompaniment (order_item_id, accompaniment_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 1),
(3, 4),
(4, 6),
(5, 5),
(6, 2);
