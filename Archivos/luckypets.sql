-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: luckypets
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `anuncios`
--

DROP TABLE IF EXISTS `anuncios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anuncios` (
  `AnuncioID` int NOT NULL AUTO_INCREMENT,
  `MascotaID` int NOT NULL,
  `UserID` int NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime NOT NULL,
  `Descripcion` varchar(555) DEFAULT NULL,
  `Estado` varchar(255) DEFAULT NULL,
  `costo_cr` decimal(10,2) NOT NULL,
  `foto_anuncio` mediumblob,
  PRIMARY KEY (`AnuncioID`),
  KEY `MascotaID` (`MascotaID`),
  KEY `anuncios_ibfk_2` (`UserID`),
  CONSTRAINT `anuncios_ibfk_1` FOREIGN KEY (`MascotaID`) REFERENCES `mascotas` (`MascotaID`) ON DELETE CASCADE,
  CONSTRAINT `anuncios_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `usuarios` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anuncios`
--

LOCK TABLES `anuncios` WRITE;
/*!40000 ALTER TABLE `anuncios` DISABLE KEYS */;
INSERT INTO `anuncios` VALUES (1,1,2,'2024-03-20 08:00:00','2024-03-20 16:00:00','Dar de comer a mi gato 2 veces al día, por la mañana y por la noche.','pendiente',20.00,NULL),(2,2,3,'2024-03-21 10:00:00','2024-03-21 11:00:00','Dar de comer a mi loro 1 vez al día.','completado',5.00,NULL),(3,3,1,'2024-03-22 00:00:00','2024-03-23 00:00:00','Dar paseos por el parque durante 1 hora al día, con mi perro.','en_curso',50.00,NULL);
/*!40000 ALTER TABLE `anuncios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historialtransacciones`
--

DROP TABLE IF EXISTS `historialtransacciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historialtransacciones` (
  `TransaccionID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `Fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  `MontoCR` decimal(10,2) NOT NULL,
  `Tipo` enum('adición','sustracción') NOT NULL,
  `ReservaID` int DEFAULT NULL,
  PRIMARY KEY (`TransaccionID`),
  KEY `UserID` (`UserID`),
  KEY `ReservaID` (`ReservaID`),
  CONSTRAINT `historialtransacciones_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `usuarios` (`UserID`) ON DELETE CASCADE,
  CONSTRAINT `historialtransacciones_ibfk_2` FOREIGN KEY (`ReservaID`) REFERENCES `anuncios` (`AnuncioID`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historialtransacciones`
--

LOCK TABLES `historialtransacciones` WRITE;
/*!40000 ALTER TABLE `historialtransacciones` DISABLE KEYS */;
INSERT INTO `historialtransacciones` VALUES (1,2,'2024-03-20 10:00:00',20.00,'sustracción',1),(2,3,'2024-03-21 12:00:00',5.00,'adición',2),(3,1,'2024-03-22 01:00:00',50.00,'sustracción',3);
/*!40000 ALTER TABLE `historialtransacciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mascotas`
--

DROP TABLE IF EXISTS `mascotas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mascotas` (
  `MascotaID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `Nombre` varchar(35) NOT NULL,
  `Tipo` varchar(35) NOT NULL,
  `Raza` varchar(35) DEFAULT NULL,
  `Edad` int DEFAULT NULL,
  `requisitos_especiales` text,
  PRIMARY KEY (`MascotaID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `mascotas_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `usuarios` (`UserID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mascotas`
--

LOCK TABLES `mascotas` WRITE;
/*!40000 ALTER TABLE `mascotas` DISABLE KEYS */;
INSERT INTO `mascotas` VALUES (1,1,'Bobby','Perro','Labrador',5,'Necesita medicina para la alergia'),(2,2,'Mia','Gato','Siames',3,''),(3,3,'Rex','Perro','Pastor Alemán',4,'Ejercicio intenso diario');
/*!40000 ALTER TABLE `mascotas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjetabancaria`
--

DROP TABLE IF EXISTS `tarjetabancaria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarjetabancaria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero_tarjeta` bigint NOT NULL,
  `fecha_caducidad` date NOT NULL,
  `titular_tarjeta` varchar(255) NOT NULL,
  `emisor_tarjeta` varchar(100) NOT NULL,
  `cvv` int NOT NULL,
  `img_tarjeta` varchar(225) DEFAULT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_tarjeta_unique` (`numero_tarjeta`),
  KEY `fk_usuario_tarjeta_new` (`usuario_id`),
  CONSTRAINT `fk_usuario_tarjeta_new_temp` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjetabancaria`
--

LOCK TABLES `tarjetabancaria` WRITE;
/*!40000 ALTER TABLE `tarjetabancaria` DISABLE KEYS */;
INSERT INTO `tarjetabancaria` VALUES (2,4222222222222222,'2024-11-30','Ana González','MasterCard',456,NULL,2),(3,4333333333333333,'2026-01-15','Luis Rodríguez','American Express',789,NULL,3),(4,1234567890123456,'2025-12-31','Juan Pérez','Visa',123,'http://example.com/path/to/image.png',1),(5,1234567890123457,'2028-06-13','Juan Pérez','MasterCard',123,'http://localhost:8080/images/mc_logo.png',1),(6,4111111111111112,'2028-04-11','Juan Perez','Otros',456,'http://10.0.2.2:8080/images/otros_logo.png',1),(7,1234567890123455,'2028-01-17','Juan Pérez','AmericanExpress',578,'http://10.0.2.2:8080/images/ae_logo.png',1),(9,1234567890123459,'2030-05-11','Vladyslav Golovatyi','MasterCard',367,'http://10.0.2.2:8080/images/mc_logo.png',16),(10,1234567890123454,'2031-02-10','Vladyslav Golovatyi','Visa',436,'http://10.0.2.2:8080/images/visa_logo.png',16),(11,1234567890123451,'2028-08-23','Vladyslav Golovatyi','Visa',321,'http://10.0.2.2:8080/images/visa_logo.png',16),(12,4111118111111112,'2027-02-17','Vladyslav Golovatyi','AmericanExpress',231,'http://10.0.2.2:8080/images/ae_logo.png',16),(13,4181111111111112,'2030-04-10','Vladyslav Golovatyi','Otros',789,'http://10.0.2.2:8080/images/otros_logo.png',16);
/*!40000 ALTER TABLE `tarjetabancaria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `DNI` varchar(9) DEFAULT NULL,
  `Nombre` varchar(155) DEFAULT NULL,
  `Apellidos` varchar(255) DEFAULT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Direccion` varchar(255) DEFAULT NULL,
  `Provincia` varchar(255) DEFAULT NULL,
  `codigo_postal` varchar(10) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  `SaldoCR` decimal(10,2) DEFAULT '0.00',
  `codigo_restablecimiento` varchar(9) DEFAULT NULL,
  `codigo_expiry` datetime DEFAULT NULL,
  `es_administrador` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'12345678A','Juan','Pérez','juan.perez@example.com','password1','Calle Falsa 123','Madrid','28080','600100200','2024-04-15 00:00:00',113.00,NULL,NULL,0),(2,'23456789B','Ana','González','ana.gonzalez@example.com','password2','Avenida de la Constitución 45','Barcelona','08080','610200300','2024-04-15 12:39:45',150.00,NULL,NULL,0),(3,'34567890C','Luis','Rodríguez','luis.rodriguez@example.com','password3','Plaza Mayor 9','Valencia','46001','620300400','2024-04-15 12:39:45',200.00,NULL,NULL,0),(4,'','','','prueba1@gmail.com','2ac9cb7dc02b3c0083eb70898e549b63','','','','','2024-05-19 00:00:00',0.00,NULL,NULL,0),(16,'12345678A','Vladyslav','Golovatyi Tsymbal','vladyslav.golovatyitsymbal@iesmiguelherrero.com','2ac9cb7dc02b3c0083eb70898e549b63','Mi Dirección, 00, 0A','Cantabria','39300','600000000','2024-05-20 00:00:00',157.00,NULL,NULL,0),(17,'','','','raul@ejemplo.com','2ac9cb7dc02b3c0083eb70898e549b63','','','','','2024-06-08 00:00:00',0.00,'',NULL,0);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valoraciones`
--

DROP TABLE IF EXISTS `valoraciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `valoraciones` (
  `ValoracionID` int NOT NULL AUTO_INCREMENT,
  `ReservaID` int NOT NULL,
  `Valoracion` int NOT NULL,
  `Comentario` text,
  PRIMARY KEY (`ValoracionID`),
  KEY `ReservaID` (`ReservaID`),
  CONSTRAINT `valoraciones_ibfk_1` FOREIGN KEY (`ReservaID`) REFERENCES `anuncios` (`AnuncioID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valoraciones`
--

LOCK TABLES `valoraciones` WRITE;
/*!40000 ALTER TABLE `valoraciones` DISABLE KEYS */;
INSERT INTO `valoraciones` VALUES (1,2,5,'Excelente cuidado y atención.'),(2,3,4,'Muy buen servicio, aunque Rex parecía un poco ansioso al regresar.');
/*!40000 ALTER TABLE `valoraciones` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-17 11:37:07
