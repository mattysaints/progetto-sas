-- MariaDB dump 10.17  Distrib 10.4.11-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: catering
-- ------------------------------------------------------
-- Server version	10.4.11-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `Events`
--

DROP TABLE IF EXISTS `Events`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Events`
(
    `id`                    int(11) NOT NULL AUTO_INCREMENT,
    `name`                  varchar(128) DEFAULT NULL,
    `date_start`            date         DEFAULT NULL,
    `date_end`              date         DEFAULT NULL,
    `expected_participants` int(11)      DEFAULT NULL,
    `organizer_id`          int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Events`
--

LOCK TABLES `Events` WRITE;
/*!40000 ALTER TABLE `Events`
    DISABLE KEYS */;
INSERT INTO `Events`
VALUES (1, 'Convegno Agile Community', '2020-09-25', '2020-09-25', 100, 2),
       (2, 'Compleanno di Sara', '2020-08-13', '2020-08-13', 25, 2),
       (3, 'Fiera del Sedano Rapa', '2020-10-02', '2020-10-04', 400, 1);
/*!40000 ALTER TABLE `Events`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KitchenTaskSummaries`
--

DROP TABLE IF EXISTS `KitchenTaskSummaries`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KitchenTaskSummaries`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `service_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `KitchenTaskSummaries_service_id_uindex` (`service_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KitchenTaskSummaries`
--

LOCK TABLES `KitchenTaskSummaries` WRITE;
/*!40000 ALTER TABLE `KitchenTaskSummaries`
    DISABLE KEYS */;
INSERT INTO `KitchenTaskSummaries`
VALUES (1, 2),
       (2, 7);
/*!40000 ALTER TABLE `KitchenTaskSummaries`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KitchenTaskTurnAssignment`
--

DROP TABLE IF EXISTS `KitchenTaskTurnAssignment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KitchenTaskTurnAssignment`
(
    `kitchen_task_id` int(11) NOT NULL,
    `turn_id`         int(11) NOT NULL,
    PRIMARY KEY (`kitchen_task_id`, `turn_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KitchenTaskTurnAssignment`
--

LOCK TABLES `KitchenTaskTurnAssignment` WRITE;
/*!40000 ALTER TABLE `KitchenTaskTurnAssignment`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `KitchenTaskTurnAssignment`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KitchenTasks`
--

DROP TABLE IF EXISTS `KitchenTasks`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KitchenTasks`
(
    `id`                      int(11)      NOT NULL AUTO_INCREMENT,
    `preparation_time`        int(11)      NOT NULL,
    `product_quantity`        varchar(120) NOT NULL,
    `to_prepare`              tinyint(1)   NOT NULL,
    `completed`               tinyint(1)   NOT NULL,
    `kitchen_task_summary_id` int(11)      NOT NULL,
    `recipe_id`               int(11)      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KitchenTasks`
--

LOCK TABLES `KitchenTasks` WRITE;
/*!40000 ALTER TABLE `KitchenTasks`
    DISABLE KEYS */;
INSERT INTO `KitchenTasks`
VALUES (1, 8, '200', 1, 0, 1, 9);
/*!40000 ALTER TABLE `KitchenTasks`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KitchentTaskCookAssignment`
--

DROP TABLE IF EXISTS `KitchentTaskCookAssignment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KitchentTaskCookAssignment`
(
    `kitchen_task_id` int(11) NOT NULL,
    `user_id`         int(11) NOT NULL,
    PRIMARY KEY (`kitchen_task_id`, `user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KitchentTaskCookAssignment`
--

LOCK TABLES `KitchentTaskCookAssignment` WRITE;
/*!40000 ALTER TABLE `KitchentTaskCookAssignment`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `KitchentTaskCookAssignment`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MenuFeatures`
--

DROP TABLE IF EXISTS `MenuFeatures`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MenuFeatures`
(
    `menu_id` int(11)      NOT NULL,
    `name`    varchar(128) NOT NULL DEFAULT '',
    `value`   tinyint(1)            DEFAULT 0
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MenuFeatures`
--

LOCK TABLES `MenuFeatures` WRITE;
/*!40000 ALTER TABLE `MenuFeatures`
    DISABLE KEYS */;
INSERT INTO `MenuFeatures`
VALUES (80, 'Richiede cuoco', 0),
       (80, 'Buffet', 0),
       (80, 'Richiede cucina', 0),
       (80, 'Finger food', 0),
       (80, 'Piatti caldi', 0),
       (82, 'Richiede cuoco', 0),
       (82, 'Buffet', 0),
       (82, 'Richiede cucina', 0),
       (82, 'Finger food', 0),
       (82, 'Piatti caldi', 0),
       (86, 'Richiede cuoco', 0),
       (86, 'Buffet', 0),
       (86, 'Richiede cucina', 0),
       (86, 'Finger food', 0),
       (86, 'Piatti caldi', 0);
