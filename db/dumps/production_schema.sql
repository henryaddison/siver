-- MySQL dump 10.13  Distrib 5.1.57, for apple-darwin10.3.0 (i386)
--
-- Host: localhost    Database: siver_production
-- ------------------------------------------------------
-- Server version	5.1.57

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
-- Table structure for table `boat_records`
--

DROP TABLE IF EXISTS `boat_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boat_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scheduled_launch_id` int(11) DEFAULT NULL,
  `experiment_run_id` int(11) NOT NULL,
  `launch_tick` int(11) NOT NULL,
  `land_tick` int(11) DEFAULT NULL,
  `desired_gear` int(11) NOT NULL,
  `speed_multiplier` double NOT NULL DEFAULT '0.5',
  `distance_covered` double NOT NULL,
  `aggregate_gear_difference` int(11) DEFAULT NULL,
  `brain_type` varchar(255) DEFAULT NULL,
  `aggregate_tenth_tick_gear_difference` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `boat_record_launch_fk` (`scheduled_launch_id`),
  KEY `boat_record_experiment_run_fk` (`experiment_run_id`),
  CONSTRAINT `boat_record_experiment_run_fk` FOREIGN KEY (`experiment_run_id`) REFERENCES `experiment_runs` (`id`),
  CONSTRAINT `boat_record_launch_fk` FOREIGN KEY (`scheduled_launch_id`) REFERENCES `scheduled_launches` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66433 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crash_records`
--

DROP TABLE IF EXISTS `crash_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crash_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `experiment_run_id` int(11) NOT NULL,
  `tick` int(11) NOT NULL,
  `middle_lane` tinyint(1) DEFAULT NULL,
  `relative_velocity` double DEFAULT NULL,
  `boats_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `crash_record_experiment_run_fk` (`experiment_run_id`),
  CONSTRAINT `crash_record_experiment_run_fk` FOREIGN KEY (`experiment_run_id`) REFERENCES `experiment_runs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `experiment_runs`
--

DROP TABLE IF EXISTS `experiment_runs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experiment_runs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `experiment_id` int(11) DEFAULT NULL,
  `started_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tick_count` int(11) DEFAULT NULL,
  `random_seed` int(11) DEFAULT NULL,
  `flushed` tinyint(1) DEFAULT '0',
  `all_boats_finished` tinyint(1) DEFAULT '0',
  `brain_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `experiment_run_schedule_fk` (`experiment_id`),
  CONSTRAINT `experiment_run_schedule_fk` FOREIGN KEY (`experiment_id`) REFERENCES `experiments` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3480 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `experiments`
--

DROP TABLE IF EXISTS `experiments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experiments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `random_seed` int(11) NOT NULL,
  `schedule_id` int(11) NOT NULL,
  `brain_type` varchar(255) NOT NULL DEFAULT 'BasicBrain',
  PRIMARY KEY (`id`),
  KEY `experiment_schedule_fk` (`schedule_id`),
  CONSTRAINT `experiment_schedule_fk` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1901 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scheduled_launches`
--

DROP TABLE IF EXISTS `scheduled_launches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduled_launches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schedule_id` int(11) NOT NULL,
  `desired_gear` int(11) NOT NULL,
  `speed_multiplier` double NOT NULL DEFAULT '0.5',
  `distance_to_cover` double NOT NULL,
  `launch_tick` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tick_unique_key` (`schedule_id`,`launch_tick`),
  CONSTRAINT `launch_schedule_fk` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1502 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `schedule_name_and_version` (`name`,`version`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-08-21 19:00:38
-- MySQL dump 10.13  Distrib 5.1.57, for apple-darwin10.3.0 (i386)
--
-- Host: localhost    Database: siver_production
-- ------------------------------------------------------
-- Server version	5.1.57

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
-- Table structure for table `migrations`
--

DROP TABLE IF EXISTS `migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `migrations` (
  `version` int(11) NOT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `migrations`
--

LOCK TABLES `migrations` WRITE;
/*!40000 ALTER TABLE `migrations` DISABLE KEYS */;
INSERT INTO `migrations` VALUES (0),(1),(2),(3),(4);
/*!40000 ALTER TABLE `migrations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-08-21 19:00:38
