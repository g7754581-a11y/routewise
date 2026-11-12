-- MySQL dump 10.13  Distrib 26.7.0, for macos15 (arm64)
--
-- Host: localhost    Database: routewise_db
-- ------------------------------------------------------
-- Server version	26.7.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '6482656a-bd7f-11f1-9644-b28891408e08:1-262';

--
-- Current Database: `routewise_db`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `routewise_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `routewise_db`;

--
-- Table structure for table `booking_seats`
--

DROP TABLE IF EXISTS `booking_seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_seats` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fare` double NOT NULL,
  `seat_number` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `booking_id` bigint NOT NULL,
  `seat_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmbi9ciapn0nvat63t0a8tv478` (`booking_id`),
  KEY `FKm2vak166qv8osqwe5qcxsn1p` (`seat_id`),
  CONSTRAINT `FKm2vak166qv8osqwe5qcxsn1p` FOREIGN KEY (`seat_id`) REFERENCES `seats` (`id`),
  CONSTRAINT `FKmbi9ciapn0nvat63t0a8tv478` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_seats`
--

LOCK TABLES `booking_seats` WRITE;
/*!40000 ALTER TABLE `booking_seats` DISABLE KEYS */;
INSERT INTO `booking_seats` VALUES (1,650,'1A',1,1),(2,650,'1B',1,2),(3,700,'2C',2,47),(4,700,'2D',2,48),(5,750,'3A',3,89),(6,650,'4B',4,244),(7,650,'4C',4,245),(8,550,'1C',5,353),(9,550,'1D',5,354),(10,650,'5A',6,17),(11,650,'5B',6,18),(12,650,'5A',7,17);
/*!40000 ALTER TABLE `booking_seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_date` datetime(6) NOT NULL,
  `booking_status` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `passenger_email` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `passenger_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `passenger_phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payment_status` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pnr` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `seats_count` int NOT NULL,
  `total_amount` double NOT NULL,
  `schedule_id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmumifbrfh11nq6204aubv6dyr` (`pnr`),
  KEY `FKer0lq2qsui5vv3qn0i6sm1rom` (`schedule_id`),
  KEY `FKeyog2oic85xg7hsu2je2lx3s6` (`user_id`),
  CONSTRAINT `FKer0lq2qsui5vv3qn0i6sm1rom` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`),
  CONSTRAINT `FKeyog2oic85xg7hsu2je2lx3s6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,'2026-11-02 07:45:25.353669','CONFIRMED','2026-11-02 07:45:25.353674','passenger@routewise.com','Rahul Verma','+91 98765 43210','COMPLETED','RW-TV8V72',2,1365,1,2),(2,'2026-11-02 07:45:25.359050','CONFIRMED','2026-11-02 07:45:25.359051','rohit.sharma@example.com','Rohit Sharma','+91 98234 11223','COMPLETED','RW-22TCJ2',2,1470,2,3),(3,'2026-11-02 07:45:25.362767','CONFIRMED','2026-11-02 07:45:25.362768','priya.singh@example.com','Priya Singh','+91 98345 22334','COMPLETED','RW-2YGW47',1,787.5,3,4),(4,'2026-11-02 07:45:25.367105','CONFIRMED','2026-11-02 07:45:25.367106','aarav.mehta@example.com','Aarav Mehta','+91 98456 33445','COMPLETED','RW-48FUQS',2,1365,7,5),(5,'2026-11-02 07:45:25.371716','CONFIRMED','2026-11-02 07:45:25.371718','neha.patel@example.com','Neha Patel','+91 98567 44556','COMPLETED','RW-F3MTEG',2,1155,10,6),(6,'2026-11-02 07:46:26.311973','CANCELLED','2026-11-02 07:46:26.311974','ananya@example.com','Ananya Sharma','+91 98765 00000','REFUNDED','RW-ZXG2AR',2,1365,1,NULL),(7,'2026-11-02 07:46:44.028912','CONFIRMED','2026-11-02 07:46:44.028913','second@example.com','Second Passenger','+91 98765 22222','COMPLETED','RW-EPVW66',1,682.5,1,NULL);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bus_stops`
--

DROP TABLE IF EXISTS `bus_stops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bus_stops` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `landmark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stop_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `city_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdwjw0d8kfllk6r5l1ner9lyqn` (`city_id`),
  CONSTRAINT `FKdwjw0d8kfllk6r5l1ner9lyqn` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bus_stops`
--

