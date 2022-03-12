-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 11, 2021 at 01:52 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aviation_enrollment_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `enrolled`
--

CREATE TABLE `enrolled` (
  `paymentId` int(11) NOT NULL,
  `dateenrolled` varchar(255) NOT NULL,
  `timeenrolled` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `enrolled`
--

INSERT INTO `enrolled` (`paymentId`, `dateenrolled`, `timeenrolled`) VALUES
(22, 'February 10 2021', '07:01 PM'),
(23, 'February 10 2021', '09:54 PM'),
(24, 'February 11 2021', '04:54 PM');

-- --------------------------------------------------------

--
-- Table structure for table `fathers_info`
--

CREATE TABLE `fathers_info` (
  `studentId` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `contact` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `occupation` varchar(255) NOT NULL,
  `monthly_income` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fathers_info`
--

INSERT INTO `fathers_info` (`studentId`, `name`, `contact`, `address`, `occupation`, `monthly_income`) VALUES
('STUDENT-0001', 'Abdon Gabitanan', '0989172398', 'Taguig City', 'Driver', '2000-5000'),
('STUDENT-0002', 'Aljur Mandao', '098757656', 'Taguig', 'Driver', '2000-5000'),
('STUDENT-0003', 'Boy Gabitanan', '0919283719', 'Taguig Cty', 'Driver', '5500-10000');

-- --------------------------------------------------------

--
-- Table structure for table `guardian`
--

CREATE TABLE `guardian` (
  `studentId` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `guardian`
--

INSERT INTO `guardian` (`studentId`, `name`, `contact`) VALUES
('STUDENT-0001', 'Miles Gabitanan', '091289379131'),
('STUDENT-0002', 'Miles Antag', '09120938109'),
('STUDENT-0003', 'Mila Gabitanan', '091203910312');

-- --------------------------------------------------------

--
-- Table structure for table `mothers_info`
--

CREATE TABLE `mothers_info` (
  `studentId` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `contact` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `occupation` varchar(255) NOT NULL,
  `monthly_income` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `mothers_info`
--

INSERT INTO `mothers_info` (`studentId`, `name`, `contact`, `address`, `occupation`, `monthly_income`) VALUES
('STUDENT-0001', 'Miles Gabitanan', '098912739', 'Taguig City', 'House Wife', '2000-5000'),
('STUDENT-0002', 'Miles Antag', '09987986887', 'Taguig', 'House Wife', '2000-5000'),
('STUDENT-0003', 'Mila Gabitanan', '09129371032', 'Taguig', 'House Wife', '2000-5000');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentId` int(11) NOT NULL,
  `studentId` varchar(255) NOT NULL,
  `payment_type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`paymentId`, `studentId`, `payment_type`) VALUES
(22, 'STUDENT-0001', 'Cash'),
(23, 'STUDENT-0002', 'Cash'),
(24, 'STUDENT-0003', 'Cash');

-- --------------------------------------------------------

--
-- Table structure for table `schedulle`
--

CREATE TABLE `schedulle` (
  `sectionId` int(11) NOT NULL,
  `coursecode` varchar(255) NOT NULL,
  `subjecttitle` varchar(255) NOT NULL,
  `lecture` double NOT NULL,
  `laboratory` double NOT NULL,
  `schedulledate` varchar(255) NOT NULL,
  `room` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `schedulle`
--

INSERT INTO `schedulle` (`sectionId`, `coursecode`, `subjecttitle`, `lecture`, `laboratory`, `schedulledate`, `room`) VALUES
(1, 'GEC 1', 'Art Appreciation', 3, 0, '01:00 AM-01:00 AM Monday And Friday', 'Building B room110'),
(1, 'GEC 2', 'Reading in the Philippine History', 3, 0, '01:00 AM-01:00 AM Monday And Friday', 'Building B room110'),
(1, 'GEC 5', 'Understanding the Self', 3, 3, '01:00 AM-01:00 AM Monday And Friday', 'Building B room110'),
(1, 'IT 111', 'Introduction to Computing', 2, 3, '01:00 AM-02:00 AM Monday And Friday', 'Building B room110'),
(1, 'IT 112', 'Computer Programming 1(Java)', 2, 3, '01:00 AM-02:00 AM Monday And Friday', 'Building B room110'),
(1, 'AIT 111', 'Aviation Fundamentals', 2, 0, '01:01 AM-05:04 AM Monday And Friday', 'Building B room110'),
(1, 'IT 113', 'Web Development(HTML5 & CSS3 )', 2, 3, '01:00 AM-01:01 AM Monday And Friday', 'Building B room110'),
(1, 'PE 1', 'Physical Education 1', 2, 0, '01:01 AM-01:01 AM Monday and Tuesday', 'Building B room110'),
(1, 'NSTP 1', 'CWTS/ROTC1', 3, 0, '01:00 AM-01:01 AM Monday And Friday', 'Building B room110'),
(2, 'GEC 1', 'Art Appreciation', 3, 0, '01:00 AM-01:01 AM Monday And Friday', 'Building B 205'),
(2, 'GEC 2', 'Reading in the Philippine History', 3, 0, '01:00 AM-01:00 AM Monday And Friday', 'Building B 205'),
(2, 'GEC 5', 'Understanding the Self', 3, 3, '01:00 AM-02:00 AM Monday And Friday', 'Building B 205'),
(2, 'IT 111', 'Introduction to Computing', 2, 3, '01:01 AM-03:00 AM Monday And Friday', 'Building B 205'),
(2, 'IT 112', 'Computer Programming 1(Java)', 2, 3, '01:01 AM-03:00 AM Monday And Friday', 'Building B 205'),
(2, 'AIT 111', 'Aviation Fundamentals', 2, 0, '01:00 AM-02:00 AM Monday and Tuesday', 'Building B 205'),
(2, 'IT 113', 'Web Development(HTML5 & CSS3 )', 2, 3, '03:01 AM-05:00 AM Monday and Tuesday', 'Building B 205'),
(2, 'PE 1', 'Physical Education 1', 2, 0, '02:01 AM-01:01 AM Monday and Tuesday', 'Building B 205'),
(2, 'NSTP 1', 'CWTS/ROTC1', 3, 0, '02:00 AM-01:00 AM Monday and Thursday', 'Building B 205'),
(3, 'GEC 1', 'Art Appreciation', 3, 0, '01:00 AM-01:01 AM Monday And Friday', 'Building B 205'),
(3, 'GEC 2', 'Reading in the Philippine History', 3, 0, '01:00 AM-01:00 AM Monday And Friday', 'Building B 205'),
(3, 'GEC 5', 'Understanding the Self', 3, 3, '01:00 AM-02:00 AM Monday And Friday', 'Building B 205'),
(3, 'IT 111', 'Introduction to Computing', 2, 3, '01:01 AM-03:00 AM Monday And Friday', 'Building B 205'),
(3, 'IT 112', 'Computer Programming 1(Java)', 2, 3, '01:01 AM-03:00 AM Monday And Friday', 'Building B 205'),
(3, 'AIT 111', 'Aviation Fundamentals', 2, 0, '01:00 AM-02:00 AM Monday and Tuesday', 'Building B 205'),
(3, 'IT 113', 'Web Development(HTML5 & CSS3 )', 2, 3, '03:01 AM-05:00 AM Monday and Tuesday', 'Building B 205'),
(3, 'PE 1', 'Physical Education 1', 2, 0, '02:01 AM-01:01 AM Monday and Tuesday', 'Building B 205'),
(3, 'NSTP 1', 'CWTS/ROTC1', 3, 0, '02:00 AM-01:00 AM Monday and Thursday', 'Building B 205'),
(10, 'GEC 12', 'Environmental Science', 3, 0, '02:01 AM-02:01 AM Monday And Friday', 'Buildin A room 310'),
(10, 'GEC 7', 'Contemporary World', 3, 0, '01:00 AM-01:00 AM Monday And Friday', 'Buildin A room 310'),
(10, 'IT 411', 'Capstone Project 2', 3, 0, '01:01 AM-02:00 AM Monday And Friday', 'Buildin A room 310'),
(10, 'IT 412', 'Aviation System Administration & Maintenance', 2, 3, '02:02 AM-01:00 AM Monday And Friday', 'Buildin A room 310'),
(10, 'IT 413', 'Project Quality Management System', 3, 0, '02:00 AM-01:00 AM Monday And Friday', 'Buildin A room 310'),
(10, 'IT 414', 'Aviation Database Administration', 2, 3, '01:02 AM-02:00 AM Monday And Friday', 'Buildin A room 310'),
(10, 'IT 415', 'Virtualization & Cloud Platforms', 3, 0, '02:00 AM-01:00 AM Monday And Friday', 'Buildin A room 310'),
(10, 'AIT Elec 6', 'Professional Elective 6', 3, 0, '01:00 AM-01:00 AM Monday And Friday', 'Buildin A room 310');

-- --------------------------------------------------------

--
-- Table structure for table `school_info`
--

CREATE TABLE `school_info` (
  `studentId` varchar(255) NOT NULL,
  `sectionname` varchar(255) NOT NULL,
  `yearlevel` varchar(255) NOT NULL,
  `course` varchar(255) NOT NULL,
  `semester` varchar(255) NOT NULL,
  `schoolyear` varchar(255) NOT NULL,
  `averagegrade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `school_info`
--

INSERT INTO `school_info` (`studentId`, `sectionname`, `yearlevel`, `course`, `semester`, `schoolyear`, `averagegrade`) VALUES
('STUDENT-0001', 'BSAIT 1-1', 'First Year', 'BSAIT', '1st Semester', '2021-2022', 95),
('STUDENT-0002', 'BSAIT 1-1', 'First Year', 'BSAIT', '1st Semester', '2021-2022', 95),
('STUDENT-0003', 'BSAIT 1-1', 'First Year', 'BSAIT', '1st Semester', '2021-2022', 80);

-- --------------------------------------------------------

--
-- Table structure for table `section`
--

CREATE TABLE `section` (
  `sectionId` int(11) NOT NULL,
  `course` varchar(255) NOT NULL,
  `yearlevel` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `gradeFrom` double NOT NULL,
  `gradeTo` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `section`
--

INSERT INTO `section` (`sectionId`, `course`, `yearlevel`, `name`, `gradeFrom`, `gradeTo`) VALUES
(1, 'BSAIT', '1st Year', 'BSAIT 1-1', 89, 99),
(2, 'BSAIT', '1st Year', 'BSAIT 1-2', 85, 88),
(3, 'BSAIT', '1st Year', 'BSAIT 1-3', 75, 79),
(4, 'BSAIT', '2nd Year', 'BSAIT 2-1', 89, 99),
(5, 'BSAIT', '1st Year', 'BSAIT 1-4', 75, 79),
(6, 'BSAIT', '2nd Year', 'BSAIT 2-2', 85, 88),
(7, 'BSAIT', '2nd Year', 'BSAIT 2-3', 80, 84),
(8, 'BSAIT', '3rd Year', 'BSAIT 3-1', 90, 99),
(9, 'BSAIT', '3rd Year', 'BSAIT 3-2', 85, 89),
(10, 'BSAIT', '4th Year', 'BSAIT 4-1', 90, 99);

-- --------------------------------------------------------

--
-- Table structure for table `student_contact`
--

CREATE TABLE `student_contact` (
  `studentId` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_contact`
--

INSERT INTO `student_contact` (`studentId`, `email`, `contact`) VALUES
('STUDENT-0001', 'refuerzomaj@gmail,com', '09777547404'),
('STUDENT-0002', 'eljane@gmail.com', '099127300970'),
('STUDENT-0003', 'jayvee@gmail.com', '0910293801');

-- --------------------------------------------------------

--
-- Table structure for table `student_personal_info`
--

CREATE TABLE `student_personal_info` (
  `studentId` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `middlename` varchar(255) NOT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `gender` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  `birthdate` varchar(255) NOT NULL,
  `birthplace` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_personal_info`
--

INSERT INTO `student_personal_info` (`studentId`, `firstname`, `lastname`, `middlename`, `extension`, `gender`, `age`, `birthdate`, `birthplace`, `address`) VALUES
('STUDENT-0001', 'Jomardon', 'Gabitanan', 'Refuerzo', '', 'Male', 22, 'June 20,1998', 'QC', 'Taguig'),
('STUDENT-0002', 'Eljane', 'Mandao ', 'Antag', '', 'Female', 22, 'July 22,1996', 'Taguig', 'Taguig'),
('STUDENT-0003', 'Jayvee', 'Gabitanan', 'Refuerzo', '', 'Male', 22, 'Nov 05, 1998', 'QC', 'Taguig City');

-- --------------------------------------------------------

--
-- Table structure for table `user_account`
--

CREATE TABLE `user_account` (
  `userId` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_account`
--

INSERT INTO `user_account` (`userId`, `username`, `password`) VALUES
('ADMIN-0001', 'jam2021', 'jam2021'),
('ADMIN-0002', 'princess2021', '2021princess'),
('USER-0002', 'jayvee123', 'jayvee123');

-- --------------------------------------------------------

--
-- Table structure for table `user_contact`
--

CREATE TABLE `user_contact` (
  `userId` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_contact`
--

INSERT INTO `user_contact` (`userId`, `email`, `contact`) VALUES
('ADMIN-0001', 'refuerzomaj@gmail.com', '09777547404'),
('ADMIN-0002', 'princessstar@gmail.com', '0919203801'),
('USER-0002', 'kjkad', '0998127391');

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE `user_info` (
  `userId` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `middlename` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `position` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `age` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `usertype` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`userId`, `firstname`, `middlename`, `lastname`, `extension`, `position`, `gender`, `age`, `status`, `address`, `usertype`) VALUES
('ADMIN-0001', 'Jomardon', 'Refuerzo', 'Gabitanan', '', 'Developer', 'Male', '22', 'Single', 'Taguig City', 'Admin'),
('ADMIN-0002', 'Princess Star', 'Mandao', 'Gabitanan', '', 'Developer', 'Female', '14', 'Single', 'Taguig City', 'User'),
('USER-0002', 'Jayvee', 'Refuerzo', 'Gabitanan', '', 'Teacher', 'Male', '24', 'Single', 'Taguig City', 'User');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `enrolled`
--
ALTER TABLE `enrolled`
  ADD KEY `paymentId` (`paymentId`);

--
-- Indexes for table `fathers_info`
--
ALTER TABLE `fathers_info`
  ADD KEY `studentId` (`studentId`);

--
-- Indexes for table `guardian`
--
ALTER TABLE `guardian`
  ADD KEY `studentId` (`studentId`);

--
-- Indexes for table `mothers_info`
--
ALTER TABLE `mothers_info`
  ADD KEY `studentId` (`studentId`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentId`),
  ADD KEY `studentId` (`studentId`);

--
-- Indexes for table `schedulle`
--
ALTER TABLE `schedulle`
  ADD KEY `sectionId` (`sectionId`);

--
-- Indexes for table `school_info`
--
ALTER TABLE `school_info`
  ADD KEY `studentId` (`studentId`);

--
-- Indexes for table `section`
--
ALTER TABLE `section`
  ADD PRIMARY KEY (`sectionId`);

--
-- Indexes for table `student_contact`
--
ALTER TABLE `student_contact`
  ADD KEY `studentId` (`studentId`);

--
-- Indexes for table `student_personal_info`
--
ALTER TABLE `student_personal_info`
  ADD PRIMARY KEY (`studentId`);

--
-- Indexes for table `user_account`
--
ALTER TABLE `user_account`
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `user_contact`
--
ALTER TABLE `user_contact`
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`userId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `paymentId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `section`
--
ALTER TABLE `section`
  MODIFY `sectionId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `enrolled`
--
ALTER TABLE `enrolled`
  ADD CONSTRAINT `enrolled_ibfk_1` FOREIGN KEY (`paymentId`) REFERENCES `payment` (`paymentId`);

--
-- Constraints for table `fathers_info`
--
ALTER TABLE `fathers_info`
  ADD CONSTRAINT `fathers_info_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student_personal_info` (`studentId`);

--
-- Constraints for table `guardian`
--
ALTER TABLE `guardian`
  ADD CONSTRAINT `guardian_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student_personal_info` (`studentId`);

--
-- Constraints for table `mothers_info`
--
ALTER TABLE `mothers_info`
  ADD CONSTRAINT `mothers_info_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student_personal_info` (`studentId`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student_personal_info` (`studentId`);

--
-- Constraints for table `schedulle`
--
ALTER TABLE `schedulle`
  ADD CONSTRAINT `schedulle_ibfk_1` FOREIGN KEY (`sectionId`) REFERENCES `section` (`sectionId`);

--
-- Constraints for table `school_info`
--
ALTER TABLE `school_info`
  ADD CONSTRAINT `school_info_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student_personal_info` (`studentId`);

--
-- Constraints for table `student_contact`
--
ALTER TABLE `student_contact`
  ADD CONSTRAINT `student_contact_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student_personal_info` (`studentId`);

--
-- Constraints for table `user_account`
--
ALTER TABLE `user_account`
  ADD CONSTRAINT `user_account_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`);

--
-- Constraints for table `user_contact`
--
ALTER TABLE `user_contact`
  ADD CONSTRAINT `user_contact_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