/*!40000 ALTER TABLE `MenuFeatures`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MenuItems`
--

DROP TABLE IF EXISTS `MenuItems`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MenuItems`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `menu_id`     int(11) NOT NULL,
    `section_id`  int(11)  DEFAULT NULL,
    `description` tinytext DEFAULT NULL,
    `recipe_id`   int(11) NOT NULL,
    `position`    int(11)  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 124
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MenuItems`
--

LOCK TABLES `MenuItems` WRITE;
/*!40000 ALTER TABLE `MenuItems`
    DISABLE KEYS */;
INSERT INTO `MenuItems`
VALUES (96, 80, 0, 'Croissant vuoti', 9, 0),
       (97, 80, 0, 'Croissant alla marmellata', 9, 1),
       (98, 80, 0, 'Pane al cioccolato mignon', 10, 2),
       (99, 80, 0, 'Panini al latte con prosciutto crudo', 12, 4),
       (100, 80, 0, 'Panini al latte con prosciutto cotto', 12, 5),
       (101, 80, 0, 'Panini al latte con formaggio spalmabile alle erbe', 12, 6),
       (102, 80, 0, 'Girelle all\'uvetta mignon', 11, 3),
       (103, 82, 0, 'Biscotti', 13, 1),
       (104, 82, 0, 'Lingue di gatto', 14, 2),
       (105, 82, 0, 'Bigné alla crema', 15, 3),
       (106, 82, 0, 'Bigné al caffè', 15, 4),
       (107, 82, 0, 'Pizzette', 16, 5),
       (108, 82, 0, 'Croissant al prosciutto crudo mignon', 9, 6),
       (109, 82, 0, 'Tramezzini tonno e carciofini mignon', 17, 7),
       (112, 86, 41, 'Vitello tonnato', 1, 0),
       (113, 86, 41, 'Carpaccio di spada', 2, 1),
       (114, 86, 41, 'Alici marinate', 3, 2),
       (115, 86, 42, 'Penne alla messinese', 5, 0),
       (116, 86, 42, 'Risotto alla zucca', 20, 1),
       (117, 86, 43, 'Salmone al forno', 8, 0),
       (118, 86, 44, 'Sorbetto al limone', 18, 0),
       (119, 86, 44, 'Torta Saint Honoré', 19, 1);
/*!40000 ALTER TABLE `MenuItems`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MenuSections`
--

DROP TABLE IF EXISTS `MenuSections`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MenuSections`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `menu_id`  int(11) NOT NULL,
    `name`     tinytext DEFAULT NULL,
    `position` int(11)  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 48
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MenuSections`
--

LOCK TABLES `MenuSections` WRITE;
/*!40000 ALTER TABLE `MenuSections`
    DISABLE KEYS */;
INSERT INTO `MenuSections`
VALUES (41, 86, 'Antipasti', 0),
       (42, 86, 'Primi', 1),
       (43, 86, 'Secondi', 2),
       (44, 86, 'Dessert', 3);
/*!40000 ALTER TABLE `MenuSections`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Menus`
--

DROP TABLE IF EXISTS `Menus`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Menus`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `title`     tinytext   DEFAULT NULL,
    `owner_id`  int(11)    DEFAULT NULL,
    `published` tinyint(1) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 90
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Menus`
--

LOCK TABLES `Menus` WRITE;
/*!40000 ALTER TABLE `Menus`
    DISABLE KEYS */;
INSERT INTO `Menus`
VALUES (80, 'Coffee break mattutino', 2, 1),
       (82, 'Coffee break pomeridiano', 2, 1),
       (86, 'Cena di compleanno pesce', 3, 1);
/*!40000 ALTER TABLE `Menus`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Recipes`
--

DROP TABLE IF EXISTS `Recipes`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Recipes`
(
    `id`             int(11)    NOT NULL AUTO_INCREMENT,
    `name`           tinytext            DEFAULT NULL,
    `is_preparation` tinyint(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 24
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Recipes`
--

LOCK TABLES `Recipes` WRITE;
/*!40000 ALTER TABLE `Recipes`
    DISABLE KEYS */;
INSERT INTO `Recipes`
VALUES (1, 'Vitello tonnato', 0),
       (2, 'Carpaccio di spada', 0),
       (3, 'Alici marinate', 0),
       (4, 'Insalata di riso', 0),
       (5, 'Penne al sugo di baccalà', 0),
       (6, 'Pappa al pomodoro', 0),
       (7, 'Hamburger con bacon e cipolla caramellata', 0),
       (8, 'Salmone al forno', 0),
       (9, 'Croissant', 0),
       (10, 'Pane al cioccolato', 0),
       (11, 'Girelle all\'uvetta', 0),
       (12, 'Panini al latte', 0),
       (13, 'Biscotti di pasta frolla', 0),
       (14, 'Lingue di gatto', 0),
       (15, 'Bigné farciti', 0),
       (16, 'Pizzette', 0),
       (17, 'Tramezzini', 0),
       (18, 'Sorbetto al limone', 0),
       (19, 'Torta Saint Honoré', 0),
       (20, 'Risotto alla zucca', 0),
       (21, 'Ragù', 1),
       (22, 'Besciamella', 1),
       (23, 'Sfoglia di pasta', 1);
