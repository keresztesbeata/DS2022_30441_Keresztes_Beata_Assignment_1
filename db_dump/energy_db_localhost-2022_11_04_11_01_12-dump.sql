-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: energy-db
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gex1lmaqpg0ir5g1f5eftyaa1` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('2fcb89dd-376f-452f-ac2c-0058d369a01a','client','$2a$10$j.glPFuX9Nu5HJJno.mkoe1NCaCyoBVMCzH1Vaw6TUuvfEeHHJfUK','CLIENT','client'),('47f8e492-0539-43ca-813b-a7f7d3c4d624','client2','$2a$10$qp6f6lq/V97DUYlECDmKJO2NjsyEhOvJ1xPHNqzZtcEVPHrRLaF.y','CLIENT','client2'),('d90b8854-2eb2-4144-af09-60782fe6c12c','admin','$2a$10$4lRX9HEabStvr3dZQ8aqx.zyT2CGeJatX96l0Z2XZi8FE5BHta7BO','ADMIN','admin');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device` (
  `id` varchar(36) NOT NULL,
  `address` varchar(300) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `max_hourly_energy_consumption` float DEFAULT '0',
  `account_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaigctov0rj5f048cd1ckauoov` (`account_id`),
  CONSTRAINT `FKaigctov0rj5f048cd1ckauoov` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES ('11a919f8-4eb6-4a70-b663-bbe35b3723e9','d2','',0,'2fcb89dd-376f-452f-ac2c-0058d369a01a'),('1efcab98-bb11-4495-aa96-f0dff9e924c2','d6',NULL,0,'47f8e492-0539-43ca-813b-a7f7d3c4d624'),('2a003a53-8d52-4e69-8679-ed8a2609dad6','d1','',0,'2fcb89dd-376f-452f-ac2c-0058d369a01a'),('58feb4ab-7253-4e80-95ff-772b69ed4d70','d55',NULL,0,'2fcb89dd-376f-452f-ac2c-0058d369a01a'),('9c2ceb70-781a-487a-a9b5-3f7740f2ba82','d7',NULL,0,'2fcb89dd-376f-452f-ac2c-0058d369a01a'),('9d7e0f08-5353-441f-8721-55238211ebcc','d4',NULL,0,'2fcb89dd-376f-452f-ac2c-0058d369a01a'),('b0b1f3a7-9387-4d55-8391-2e8b61604060','d5',NULL,0,'2fcb89dd-376f-452f-ac2c-0058d369a01a'),('bfec987e-abe0-487b-a303-3cd5a8de668c','d8',NULL,0,'47f8e492-0539-43ca-813b-a7f7d3c4d624'),('dbb3d70a-0358-4b43-af49-a6f9617086cc','d3','',0,'2fcb89dd-376f-452f-ac2c-0058d369a01a');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `energy_consumption`
--

DROP TABLE IF EXISTS `energy_consumption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `energy_consumption` (
  `id` int NOT NULL,
  `energy` float DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `device_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7cjx5e0uq41oxj8scflvtehny` (`device_id`),
  CONSTRAINT `FK7cjx5e0uq41oxj8scflvtehny` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `energy_consumption`
--

LOCK TABLES `energy_consumption` WRITE;
/*!40000 ALTER TABLE `energy_consumption` DISABLE KEYS */;
/*!40000 ALTER TABLE `energy_consumption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `total_energy_consumption_view`
--

DROP TABLE IF EXISTS `total_energy_consumption_view`;
/*!50001 DROP VIEW IF EXISTS `total_energy_consumption_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `total_energy_consumption_view` AS SELECT 
 1 AS `energy`,
 1 AS `timestamp`,
 1 AS `accountId`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `total_energy_consumption_view`
--

/*!50001 DROP VIEW IF EXISTS `total_energy_consumption_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `total_energy_consumption_view` AS select sum(`e`.`energy`) AS `energy`,`e`.`timestamp` AS `timestamp`,`d`.`account_id` AS `accountId` from (`energy_consumption` `e` join `device` `d` on((`e`.`device_id` = `d`.`id`))) group by `d`.`account_id`,hour(`e`.`timestamp`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-04 11:01:12
