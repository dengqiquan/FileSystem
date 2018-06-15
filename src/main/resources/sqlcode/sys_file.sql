/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.14-log : Database - file_system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`file_system` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

/*Table structure for table `directory` */

CREATE TABLE `directory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '目录名称',
  `parentId` int(11) NOT NULL COMMENT '父目录ID',
  `createTime` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `createUserName` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `updateTime` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改时间',
  `updateUserName` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `curPath` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '当前路径',
  `isDelete` int(3) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `directory` */

/*Table structure for table `sys_file` */

CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '文件名',
  `size` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '文件大小',
  `type` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '文件类型',
  `suffix` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '后缀名',
  `path` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '文件路径',
  `isDelete` int(3) NOT NULL COMMENT '是否删除',
  `createTime` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改时间',
  `dirId` int(11) DEFAULT NULL COMMENT '目录ID',
  `createUserName` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `updateUserName` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `previewPath` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '预览地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_file` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