/*!40000 ALTER TABLE `Recipes`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS `Roles`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Roles`
(
    `id`   char(1)      NOT NULL,
    `role` varchar(128) NOT NULL DEFAULT 'servizio',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Roles`
--

LOCK TABLES `Roles` WRITE;
/*!40000 ALTER TABLE `Roles`
    DISABLE KEYS */;
INSERT INTO `Roles`
VALUES ('c', 'cuoco'),
       ('h', 'chef'),
       ('o', 'organizzatore'),
       ('s', 'servizio');
/*!40000 ALTER TABLE `Roles`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Services`
--

DROP TABLE IF EXISTS `Services`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Services`
(
    `id`                    int(11) NOT NULL AUTO_INCREMENT,
    `event_id`              int(11) NOT NULL,
    `name`                  varchar(128)     DEFAULT NULL,
    `proposed_menu_id`      int(11) NOT NULL DEFAULT 0,
    `approved_menu_id`      int(11)          DEFAULT 0,
    `service_date`          date             DEFAULT NULL,
    `time_start`            time             DEFAULT NULL,
    `time_end`              time             DEFAULT NULL,
    `expected_participants` int(11)          DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Services`
--

LOCK TABLES `Services` WRITE;
/*!40000 ALTER TABLE `Services`
    DISABLE KEYS */;
INSERT INTO `Services`
VALUES (1, 2, 'Cena', 86, 0, '2020-08-13', '20:00:00', '23:30:00', 25),
       (2, 1, 'Coffee break mattino', 0, 80, '2020-09-25', '10:30:00', '11:30:00', 100),
       (3, 1, 'Colazione di lavoro', 0, 0, '2020-09-25', '13:00:00', '14:00:00', 80),
       (4, 1, 'Coffee break pomeriggio', 0, 82, '2020-09-25', '16:00:00', '16:30:00', 100),
       (5, 1, 'Cena sociale', 0, 0, '2020-09-25', '20:00:00', '22:30:00', 40),
       (6, 3, 'Pranzo giorno 1', 0, 0, '2020-10-02', '12:00:00', '15:00:00', 200),
       (7, 3, 'Pranzo giorno 2', 0, 0, '2020-10-03', '12:00:00', '15:00:00', 300),
       (8, 3, 'Pranzo giorno 3', 0, 0, '2020-10-04', '12:00:00', '15:00:00', 400);
/*!40000 ALTER TABLE `Services`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Turns`
--

DROP TABLE IF EXISTS `Turns`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Turns`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `time_start` time    NOT NULL,
    `time_end`   time    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Turns`
--

LOCK TABLES `Turns` WRITE;
/*!40000 ALTER TABLE `Turns`
    DISABLE KEYS */;
INSERT INTO `Turns`
VALUES (1, '08:00:00', '10:00:00'),
       (2, '10:00:00', '12:00:00'),
       (3, '12:00:00', '14:00:00'),
       (4, '15:00:00', '17:00:00'),
       (5, '17:00:00', '19:00:00'),
       (6, '19:00:00', '21:00:00'),
       (7, '21:00:00', '23:00:00'),
       (8, '23:00:00', '01:00:00');
/*!40000 ALTER TABLE `Turns`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserRoles`
--

DROP TABLE IF EXISTS `UserRoles`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserRoles`
(
    `user_id` int(11) NOT NULL,
    `role_id` char(1) NOT NULL DEFAULT 's'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserRoles`
--

LOCK TABLES `UserRoles` WRITE;
/*!40000 ALTER TABLE `UserRoles`
    DISABLE KEYS */;
INSERT INTO `UserRoles`
VALUES (1, 'o'),
       (2, 'o'),
       (2, 'h'),
       (3, 'h'),
       (4, 'h'),
       (4, 'c'),
       (5, 'c'),
       (6, 'c'),
       (7, 'c'),
       (8, 's'),
       (9, 's'),
       (10, 's'),
       (7, 's');
/*!40000 ALTER TABLE `UserRoles`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserTurns`
--

DROP TABLE IF EXISTS `UserTurns`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserTurns`
(
    `user_id` int(11) NOT NULL,
    `turn_id` int(11) NOT NULL,
    PRIMARY KEY (`user_id`, `turn_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserTurns`
--

LOCK TABLES `UserTurns` WRITE;
/*!40000 ALTER TABLE `UserTurns`
    DISABLE KEYS */;
INSERT INTO `UserTurns`
VALUES (5, 1),
       (5, 2),
       (5, 3),
       (6, 4),
       (6, 5),
       (6, 6),
       (6, 7),
       (6, 8);
/*!40000 ALTER TABLE `UserTurns`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users`
(
    `id`       int(11)      NOT NULL AUTO_INCREMENT,
    `username` varchar(128) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users`
    DISABLE KEYS */;
INSERT INTO `Users`
VALUES (1, 'Carlin'),
       (2, 'Lidia'),
       (3, 'Tony'),
       (4, 'Marinella'),
       (5, 'Guido'),
       (6, 'Antonietta'),
       (7, 'Paola'),
       (8, 'Silvia'),
       (9, 'Marco'),
       (10, 'Piergiorgio');
/*!40000 ALTER TABLE `Users`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-06-29 10:21:50