LOCK TABLES `bus_stops` WRITE;
/*!40000 ALTER TABLE `bus_stops` DISABLE KEYS */;
INSERT INTO `bus_stops` VALUES (1,'Near Gate 1 Metro','ISBT Kashmere Gate, Delhi',1),(2,'Near Tibetan Market','Majnu Ka Tilla, Delhi',1),(3,'Platform 12','ISBT Sector 43, Chandigarh',2),(4,'Highway Flyover','Tribune Chowk, Chandigarh',2),(5,'Main Terminal','Sindhi Camp Bus Stand, Jaipur',3),(6,'New Bus Stand','ISBT Tutikandi, Shimla',4),(7,'Near Golden Temple Road','Amritsar Bus Stand',5),(8,'GT Road','Sherpur Chowk, Ludhiana',6),(9,'Haridwar Bypass','ISBT Dehradun',7),(10,'Mall Road','Private Bus Stand, Manali',8),(11,'Railway Station Road','Ambala Cantt Bus Stand',9),(12,'GT Karnal Road','Karnal Toll Plaza / Oasis',10);
/*!40000 ALTER TABLE `bus_stops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buses`
--

DROP TABLE IF EXISTS `buses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amenities` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bus_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bus_number` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bus_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` bit(1) NOT NULL,
  `seat_layout_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_seats` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKf0wpejbtx1fk17hi1t6ba5vbv` (`bus_number`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buses`
--

LOCK TABLES `buses` WRITE;
/*!40000 ALTER TABLE `buses` DISABLE KEYS */;
INSERT INTO `buses` VALUES (1,'High-Speed WiFi, AC, Reading Light, USB Charging, Reclining Seats, Blanket, Mineral Water','RouteWise Royal Volvo 9600 Multi-Axle','DL01-RW-1001','Volvo AC Multi-Axle',_binary '','2x2',40),(2,'Individual TV, AC, Blankets, USB Fast Charger, Curtains, Mineral Water','RouteWise Platinum Sleeper Express','CH01-RW-2002','AC Sleeper (2+1)',_binary '','2x1',30),(3,'Air Suspension, AC, Emergency SOS, Clean Restroom on board, Water Bottle','RouteWise Scania Metroline Luxury','HR03-RW-3003','Scania AC Luxury',_binary '','2x2',40),(4,'Push-back Seats, AC, Reading Lamp, Bottle Holder, USB Port','RouteWise BharatBenz Glider','PB65-RW-4004','BharatBenz Luxury',_binary '','2x2',36),(5,'Panoramic Windows, WiFi, 140° Reclining, Complimentary Snack Box, CCTV','RouteWise Mercedes SuperClass','DL04-RW-5005','Mercedes Multi-Axle AC',_binary '','2x2',40),(6,'Fast Charger, AC, Overhead Luggage, Live GPS Tracking, CCTV','RouteWise Express Executive Seater','RJ14-RW-6006','Executive AC Seater',_binary '','2x2',40),(7,'All-Weather Heating/AC, Mountain Suspension, Panoramic View, First Aid','RouteWise Himalayan Cruiser Volvo B11R','HP01-RW-7007','Volvo Mountain Spec AC',_binary '','2x2',36),(8,'WiFi, USB Charging, Ergonomic Headrest, Water Bottle','RouteWise Golden Triangle Express','DL09-RW-8008','Luxury AC Seater',_binary '','2x2',40),(9,'Curtains, Pillow, Soft Blanket, Reading Light, Charging Port','RouteWise Night Rider Sleeper','PB02-RW-9009','Luxury AC Sleeper',_binary '','2x1',30),(10,'Zero Emissions, Whisper Quiet Cabin, Type-C Fast Ports, Air Purifier','RouteWise Green Eco Electric Cruiser','UK07-RW-1010','Electric Intercity AC',_binary '','2x2',36);
/*!40000 ALTER TABLE `buses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `name` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `state` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKl61tawv0e2a93es77jkyvi7qa` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` VALUES (1,'DEL',28.6139,77.209,'Delhi','Delhi'),(2,'CH',30.7333,76.7794,'Chandigarh','Punjab / Haryana'),(3,'JAI',26.9124,75.7873,'Jaipur','Rajasthan'),(4,'SHM',31.1048,77.1734,'Shimla','Himachal Pradesh'),(5,'ASR',31.634,74.8723,'Amritsar','Punjab'),(6,'LDH',30.901,75.8573,'Ludhiana','Punjab'),(7,'DED',30.3165,78.0322,'Dehradun','Uttarakhand'),(8,'MNL',32.2432,77.1892,'Manali','Himachal Pradesh'),(9,'AMB',30.3782,76.7767,'Ambala','Haryana'),(10,'KRN',29.6857,76.9905,'Karnal','Haryana');
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_stops`
--

DROP TABLE IF EXISTS `route_stops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route_stops` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `distance_from_source_km` double DEFAULT NULL,
  `halt_duration_mins` int DEFAULT NULL,
  `stop_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `stop_order` int NOT NULL,
  `route_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK63y33daxb1qs5nbnkuicbpkej` (`route_id`),
  CONSTRAINT `FK63y33daxb1qs5nbnkuicbpkej` FOREIGN KEY (`route_id`) REFERENCES `routes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_stops`
--

LOCK TABLES `route_stops` WRITE;
/*!40000 ALTER TABLE `route_stops` DISABLE KEYS */;
INSERT INTO `route_stops` VALUES (1,125,15,'Karnal Oasis Stop',1,1),(2,200,10,'Ambala Cantt Junction',2,1),(3,45,10,'Ambala Cantt Junction',1,2),(4,120,15,'Karnal Oasis Stop',2,2),(5,30,10,'Kalka Bypass',1,3),(6,70,15,'Solan Highway Stop',2,3),(7,35,10,'Gurugram IFFCO Chowk',1,5),(8,130,20,'Behror Mid-way',2,5),(9,95,15,'Ludhiana Samrala Bypass',1,7),(10,155,10,'Jalandhar Rama Mandi',2,7),(11,135,20,'Bilaspur Lake View',1,9),(12,200,20,'Mandi Town Terminal',2,9),(13,265,15,'Kullu Bypass',3,9),(14,65,10,'Meerut Expressway Toll',1,11),(15,180,15,'Roorkee Bypass',2,11);
/*!40000 ALTER TABLE `route_stops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routes`
--

DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `routes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `distance_km` double NOT NULL,
  `estimated_duration_mins` int NOT NULL,
  `is_active` bit(1) NOT NULL,
  `destination_city_id` bigint NOT NULL,
  `source_city_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5sgtl8tsuh6ic53clyjg724kn` (`destination_city_id`),
  KEY `FKmqkfmw64fbmgquqmj9mpfjr8g` (`source_city_id`),
  CONSTRAINT `FK5sgtl8tsuh6ic53clyjg724kn` FOREIGN KEY (`destination_city_id`) REFERENCES `cities` (`id`),
  CONSTRAINT `FKmqkfmw64fbmgquqmj9mpfjr8g` FOREIGN KEY (`source_city_id`) REFERENCES `cities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routes`
--

LOCK TABLES `routes` WRITE;
/*!40000 ALTER TABLE `routes` DISABLE KEYS */;
INSERT INTO `routes` VALUES (1,245,270,_binary '',2,1),(2,245,270,_binary '',1,2),(3,115,210,_binary '',4,2),(4,115,210,_binary '',2,4),(5,275,310,_binary '',3,1),(6,275,310,_binary '',1,3),(7,225,260,_binary '',5,2),(8,225,260,_binary '',2,5),(9,305,480,_binary '',8,2),(10,305,480,_binary '',2,8),(11,250,330,_binary '',7,1),(12,250,330,_binary '',1,7),(13,125,135,_binary '',10,1),(14,125,135,_binary '',1,10),(15,75,80,_binary '',9,10),(16,75,80,_binary '',10,9),(17,45,55,_binary '',2,9),(18,45,55,_binary '',9,2),(19,95,110,_binary '',6,2),(20,130,150,_binary '',5,6);
/*!40000 ALTER TABLE `routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedules` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `arrival_time` datetime(6) NOT NULL,
  `base_fare` double NOT NULL,
  `departure_time` datetime(6) NOT NULL,
  `status` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bus_id` bigint NOT NULL,
  `route_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpuih24muu99o0lkfkasqh26uj` (`bus_id`),
  KEY `FKc29vj8art9umx13trmnv0pqw7` (`route_id`),
  CONSTRAINT `FKc29vj8art9umx13trmnv0pqw7` FOREIGN KEY (`route_id`) REFERENCES `routes` (`id`),
  CONSTRAINT `FKpuih24muu99o0lkfkasqh26uj` FOREIGN KEY (`bus_id`) REFERENCES `buses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedules`
--

LOCK TABLES `schedules` WRITE;
/*!40000 ALTER TABLE `schedules` DISABLE KEYS */;
INSERT INTO `schedules` VALUES (1,'2026-11-02 05:30:00.000000',650,'2026-11-02 01:00:00.000000','SCHEDULED',1,1),(2,'2026-11-02 09:00:00.000000',700,'2026-11-02 04:30:00.000000','SCHEDULED',3,1),(3,'2026-11-02 15:00:00.000000',750,'2026-11-02 10:30:00.000000','SCHEDULED',5,1),(4,'2026-11-02 21:30:00.000000',850,'2026-11-02 17:00:00.000000','SCHEDULED',2,1),(5,'2026-11-03 06:00:00.000000',650,'2026-11-03 01:30:00.000000','SCHEDULED',1,1),(6,'2026-11-03 16:00:00.000000',750,'2026-11-03 11:30:00.000000','SCHEDULED',5,1),(7,'2026-11-02 07:00:00.000000',650,'2026-11-02 02:30:00.000000','SCHEDULED',1,2),(8,'2026-11-02 14:30:00.000000',700,'2026-11-02 10:00:00.000000','SCHEDULED',3,2),(9,'2026-11-03 08:00:00.000000',750,'2026-11-03 03:30:00.000000','SCHEDULED',5,2),(10,'2026-11-02 07:10:00.000000',550,'2026-11-02 02:00:00.000000','SCHEDULED',6,5),(11,'2026-11-02 13:40:00.000000',600,'2026-11-02 08:30:00.000000','SCHEDULED',8,5),(12,'2026-11-03 08:10:00.000000',650,'2026-11-03 03:00:00.000000','SCHEDULED',2,5),(13,'2026-11-02 07:00:00.000000',480,'2026-11-02 03:30:00.000000','SCHEDULED',7,3),(14,'2026-11-03 08:30:00.000000',480,'2026-11-03 05:00:00.000000','SCHEDULED',7,3),(15,'2026-11-02 23:30:00.000000',950,'2026-11-02 15:30:00.000000','SCHEDULED',7,9),(16,'2026-11-03 23:00:00.000000',1050,'2026-11-03 15:00:00.000000','SCHEDULED',9,9),(17,'2026-11-02 07:20:00.000000',520,'2026-11-02 03:00:00.000000','SCHEDULED',4,7),(18,'2026-11-03 12:50:00.000000',520,'2026-11-03 08:30:00.000000','SCHEDULED',4,7),(19,'2026-11-02 06:00:00.000000',580,'2026-11-02 00:30:00.000000','SCHEDULED',10,11),(20,'2026-11-03 15:00:00.000000',580,'2026-11-03 09:30:00.000000','SCHEDULED',10,11);
/*!40000 ALTER TABLE `schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seats` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `deck` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_blocked` bit(1) NOT NULL,
  `is_booked` bit(1) NOT NULL,
  `price` double NOT NULL,
  `seat_number` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `seat_type` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `schedule_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_schedule_seat` (`schedule_id`,`seat_number`),
  CONSTRAINT `FKfbw4qi0nr80b7o8kpb25gy2q7` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=743 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (1,'LOWER',_binary '\0',_binary '',650,'1A','SEATER',1),(2,'LOWER',_binary '\0',_binary '',650,'1B','SEATER',1),(3,'LOWER',_binary '\0',_binary '\0',650,'1C','SEATER',1),(4,'LOWER',_binary '\0',_binary '\0',650,'1D','SEATER',1),(5,'LOWER',_binary '\0',_binary '\0',650,'2A','SEATER',1),(6,'LOWER',_binary '\0',_binary '\0',650,'2B','SEATER',1),(7,'LOWER',_binary '\0',_binary '\0',650,'2C','SEATER',1),(8,'LOWER',_binary '\0',_binary '\0',650,'2D','SEATER',1),(9,'LOWER',_binary '\0',_binary '\0',650,'3A','SEATER',1),(10,'LOWER',_binary '\0',_binary '\0',650,'3B','SEATER',1),(11,'LOWER',_binary '\0',_binary '\0',650,'3C','SEATER',1),(12,'LOWER',_binary '\0',_binary '\0',650,'3D','SEATER',1),(13,'LOWER',_binary '\0',_binary '\0',650,'4A','SEATER',1),(14,'LOWER',_binary '\0',_binary '\0',650,'4B','SEATER',1),(15,'LOWER',_binary '\0',_binary '\0',650,'4C','SEATER',1),(16,'LOWER',_binary '\0',_binary '\0',650,'4D','SEATER',1),(17,'LOWER',_binary '\0',_binary '',650,'5A','SEATER',1),(18,'LOWER',_binary '\0',_binary '\0',650,'5B','SEATER',1),(19,'LOWER',_binary '\0',_binary '\0',650,'5C','SEATER',1),(20,'LOWER',_binary '\0',_binary '\0',650,'5D','SEATER',1),(21,'LOWER',_binary '\0',_binary '\0',650,'6A','SEATER',1),(22,'LOWER',_binary '\0',_binary '\0',650,'6B','SEATER',1),(23,'LOWER',_binary '\0',_binary '\0',650,'6C','SEATER',1),(24,'LOWER',_binary '\0',_binary '\0',650,'6D','SEATER',1),(25,'LOWER',_binary '\0',_binary '\0',650,'7A','SEATER',1),(26,'LOWER',_binary '\0',_binary '\0',650,'7B','SEATER',1),(27,'LOWER',_binary '\0',_binary '\0',650,'7C','SEATER',1),(28,'LOWER',_binary '\0',_binary '\0',650,'7D','SEATER',1),(29,'LOWER',_binary '\0',_binary '\0',650,'8A','SEATER',1),(30,'LOWER',_binary '\0',_binary '\0',650,'8B','SEATER',1),(31,'LOWER',_binary '\0',_binary '\0',650,'8C','SEATER',1),(32,'LOWER',_binary '\0',_binary '\0',650,'8D','SEATER',1),(33,'LOWER',_binary '\0',_binary '\0',650,'9A','SEATER',1),(34,'LOWER',_binary '\0',_binary '\0',650,'9B','SEATER',1),(35,'LOWER',_binary '\0',_binary '\0',650,'9C','SEATER',1),(36,'LOWER',_binary '\0',_binary '\0',650,'9D','SEATER',1),(37,'LOWER',_binary '\0',_binary '\0',650,'10A','SEATER',1),(38,'LOWER',_binary '\0',_binary '\0',650,'10B','SEATER',1),(39,'LOWER',_binary '\0',_binary '\0',650,'10C','SEATER',1),(40,'LOWER',_binary '\0',_binary '\0',650,'10D','SEATER',1),(41,'LOWER',_binary '\0',_binary '\0',700,'1A','SEATER',2),(42,'LOWER',_binary '\0',_binary '\0',700,'1B','SEATER',2),(43,'LOWER',_binary '\0',_binary '\0',700,'1C','SEATER',2),(44,'LOWER',_binary '\0',_binary '\0',700,'1D','SEATER',2),(45,'LOWER',_binary '\0',_binary '\0',700,'2A','SEATER',2),(46,'LOWER',_binary '\0',_binary '\0',700,'2B','SEATER',2),(47,'LOWER',_binary '\0',_binary '',700,'2C','SEATER',2),(48,'LOWER',_binary '\0',_binary '',700,'2D','SEATER',2),(49,'LOWER',_binary '\0',_binary '\0',700,'3A','SEATER',2),(50,'LOWER',_binary '\0',_binary '\0',700,'3B','SEATER',2),(51,'LOWER',_binary '\0',_binary '\0',700,'3C','SEATER',2),(52,'LOWER',_binary '\0',_binary '\0',700,'3D','SEATER',2),(53,'LOWER',_binary '\0',_binary '\0',700,'4A','SEATER',2),(54,'LOWER',_binary '\0',_binary '\0',700,'4B','SEATER',2),(55,'LOWER',_binary '\0',_binary '\0',700,'4C','SEATER',2),(56,'LOWER',_binary '\0',_binary '\0',700,'4D','SEATER',2),(57,'LOWER',_binary '\0',_binary '\0',700,'5A','SEATER',2),(58,'LOWER',_binary '\0',_binary '\0',700,'5B','SEATER',2),(59,'LOWER',_binary '\0',_binary '\0',700,'5C','SEATER',2),(60,'LOWER',_binary '\0',_binary '\0',700,'5D','SEATER',2),(61,'LOWER',_binary '\0',_binary '\0',700,'6A','SEATER',2),(62,'LOWER',_binary '\0',_binary '\0',700,'6B','SEATER',2),(63,'LOWER',_binary '\0',_binary '\0',700,'6C','SEATER',2),(64,'LOWER',_binary '\0',_binary '\0',700,'6D','SEATER',2),(65,'LOWER',_binary '\0',_binary '\0',700,'7A','SEATER',2),(66,'LOWER',_binary '\0',_binary '\0',700,'7B','SEATER',2),(67,'LOWER',_binary '\0',_binary '\0',700,'7C','SEATER',2),(68,'LOWER',_binary '\0',_binary '\0',700,'7D','SEATER',2),(69,'LOWER',_binary '\0',_binary '\0',700,'8A','SEATER',2),(70,'LOWER',_binary '\0',_binary '\0',700,'8B','SEATER',2),(71,'LOWER',_binary '\0',_binary '\0',700,'8C','SEATER',2),(72,'LOWER',_binary '\0',_binary '\0',700,'8D','SEATER',2),(73,'LOWER',_binary '\0',_binary '\0',700,'9A','SEATER',2),(74,'LOWER',_binary '\0',_binary '\0',700,'9B','SEATER',2),(75,'LOWER',_binary '\0',_binary '\0',700,'9C','SEATER',2),(76,'LOWER',_binary '\0',_binary '\0',700,'9D','SEATER',2),(77,'LOWER',_binary '\0',_binary '\0',700,'10A','SEATER',2),(78,'LOWER',_binary '\0',_binary '\0',700,'10B','SEATER',2),(79,'LOWER',_binary '\0',_binary '\0',700,'10C','SEATER',2),(80,'LOWER',_binary '\0',_binary '\0',700,'10D','SEATER',2),(81,'LOWER',_binary '\0',_binary '\0',750,'1A','SEATER',3),(82,'LOWER',_binary '\0',_binary '\0',750,'1B','SEATER',3),(83,'LOWER',_binary '\0',_binary '\0',750,'1C','SEATER',3),(84,'LOWER',_binary '\0',_binary '\0',750,'1D','SEATER',3),(85,'LOWER',_binary '\0',_binary '\0',750,'2A','SEATER',3),(86,'LOWER',_binary '\0',_binary '\0',750,'2B','SEATER',3),(87,'LOWER',_binary '\0',_binary '\0',750,'2C','SEATER',3),(88,'LOWER',_binary '\0',_binary '\0',750,'2D','SEATER',3),(89,'LOWER',_binary '\0',_binary '',750,'3A','SEATER',3),(90,'LOWER',_binary '\0',_binary '\0',750,'3B','SEATER',3),(91,'LOWER',_binary '\0',_binary '\0',750,'3C','SEATER',3),(92,'LOWER',_binary '\0',_binary '\0',750,'3D','SEATER',3),(93,'LOWER',_binary '\0',_binary '\0',750,'4A','SEATER',3),(94,'LOWER',_binary '\0',_binary '\0',750,'4B','SEATER',3),(95,'LOWER',_binary '\0',_binary '\0',750,'4C','SEATER',3),(96,'LOWER',_binary '\0',_binary '\0',750,'4D','SEATER',3),(97,'LOWER',_binary '\0',_binary '\0',750,'5A','SEATER',3),(98,'LOWER',_binary '\0',_binary '\0',750,'5B','SEATER',3),(99,'LOWER',_binary '\0',_binary '\0',750,'5C','SEATER',3),(100,'LOWER',_binary '\0',_binary '\0',750,'5D','SEATER',3),(101,'LOWER',_binary '\0',_binary '\0',750,'6A','SEATER',3),(102,'LOWER',_binary '\0',_binary '\0',750,'6B','SEATER',3),(103,'LOWER',_binary '\0',_binary '\0',750,'6C','SEATER',3),(104,'LOWER',_binary '\0',_binary '\0',750,'6D','SEATER',3),(105,'LOWER',_binary '\0',_binary '\0',750,'7A','SEATER',3),(106,'LOWER',_binary '\0',_binary '\0',750,'7B','SEATER',3),(107,'LOWER',_binary '\0',_binary '\0',750,'7C','SEATER',3),(108,'LOWER',_binary '\0',_binary '\0',750,'7D','SEATER',3),(109,'LOWER',_binary '\0',_binary '\0',750,'8A','SEATER',3),(110,'LOWER',_binary '\0',_binary '\0',750,'8B','SEATER',3),(111,'LOWER',_binary '\0',_binary '\0',750,'8C','SEATER',3),(112,'LOWER',_binary '\0',_binary '\0',750,'8D','SEATER',3),(113,'LOWER',_binary '\0',_binary '\0',750,'9A','SEATER',3),(114,'LOWER',_binary '\0',_binary '\0',750,'9B','SEATER',3),(115,'LOWER',_binary '\0',_binary '\0',750,'9C','SEATER',3),(116,'LOWER',_binary '\0',_binary '\0',750,'9D','SEATER',3),(117,'LOWER',_binary '\0',_binary '\0',750,'10A','SEATER',3),(118,'LOWER',_binary '\0',_binary '\0',750,'10B','SEATER',3),(119,'LOWER',_binary '\0',_binary '\0',750,'10C','SEATER',3),(120,'LOWER',_binary '\0',_binary '\0',750,'10D','SEATER',3),(121,'LOWER',_binary '\0',_binary '\0',850,'1A','SLEEPER',4),(122,'LOWER',_binary '\0',_binary '\0',850,'1B','SLEEPER',4),(123,'LOWER',_binary '\0',_binary '\0',850,'1C','SLEEPER',4),(124,'LOWER',_binary '\0',_binary '\0',850,'1D','SLEEPER',4),(125,'LOWER',_binary '\0',_binary '\0',850,'2A','SLEEPER',4),(126,'LOWER',_binary '\0',_binary '\0',850,'2B','SLEEPER',4),(127,'LOWER',_binary '\0',_binary '\0',850,'2C','SLEEPER',4),(128,'LOWER',_binary '\0',_binary '\0',850,'2D','SLEEPER',4),(129,'LOWER',_binary '\0',_binary '\0',850,'3A','SLEEPER',4),(130,'LOWER',_binary '\0',_binary '\0',850,'3B','SLEEPER',4),(131,'LOWER',_binary '\0',_binary '\0',850,'3C','SLEEPER',4),(132,'LOWER',_binary '\0',_binary '\0',850,'3D','SLEEPER',4),(133,'LOWER',_binary '\0',_binary '\0',850,'4A','SLEEPER',4),(134,'LOWER',_binary '\0',_binary '\0',850,'4B','SLEEPER',4),(135,'LOWER',_binary '\0',_binary '\0',850,'4C','SLEEPER',4),(136,'LOWER',_binary '\0',_binary '\0',850,'4D','SLEEPER',4),(137,'LOWER',_binary '\0',_binary '\0',850,'5A','SLEEPER',4),(138,'LOWER',_binary '\0',_binary '\0',850,'5B','SLEEPER',4),(139,'LOWER',_binary '\0',_binary '\0',850,'5C','SLEEPER',4),(140,'LOWER',_binary '\0',_binary '\0',850,'5D','SLEEPER',4),(141,'LOWER',_binary '\0',_binary '\0',850,'6A','SLEEPER',4),(142,'LOWER',_binary '\0',_binary '\0',850,'6B','SLEEPER',4),(143,'LOWER',_binary '\0',_binary '\0',850,'6C','SLEEPER',4),(144,'LOWER',_binary '\0',_binary '\0',850,'6D','SLEEPER',4),(145,'LOWER',_binary '\0',_binary '\0',850,'7A','SLEEPER',4),(146,'LOWER',_binary '\0',_binary '\0',850,'7B','SLEEPER',4),(147,'LOWER',_binary '\0',_binary '\0',850,'7C','SLEEPER',4),(148,'LOWER',_binary '\0',_binary '\0',850,'7D','SLEEPER',4),(149,'LOWER',_binary '\0',_binary '\0',850,'8A','SLEEPER',4),(150,'LOWER',_binary '\0',_binary '\0',850,'8B','SLEEPER',4),(151,'LOWER',_binary '\0',_binary '\0',650,'1A','SEATER',5),(152,'LOWER',_binary '\0',_binary '\0',650,'1B','SEATER',5),(153,'LOWER',_binary '\0',_binary '\0',650,'1C','SEATER',5),(154,'LOWER',_binary '\0',_binary '\0',650,'1D','SEATER',5),(155,'LOWER',_binary '\0',_binary '\0',650,'2A','SEATER',5),(156,'LOWER',_binary '\0',_binary '\0',650,'2B','SEATER',5),(157,'LOWER',_binary '\0',_binary '\0',650,'2C','SEATER',5),(158,'LOWER',_binary '\0',_binary '\0',650,'2D','SEATER',5),(159,'LOWER',_binary '\0',_binary '\0',650,'3A','SEATER',5),(160,'LOWER',_binary '\0',_binary '\0',650,'3B','SEATER',5),(161,'LOWER',_binary '\0',_binary '\0',650,'3C','SEATER',5),(162,'LOWER',_binary '\0',_binary '\0',650,'3D','SEATER',5),(163,'LOWER',_binary '\0',_binary '\0',650,'4A','SEATER',5),(164,'LOWER',_binary '\0',_binary '\0',650,'4B','SEATER',5),(165,'LOWER',_binary '\0',_binary '\0',650,'4C','SEATER',5),(166,'LOWER',_binary '\0',_binary '\0',650,'4D','SEATER',5),(167,'LOWER',_binary '\0',_binary '\0',650,'5A','SEATER',5),(168,'LOWER',_binary '\0',_binary '\0',650,'5B','SEATER',5),(169,'LOWER',_binary '\0',_binary '\0',650,'5C','SEATER',5),(170,'LOWER',_binary '\0',_binary '\0',650,'5D','SEATER',5),(171,'LOWER',_binary '\0',_binary '\0',650,'6A','SEATER',5),(172,'LOWER',_binary '\0',_binary '\0',650,'6B','SEATER',5),(173,'LOWER',_binary '\0',_binary '\0',650,'6C','SEATER',5),(174,'LOWER',_binary '\0',_binary '\0',650,'6D','SEATER',5),(175,'LOWER',_binary '\0',_binary '\0',650,'7A','SEATER',5),(176,'LOWER',_binary '\0',_binary '\0',650,'7B','SEATER',5),(177,'LOWER',_binary '\0',_binary '\0',650,'7C','SEATER',5),(178,'LOWER',_binary '\0',_binary '\0',650,'7D','SEATER',5),(179,'LOWER',_binary '\0',_binary '\0',650,'8A','SEATER',5),(180,'LOWER',_binary '\0',_binary '\0',650,'8B','SEATER',5),(181,'LOWER',_binary '\0',_binary '\0',650,'8C','SEATER',5),(182,'LOWER',_binary '\0',_binary '\0',650,'8D','SEATER',5),(183,'LOWER',_binary '\0',_binary '\0',650,'9A','SEATER',5),(184,'LOWER',_binary '\0',_binary '\0',650,'9B','SEATER',5),(185,'LOWER',_binary '\0',_binary '\0',650,'9C','SEATER',5),(186,'LOWER',_binary '\0',_binary '\0',650,'9D','SEATER',5),(187,'LOWER',_binary '\0',_binary '\0',650,'10A','SEATER',5),(188,'LOWER',_binary '\0',_binary '\0',650,'10B','SEATER',5),(189,'LOWER',_binary '\0',_binary '\0',650,'10C','SEATER',5),(190,'LOWER',_binary '\0',_binary '\0',650,'10D','SEATER',5),(191,'LOWER',_binary '\0',_binary '\0',750,'1A','SEATER',6),(192,'LOWER',_binary '\0',_binary '\0',750,'1B','SEATER',6),(193,'LOWER',_binary '\0',_binary '\0',750,'1C','SEATER',6),(194,'LOWER',_binary '\0',_binary '\0',750,'1D','SEATER',6),(195,'LOWER',_binary '\0',_binary '\0',750,'2A','SEATER',6),(196,'LOWER',_binary '\0',_binary '\0',750,'2B','SEATER',6),(197,'LOWER',_binary '\0',_binary '\0',750,'2C','SEATER',6),(198,'LOWER',_binary '\0',_binary '\0',750,'2D','SEATER',6),(199,'LOWER',_binary '\0',_binary '\0',750,'3A','SEATER',6),(200,'LOWER',_binary '\0',_binary '\0',750,'3B','SEATER',6),(201,'LOWER',_binary '\0',_binary '\0',750,'3C','SEATER',6),(202,'LOWER',_binary '\0',_binary '\0',750,'3D','SEATER',6),(203,'LOWER',_binary '\0',_binary '\0',750,'4A','SEATER',6),(204,'LOWER',_binary '\0',_binary '\0',750,'4B','SEATER',6),(205,'LOWER',_binary '\0',_binary '\0',750,'4C','SEATER',6),(206,'LOWER',_binary '\0',_binary '\0',750,'4D','SEATER',6),(207,'LOWER',_binary '\0',_binary '\0',750,'5A','SEATER',6),(208,'LOWER',_binary '\0',_binary '\0',750,'5B','SEATER',6),(209,'LOWER',_binary '\0',_binary '\0',750,'5C','SEATER',6),(210,'LOWER',_binary '\0',_binary '\0',750,'5D','SEATER',6),(211,'LOWER',_binary '\0',_binary '\0',750,'6A','SEATER',6),(212,'LOWER',_binary '\0',_binary '\0',750,'6B','SEATER',6),(213,'LOWER',_binary '\0',_binary '\0',750,'6C','SEATER',6),(214,'LOWER',_binary '\0',_binary '\0',750,'6D','SEATER',6),(215,'LOWER',_binary '\0',_binary '\0',750,'7A','SEATER',6),(216,'LOWER',_binary '\0',_binary '\0',750,'7B','SEATER',6),(217,'LOWER',_binary '\0',_binary '\0',750,'7C','SEATER',6),(218,'LOWER',_binary '\0',_binary '\0',750,'7D','SEATER',6),(219,'LOWER',_binary '\0',_binary '\0',750,'8A','SEATER',6),(220,'LOWER',_binary '\0',_binary '\0',750,'8B','SEATER',6),(221,'LOWER',_binary '\0',_binary '\0',750,'8C','SEATER',6),(222,'LOWER',_binary '\0',_binary '\0',750,'8D','SEATER',6),(223,'LOWER',_binary '\0',_binary '\0',750,'9A','SEATER',6),(224,'LOWER',_binary '\0',_binary '\0',750,'9B','SEATER',6),(225,'LOWER',_binary '\0',_binary '\0',750,'9C','SEATER',6),(226,'LOWER',_binary '\0',_binary '\0',750,'9D','SEATER',6),(227,'LOWER',_binary '\0',_binary '\0',750,'10A','SEATER',6),(228,'LOWER',_binary '\0',_binary '\0',750,'10B','SEATER',6),(229,'LOWER',_binary '\0',_binary '\0',750,'10C','SEATER',6),(230,'LOWER',_binary '\0',_binary '\0',750,'10D','SEATER',6),(231,'LOWER',_binary '\0',_binary '\0',650,'1A','SEATER',7),(232,'LOWER',_binary '\0',_binary '\0',650,'1B','SEATER',7),(233,'LOWER',_binary '\0',_binary '\0',650,'1C','SEATER',7),(234,'LOWER',_binary '\0',_binary '\0',650,'1D','SEATER',7),(235,'LOWER',_binary '\0',_binary '\0',650,'2A','SEATER',7),(236,'LOWER',_binary '\0',_binary '\0',650,'2B','SEATER',7),(237,'LOWER',_binary '\0',_binary '\0',650,'2C','SEATER',7),(238,'LOWER',_binary '\0',_binary '\0',650,'2D','SEATER',7),(239,'LOWER',_binary '\0',_binary '\0',650,'3A','SEATER',7),(240,'LOWER',_binary '\0',_binary '\0',650,'3B','SEATER',7),(241,'LOWER',_binary '\0',_binary '\0',650,'3C','SEATER',7),(242,'LOWER',_binary '\0',_binary '\0',650,'3D','SEATER',7),(243,'LOWER',_binary '\0',_binary '\0',650,'4A','SEATER',7),(244,'LOWER',_binary '\0',_binary '',650,'4B','SEATER',7),(245,'LOWER',_binary '\0',_binary '',650,'4C','SEATER',7),(246,'LOWER',_binary '\0',_binary '\0',650,'4D','SEATER',7),(247,'LOWER',_binary '\0',_binary '\0',650,'5A','SEATER',7),(248,'LOWER',_binary '\0',_binary '\0',650,'5B','SEATER',7),(249,'LOWER',_binary '\0',_binary '\0',650,'5C','SEATER',7),(250,'LOWER',_binary '\0',_binary '\0',650,'5D','SEATER',7),(251,'LOWER',_binary '\0',_binary '\0',650,'6A','SEATER',7),(252,'LOWER',_binary '\0',_binary '\0',650,'6B','SEATER',7),(253,'LOWER',_binary '\0',_binary '\0',650,'6C','SEATER',7),(254,'LOWER',_binary '\0',_binary '\0',650,'6D','SEATER',7),(255,'LOWER',_binary '\0',_binary '\0',650,'7A','SEATER',7),(256,'LOWER',_binary '\0',_binary '\0',650,'7B','SEATER',7),(257,'LOWER',_binary '\0',_binary '\0',650,'7C','SEATER',7),(258,'LOWER',_binary '\0',_binary '\0',650,'7D','SEATER',7),(259,'LOWER',_binary '\0',_binary '\0',650,'8A','SEATER',7),(260,'LOWER',_binary '\0',_binary '\0',650,'8B','SEATER',7),(261,'LOWER',_binary '\0',_binary '\0',650,'8C','SEATER',7),(262,'LOWER',_binary '\0',_binary '\0',650,'8D','SEATER',7),(263,'LOWER',_binary '\0',_binary '\0',650,'9A','SEATER',7),(264,'LOWER',_binary '\0',_binary '\0',650,'9B','SEATER',7),(265,'LOWER',_binary '\0',_binary '\0',650,'9C','SEATER',7),(266,'LOWER',_binary '\0',_binary '\0',650,'9D','SEATER',7),(267,'LOWER',_binary '\0',_binary '\0',650,'10A','SEATER',7),(268,'LOWER',_binary '\0',_binary '\0',650,'10B','SEATER',7),(269,'LOWER',_binary '\0',_binary '\0',650,'10C','SEATER',7),(270,'LOWER',_binary '\0',_binary '\0',650,'10D','SEATER',7),(271,'LOWER',_binary '\0',_binary '\0',700,'1A','SEATER',8),(272,'LOWER',_binary '\0',_binary '\0',700,'1B','SEATER',8),(273,'LOWER',_binary '\0',_binary '\0',700,'1C','SEATER',8),(274,'LOWER',_binary '\0',_binary '\0',700,'1D','SEATER',8),(275,'LOWER',_binary '\0',_binary '\0',700,'2A','SEATER',8),(276,'LOWER',_binary '\0',_binary '\0',700,'2B','SEATER',8),(277,'LOWER',_binary '\0',_binary '\0',700,'2C','SEATER',8),(278,'LOWER',_binary '\0',_binary '\0',700,'2D','SEATER',8),(279,'LOWER',_binary '\0',_binary '\0',700,'3A','SEATER',8),(280,'LOWER',_binary '\0',_binary '\0',700,'3B','SEATER',8),(281,'LOWER',_binary '\0',_binary '\0',700,'3C','SEATER',8),(282,'LOWER',_binary '\0',_binary '\0',700,'3D','SEATER',8),(283,'LOWER',_binary '\0',_binary '\0',700,'4A','SEATER',8),(284,'LOWER',_binary '\0',_binary '\0',700,'4B','SEATER',8),(285,'LOWER',_binary '\0',_binary '\0',700,'4C','SEATER',8),(286,'LOWER',_binary '\0',_binary '\0',700,'4D','SEATER',8),(287,'LOWER',_binary '\0',_binary '\0',700,'5A','SEATER',8),(288,'LOWER',_binary '\0',_binary '\0',700,'5B','SEATER',8),(289,'LOWER',_binary '\0',_binary '\0',700,'5C','SEATER',8),(290,'LOWER',_binary '\0',_binary '\0',700,'5D','SEATER',8),(291,'LOWER',_binary '\0',_binary '\0',700,'6A','SEATER',8),(292,'LOWER',_binary '\0',_binary '\0',700,'6B','SEATER',8),(293,'LOWER',_binary '\0',_binary '\0',700,'6C','SEATER',8),(294,'LOWER',_binary '\0',_binary '\0',700,'6D','SEATER',8),(295,'LOWER',_binary '\0',_binary '\0',700,'7A','SEATER',8),(296,'LOWER',_binary '\0',_binary '\0',700,'7B','SEATER',8),(297,'LOWER',_binary '\0',_binary '\0',700,'7C','SEATER',8),(298,'LOWER',_binary '\0',_binary '\0',700,'7D','SEATER',8),(299,'LOWER',_binary '\0',_binary '\0',700,'8A','SEATER',8),(300,'LOWER',_binary '\0',_binary '\0',700,'8B','SEATER',8),(301,'LOWER',_binary '\0',_binary '\0',700,'8C','SEATER',8),(302,'LOWER',_binary '\0',_binary '\0',700,'8D','SEATER',8),(303,'LOWER',_binary '\0',_binary '\0',700,'9A','SEATER',8),(304,'LOWER',_binary '\0',_binary '\0',700,'9B','SEATER',8),(305,'LOWER',_binary '\0',_binary '\0',700,'9C','SEATER',8),(306,'LOWER',_binary '\0',_binary '\0',700,'9D','SEATER',8),(307,'LOWER',_binary '\0',_binary '\0',700,'10A','SEATER',8),(308,'LOWER',_binary '\0',_binary '\0',700,'10B','SEATER',8),(309,'LOWER',_binary '\0',_binary '\0',700,'10C','SEATER',8),(310,'LOWER',_binary '\0',_binary '\0',700,'10D','SEATER',8),(311,'LOWER',_binary '\0',_binary '\0',750,'1A','SEATER',9),(312,'LOWER',_binary '\0',_binary '\0',750,'1B','SEATER',9),(313,'LOWER',_binary '\0',_binary '\0',750,'1C','SEATER',9),(314,'LOWER',_binary '\0',_binary '\0',750,'1D','SEATER',9),(315,'LOWER',_binary '\0',_binary '\0',750,'2A','SEATER',9),(316,'LOWER',_binary '\0',_binary '\0',750,'2B','SEATER',9),(317,'LOWER',_binary '\0',_binary '\0',750,'2C','SEATER',9),(318,'LOWER',_binary '\0',_binary '\0',750,'2D','SEATER',9),(319,'LOWER',_binary '\0',_binary '\0',750,'3A','SEATER',9),(320,'LOWER',_binary '\0',_binary '\0',750,'3B','SEATER',9),(321,'LOWER',_binary '\0',_binary '\0',750,'3C','SEATER',9),(322,'LOWER',_binary '\0',_binary '\0',750,'3D','SEATER',9),(323,'LOWER',_binary '\0',_binary '\0',750,'4A','SEATER',9),(324,'LOWER',_binary '\0',_binary '\0',750,'4B','SEATER',9),(325,'LOWER',_binary '\0',_binary '\0',750,'4C','SEATER',9),(326,'LOWER',_binary '\0',_binary '\0',750,'4D','SEATER',9),(327,'LOWER',_binary '\0',_binary '\0',750,'5A','SEATER',9),(328,'LOWER',_binary '\0',_binary '\0',750,'5B','SEATER',9),(329,'LOWER',_binary '\0',_binary '\0',750,'5C','SEATER',9),(330,'LOWER',_binary '\0',_binary '\0',750,'5D','SEATER',9),(331,'LOWER',_binary '\0',_binary '\0',750,'6A','SEATER',9),(332,'LOWER',_binary '\0',_binary '\0',750,'6B','SEATER',9),(333,'LOWER',_binary '\0',_binary '\0',750,'6C','SEATER',9),(334,'LOWER',_binary '\0',_binary '\0',750,'6D','SEATER',9),(335,'LOWER',_binary '\0',_binary '\0',750,'7A','SEATER',9),(336,'LOWER',_binary '\0',_binary '\0',750,'7B','SEATER',9),(337,'LOWER',_binary '\0',_binary '\0',750,'7C','SEATER',9),(338,'LOWER',_binary '\0',_binary '\0',750,'7D','SEATER',9),(339,'LOWER',_binary '\0',_binary '\0',750,'8A','SEATER',9),(340,'LOWER',_binary '\0',_binary '\0',750,'8B','SEATER',9),(341,'LOWER',_binary '\0',_binary '\0',750,'8C','SEATER',9),(342,'LOWER',_binary '\0',_binary '\0',750,'8D','SEATER',9),(343,'LOWER',_binary '\0',_binary '\0',750,'9A','SEATER',9),(344,'LOWER',_binary '\0',_binary '\0',750,'9B','SEATER',9),(345,'LOWER',_binary '\0',_binary '\0',750,'9C','SEATER',9),(346,'LOWER',_binary '\0',_binary '\0',750,'9D','SEATER',9),(347,'LOWER',_binary '\0',_binary '\0',750,'10A','SEATER',9),(348,'LOWER',_binary '\0',_binary '\0',750,'10B','SEATER',9),(349,'LOWER',_binary '\0',_binary '\0',750,'10C','SEATER',9),(350,'LOWER',_binary '\0',_binary '\0',750,'10D','SEATER',9),(351,'LOWER',_binary '\0',_binary '\0',550,'1A','SEATER',10),(352,'LOWER',_binary '\0',_binary '\0',550,'1B','SEATER',10),(353,'LOWER',_binary '\0',_binary '',550,'1C','SEATER',10),(354,'LOWER',_binary '\0',_binary '',550,'1D','SEATER',10),(355,'LOWER',_binary '\0',_binary '\0',550,'2A','SEATER',10),(356,'LOWER',_binary '\0',_binary '\0',550,'2B','SEATER',10),(357,'LOWER',_binary '\0',_binary '\0',550,'2C','SEATER',10),(358,'LOWER',_binary '\0',_binary '\0',550,'2D','SEATER',10),(359,'LOWER',_binary '\0',_binary '\0',550,'3A','SEATER',10),(360,'LOWER',_binary '\0',_binary '\0',550,'3B','SEATER',10),(361,'LOWER',_binary '\0',_binary '\0',550,'3C','SEATER',10),(362,'LOWER',_binary '\0',_binary '\0',550,'3D','SEATER',10),(363,'LOWER',_binary '\0',_binary '\0',550,'4A','SEATER',10),(364,'LOWER',_binary '\0',_binary '\0',550,'4B','SEATER',10),(365,'LOWER',_binary '\0',_binary '\0',550,'4C','SEATER',10),(366,'LOWER',_binary '\0',_binary '\0',550,'4D','SEATER',10),(367,'LOWER',_binary '\0',_binary '\0',550,'5A','SEATER',10),(368,'LOWER',_binary '\0',_binary '\0',550,'5B','SEATER',10),(369,'LOWER',_binary '\0',_binary '\0',550,'5C','SEATER',10),(370,'LOWER',_binary '\0',_binary '\0',550,'5D','SEATER',10),(371,'LOWER',_binary '\0',_binary '\0',550,'6A','SEATER',10),(372,'LOWER',_binary '\0',_binary '\0',550,'6B','SEATER',10),(373,'LOWER',_binary '\0',_binary '\0',550,'6C','SEATER',10),(374,'LOWER',_binary '\0',_binary '\0',550,'6D','SEATER',10),(375,'LOWER',_binary '\0',_binary '\0',550,'7A','SEATER',10),(376,'LOWER',_binary '\0',_binary '\0',550,'7B','SEATER',10),(377,'LOWER',_binary '\0',_binary '\0',550,'7C','SEATER',10),(378,'LOWER',_binary '\0',_binary '\0',550,'7D','SEATER',10),(379,'LOWER',_binary '\0',_binary '\0',550,'8A','SEATER',10),(380,'LOWER',_binary '\0',_binary '\0',550,'8B','SEATER',10),(381,'LOWER',_binary '\0',_binary '\0',550,'8C','SEATER',10),(382,'LOWER',_binary '\0',_binary '\0',550,'8D','SEATER',10),(383,'LOWER',_binary '\0',_binary '\0',550,'9A','SEATER',10),(384,'LOWER',_binary '\0',_binary '\0',550,'9B','SEATER',10),(385,'LOWER',_binary '\0',_binary '\0',550,'9C','SEATER',10),(386,'LOWER',_binary '\0',_binary '\0',550,'9D','SEATER',10),(387,'LOWER',_binary '\0',_binary '\0',550,'10A','SEATER',10),(388,'LOWER',_binary '\0',_binary '\0',550,'10B','SEATER',10),(389,'LOWER',_binary '\0',_binary '\0',550,'10C','SEATER',10),(390,'LOWER',_binary '\0',_binary '\0',550,'10D','SEATER',10),(391,'LOWER',_binary '\0',_binary '\0',600,'1A','SEATER',11),(392,'LOWER',_binary '\0',_binary '\0',600,'1B','SEATER',11),(393,'LOWER',_binary '\0',_binary '\0',600,'1C','SEATER',11),(394,'LOWER',_binary '\0',_binary '\0',600,'1D','SEATER',11),(395,'LOWER',_binary '\0',_binary '\0',600,'2A','SEATER',11),(396,'LOWER',_binary '\0',_binary '\0',600,'2B','SEATER',11),(397,'LOWER',_binary '\0',_binary '\0',600,'2C','SEATER',11),(398,'LOWER',_binary '\0',_binary '\0',600,'2D','SEATER',11),(399,'LOWER',_binary '\0',_binary '\0',600,'3A','SEATER',11),(400,'LOWER',_binary '\0',_binary '\0',600,'3B','SEATER',11),(401,'LOWER',_binary '\0',_binary '\0',600,'3C','SEATER',11),(402,'LOWER',_binary '\0',_binary '\0',600,'3D','SEATER',11),(403,'LOWER',_binary '\0',_binary '\0',600,'4A','SEATER',11),(404,'LOWER',_binary '\0',_binary '\0',600,'4B','SEATER',11),(405,'LOWER',_binary '\0',_binary '\0',600,'4C','SEATER',11),(406,'LOWER',_binary '\0',_binary '\0',600,'4D','SEATER',11),(407,'LOWER',_binary '\0',_binary '\0',600,'5A','SEATER',11),(408,'LOWER',_binary '\0',_binary '\0',600,'5B','SEATER',11),(409,'LOWER',_binary '\0',_binary '\0',600,'5C','SEATER',11),(410,'LOWER',_binary '\0',_binary '\0',600,'5D','SEATER',11),(411,'LOWER',_binary '\0',_binary '\0',600,'6A','SEATER',11),(412,'LOWER',_binary '\0',_binary '\0',600,'6B','SEATER',11),(413,'LOWER',_binary '\0',_binary '\0',600,'6C','SEATER',11),(414,'LOWER',_binary '\0',_binary '\0',600,'6D','SEATER',11),(415,'LOWER',_binary '\0',_binary '\0',600,'7A','SEATER',11),(416,'LOWER',_binary '\0',_binary '\0',600,'7B','SEATER',11),(417,'LOWER',_binary '\0',_binary '\0',600,'7C','SEATER',11),(418,'LOWER',_binary '\0',_binary '\0',600,'7D','SEATER',11),(419,'LOWER',_binary '\0',_binary '\0',600,'8A','SEATER',11),(420,'LOWER',_binary '\0',_binary '\0',600,'8B','SEATER',11),(421,'LOWER',_binary '\0',_binary '\0',600,'8C','SEATER',11),(422,'LOWER',_binary '\0',_binary '\0',600,'8D','SEATER',11),(423,'LOWER',_binary '\0',_binary '\0',600,'9A','SEATER',11),(424,'LOWER',_binary '\0',_binary '\0',600,'9B','SEATER',11),(425,'LOWER',_binary '\0',_binary '\0',600,'9C','SEATER',11),(426,'LOWER',_binary '\0',_binary '\0',600,'9D','SEATER',11),(427,'LOWER',_binary '\0',_binary '\0',600,'10A','SEATER',11),(428,'LOWER',_binary '\0',_binary '\0',600,'10B','SEATER',11),(429,'LOWER',_binary '\0',_binary '\0',600,'10C','SEATER',11),(430,'LOWER',_binary '\0',_binary '\0',600,'10D','SEATER',11),(431,'LOWER',_binary '\0',_binary '\0',650,'1A','SLEEPER',12),(432,'LOWER',_binary '\0',_binary '\0',650,'1B','SLEEPER',12),(433,'LOWER',_binary '\0',_binary '\0',650,'1C','SLEEPER',12),(434,'LOWER',_binary '\0',_binary '\0',650,'1D','SLEEPER',12),(435,'LOWER',_binary '\0',_binary '\0',650,'2A','SLEEPER',12),(436,'LOWER',_binary '\0',_binary '\0',650,'2B','SLEEPER',12),(437,'LOWER',_binary '\0',_binary '\0',650,'2C','SLEEPER',12),(438,'LOWER',_binary '\0',_binary '\0',650,'2D','SLEEPER',12),(439,'LOWER',_binary '\0',_binary '\0',650,'3A','SLEEPER',12),(440,'LOWER',_binary '\0',_binary '\0',650,'3B','SLEEPER',12),(441,'LOWER',_binary '\0',_binary '\0',650,'3C','SLEEPER',12),(442,'LOWER',_binary '\0',_binary '\0',650,'3D','SLEEPER',12),(443,'LOWER',_binary '\0',_binary '\0',650,'4A','SLEEPER',12),(444,'LOWER',_binary '\0',_binary '\0',650,'4B','SLEEPER',12),(445,'LOWER',_binary '\0',_binary '\0',650,'4C','SLEEPER',12),(446,'LOWER',_binary '\0',_binary '\0',650,'4D','SLEEPER',12),(447,'LOWER',_binary '\0',_binary '\0',650,'5A','SLEEPER',12),(448,'LOWER',_binary '\0',_binary '\0',650,'5B','SLEEPER',12),(449,'LOWER',_binary '\0',_binary '\0',650,'5C','SLEEPER',12),(450,'LOWER',_binary '\0',_binary '\0',650,'5D','SLEEPER',12),(451,'LOWER',_binary '\0',_binary '\0',650,'6A','SLEEPER',12),(452,'LOWER',_binary '\0',_binary '\0',650,'6B','SLEEPER',12),(453,'LOWER',_binary '\0',_binary '\0',650,'6C','SLEEPER',12),(454,'LOWER',_binary '\0',_binary '\0',650,'6D','SLEEPER',12),(455,'LOWER',_binary '\0',_binary '\0',650,'7A','SLEEPER',12),(456,'LOWER',_binary '\0',_binary '\0',650,'7B','SLEEPER',12),(457,'LOWER',_binary '\0',_binary '\0',650,'7C','SLEEPER',12),(458,'LOWER',_binary '\0',_binary '\0',650,'7D','SLEEPER',12),(459,'LOWER',_binary '\0',_binary '\0',650,'8A','SLEEPER',12),(460,'LOWER',_binary '\0',_binary '\0',650,'8B','SLEEPER',12),(461,'LOWER',_binary '\0',_binary '\0',480,'1A','SEATER',13),(462,'LOWER',_binary '\0',_binary '\0',480,'1B','SEATER',13),(463,'LOWER',_binary '\0',_binary '\0',480,'1C','SEATER',13),(464,'LOWER',_binary '\0',_binary '\0',480,'1D','SEATER',13),(465,'LOWER',_binary '\0',_binary '\0',480,'2A','SEATER',13),(466,'LOWER',_binary '\0',_binary '\0',480,'2B','SEATER',13),(467,'LOWER',_binary '\0',_binary '\0',480,'2C','SEATER',13),(468,'LOWER',_binary '\0',_binary '\0',480,'2D','SEATER',13),(469,'LOWER',_binary '\0',_binary '\0',480,'3A','SEATER',13),(470,'LOWER',_binary '\0',_binary '\0',480,'3B','SEATER',13),(471,'LOWER',_binary '\0',_binary '\0',480,'3C','SEATER',13),(472,'LOWER',_binary '\0',_binary '\0',480,'3D','SEATER',13),(473,'LOWER',_binary '\0',_binary '\0',480,'4A','SEATER',13),(474,'LOWER',_binary '\0',_binary '\0',480,'4B','SEATER',13),(475,'LOWER',_binary '\0',_binary '\0',480,'4C','SEATER',13),(476,'LOWER',_binary '\0',_binary '\0',480,'4D','SEATER',13),(477,'LOWER',_binary '\0',_binary '\0',480,'5A','SEATER',13),(478,'LOWER',_binary '\0',_binary '\0',480,'5B','SEATER',13),(479,'LOWER',_binary '\0',_binary '\0',480,'5C','SEATER',13),(480,'LOWER',_binary '\0',_binary '\0',480,'5D','SEATER',13),(481,'LOWER',_binary '\0',_binary '\0',480,'6A','SEATER',13),(482,'LOWER',_binary '\0',_binary '\0',480,'6B','SEATER',13),(483,'LOWER',_binary '\0',_binary '\0',480,'6C','SEATER',13),(484,'LOWER',_binary '\0',_binary '\0',480,'6D','SEATER',13),(485,'LOWER',_binary '\0',_binary '\0',480,'7A','SEATER',13),(486,'LOWER',_binary '\0',_binary '\0',480,'7B','SEATER',13),(487,'LOWER',_binary '\0',_binary '\0',480,'7C','SEATER',13),(488,'LOWER',_binary '\0',_binary '\0',480,'7D','SEATER',13),(489,'LOWER',_binary '\0',_binary '\0',480,'8A','SEATER',13),(490,'LOWER',_binary '\0',_binary '\0',480,'8B','SEATER',13),(491,'LOWER',_binary '\0',_binary '\0',480,'8C','SEATER',13),(492,'LOWER',_binary '\0',_binary '\0',480,'8D','SEATER',13),(493,'LOWER',_binary '\0',_binary '\0',480,'9A','SEATER',13),(494,'LOWER',_binary '\0',_binary '\0',480,'9B','SEATER',13),(495,'LOWER',_binary '\0',_binary '\0',480,'9C','SEATER',13),(496,'LOWER',_binary '\0',_binary '\0',480,'9D','SEATER',13),(497,'LOWER',_binary '\0',_binary '\0',480,'1A','SEATER',14),(498,'LOWER',_binary '\0',_binary '\0',480,'1B','SEATER',14),(499,'LOWER',_binary '\0',_binary '\0',480,'1C','SEATER',14),(500,'LOWER',_binary '\0',_binary '\0',480,'1D','SEATER',14),(501,'LOWER',_binary '\0',_binary '\0',480,'2A','SEATER',14),(502,'LOWER',_binary '\0',_binary '\0',480,'2B','SEATER',14),(503,'LOWER',_binary '\0',_binary '\0',480,'2C','SEATER',14),(504,'LOWER',_binary '\0',_binary '\0',480,'2D','SEATER',14),(505,'LOWER',_binary '\0',_binary '\0',480,'3A','SEATER',14),(506,'LOWER',_binary '\0',_binary '\0',480,'3B','SEATER',14),(507,'LOWER',_binary '\0',_binary '\0',480,'3C','SEATER',14),(508,'LOWER',_binary '\0',_binary '\0',480,'3D','SEATER',14),(509,'LOWER',_binary '\0',_binary '\0',480,'4A','SEATER',14),(510,'LOWER',_binary '\0',_binary '\0',480,'4B','SEATER',14),(511,'LOWER',_binary '\0',_binary '\0',480,'4C','SEATER',14),(512,'LOWER',_binary '\0',_binary '\0',480,'4D','SEATER',14),(513,'LOWER',_binary '\0',_binary '\0',480,'5A','SEATER',14),(514,'LOWER',_binary '\0',_binary '\0',480,'5B','SEATER',14),(515,'LOWER',_binary '\0',_binary '\0',480,'5C','SEATER',14),(516,'LOWER',_binary '\0',_binary '\0',480,'5D','SEATER',14),(517,'LOWER',_binary '\0',_binary '\0',480,'6A','SEATER',14),(518,'LOWER',_binary '\0',_binary '\0',480,'6B','SEATER',14),(519,'LOWER',_binary '\0',_binary '\0',480,'6C','SEATER',14),(520,'LOWER',_binary '\0',_binary '\0',480,'6D','SEATER',14),(521,'LOWER',_binary '\0',_binary '\0',480,'7A','SEATER',14),(522,'LOWER',_binary '\0',_binary '\0',480,'7B','SEATER',14),(523,'LOWER',_binary '\0',_binary '\0',480,'7C','SEATER',14),(524,'LOWER',_binary '\0',_binary '\0',480,'7D','SEATER',14),(525,'LOWER',_binary '\0',_binary '\0',480,'8A','SEATER',14),(526,'LOWER',_binary '\0',_binary '\0',480,'8B','SEATER',14),(527,'LOWER',_binary '\0',_binary '\0',480,'8C','SEATER',14),(528,'LOWER',_binary '\0',_binary '\0',480,'8D','SEATER',14),(529,'LOWER',_binary '\0',_binary '\0',480,'9A','SEATER',14),(530,'LOWER',_binary '\0',_binary '\0',480,'9B','SEATER',14),(531,'LOWER',_binary '\0',_binary '\0',480,'9C','SEATER',14),(532,'LOWER',_binary '\0',_binary '\0',480,'9D','SEATER',14),(533,'LOWER',_binary '\0',_binary '\0',950,'1A','SEATER',15),(534,'LOWER',_binary '\0',_binary '\0',950,'1B','SEATER',15),(535,'LOWER',_binary '\0',_binary '\0',950,'1C','SEATER',15),(536,'LOWER',_binary '\0',_binary '\0',950,'1D','SEATER',15),(537,'LOWER',_binary '\0',_binary '\0',950,'2A','SEATER',15),(538,'LOWER',_binary '\0',_binary '\0',950,'2B','SEATER',15),(539,'LOWER',_binary '\0',_binary '\0',950,'2C','SEATER',15),(540,'LOWER',_binary '\0',_binary '\0',950,'2D','SEATER',15),(541,'LOWER',_binary '\0',_binary '\0',950,'3A','SEATER',15),(542,'LOWER',_binary '\0',_binary '\0',950,'3B','SEATER',15),(543,'LOWER',_binary '\0',_binary '\0',950,'3C','SEATER',15),(544,'LOWER',_binary '\0',_binary '\0',950,'3D','SEATER',15),(545,'LOWER',_binary '\0',_binary '\0',950,'4A','SEATER',15),(546,'LOWER',_binary '\0',_binary '\0',950,'4B','SEATER',15),(547,'LOWER',_binary '\0',_binary '\0',950,'4C','SEATER',15),(548,'LOWER',_binary '\0',_binary '\0',950,'4D','SEATER',15),(549,'LOWER',_binary '\0',_binary '\0',950,'5A','SEATER',15),(550,'LOWER',_binary '\0',_binary '\0',950,'5B','SEATER',15),(551,'LOWER',_binary '\0',_binary '\0',950,'5C','SEATER',15),(552,'LOWER',_binary '\0',_binary '\0',950,'5D','SEATER',15),(553,'LOWER',_binary '\0',_binary '\0',950,'6A','SEATER',15),(554,'LOWER',_binary '\0',_binary '\0',950,'6B','SEATER',15),(555,'LOWER',_binary '\0',_binary '\0',950,'6C','SEATER',15),(556,'LOWER',_binary '\0',_binary '\0',950,'6D','SEATER',15),(557,'LOWER',_binary '\0',_binary '\0',950,'7A','SEATER',15),(558,'LOWER',_binary '\0',_binary '\0',950,'7B','SEATER',15),(559,'LOWER',_binary '\0',_binary '\0',950,'7C','SEATER',15),(560,'LOWER',_binary '\0',_binary '\0',950,'7D','SEATER',15),(561,'LOWER',_binary '\0',_binary '\0',950,'8A','SEATER',15),(562,'LOWER',_binary '\0',_binary '\0',950,'8B','SEATER',15),(563,'LOWER',_binary '\0',_binary '\0',950,'8C','SEATER',15),(564,'LOWER',_binary '\0',_binary '\0',950,'8D','SEATER',15),(565,'LOWER',_binary '\0',_binary '\0',950,'9A','SEATER',15),(566,'LOWER',_binary '\0',_binary '\0',950,'9B','SEATER',15),(567,'LOWER',_binary '\0',_binary '\0',950,'9C','SEATER',15),(568,'LOWER',_binary '\0',_binary '\0',950,'9D','SEATER',15),(569,'LOWER',_binary '\0',_binary '\0',1050,'1A','SLEEPER',16),(570,'LOWER',_binary '\0',_binary '\0',1050,'1B','SLEEPER',16),(571,'LOWER',_binary '\0',_binary '\0',1050,'1C','SLEEPER',16),(572,'LOWER',_binary '\0',_binary '\0',1050,'1D','SLEEPER',16),(573,'LOWER',_binary '\0',_binary '\0',1050,'2A','SLEEPER',16),(574,'LOWER',_binary '\0',_binary '\0',1050,'2B','SLEEPER',16),(575,'LOWER',_binary '\0',_binary '\0',1050,'2C','SLEEPER',16),(576,'LOWER',_binary '\0',_binary '\0',1050,'2D','SLEEPER',16),(577,'LOWER',_binary '\0',_binary '\0',1050,'3A','SLEEPER',16),(578,'LOWER',_binary '\0',_binary '\0',1050,'3B','SLEEPER',16),(579,'LOWER',_binary '\0',_binary '\0',1050,'3C','SLEEPER',16),(580,'LOWER',_binary '\0',_binary '\0',1050,'3D','SLEEPER',16),(581,'LOWER',_binary '\0',_binary '\0',1050,'4A','SLEEPER',16),(582,'LOWER',_binary '\0',_binary '\0',1050,'4B','SLEEPER',16),(583,'LOWER',_binary '\0',_binary '\0',1050,'4C','SLEEPER',16),(584,'LOWER',_binary '\0',_binary '\0',1050,'4D','SLEEPER',16),(585,'LOWER',_binary '\0',_binary '\0',1050,'5A','SLEEPER',16),(586,'LOWER',_binary '\0',_binary '\0',1050,'5B','SLEEPER',16),(587,'LOWER',_binary '\0',_binary '\0',1050,'5C','SLEEPER',16),(588,'LOWER',_binary '\0',_binary '\0',1050,'5D','SLEEPER',16),(589,'LOWER',_binary '\0',_binary '\0',1050,'6A','SLEEPER',16),(590,'LOWER',_binary '\0',_binary '\0',1050,'6B','SLEEPER',16),(591,'LOWER',_binary '\0',_binary '\0',1050,'6C','SLEEPER',16),(592,'LOWER',_binary '\0',_binary '\0',1050,'6D','SLEEPER',16),(593,'LOWER',_binary '\0',_binary '\0',1050,'7A','SLEEPER',16),(594,'LOWER',_binary '\0',_binary '\0',1050,'7B','SLEEPER',16),(595,'LOWER',_binary '\0',_binary '\0',1050,'7C','SLEEPER',16),(596,'LOWER',_binary '\0',_binary '\0',1050,'7D','SLEEPER',16),(597,'LOWER',_binary '\0',_binary '\0',1050,'8A','SLEEPER',16),(598,'LOWER',_binary '\0',_binary '\0',1050,'8B','SLEEPER',16),(599,'LOWER',_binary '\0',_binary '\0',520,'1A','SEATER',17),(600,'LOWER',_binary '\0',_binary '\0',520,'1B','SEATER',17),(601,'LOWER',_binary '\0',_binary '\0',520,'1C','SEATER',17),(602,'LOWER',_binary '\0',_binary '\0',520,'1D','SEATER',17),(603,'LOWER',_binary '\0',_binary '\0',520,'2A','SEATER',17),(604,'LOWER',_binary '\0',_binary '\0',520,'2B','SEATER',17),(605,'LOWER',_binary '\0',_binary '\0',520,'2C','SEATER',17),(606,'LOWER',_binary '\0',_binary '\0',520,'2D','SEATER',17),(607,'LOWER',_binary '\0',_binary '\0',520,'3A','SEATER',17),(608,'LOWER',_binary '\0',_binary '\0',520,'3B','SEATER',17),(609,'LOWER',_binary '\0',_binary '\0',520,'3C','SEATER',17),(610,'LOWER',_binary '\0',_binary '\0',520,'3D','SEATER',17),(611,'LOWER',_binary '\0',_binary '\0',520,'4A','SEATER',17),(612,'LOWER',_binary '\0',_binary '\0',520,'4B','SEATER',17),(613,'LOWER',_binary '\0',_binary '\0',520,'4C','SEATER',17),(614,'LOWER',_binary '\0',_binary '\0',520,'4D','SEATER',17),(615,'LOWER',_binary '\0',_binary '\0',520,'5A','SEATER',17),(616,'LOWER',_binary '\0',_binary '\0',520,'5B','SEATER',17),(617,'LOWER',_binary '\0',_binary '\0',520,'5C','SEATER',17),(618,'LOWER',_binary '\0',_binary '\0',520,'5D','SEATER',17),(619,'LOWER',_binary '\0',_binary '\0',520,'6A','SEATER',17),(620,'LOWER',_binary '\0',_binary '\0',520,'6B','SEATER',17),(621,'LOWER',_binary '\0',_binary '\0',520,'6C','SEATER',17),(622,'LOWER',_binary '\0',_binary '\0',520,'6D','SEATER',17),(623,'LOWER',_binary '\0',_binary '\0',520,'7A','SEATER',17),(624,'LOWER',_binary '\0',_binary '\0',520,'7B','SEATER',17),(625,'LOWER',_binary '\0',_binary '\0',520,'7C','SEATER',17),(626,'LOWER',_binary '\0',_binary '\0',520,'7D','SEATER',17),(627,'LOWER',_binary '\0',_binary '\0',520,'8A','SEATER',17),(628,'LOWER',_binary '\0',_binary '\0',520,'8B','SEATER',17),(629,'LOWER',_binary '\0',_binary '\0',520,'8C','SEATER',17),(630,'LOWER',_binary '\0',_binary '\0',520,'8D','SEATER',17),(631,'LOWER',_binary '\0',_binary '\0',520,'9A','SEATER',17),(632,'LOWER',_binary '\0',_binary '\0',520,'9B','SEATER',17),(633,'LOWER',_binary '\0',_binary '\0',520,'9C','SEATER',17),(634,'LOWER',_binary '\0',_binary '\0',520,'9D','SEATER',17),(635,'LOWER',_binary '\0',_binary '\0',520,'1A','SEATER',18),(636,'LOWER',_binary '\0',_binary '\0',520,'1B','SEATER',18),(637,'LOWER',_binary '\0',_binary '\0',520,'1C','SEATER',18),(638,'LOWER',_binary '\0',_binary '\0',520,'1D','SEATER',18),(639,'LOWER',_binary '\0',_binary '\0',520,'2A','SEATER',18),(640,'LOWER',_binary '\0',_binary '\0',520,'2B','SEATER',18),(641,'LOWER',_binary '\0',_binary '\0',520,'2C','SEATER',18),(642,'LOWER',_binary '\0',_binary '\0',520,'2D','SEATER',18),(643,'LOWER',_binary '\0',_binary '\0',520,'3A','SEATER',18),(644,'LOWER',_binary '\0',_binary '\0',520,'3B','SEATER',18),(645,'LOWER',_binary '\0',_binary '\0',520,'3C','SEATER',18),(646,'LOWER',_binary '\0',_binary '\0',520,'3D','SEATER',18),(647,'LOWER',_binary '\0',_binary '\0',520,'4A','SEATER',18),(648,'LOWER',_binary '\0',_binary '\0',520,'4B','SEATER',18),(649,'LOWER',_binary '\0',_binary '\0',520,'4C','SEATER',18),(650,'LOWER',_binary '\0',_binary '\0',520,'4D','SEATER',18),(651,'LOWER',_binary '\0',_binary '\0',520,'5A','SEATER',18),(652,'LOWER',_binary '\0',_binary '\0',520,'5B','SEATER',18),(653,'LOWER',_binary '\0',_binary '\0',520,'5C','SEATER',18),(654,'LOWER',_binary '\0',_binary '\0',520,'5D','SEATER',18),(655,'LOWER',_binary '\0',_binary '\0',520,'6A','SEATER',18),(656,'LOWER',_binary '\0',_binary '\0',520,'6B','SEATER',18),(657,'LOWER',_binary '\0',_binary '\0',520,'6C','SEATER',18),(658,'LOWER',_binary '\0',_binary '\0',520,'6D','SEATER',18),(659,'LOWER',_binary '\0',_binary '\0',520,'7A','SEATER',18),(660,'LOWER',_binary '\0',_binary '\0',520,'7B','SEATER',18),(661,'LOWER',_binary '\0',_binary '\0',520,'7C','SEATER',18),(662,'LOWER',_binary '\0',_binary '\0',520,'7D','SEATER',18),(663,'LOWER',_binary '\0',_binary '\0',520,'8A','SEATER',18),(664,'LOWER',_binary '\0',_binary '\0',520,'8B','SEATER',18),(665,'LOWER',_binary '\0',_binary '\0',520,'8C','SEATER',18),(666,'LOWER',_binary '\0',_binary '\0',520,'8D','SEATER',18),(667,'LOWER',_binary '\0',_binary '\0',520,'9A','SEATER',18),(668,'LOWER',_binary '\0',_binary '\0',520,'9B','SEATER',18),(669,'LOWER',_binary '\0',_binary '\0',520,'9C','SEATER',18),(670,'LOWER',_binary '\0',_binary '\0',520,'9D','SEATER',18),(671,'LOWER',_binary '\0',_binary '\0',580,'1A','SEATER',19),(672,'LOWER',_binary '\0',_binary '\0',580,'1B','SEATER',19),(673,'LOWER',_binary '\0',_binary '\0',580,'1C','SEATER',19),(674,'LOWER',_binary '\0',_binary '\0',580,'1D','SEATER',19),(675,'LOWER',_binary '\0',_binary '\0',580,'2A','SEATER',19),(676,'LOWER',_binary '\0',_binary '\0',580,'2B','SEATER',19),(677,'LOWER',_binary '\0',_binary '\0',580,'2C','SEATER',19),(678,'LOWER',_binary '\0',_binary '\0',580,'2D','SEATER',19),(679,'LOWER',_binary '\0',_binary '\0',580,'3A','SEATER',19),(680,'LOWER',_binary '\0',_binary '\0',580,'3B','SEATER',19),(681,'LOWER',_binary '\0',_binary '\0',580,'3C','SEATER',19),(682,'LOWER',_binary '\0',_binary '\0',580,'3D','SEATER',19),(683,'LOWER',_binary '\0',_binary '\0',580,'4A','SEATER',19),(684,'LOWER',_binary '\0',_binary '\0',580,'4B','SEATER',19),(685,'LOWER',_binary '\0',_binary '\0',580,'4C','SEATER',19),(686,'LOWER',_binary '\0',_binary '\0',580,'4D','SEATER',19),(687,'LOWER',_binary '\0',_binary '\0',580,'5A','SEATER',19),(688,'LOWER',_binary '\0',_binary '\0',580,'5B','SEATER',19),(689,'LOWER',_binary '\0',_binary '\0',580,'5C','SEATER',19),(690,'LOWER',_binary '\0',_binary '\0',580,'5D','SEATER',19),(691,'LOWER',_binary '\0',_binary '\0',580,'6A','SEATER',19),(692,'LOWER',_binary '\0',_binary '\0',580,'6B','SEATER',19),(693,'LOWER',_binary '\0',_binary '\0',580,'6C','SEATER',19),(694,'LOWER',_binary '\0',_binary '\0',580,'6D','SEATER',19),(695,'LOWER',_binary '\0',_binary '\0',580,'7A','SEATER',19),(696,'LOWER',_binary '\0',_binary '\0',580,'7B','SEATER',19),(697,'LOWER',_binary '\0',_binary '\0',580,'7C','SEATER',19),(698,'LOWER',_binary '\0',_binary '\0',580,'7D','SEATER',19),(699,'LOWER',_binary '\0',_binary '\0',580,'8A','SEATER',19),(700,'LOWER',_binary '\0',_binary '\0',580,'8B','SEATER',19),(701,'LOWER',_binary '\0',_binary '\0',580,'8C','SEATER',19),(702,'LOWER',_binary '\0',_binary '\0',580,'8D','SEATER',19),(703,'LOWER',_binary '\0',_binary '\0',580,'9A','SEATER',19),(704,'LOWER',_binary '\0',_binary '\0',580,'9B','SEATER',19),(705,'LOWER',_binary '\0',_binary '\0',580,'9C','SEATER',19),(706,'LOWER',_binary '\0',_binary '\0',580,'9D','SEATER',19),(707,'LOWER',_binary '\0',_binary '\0',580,'1A','SEATER',20),(708,'LOWER',_binary '\0',_binary '\0',580,'1B','SEATER',20),(709,'LOWER',_binary '\0',_binary '\0',580,'1C','SEATER',20),(710,'LOWER',_binary '\0',_binary '\0',580,'1D','SEATER',20),(711,'LOWER',_binary '\0',_binary '\0',580,'2A','SEATER',20),(712,'LOWER',_binary '\0',_binary '\0',580,'2B','SEATER',20),(713,'LOWER',_binary '\0',_binary '\0',580,'2C','SEATER',20),(714,'LOWER',_binary '\0',_binary '\0',580,'2D','SEATER',20),(715,'LOWER',_binary '\0',_binary '\0',580,'3A','SEATER',20),(716,'LOWER',_binary '\0',_binary '\0',580,'3B','SEATER',20),(717,'LOWER',_binary '\0',_binary '\0',580,'3C','SEATER',20),(718,'LOWER',_binary '\0',_binary '\0',580,'3D','SEATER',20),(719,'LOWER',_binary '\0',_binary '\0',580,'4A','SEATER',20),(720,'LOWER',_binary '\0',_binary '\0',580,'4B','SEATER',20),(721,'LOWER',_binary '\0',_binary '\0',580,'4C','SEATER',20),(722,'LOWER',_binary '\0',_binary '\0',580,'4D','SEATER',20),(723,'LOWER',_binary '\0',_binary '\0',580,'5A','SEATER',20),(724,'LOWER',_binary '\0',_binary '\0',580,'5B','SEATER',20),(725,'LOWER',_binary '\0',_binary '\0',580,'5C','SEATER',20),(726,'LOWER',_binary '\0',_binary '\0',580,'5D','SEATER',20),(727,'LOWER',_binary '\0',_binary '\0',580,'6A','SEATER',20),(728,'LOWER',_binary '\0',_binary '\0',580,'6B','SEATER',20),(729,'LOWER',_binary '\0',_binary '\0',580,'6C','SEATER',20),(730,'LOWER',_binary '\0',_binary '\0',580,'6D','SEATER',20),(731,'LOWER',_binary '\0',_binary '\0',580,'7A','SEATER',20),(732,'LOWER',_binary '\0',_binary '\0',580,'7B','SEATER',20),(733,'LOWER',_binary '\0',_binary '\0',580,'7C','SEATER',20),(734,'LOWER',_binary '\0',_binary '\0',580,'7D','SEATER',20),(735,'LOWER',_binary '\0',_binary '\0',580,'8A','SEATER',20),(736,'LOWER',_binary '\0',_binary '\0',580,'8B','SEATER',20),(737,'LOWER',_binary '\0',_binary '\0',580,'8C','SEATER',20),(738,'LOWER',_binary '\0',_binary '\0',580,'8D','SEATER',20),(739,'LOWER',_binary '\0',_binary '\0',580,'9A','SEATER',20),(740,'LOWER',_binary '\0',_binary '\0',580,'9B','SEATER',20),(741,'LOWER',_binary '\0',_binary '\0',580,'9C','SEATER',20),(742,'LOWER',_binary '\0',_binary '\0',580,'9D','SEATER',20);
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` enum('ROLE_ADMIN','ROLE_PASSENGER') COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-11-02 07:45:25.086267','admin@routewise.com','System Administrator','$2a$10$plvE9SrVXmv4qF2iW5LDHOpRYBK2mKuP8kmvXwjPy1q4KsZ8hl8Ta','+91 98123 45678','ROLE_ADMIN'),(2,'2026-11-02 07:45:25.103042','passenger@routewise.com','Rahul Verma','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98765 43210','ROLE_PASSENGER'),(3,'2026-11-02 07:45:25.104084','rohit.sharma@example.com','Rohit Sharma','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98234 11223','ROLE_PASSENGER'),(4,'2026-11-02 07:45:25.104934','priya.singh@example.com','Priya Singh','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98345 22334','ROLE_PASSENGER'),(5,'2026-11-02 07:45:25.105749','aarav.mehta@example.com','Aarav Mehta','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98456 33445','ROLE_PASSENGER'),(6,'2026-11-02 07:45:25.106640','neha.patel@example.com','Neha Patel','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98567 44556','ROLE_PASSENGER'),(7,'2026-11-02 07:45:25.107418','vikram.verma@example.com','Vikram Verma','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98678 55667','ROLE_PASSENGER'),(8,'2026-11-02 07:45:25.108416','anjali.sharma@example.com','Anjali Sharma','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98789 66778','ROLE_PASSENGER'),(9,'2026-11-02 07:45:25.109254','kabir.das@example.com','Kabir Das','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98890 77889','ROLE_PASSENGER'),(10,'2026-11-02 07:45:25.110066','simran.kaur@example.com','Simran Kaur','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98901 88990','ROLE_PASSENGER'),(11,'2026-11-02 07:45:25.110866','aditya.roy@example.com','Aditya Roy','$2a$10$IwsrhNUpSXL9lkMS6aIemeEHJofJgOc7MWFKC.0PhPLfTcfLMt4WK','+91 98012 99001','ROLE_PASSENGER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `waiting_list`
--

DROP TABLE IF EXISTS `waiting_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `waiting_list` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `passenger_email` varchar(120) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `passenger_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `priority_level` int NOT NULL,
  `seats_requested` int NOT NULL,
  `status` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `schedule_id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5pu5o9keism6d59dpb6oij7d1` (`schedule_id`),
  KEY `FKb784wphucj87r0lsm4ffwgmo8` (`user_id`),
  CONSTRAINT `FK5pu5o9keism6d59dpb6oij7d1` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`),
  CONSTRAINT `FKb784wphucj87r0lsm4ffwgmo8` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waiting_list`
--

LOCK TABLES `waiting_list` WRITE;
/*!40000 ALTER TABLE `waiting_list` DISABLE KEYS */;
INSERT INTO `waiting_list` VALUES (1,'+91 99887 76655','2026-11-02 07:45:25.374579','sunil.verma@example.com','Sunil Verma',2,1,'CONFIRMED',1,NULL);
/*!40000 ALTER TABLE `waiting_list` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-02 13:19:42
