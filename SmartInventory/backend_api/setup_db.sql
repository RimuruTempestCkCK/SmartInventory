-- ============================================
-- DATABASE: db_inventory_aksesoris
-- ============================================

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin','staff') DEFAULT 'admin'
);

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE brands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address TEXT,
    phone VARCHAR(20)
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    category_id INT,
    brand_id INT,
    supplier_id INT,
    buy_price DECIMAL(15,2),
    sell_price DECIMAL(15,2),
    stok INT DEFAULT 0,
    lokasi_rak VARCHAR(50),
    warna VARCHAR(50),
    kondisi ENUM('Baru','Second') DEFAULT 'Baru',
    gambar VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(category_id) REFERENCES categories(id),
    FOREIGN KEY(brand_id) REFERENCES brands(id),
    FOREIGN KEY(supplier_id) REFERENCES suppliers(id)
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    type ENUM('in','out','reject') NOT NULL,
    qty INT NOT NULL,
    date DATE NOT NULL,
    description TEXT,
    FOREIGN KEY(product_id) REFERENCES products(id) ON DELETE CASCADE
);
