-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 01, 2026 at 10:58 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_inventory_aksesoris`
--

-- --------------------------------------------------------

--
-- Table structure for table `brands`
--

CREATE TABLE `brands` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `brands`
--

INSERT INTO `brands` (`id`, `name`) VALUES
(1, 'Anker'),
(2, 'Apple'),
(3, 'Samsung'),
(4, 'Baseus'),
(5, 'Ugreen'),
(6, 'Robot'),
(7, 'Vivan'),
(8, 'Xiaomi');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Charger'),
(2, 'Kabel'),
(3, 'Earphone'),
(4, 'Power Bank'),
(5, 'Case'),
(6, 'Tempered Glass'),
(7, 'Adapter'),
(8, 'Holder HP');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `supplier_id` int(11) DEFAULT NULL,
  `buy_price` decimal(15,2) DEFAULT NULL,
  `sell_price` decimal(15,2) DEFAULT NULL,
  `stok` int(11) DEFAULT 0,
  `lokasi_rak` varchar(50) DEFAULT NULL,
  `warna` varchar(50) DEFAULT NULL,
  `kondisi` enum('Baru','Second') DEFAULT 'Baru',
  `gambar` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `code`, `name`, `category_id`, `brand_id`, `supplier_id`, `buy_price`, `sell_price`, `stok`, `lokasi_rak`, `warna`, `kondisi`, `gambar`, `created_at`) VALUES
(1, 'PRD001', 'Charger iPhone 20W', 1, 2, 1, 180000.00, 250000.00, 25, 'A01', 'Putih', 'Baru', 'charger_iphone.jpg', '2026-07-01 07:59:59'),
(2, 'PRD002', 'Power Bank 10000mAh', 4, 1, 2, 250000.00, 350000.00, 15, 'A02', 'Hitam', 'Baru', 'powerbank.jpg', '2026-07-01 07:59:59'),
(3, 'PRD003', 'Kabel Type C Fast Charging', 2, 4, 2, 35000.00, 70000.00, 50, 'B01', 'Putih', 'Baru', 'kabel.jpg', '2026-07-01 07:59:59'),
(4, 'PRD004', 'Earphone Bluetooth', 3, 6, 3, 120000.00, 190000.00, 18, 'B02', 'Hitam', 'Baru', 'earphone.jpg', '2026-07-01 07:59:59'),
(5, 'PRD005', 'Case iPhone 15 Pro', 5, 5, 2, 45000.00, 90000.00, 30, 'C01', 'Bening', 'Baru', 'case.jpg', '2026-07-01 07:59:59'),
(6, 'PRD006', 'Tempered Glass Samsung A55', 6, 3, 3, 15000.00, 45000.00, 60, 'C02', 'Transparan', 'Baru', 'tg.jpg', '2026-07-01 07:59:59'),
(7, 'PRD007', 'Holder Mobil Magnetic', 8, 8, 1, 30000.00, 80000.00, 20, 'D01', 'Hitam', 'Baru', 'holder.jpg', '2026-07-01 07:59:59'),
(8, 'PRD008', 'Adapter USB-C to USB', 7, 4, 1, 40000.00, 85000.00, 12, 'D02', 'Abu-abu', 'Baru', 'adapter.jpg', '2026-07-01 07:59:59');

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` text DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`id`, `name`, `address`, `phone`) VALUES
(1, 'CV Sumber Gadget', 'Jakarta', '081111111111'),
(2, 'PT Aksesoris Nusantara', 'Bandung', '082222222222'),
(3, 'PT Digital Indonesia', 'Surabaya', '083333333333');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `type` enum('in','out','reject') NOT NULL,
  `qty` int(11) NOT NULL,
  `date` date NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `product_id`, `type`, `qty`, `date`, `description`) VALUES
(1, 1, 'in', 25, '2026-07-01', 'Stok awal'),
(2, 2, 'in', 15, '2026-07-01', 'Stok awal'),
(3, 3, 'in', 50, '2026-07-01', 'Stok awal'),
(4, 4, 'in', 18, '2026-07-01', 'Stok awal'),
(5, 5, 'in', 30, '2026-07-01', 'Stok awal'),
(6, 6, 'in', 60, '2026-07-01', 'Stok awal'),
(7, 7, 'in', 20, '2026-07-01', 'Stok awal'),
(8, 8, 'in', 12, '2026-07-01', 'Stok awal'),
(9, 3, 'out', 5, '2026-07-02', 'Penjualan'),
(10, 5, 'out', 2, '2026-07-02', 'Penjualan'),
(11, 6, 'reject', 1, '2026-07-02', 'Barang rusak');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('admin','staff') DEFAULT 'admin'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'admin', 'admin123', 'admin'),
(2, 'staff', 'staff123', 'staff');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brands`
--
ALTER TABLE `brands`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`),
  ADD KEY `fk_product_category` (`category_id`),
  ADD KEY `fk_product_brand` (`brand_id`),
  ADD KEY `fk_product_supplier` (`supplier_id`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_transaction_product` (`product_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brands`
--
ALTER TABLE `brands`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_product_brand` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  ADD CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `fk_product_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `fk_transaction_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
