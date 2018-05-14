-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book_loans`
--

DROP TABLE IF EXISTS `book_loans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_loans` (
  `Loan_id` int(11) NOT NULL AUTO_INCREMENT,
  `Isbn` char(10) DEFAULT NULL,
  `Card_id` int(6) unsigned zerofill DEFAULT NULL,
  `Date_out` datetime DEFAULT CURRENT_TIMESTAMP,
  `Due_date` datetime DEFAULT NULL,
  `Datein` datetime DEFAULT NULL,
  PRIMARY KEY (`Loan_id`),
  KEY `FKbab0urqvpu2igbakgfe04ljq1` (`Isbn`),
  KEY `FKifw7n6a71t8tpcdvdhc5eaeci` (`Card_id`),
  CONSTRAINT `FKbab0urqvpu2igbakgfe04ljq1` FOREIGN KEY (`Isbn`) REFERENCES `book` (`Isbn`),
  CONSTRAINT `FKifw7n6a71t8tpcdvdhc5eaeci` FOREIGN KEY (`Card_id`) REFERENCES `borrower` (`card_id`),
  CONSTRAINT `book_loans_ibfk_1` FOREIGN KEY (`Isbn`) REFERENCES `book` (`Isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_loans`
--

LOCK TABLES `book_loans` WRITE;
/*!40000 ALTER TABLE `book_loans` DISABLE KEYS */;
INSERT INTO `book_loans` (`Loan_id`, `Isbn`, `Card_id`, `Date_out`, `Due_date`, `Datein`) VALUES (1,'0060233346',000003,'2017-03-15 20:52:06','2017-03-18 20:52:06',NULL),(2,'0060675233',000005,'2017-03-15 20:52:52','2017-03-18 20:52:52',NULL),(3,'0060505559',000001,'2017-03-09 13:54:02','2017-03-11 13:54:02',NULL),(4,'0060555513',000001,'2017-03-09 13:54:15','2017-03-10 13:54:15',NULL),(5,'0007119860',000001,'2017-03-08 13:59:10','2017-03-13 13:59:10',NULL);
/*!40000 ALTER TABLE `book_loans` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-19 16:35:56
