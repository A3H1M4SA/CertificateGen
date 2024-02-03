-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 03, 2024 at 06:01 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `certificate_gen`
--

-- --------------------------------------------------------

--
-- Table structure for table `certificate_db`
--

CREATE TABLE `certificate_db` (
  `certificate_id` int(100) NOT NULL,
  `name_loc` varchar(100) NOT NULL,
  `signedby_loc` varchar(100) NOT NULL,
  `company_loc` varchar(200) NOT NULL,
  `date_loc` varchar(100) NOT NULL,
  `name_textcolor` varchar(100) NOT NULL,
  `signedby_textcolor` varchar(100) NOT NULL,
  `company_textcolor` varchar(100) NOT NULL,
  `date_textcolor` varchar(100) NOT NULL,
  `name_fontsize` varchar(100) NOT NULL,
  `signedby_fontsize` varchar(100) NOT NULL,
  `company_fontsize` varchar(100) NOT NULL,
  `other_fontsize` varchar(100) NOT NULL,
  `date_fontsize` varchar(200) NOT NULL,
  `othertext_color` varchar(100) NOT NULL,
  `primaryfont` varchar(100) NOT NULL,
  `certificate_name` varchar(100) NOT NULL,
  `certificate_path` varchar(100) NOT NULL,
  `certificate_used` varchar(100) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `certificate_db`
--

INSERT INTO `certificate_db` (`certificate_id`, `name_loc`, `signedby_loc`, `company_loc`, `date_loc`, `name_textcolor`, `signedby_textcolor`, `company_textcolor`, `date_textcolor`, `name_fontsize`, `signedby_fontsize`, `company_fontsize`, `other_fontsize`, `date_fontsize`, `othertext_color`, `primaryfont`, `certificate_name`, `certificate_path`, `certificate_used`) VALUES
(1, '(0,300)', '(0,1000)', '(0,0)', 'NIL', '(160,82,45)', '(160,82,45)', '(160,82,45)', '(160,82,45)', '100', '100', '100', '100', '100', '(160,82,45)', 'fonts/ArianaVioleta.ttf', 'appreciation_1', 'assets/certificate_of_appreciation/appreciation_1', '22'),
(2, '(0,150)', '(0,950)', '(200,380)', 'NIL', '(160,82,45)', '(160,82,45)', '(160,82,45)', '(160,82,45)', '300', '100', '100', '100', '100', '(160,82,45)', 'fonts/ArianaVioleta.ttf', 'participation_1', 'assets/certificate_of_appreciation/participation_1', '81'),
(3, '(0,100)', '(-150,700)', '(50,-900)', '(470,680)', '(160,82,45)', '(160,82,45)', '(255,255,255)', '(160,82,45)', '220', '70', '150', '150', '70', '(255,255,255)', 'fonts/ArianaVioleta.ttf', 'bestemployee', 'assets/certificate_of_appreciation/bestemployee', '111');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `certificate_db`
--
ALTER TABLE `certificate_db`
  ADD PRIMARY KEY (`certificate_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `certificate_db`
--
ALTER TABLE `certificate_db`
  MODIFY `certificate_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
