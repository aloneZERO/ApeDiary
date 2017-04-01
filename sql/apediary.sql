/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.11 : Database - db_diary
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_diary` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `db_diary`;

/*Table structure for table `categories` */

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `categories` */

insert  into `categories`(`id`,`name`) values ('2','学习'),('1','工作'),('3','测试'),('1e99xb7orrbos5lnvjgsxloypxl77yuy','音乐');

/*Table structure for table `diaries` */

DROP TABLE IF EXISTS `diaries`;

CREATE TABLE `diaries` (
  `id` varchar(50) NOT NULL,
  `title` varchar(30) DEFAULT NULL,
  `content` text,
  `categoryId` varchar(50) DEFAULT NULL,
  `releaseDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_diary` (`categoryId`),
  CONSTRAINT `fk_category_diary` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `diaries` */

insert  into `diaries`(`id`,`title`,`content`,`categoryId`,`releaseDate`) values ('1','测试君','<p>测试中ing...</p>\r\n','3','2017-04-01 16:26:04'),('1zdf3487od12zuo0qresqkgcuj86z41l','测试爸','<p>来自老爸的测试~</p>\r\n','2','2017-04-01 16:27:43'),('2','测试姐','继续测试','1','2017-01-01 23:44:13'),('2r9qj91gmeu1geon77vn2wbstb8z4x6o','测试哥','<p><ins>测试，测试，年轻人的测试</ins>~</p>\r\n','3','2017-04-01 16:21:38'),('568uen1v25mrrzylszldo81bwh34g881','测试妹','<h1><strong>测试ing</strong></h1>\r\n','1e99xb7orrbos5lnvjgsxloypxl77yuy','2017-04-01 16:27:54'),('8dsbrn6ycqj5dxpr4vnj91w5oakbycnv','测试妈','<h1><strong><s>测试到天亮</s></strong></h1>\r\n','3','2017-02-24 22:55:38'),('etxu76i538zaa5sx5oeb1xbl2jazo9y8','测试弟','<h2><em>还是测试</em></h2>\r\n','3','2017-02-24 22:54:56');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `path` varchar(50) DEFAULT NULL,
  `imagename` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`password`,`nickname`,`path`,`imagename`,`description`) values ('1','jack','233','长者','7\\1','1_123.jpg','苟利国家生死已,岂因福祸避趋之');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
