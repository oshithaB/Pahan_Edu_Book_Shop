-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Aug 20, 2025 at 08:48 AM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pahana_bookshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
CREATE TABLE IF NOT EXISTS `bills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bill_number` varchar(20) NOT NULL,
  `customer_id` int NOT NULL,
  `cashier_id` int NOT NULL,
  `subtotal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `tax_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `discount_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `payment_status` enum('pending','paid','cancelled') DEFAULT 'paid',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `bill_number` (`bill_number`),
  KEY `cashier_id` (`cashier_id`),
  KEY `idx_bills_bill_number` (`bill_number`),
  KEY `idx_bills_customer_id` (`customer_id`),
  KEY `idx_bills_created_at` (`created_at`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bills`
--

INSERT INTO `bills` (`id`, `bill_number`, `customer_id`, `cashier_id`, `subtotal`, `tax_amount`, `discount_amount`, `total_amount`, `payment_status`, `created_at`) VALUES
(1, 'BILL000001', 2, 2, 4500.00, 0.00, 0.00, 4500.00, 'paid', '2025-07-28 16:37:20'),
(2, 'BILL000002', 1, 2, 4500.00, 0.00, 0.00, 4500.00, 'paid', '2025-07-28 19:40:44'),
(3, 'BILL000003', 3, 2, 13500.00, 900.00, 2700.00, 11700.00, 'paid', '2025-07-28 21:10:50'),
(4, 'BILL000004', 2, 2, 4500.00, 90.00, 0.00, 4590.00, 'paid', '2025-08-09 02:38:11'),
(5, 'BILL000005', 1, 2, 4500.00, 0.00, 0.00, 4500.00, 'paid', '2025-08-11 01:54:05'),
(6, 'BILL000006', 1, 2, 4500.00, 0.00, 0.00, 4500.00, 'paid', '2025-08-11 03:22:52'),
(7, 'BILL000007', 1, 2, 4500.00, 87.30, 135.00, 4452.30, 'paid', '2025-08-16 01:44:51'),
(8, 'BILL000008', 3, 2, 4500.00, 0.00, 0.00, 4500.00, 'paid', '2025-08-20 01:56:19'),
(9, 'BILL000009', 1, 2, 3200.00, 0.00, 0.00, 3200.00, 'paid', '2025-08-20 01:56:53'),
(10, 'BILL000010', 6, 2, 18600.00, 1045.08, 546.00, 19099.08, 'paid', '2025-08-20 03:19:00');

-- --------------------------------------------------------

--
-- Table structure for table `bill_items`
--

DROP TABLE IF EXISTS `bill_items`;
CREATE TABLE IF NOT EXISTS `bill_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bill_id` int NOT NULL,
  `book_id` int NOT NULL,
  `quantity` int NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `tax_rate` decimal(5,2) DEFAULT '0.00',
  `discount_rate` decimal(5,2) DEFAULT '0.00',
  `tax_amount` decimal(10,2) DEFAULT '0.00',
  `discount_amount` decimal(10,2) DEFAULT '0.00',
  `line_total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `bill_id` (`bill_id`),
  KEY `book_id` (`book_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bill_items`
--

INSERT INTO `bill_items` (`id`, `bill_id`, `book_id`, `quantity`, `unit_price`, `tax_rate`, `discount_rate`, `tax_amount`, `discount_amount`, `line_total`) VALUES
(1, 1, 4, 1, 4500.00, 0.00, 0.00, 0.00, 0.00, 4500.00),
(2, 2, 4, 1, 4500.00, 0.00, 0.00, 0.00, 0.00, 4500.00),
(3, 3, 4, 1, 4500.00, 20.00, 0.00, 900.00, 0.00, 5400.00),
(4, 3, 4, 2, 4500.00, 0.00, 30.00, 0.00, 2700.00, 6300.00),
(5, 4, 4, 1, 4500.00, 2.00, 0.00, 90.00, 0.00, 4590.00),
(6, 5, 4, 1, 4500.00, 0.00, 0.00, 0.00, 0.00, 4500.00),
(7, 6, 4, 1, 4500.00, 0.00, 0.00, 0.00, 0.00, 4500.00),
(8, 7, 4, 1, 4500.00, 2.00, 3.00, 87.30, 135.00, 4452.30),
(9, 8, 4, 1, 4500.00, 0.00, 0.00, 0.00, 0.00, 4500.00),
(10, 9, 2, 1, 3200.00, 0.00, 0.00, 0.00, 0.00, 3200.00),
(11, 10, 4, 2, 4500.00, 10.00, 5.00, 855.00, 450.00, 9405.00),
(12, 10, 2, 3, 3200.00, 2.00, 1.00, 190.08, 96.00, 9694.08);

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `author` varchar(100) DEFAULT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `quantity` int DEFAULT '0',
  `description` text,
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn` (`isbn`),
  KEY `idx_books_title` (`title`),
  KEY `idx_books_isbn` (`isbn`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `isbn`, `category`, `price`, `quantity`, `description`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 'Java Programming Fundamentals', 'Robert Martin', '978-0134685991', 'Programming', 2500.00, 50, 'Complete guide to Java programming', 1, '2025-07-28 15:34:00', '2025-07-28 15:34:00'),
