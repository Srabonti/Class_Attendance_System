-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 07, 2017 at 08:42 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `class_attendance`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE IF NOT EXISTS `admins` (
  `admin_id` int(11) NOT NULL,
  `admin_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `admin_email` varchar(100) COLLATE utf8_bin NOT NULL,
  `admin_contact` char(20) COLLATE utf8_bin NOT NULL,
  `admin_pass` varchar(32) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`admin_id`, `admin_name`, `admin_email`, `admin_contact`, `admin_pass`) VALUES
(6, 'Srabonti Dutta', 'sd.mukta12@gmail.com', '01777697621', 'adeb3524f48bee777e78a2bc535f55fa'),
(7, 'Priya Das', 'priya_cse36@yahoo.com', '01673240508', '15e58cefc151442625159338c4dd5f1b'),
(8, 'Partha Pratim Nath', 'parthanath95@gmail.com', '01679245300', 'a553288817b7309b83c0dc0e76656779');

-- --------------------------------------------------------

--
-- Table structure for table `attendances`
--

CREATE TABLE IF NOT EXISTS `attendances` (
  `attendance_id` int(10) NOT NULL,
  `attended_classes` int(11) DEFAULT '0',
  `attendance_state` char(2) COLLATE utf8_bin NOT NULL DEFAULT 'A',
  `attended_date` date NOT NULL,
  `course_id` int(10) NOT NULL,
  `student_id` char(10) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE IF NOT EXISTS `courses` (
  `course_id` int(10) NOT NULL,
  `course_code` char(15) COLLATE utf8_bin NOT NULL,
  `course_title` varchar(255) COLLATE utf8_bin NOT NULL,
  `course_credit` int(11) NOT NULL,
  `admin_id` int(10) NOT NULL,
  `semester_id` char(5) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `course_info`
--

CREATE TABLE IF NOT EXISTS `course_info` (
  `course_id` int(10) NOT NULL,
  `course_code` char(15) COLLATE utf8_bin NOT NULL,
  `course_title` varchar(255) COLLATE utf8_bin NOT NULL,
  `course_credit` char(2) COLLATE utf8_bin NOT NULL,
  `course_type` char(10) COLLATE utf8_bin NOT NULL,
  `total_class_no` char(3) COLLATE utf8_bin NOT NULL,
  `semester_id` char(5) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `course_info`
--

INSERT INTO `course_info` (`course_id`, `course_code`, `course_title`, `course_credit`, `course_type`, `total_class_no`, `semester_id`) VALUES
(2, 'CSE 111', 'Introduction to Computer Systems', '2', 'Theory', '32', '1st'),
(3, 'CSE 112', 'Introduction to Computer Systems Lab', '1', 'Lab', '32', '1st'),
(4, 'CSE 113', 'Discrete Mathematics', '3', 'Theory', '48', '1st'),
(5, 'EEE 121', 'Introduction to Electrical Engineering', '2', 'Theory', '32', '1st'),
(6, 'EEE 122', 'Introduction to Electrical Engineering Lab', '1', 'Lab', '32', '1st'),
(7, 'MATH 131', 'Matrices, Vector Analyses and Geometry', '3', 'Theory', '48', '1st'),
(8, 'PHY 141', 'Heat and Thermodynamics, Structure of Matter, Waves and Oscillations', '3', 'Theory', '48', '1st'),
(9, 'PHY 142', 'Heat and Thermodynamics, Structure of Matter, Waves and Oscillations Lab', '1', 'Lab', '32', '1st'),
(10, 'CHEM 161', 'Chemistry', '2', 'Theory', '32', '1st'),
(11, 'CSE 211', 'Structured Programming Language', '3', 'Theory', '48', '2nd'),
(12, 'CSE 212', 'Structured Programming Language Lab', '2', 'Lab', '48', '2nd'),
(13, 'EEE 221', 'Basic Electronic Devices & Circuits', '3', 'Theory', '48', '2nd'),
(14, 'EEE 222', 'Basic Electronic Devices & Circuits Lab', '1', 'Lab', '32', '2nd'),
(15, 'PHY 241', 'Electromagnetism, Optics & Modern Physics', '3', 'Theory', '48', '2nd'),
(16, 'PHY 242', 'Electromagnetism, Optics & Modern Physics Lab', '1', 'Lab', '32', '2nd'),
(17, 'MATH 231', 'Calculus & Differential Equations', '3', 'Theory', '48', '2nd'),
(18, 'STAT 251', 'Basic Statistics', '2', 'Theory', '32', '2nd'),
(19, 'STAT 252', 'Basic Statistics Lab', '1', 'Lab', '32', '2nd'),
(20, 'ENG 271', 'English', '2', 'Theory', '32', '2nd');

-- --------------------------------------------------------

--
-- Table structure for table `guardians`
--

CREATE TABLE IF NOT EXISTS `guardians` (
  `guardian_id` int(10) NOT NULL,
  `guardian_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `relation` varchar(20) COLLATE utf8_bin NOT NULL,
  `guardian_contact` char(20) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `semesters`
--

CREATE TABLE IF NOT EXISTS `semesters` (
  `semester_id` char(5) COLLATE utf8_bin NOT NULL,
  `semester_name` varchar(15) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `semesters`
--

INSERT INTO `semesters` (`semester_id`, `semester_name`) VALUES
('8th', 'Eighth Semester'),
('5th', 'Fifth Semester'),
('1st', 'First Semester'),
('4th', 'Fourth Semester'),
('2nd', 'Second Semester'),
('7th', 'Seventh Semeste'),
('6th', 'Sixth Semester'),
('3rd', 'Third Semester');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE IF NOT EXISTS `students` (
  `student_id` char(10) COLLATE utf8_bin NOT NULL,
  `student_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `student_contact` char(20) COLLATE utf8_bin NOT NULL,
  `student_email` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `student_address` text COLLATE utf8_bin NOT NULL,
  `admin_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `student_guardians`
--

CREATE TABLE IF NOT EXISTS `student_guardians` (
  `student_id` char(10) COLLATE utf8_bin NOT NULL,
  `guardian_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `student_info`
--

CREATE TABLE IF NOT EXISTS `student_info` (
  `student_id` char(10) COLLATE utf8_bin NOT NULL,
  `student_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `student_contact` char(20) COLLATE utf8_bin NOT NULL,
  `student_email` varchar(100) COLLATE utf8_bin NOT NULL,
  `student_address` text COLLATE utf8_bin NOT NULL,
  `guardian_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `guardian_contact` char(20) COLLATE utf8_bin NOT NULL,
  `relation` char(20) COLLATE utf8_bin NOT NULL,
  `semester_id` char(5) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `student_info`
--

INSERT INTO `student_info` (`student_id`, `student_name`, `student_contact`, `student_email`, `student_address`, `guardian_name`, `guardian_contact`, `relation`, `semester_id`) VALUES
('14701038', 'Sazzad Hossain', '01829784502', 'sazzad@gmail.com', 'Patiya', 'Md. Yusuf', '01819358387', 'Father', '7th');

-- --------------------------------------------------------

--
-- Table structure for table `timeslots`
--

CREATE TABLE IF NOT EXISTS `timeslots` (
  `timeslot_id` int(10) NOT NULL,
  `day` char(10) COLLATE utf8_bin DEFAULT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `course_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `attendances`
--
ALTER TABLE `attendances`
  ADD PRIMARY KEY (`attendance_id`), ADD KEY `course_id` (`course_id`), ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`course_id`), ADD UNIQUE KEY `course_code` (`course_code`), ADD UNIQUE KEY `course_title` (`course_title`), ADD KEY `admin_id` (`admin_id`), ADD KEY `semester_id` (`semester_id`);

--
-- Indexes for table `course_info`
--
ALTER TABLE `course_info`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `guardians`
--
ALTER TABLE `guardians`
  ADD PRIMARY KEY (`guardian_id`);

--
-- Indexes for table `semesters`
--
ALTER TABLE `semesters`
  ADD PRIMARY KEY (`semester_id`), ADD UNIQUE KEY `semester_name` (`semester_name`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`), ADD UNIQUE KEY `student_contact` (`student_contact`), ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `student_guardians`
--
ALTER TABLE `student_guardians`
  ADD PRIMARY KEY (`student_id`,`guardian_id`), ADD KEY `guardian_id` (`guardian_id`);

--
-- Indexes for table `student_info`
--
ALTER TABLE `student_info`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `timeslots`
--
ALTER TABLE `timeslots`
  ADD PRIMARY KEY (`timeslot_id`), ADD KEY `course_id` (`course_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `attendances`
--
ALTER TABLE `attendances`
  MODIFY `attendance_id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `admin_id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `course_info`
--
ALTER TABLE `course_info`
  MODIFY `course_id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `guardians`
--
ALTER TABLE `guardians`
  MODIFY `guardian_id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `admin_id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `student_guardians`
--
ALTER TABLE `student_guardians`
  MODIFY `guardian_id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `timeslots`
--
ALTER TABLE `timeslots`
  MODIFY `timeslot_id` int(10) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `attendances`
--
ALTER TABLE `attendances`
ADD CONSTRAINT `attendances_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON UPDATE CASCADE,
ADD CONSTRAINT `attendances_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON UPDATE CASCADE;

--
-- Constraints for table `courses`
--
ALTER TABLE `courses`
ADD CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`) ON UPDATE CASCADE,
ADD CONSTRAINT `courses_ibfk_2` FOREIGN KEY (`semester_id`) REFERENCES `semesters` (`semester_id`) ON UPDATE CASCADE;

--
-- Constraints for table `students`
--
ALTER TABLE `students`
ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`) ON UPDATE CASCADE;

--
-- Constraints for table `student_guardians`
--
ALTER TABLE `student_guardians`
ADD CONSTRAINT `student_guardians_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON UPDATE CASCADE,
ADD CONSTRAINT `student_guardians_ibfk_2` FOREIGN KEY (`guardian_id`) REFERENCES `guardians` (`guardian_id`) ON UPDATE CASCADE;

--
-- Constraints for table `timeslots`
--
ALTER TABLE `timeslots`
ADD CONSTRAINT `timeslots_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
