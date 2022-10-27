-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.6.5-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for inventory
CREATE DATABASE IF NOT EXISTS `inventory` /*!40100 DEFAULT CHARACTER SET utf8mb3 */;
USE `inventory`;

-- Dumping structure for table inventory.employees
CREATE TABLE IF NOT EXISTS `employees` (
  `EMP_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `EMP_PASS` varchar(50) DEFAULT NULL,
  `EMP_NAME` varchar(50) DEFAULT NULL,
  `EMP_JOB` varchar(50) DEFAULT NULL,
  `EMP_SSN` varchar(50) DEFAULT NULL,
  `EMP_SALARY` float DEFAULT NULL,
  PRIMARY KEY (`EMP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6971 DEFAULT CHARSET=utf8mb3 COMMENT='employees table';

-- Dumping data for table inventory.employees: ~11 rows (approximately)
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` (`EMP_ID`, `EMP_PASS`, `EMP_NAME`, `EMP_JOB`, `EMP_SSN`, `EMP_SALARY`) VALUES
	(1000, 'pass', 'TEST', 'IT MANAGER', NULL, NULL),
	(1123, '4qh5VBEy', 'Karrie Rusty', 'EMPLOYEE', '648-38-9123', 26000),
	(1132, '758QseD6', 'Bryanna Gloria', 'MANAGER', '478-20-5512', 25000),
	(1220, '8bD6duuG', 'Parker Brandt', 'IT MANAGER', '018-38-2025', 46000),
	(1245, 'eXuQv52z', 'Rodney Harleigh', 'MANAGER', '089-90-9208', 25000),
	(1311, '9uZYGz3g', 'Dee Jim', 'EMPLOYEE', '029-88-3642', 20000),
	(1344, 'Wwrk4HwV', 'Lincoln Astra', 'EMPLOYEE', '417-33-7647', 30000),
	(1367, 'pHbmBs2U', 'Angelia Laura', 'EMPLOYEE', '483-23-5501', 21000),
	(1427, 'F3mGpK9v', 'Alani Clement', 'EMPLOYEE', '516-74-7437', 35000),
	(1435, 'SAtkTJbD', 'Valentine Edythe', 'MANAGER', '668-34-5367', 34000),
	(1452, 'xJqdwJc6', 'Geoff Warren', 'EMPLOYEE', '648-20-3227', 49000);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;

-- Dumping structure for table inventory.products
CREATE TABLE IF NOT EXISTS `products` (
  `PT_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `PT_NAME` varchar(50) DEFAULT NULL,
  `PT_STOCK` int(11) DEFAULT NULL,
  `PT_DESC` varchar(50) DEFAULT NULL,
  `PT_STATUS` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=123124 DEFAULT CHARSET=utf8mb3 COMMENT='products table';

-- Dumping data for table inventory.products: ~10 rows (approximately)
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`PT_ID`, `PT_NAME`, `PT_STOCK`, `PT_DESC`, `PT_STATUS`) VALUES
	(10000, 'APPLE', 134, 'RED', 'GOOD'),
	(10020, 'WATERMELON', 37, 'GREEN', 'LOW'),
	(10026, 'BANANA', 112, 'YELLOW', 'GOOD'),
	(10034, 'ORANGE', 245, 'ORANGE', 'GOOD'),
	(10044, 'STRAWBERRIES', 78, 'SMALL, RED', 'LOW'),
	(10065, 'POTATO', 324, 'BROWN', 'GREAT'),
	(10067, 'WHOLE MILK', 67, 'DAIRY', 'OK'),
	(10075, 'LEMON', 159, 'LEMON', 'GREAT'),
	(10087, 'CARROT', 250, 'ORANGE', 'OK'),
	(10090, 'KETCHUP', 456, 'RED', 'GREAT');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