(2, 'Database Systems Concepts', 'Abraham Silberschatz', '978-0073523323', 'Database', 3200.00, 0, 'Comprehensive database systems textbook', 1, '2025-07-28 15:34:00', '2025-08-20 07:09:10'),
(3, 'Web Development with HTML & CSS', 'John Duckett', '978-1118008188', 'Web Development', 1800.00, 25, 'Modern web development techniques', 1, '2025-07-28 15:34:00', '2025-07-28 15:34:00'),
(4, 'Data Structures and Algorithms', 'Thomas Cormen', '978-0262033848', 'Computer Science', 4500.00, 8, 'Classic algorithms and data structures', 1, '2025-07-28 15:34:00', '2025-08-20 03:19:00'),
(5, 'Network Security Essentials', 'William Stallings', '978-0134527338', 'Security', 2800.00, 15, 'Network security fundamentals', 1, '2025-07-28 15:34:00', '2025-07-28 15:34:00'),
(6, 'test book', 'test author', '9781234567890', 'Programming', 30.00, 45, '', 0, '2025-08-20 01:37:55', '2025-08-20 01:44:44');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_number` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` text,
  `telephone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_number` (`account_number`),
  KEY `idx_customers_account_number` (`account_number`),
  KEY `idx_customers_name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `account_number`, `name`, `address`, `telephone`, `email`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 'CUST001', 'John Doe', '123 Main Street, Colombo 01', '0771234567', 'john.doe@email.com', 1, '2025-07-28 15:34:00', '2025-07-28 15:34:00'),
(2, 'CUST002', 'Jane Smit', '456 Oak Avenue, Kandy', '0772345678', 'jane.smith@email.com', 0, '2025-07-28 15:34:00', '2025-08-11 03:14:38'),
(3, 'CUST003', 'Mike Johnson', '789 Pine Road, Galle', '0773456789', 'mike.johnson@email.com', 1, '2025-07-28 15:34:00', '2025-07-28 15:34:00'),
(4, 'CUST004', 'oshitha kalhara', 'asss\r\nasd', '0783396691', 'oxxakala@gmail.com', 1, '2025-07-28 21:28:23', '2025-07-28 21:28:23'),
(5, 'CUST005', 'jane Smith', '456 Oak Ave', '0771234569', 'jane@test.com', 1, '2025-08-20 01:20:04', '2025-08-20 01:23:41'),
(6, 'CUST006', 'test customer', 'test address', '077111111', 'test@test.com', 1, '2025-08-20 03:01:11', '2025-08-20 03:01:11');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('admin','cashier') NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_users_username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `full_name`, `email`, `phone`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 'admin', '$2a$12$3f8CrMsHEVNM5cQRZKzDQ.THdpV.0GC.gH3.FdHC6sOr5ks/Bl2Lq', 'admin', 'System Administrator', 'admin@pahanaedu.com', '0766961189', 1, '2025-07-28 15:34:00', '2025-08-20 07:16:58'),
(2, 'cashier', '$2a$12$CoA74sTSOEKcnjT3ndhaRuoJChmtbzomO3AfS.1KqAxVFZTimkOZ6', 'cashier', 'Default Cashier', 'cashier@pahanaedu.com', '123456789', 1, '2025-07-28 15:34:00', '2025-08-20 07:17:09'),
(3, 'oshitha', '$2a$12$K1ldqDhhVmfFziKuCMrL1.p5BBEa3x3pldBPgqEQ1eb25sJuQdFu6', 'cashier', 'System Administrator', 'cashier@pahanaedu.com', '0123455678', 0, '2025-08-12 22:21:30', '2025-08-20 01:06:01'),
(4, 'john01', '$2a$12$8QVis4qPvEps80kTvLFEtuEQeSMKqYD3X32BssluBmMUpnn6Qo46S', 'cashier', 'John Doe', 'john.doe@test.com', '0761234567', 1, '2025-08-20 01:01:47', '2025-08-20 01:04:19');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
