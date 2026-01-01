-- --------------------------------------------------------
-- Sunucu:                       127.0.0.1
-- Sunucu sürümü:                12.1.2-MariaDB - MariaDB Server
-- Sunucu İşletim Sistemi:       Win64
-- HeidiSQL Sürüm:               12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- canbazlar_otomasyon için veritabanı yapısı dökülüyor
CREATE DATABASE IF NOT EXISTS `canbazlar_otomasyon` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_turkish_ci */;
USE `canbazlar_otomasyon`;

-- tablo yapısı dökülüyor canbazlar_otomasyon.personel
CREATE TABLE IF NOT EXISTS `personel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tc_no` varchar(11) NOT NULL,
  `ad` varchar(50) NOT NULL,
  `soyad` varchar(50) NOT NULL,
  `sifre` varchar(50) NOT NULL,
  `calisma_durumu` enum('Aktif','Boşta') DEFAULT 'Boşta',
  `zimmetli_para` decimal(10,2) DEFAULT 0.00,
  `maas` decimal(10,2) DEFAULT 0.00,
  `unvan` varchar(20) DEFAULT 'personel',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tc_no` (`tc_no`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

-- canbazlar_otomasyon.personel: ~3 rows (yaklaşık) tablosu için veriler indiriliyor
INSERT INTO `personel` (`id`, `tc_no`, `ad`, `soyad`, `sifre`, `calisma_durumu`, `zimmetli_para`, `maas`, `unvan`) VALUES
	(1, '123', 'Arda', 'Canbaz', '123', 'Aktif', 0.00, 0.00, 'yonetici'),
	(12, '1234', 'Ahmet', 'Yılmaz', '1414', 'Aktif', 3000.00, 52000.00, 'personel'),
	(13, '12345', 'Mehmet', 'Yılmaz', '1414', 'Boşta', 7000.00, 50000.00, 'personel');

-- tablo yapısı dökülüyor canbazlar_otomasyon.zimmet_talepleri
CREATE TABLE IF NOT EXISTS `zimmet_talepleri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personel_id` int(11) NOT NULL,
  `miktar` decimal(10,2) NOT NULL,
  `durum` enum('Bekliyor','Onaylandı','Reddedildi') DEFAULT 'Bekliyor',
  `tarih` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `personel_id` (`personel_id`),
  CONSTRAINT `1` FOREIGN KEY (`personel_id`) REFERENCES `personel` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

-- canbazlar_otomasyon.zimmet_talepleri: ~3 rows (yaklaşık) tablosu için veriler indiriliyor
INSERT INTO `zimmet_talepleri` (`id`, `personel_id`, `miktar`, `durum`, `tarih`) VALUES
	(12, 12, 2000.00, 'Onaylandı', '2026-01-01 16:00:48'),
	(13, 13, 3500.00, 'Bekliyor', '2026-01-01 16:01:07'),
	(14, 13, 2000.00, 'Bekliyor', '2026-01-01 16:01:29');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
