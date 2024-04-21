-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 20, 2024 at 04:19 PM
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
-- Table structure for table `api_keys`
--

CREATE TABLE `api_keys` (
  `api_id` int(11) NOT NULL,
  `api_key` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `issuedby` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `issuedon` date NOT NULL DEFAULT current_timestamp(),
  `duration` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `key_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'HEALTHY'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `api_request`
--

CREATE TABLE `api_request` (
  `accessid` int(20) NOT NULL,
  `ip` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `date` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `time` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `certificate_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `api_key` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
(1, '(0,390)', '(0,1000)', '(-30,-1000)', 'NIL', '(160,82,45)', '(160,82,45)', '(160,82,45)', '(160,82,45)', '250', '100', '100', '100', '100', '(160,82,45)', 'fonts/ArianaVioleta.ttf', 'appreciation_1', 'assets/certificate_of_appreciation/appreciation_1', '82'),
(2, '(0,100)', '(0,950)', '(240,360)', 'NIL', '(218,165,32)', '(0,0,0)', '(0,0,0)', '(0,0,0)', '200', '100', '80', '100', '100', '(160,82,45)', 'fonts/Comfortaa.ttf', 'participation', 'assets/certificate_of_appreciation/participation', '143'),
(3, '(0,100)', '(-150,700)', '(50,-900)', '(470,680)', '(160,82,45)', '(160,82,45)', '(255,255,255)', '(160,82,45)', '220', '70', '150', '150', '70', '(255,255,255)', 'fonts/ArianaVioleta.ttf', 'bestemployee', 'assets/certificate_of_appreciation/bestemployee', '182');

-- --------------------------------------------------------

--
-- Table structure for table `font_db`
--

CREATE TABLE `font_db` (
  `font_id` int(100) NOT NULL,
  `font_name` varchar(100) NOT NULL,
  `font_location` varchar(100) NOT NULL,
  `font_used` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `font_db`
--

INSERT INTO `font_db` (`font_id`, `font_name`, `font_location`, `font_used`) VALUES
(1, 'AbrilFatface', 'fonts/AbrilFatface.ttf', ''),
(2, 'ArianaVioleta', 'fonts/ArianaVioleta.ttf', ''),
(3, 'Comfortaa', 'fonts/Comfortaa.ttf', '');

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `ip` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `location` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `time` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `accessid` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `api_keys`
--
ALTER TABLE `api_keys`
  ADD PRIMARY KEY (`api_id`);

--
-- Indexes for table `api_request`
--
ALTER TABLE `api_request`
  ADD PRIMARY KEY (`accessid`);

--
-- Indexes for table `certificate_db`
--
ALTER TABLE `certificate_db`
  ADD PRIMARY KEY (`certificate_id`);

--
-- Indexes for table `font_db`
--
ALTER TABLE `font_db`
  ADD PRIMARY KEY (`font_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `api_keys`
--
ALTER TABLE `api_keys`
  MODIFY `api_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `api_request`
--
ALTER TABLE `api_request`
  MODIFY `accessid` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `certificate_db`
--
ALTER TABLE `certificate_db`
  MODIFY `certificate_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `font_db`
--
ALTER TABLE `font_db`
  MODIFY `font_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
