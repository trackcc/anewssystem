-- MySQL dump 10.11
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.0.41-community-nt

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
-- Table structure for table `a_news_category`
--

DROP TABLE IF EXISTS `a_news_category`;
CREATE TABLE `a_news_category` (
  `ID` bigint(20) NOT NULL,
  `THE_SORT` int(11) default NULL,
  `BIT_CODE` bigint(20) default NULL,
  `CHAR_CODE` varchar(255) default NULL,
  `NAME` varchar(50) default NULL,
  `STATUS` int(11) default NULL,
  `PARENT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK6567A56CAF1C28CB` (`PARENT_ID`),
  CONSTRAINT `FK6567A56CAF1C28CB` FOREIGN KEY (`PARENT_ID`) REFERENCES `a_news_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_news_category`
--

LOCK TABLES `a_news_category` WRITE;
/*!40000 ALTER TABLE `a_news_category` DISABLE KEYS */;
INSERT INTO `a_news_category` VALUES (1,0,72057594037927936,NULL,'地方新闻',NULL,NULL),(2,1,72339069014638592,NULL,'河北新闻',NULL,1),(3,2,72340168526266368,NULL,'唐山新闻',NULL,2);
/*!40000 ALTER TABLE `a_news_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `a_news`
--

DROP TABLE IF EXISTS `a_news`;
CREATE TABLE `a_news` (
  `ID` bigint(20) NOT NULL,
  `SUBTITLE` varchar(100) default NULL,
  `LINK` varchar(100) default NULL,
  `HIT` int(11) default NULL,
  `SUMMARY` varchar(255) default NULL,
  `UPDATE_DATE` datetime default NULL,
  `NAME` varchar(100) default NULL,
  `CONTENT` text,
  `SOURCE` varchar(50) default NULL,
  `IMAGE` varchar(50) default NULL,
  `EDITOR` varchar(50) default NULL,
  `STATUS` int(11) default NULL,
  `CATEGORY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK744A3C118F5E23F7` (`CATEGORY_ID`),
  CONSTRAINT `FK744A3C118F5E23F7` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `a_news_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_news`
--

LOCK TABLES `a_news` WRITE;
/*!40000 ALTER TABLE `a_news` DISABLE KEYS */;
INSERT INTO `a_news` VALUES (1,'ddd','/anews/html/3/2007/09/17/1.html',NULL,'dd','2007-09-17 00:00:00','dd','dd','【本站原创】','','',2,3),(2,'est','/anews/html/2/2007/10/06/2.html',NULL,'tset','2007-10-06 00:00:00','tt','sadf','【本站原创】','','',3,1),(3,'45','',NULL,'4541','2007-10-06 00:00:00','1454','&nbsp;654','【本站原创】',NULL,'',NULL,NULL),(4,'1','',NULL,'564','2007-10-06 00:00:00','12','&nbsp;54','【本站原创】',NULL,'',NULL,NULL),(5,'54','',NULL,'54','2007-10-06 00:00:00','6','&nbsp;656','【本站原创】',NULL,'',3,1),(6,'唐山很好','/anews/html/3/2007/10/12/6.html',NULL,'唐山特别好','2007-10-12 00:00:00','唐山好','&nbsp;唐山特别特别好','【本站原创】',NULL,'',3,3),(7,'tset','/anews/html/3/2007/10/12/7.html',NULL,'tset','2007-10-12 00:00:00','test','&nbsp;tse','【本站原创】',NULL,'临远',3,3);
/*!40000 ALTER TABLE `a_news` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `a_news_comment`
--

DROP TABLE IF EXISTS `a_news_comment`;
CREATE TABLE `a_news_comment` (
  `ID` bigint(20) NOT NULL,
  `USERNAME` varchar(50) default NULL,
  `UPDATE_DATE` datetime default NULL,
  `IP` varchar(20) default NULL,
  `NAME` varchar(100) default NULL,
  `CONTENT` text,
  `STATUS` int(11) default NULL,
  `NEWS_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK5CDABC117E0115E4` (`NEWS_ID`),
  CONSTRAINT `FK5CDABC117E0115E4` FOREIGN KEY (`NEWS_ID`) REFERENCES `a_news` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_news_comment`
--

LOCK TABLES `a_news_comment` WRITE;
/*!40000 ALTER TABLE `a_news_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `a_news_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `a_news_config`
--

DROP TABLE IF EXISTS `a_news_config`;
CREATE TABLE `a_news_config` (
  `ID` bigint(20) NOT NULL,
  `NEWS_NEED_AUDIT` int(11) default NULL,
  `COMMENT_NEED_AUDIT` int(11) default NULL,
  `COULD_COMMENT` int(11) default NULL,
  `CATEGORY_STRATEGY` int(11) default NULL,
  `TEMPLATE_NAME` varchar(50) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_news_config`
--

LOCK TABLES `a_news_config` WRITE;
/*!40000 ALTER TABLE `a_news_config` DISABLE KEYS */;
INSERT INTO `a_news_config` VALUES (1,0,0,0,0,'extjs');
/*!40000 ALTER TABLE `a_news_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `a_news_tag`
--

DROP TABLE IF EXISTS `a_news_tag`;
CREATE TABLE `a_news_tag` (
  `ID` bigint(20) NOT NULL,
  `THE_SORT` int(11) default NULL,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_news_tag`
--

LOCK TABLES `a_news_tag` WRITE;
/*!40000 ALTER TABLE `a_news_tag` DISABLE KEYS */;
INSERT INTO `a_news_tag` VALUES (2,NULL,'222'),(3,NULL,'2'),(4,NULL,'444'),(5,NULL,'test');
/*!40000 ALTER TABLE `a_news_tag` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `a_news_newstag`
--

DROP TABLE IF EXISTS `a_news_newstag`;
CREATE TABLE `a_news_newstag` (
  `NEWSTAG_ID` bigint(20) NOT NULL,
  `NEWS_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`NEWS_ID`,`NEWSTAG_ID`),
  KEY `FK923EAD997E0115E4` (`NEWS_ID`),
  KEY `FK923EAD9963BB8EF0` (`NEWSTAG_ID`),
  CONSTRAINT `FK923EAD9963BB8EF0` FOREIGN KEY (`NEWSTAG_ID`) REFERENCES `a_news_tag` (`ID`),
  CONSTRAINT `FK923EAD997E0115E4` FOREIGN KEY (`NEWS_ID`) REFERENCES `a_news` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_news_newstag`
--

LOCK TABLES `a_news_newstag` WRITE;
/*!40000 ALTER TABLE `a_news_newstag` DISABLE KEYS */;
INSERT INTO `a_news_newstag` VALUES (2,2),(3,2),(4,2),(5,7);
/*!40000 ALTER TABLE `a_news_newstag` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `a_security_dept`
--

DROP TABLE IF EXISTS `a_security_dept`;
CREATE TABLE `a_security_dept` (
  `ID` bigint(20) NOT NULL,
  `THE_SORT` int(11) default NULL,
  `DESCN` varchar(200) default NULL,
  `NAME` varchar(50) default NULL,
  `PARENT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK33849C668034938C` (`PARENT_ID`),
  CONSTRAINT `FK33849C668034938C` FOREIGN KEY (`PARENT_ID`) REFERENCES `a_security_dept` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_security_dept`
--

LOCK TABLES `a_security_dept` WRITE;
/*!40000 ALTER TABLE `a_security_dept` DISABLE KEYS */;
INSERT INTO `a_security_dept` VALUES (1,0,'','技术部',NULL),(2,4,'市场','市场部',NULL),(3,5,'销售的部门，卖不出去可不行啊','销售部',NULL),(4,2,NULL,'研发部',1),(5,1,'','产品部',1);
/*!40000 ALTER TABLE `a_security_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `a_security_menu`
--

DROP TABLE IF EXISTS `a_security_menu`;
CREATE TABLE `a_security_menu` (
  `ID` bigint(20) NOT NULL,
  `URL` varchar(50) default NULL,
  `THE_SORT` int(11) default NULL,
  `QTIP` varchar(50) default NULL,
  `DESCN` varchar(200) default NULL,
  `NAME` varchar(50) default NULL,
  `IMAGE` varchar(50) default NULL,
  `PARENT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK3388B3808038AAA6` (`PARENT_ID`),
  CONSTRAINT `FK3388B3808038AAA6` FOREIGN KEY (`PARENT_ID`) REFERENCES `a_security_menu` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_security_menu`
--

LOCK TABLES `a_security_menu` WRITE;
/*!40000 ALTER TABLE `a_security_menu` DISABLE KEYS */;
INSERT INTO `a_security_menu` VALUES (44,'./',13,'1','11','sandbox','set.gif',NULL),(45,'../newstag2/dialogManage.htm',24,'','','体验dialog','user.gif',44),(46,'../newstag2/inlineManage.htm',23,'','','体验editor','user.gif',44),(47,'./',0,'','','新闻发布','set.gif',NULL),(48,'../newscategory/index.htm',2,'','','新闻分类管理','user.gif',47),(50,'../news/index.htm',3,'管理新闻','管理新闻','管理新闻','user.gif',47),(52,'../newstag/index.htm',4,'','','管理关键字','user.gif',47),(53,'../newsconfig/index.htm',5,'新闻属性设置','新闻属性设置','新闻属性设置','user.gif',47),(54,'../template/dd.htm',22,'','','设置模板','user.gif',44),(56,'./',6,'','','权限设置','set.gif',NULL),(57,'../user/index.htm',12,'','','管理用户','user.gif',56),(58,'../resource/index.htm',11,'','','管理资源','user.gif',56),(59,'../role/index.htm',10,'','','管理角色','user.gif',56),(60,'../menu/index.htm',7,'','','管理菜单','user.gif',56),(61,'../sandbox/datagrid.htm',21,'','','DataGrid','user.gif',44),(62,'../dept/index.htm',8,'部门','部门','管理部门','user.gif',56),(63,'../widgets/lingo/jsontree/Ext.lingo.JsonTree.html',19,'','','Ext.lingo.JsonTree','user.gif',44),(64,'../widgets/lingo/jsongrid/Ext.lingo.JsonGrid.html',18,'','','Ext.lingo.JsonGrid','user.gif',44),(65,'../widgets/lingo/checkboxtree/checkboxtree.html',20,'带选择框的树','带选择框的树','CheckBoxTree','user.gif',44),(66,'../widgets/lingo/form/ddgrid.html',17,'两个表格间拖动','两个表格间拖动','grid拖拽','user.gif',44),(67,'../widgets/lingo/form/switchField.html',16,'切换输入框','切换输入框','切换输入框','user.gif',44),(68,'../widgets/lingo/form/ddtree2.html',26,'tree与grid拖拽','tree与grid拖拽','tree与grid拖拽','user.gif',44),(69,'../widgets/lingo/form/ddtree.html',25,'tree与grid拖拽，不知道为啥失败','tree与grid拖拽，不知道为啥失败','tree与grid拖拽，不知道为啥失败','user.gif',44),(70,'../dept/orgmap.htm',9,'组织结构图','组织结构图','组织结构图','user.gif',56),(71,'../widgets/lingo/form/treeField.html',15,'树形下拉框','树形下拉框','树形下拉框','user.gif',44),(72,'../widgets/lingo/form/room.html',14,'级联combobox','级联combobox','级联combobox','user.gif',44),(73,'../news/manage.htm',1,'审核新闻','审核新闻','审核新闻','user.gif',47);
/*!40000 ALTER TABLE `a_security_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `a_security_role`
--

DROP TABLE IF EXISTS `a_security_role`;
CREATE TABLE `a_security_role` (
  `ID` bigint(20) NOT NULL,
  `DESCN` varchar(200) default NULL,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_security_role`
--

LOCK TABLES `a_security_role` WRITE;
/*!40000 ALTER TABLE `a_security_role` DISABLE KEYS */;
INSERT INTO `a_security_role` VALUES (2,'管理员','ROLE_ADMIN'),(3,'用户','ROLE_USER'),(4,'测试用户','ROLE_TEST');
/*!40000 ALTER TABLE `a_security_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `a_security_menu_role`
--

DROP TABLE IF EXISTS `a_security_menu_role`;
CREATE TABLE `a_security_menu_role` (
  `MENU_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ROLE_ID`,`MENU_ID`),
  KEY `FK14BCADD556E5FA71` (`ROLE_ID`),
  KEY `FK14BCADD53D7765D1` (`MENU_ID`),
  CONSTRAINT `FK14BCADD53D7765D1` FOREIGN KEY (`MENU_ID`) REFERENCES `a_security_menu` (`ID`),
  CONSTRAINT `FK14BCADD556E5FA71` FOREIGN KEY (`ROLE_ID`) REFERENCES `a_security_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_security_menu_role`
--

LOCK TABLES `a_security_menu_role` WRITE;
/*!40000 ALTER TABLE `a_security_menu_role` DISABLE KEYS */;
INSERT INTO `a_security_menu_role` VALUES (47,2),(48,2),(50,2),(56,2),(57,2),(58,2),(47,3),(48,3),(50,3),(52,3),(53,3),(56,3),(57,3),(58,3),(59,3),(60,3),(62,3),(44,4),(45,4),(46,4),(47,4),(48,4),(50,4),(52,4),(53,4),(54,4),(56,4),(57,4),(58,4),(59,4),(60,4),(61,4),(62,4),(63,4),(64,4),(65,4),(66,4),(67,4),(68,4),(69,4),(70,4),(71,4),(72,4),(73,4);
/*!40000 ALTER TABLE `a_security_menu_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `a_security_resource`
--

DROP TABLE IF EXISTS `a_security_resource`;
CREATE TABLE `a_security_resource` (
  `ID` bigint(20) NOT NULL,
  `DESCN` varchar(200) default NULL,
  `RES_TYPE` varchar(50) default NULL,
  `RES_STRING` varchar(200) default NULL,
  `NAME` varchar(50) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_security_resource`
--

LOCK TABLES `a_security_resource` WRITE;
/*!40000 ALTER TABLE `a_security_resource` DISABLE KEYS */;
INSERT INTO `a_security_resource` VALUES (2,'后台管理','URL','/admin/**','admin'),(3,'资源管理','URL','/resource/**','resource'),(4,'用户管理','URL','/user/**','user'),(5,'角色管理','URL','/role/**','role'),(6,'菜单管理','URL','/menu/**','menu'),(7,'部门管理','URL','/dept/**','dept');
/*!40000 ALTER TABLE `a_security_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `a_security_resource_role`
--

DROP TABLE IF EXISTS `a_security_resource_role`;
CREATE TABLE `a_security_resource_role` (
  `RESOURCE_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ROLE_ID`,`RESOURCE_ID`),
  KEY `FK6FD1850656E5FA71` (`ROLE_ID`),
  KEY `FK6FD185064E5B1FF1` (`RESOURCE_ID`),
  CONSTRAINT `FK6FD185064E5B1FF1` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `a_security_resource` (`ID`),
  CONSTRAINT `FK6FD1850656E5FA71` FOREIGN KEY (`ROLE_ID`) REFERENCES `a_security_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_security_resource_role`
--

LOCK TABLES `a_security_resource_role` WRITE;
/*!40000 ALTER TABLE `a_security_resource_role` DISABLE KEYS */;
INSERT INTO `a_security_resource_role` VALUES (2,2),(3,2),(4,2),(5,2),(7,2),(2,4),(3,4),(4,4),(5,4),(6,4),(7,4);
/*!40000 ALTER TABLE `a_security_resource_role` ENABLE KEYS */;
UNLOCK TABLES;




--
-- Table structure for table `a_security_user`
--

DROP TABLE IF EXISTS `a_security_user`;
CREATE TABLE `a_security_user` (
  `ID` bigint(20) NOT NULL,
  `USERNAME` varchar(50) default NULL,
  `EMAIL` varchar(100) default NULL,
  `DESCN` varchar(200) default NULL,
  `CODE` varchar(50) default NULL,
  `TRUENAME` varchar(50) default NULL,
  `SEX` tinyint(4) default NULL,
  `BIRTHDAY` datetime default NULL,
  `TEL` varchar(50) default NULL,
  `MOBILE` varchar(50) default NULL,
  `DUTY` varchar(50) default NULL,
  `PASSWORD` varchar(50) default NULL,
  `STATUS` tinyint(4) default NULL,
  `DEPT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK338C89EC6176F411` (`DEPT_ID`),
  CONSTRAINT `FK338C89EC6176F411` FOREIGN KEY (`DEPT_ID`) REFERENCES `a_security_dept` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_security_user`
--

LOCK TABLES `a_security_user` WRITE;
/*!40000 ALTER TABLE `a_security_user` DISABLE KEYS */;
INSERT INTO `a_security_user` VALUES (22,'test2','echo_o@163.com','test',NULL,'test',0,'2007-09-23 00:00:00','111111','11111111111','无','098f6bcd4621d373cade4e832627b4f6',0,1),(23,'test1','test','test',NULL,'afdasdf',0,'2007-09-27 00:00:00','asdfasdf','sdfasdf','sdf','098f6bcd4621d373cade4e832627b4f6',0,3),(27,'aaa','aa','销售部的aaa',NULL,'aaa',0,'2007-09-27 00:00:00','d','aaa','aa','aa',0,3),(28,'122222','222','',NULL,'22222',1,'2007-09-27 00:00:00','222','22','222','2222222',0,5),(29,'test','test','test',NULL,'临远',0,'2007-09-27 00:00:00','test','test','tset','098f6bcd4621d373cade4e832627b4f6',1,1),(30,'user','echo_o@163.com','小兵',NULL,'user',NULL,'2007-09-28 00:00:00','11111111','11111111111','小兵','ee11cbb19052e40b07aac0ca060c23ee',1,2);
/*!40000 ALTER TABLE `a_security_user` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `a_security_role_user`
--

DROP TABLE IF EXISTS `a_security_role_user`;
CREATE TABLE `a_security_role_user` (
  `ROLE_ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`USER_ID`,`ROLE_ID`),
  KEY `FK83C60473FC10BE51` (`USER_ID`),
  KEY `FK83C6047356E5FA71` (`ROLE_ID`),
  CONSTRAINT `FK83C6047356E5FA71` FOREIGN KEY (`ROLE_ID`) REFERENCES `a_security_role` (`ID`),
  CONSTRAINT `FK83C60473FC10BE51` FOREIGN KEY (`USER_ID`) REFERENCES `a_security_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_security_role_user`
--

LOCK TABLES `a_security_role_user` WRITE;
/*!40000 ALTER TABLE `a_security_role_user` DISABLE KEYS */;
INSERT INTO `a_security_role_user` VALUES (2,22),(3,22),(4,22),(3,27),(2,29),(3,29),(4,29),(3,30);
/*!40000 ALTER TABLE `a_security_role_user` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `a_tracker_project`
--

DROP TABLE IF EXISTS `a_tracker_project`;
CREATE TABLE `a_tracker_project` (
  `ID` bigint(20) NOT NULL,
  `SUMMARY` text,
  `THE_SORT` int(11) default NULL,
  `FOUNDER` varchar(50) default NULL,
  `NAME` varchar(100) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_tracker_project`
--

LOCK TABLES `a_tracker_project` WRITE;
/*!40000 ALTER TABLE `a_tracker_project` DISABLE KEYS */;
INSERT INTO `a_tracker_project` VALUES (9,'<font face=\"仿宋\">中俄商务贸易网<br>从5月底到呼</font><font face=\"仿宋\">和浩特以后，就一直在做的东西，启动很不顺利，慢慢来吧。</font><br>',NULL,'lingo','b2b'),(10,'',NULL,'lingo','新闻发布[anews]'),(11,'',NULL,'lingo','项目跟踪[atracker]'),(12,'抄袭springside-2.0项目sandbox下的security模块，完全抄袭了RBAC模型。<br>可cac老大没有演示咋用ACL，所以没抄到。<br>',NULL,'lingo','权限[asecurity]');
/*!40000 ALTER TABLE `a_tracker_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `a_tracker_issue`
--

DROP TABLE IF EXISTS `a_tracker_issue`;
CREATE TABLE `a_tracker_issue` (
  `ID` bigint(20) NOT NULL,
  `UPDATE_DATE` datetime default NULL,
  `SEVERITY` int(11) default NULL,
  `ASSIGN_TO` varchar(50) default NULL,
  `SUBMIT_BY` varchar(50) default NULL,
  `ADD_TIME` datetime default NULL,
  `NAME` varchar(50) default NULL,
  `PRIORITY` int(11) default NULL,
  `FILE` varchar(100) default NULL,
  `CONTENT` text,
  `STATUS` int(11) default NULL,
  `PROJECT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK20F791548D35A05B` (`PROJECT_ID`),
  CONSTRAINT `FK20F791548D35A05B` FOREIGN KEY (`PROJECT_ID`) REFERENCES `a_tracker_project` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `a_tracker_issue`
--

LOCK TABLES `a_tracker_issue` WRITE;
/*!40000 ALTER TABLE `a_tracker_issue` DISABLE KEYS */;
INSERT INTO `a_tracker_issue` VALUES (1,'2007-10-08 00:00:00',1,'Lingo','anyone','2007-10-08 00:00:00','b2b启动',1,'','b2b启动',1,9),(2,'2007-10-08 00:00:00',2,'Lingo','anyone','2007-10-08 00:00:00','询价部分功能不完整',2,'','询价功能不完整，原来是与留言做在一起，现在询价的功能比留言多出了几部分<br><ol><li>可以添加订购量，订购金额</li><li>可以从产品属性中选择感兴趣的部分，使用checkbox选择</li><li>与留言都存在的问题，没有验证码。<br></li></ol>',1,9),(3,'2007-10-12 00:00:00',2,'Lingo','anyone','2007-10-08 00:00:00','欠缺ACL功能',2,'','抄袭了无数次springside的权限模型，可还是不知道如何做acegi的ACL控制。在论坛上看到cac老大说acegi-1.0.3之后使用了新的ACL模型，还可以让人接受，可自己去找了acegi-1.0.5的例子，感觉丝毫没有头绪的感觉。<br>而现在新闻发布，其实需要使用ACL实现，多个内容发布员发布新闻，需要提供草稿箱，垃圾箱。站点管理员进行审核，推荐，隐藏。<br>再有一点，看到虎牙子这个名词，是说ACL进行后置验证，在分页时出现，每页数据数不一致的情况，但没有找到解决方法。<br>不过新闻里好解决这个问题，只要在查询的时候只查询属于自己的新闻就好了，配置ACL的时候，给用户自己所有的文章的权限<br><br>放入最基本配置文件，目前还没有添入数据表结构和实际操作的功能，打算在0.0.3再考虑。<br><br>',2,12),(4,'2007-10-13 00:00:00',1,'Lingo','anyone','2007-10-08 00:00:00','项目重构',1,'','因为使用了extjs做前台，以前使用的extremetable,struts-menu都没用了，需要统一重构，把这些用不到的东西删除，这样也可以减小发布包的大小。<br><br>初步计划：<br><ol><li>删除struts-menu及其依赖库，包括velocity</li><li>删除fckeditor的js库，及依赖包jar</li><li>删除jscalendar的js库</li><li>考虑用spring自己的aop替换aspectj，因为aspectj太大了。<br></li></ol>',0,10),(5,'2007-10-09 00:00:00',1,'Lingo','anyone','2007-10-09 00:00:00','角色管理，配置菜单出错',1,'','角色管理页面，选择一个角色后，按选择菜单按钮，第一次会报错，并且弹不出对话框，第二次会成功弹出对话框。<br><br>问题在页面中多了一个&lt;div id=\"menuDialog\"&gt;&lt;/div&gt;标签，现在Ext.lingo.FormUtils.createTabedDialog会根据传入的id自动生成一个对话框的div放在body的最后，第一次选择菜单，会根据id得到页面中定义的空的div，就会出错，不知道为何，第二次得到的就是正确的dialog了了。<br><br>删除多余的div后，问题解决。<br>',1,10),(6,'2007-10-10 00:00:00',1,'Lingo','anyone','2007-10-10 00:00:00','表格可选择分页条数',1,'','grid下方的pagingToolbar，可以使用comboBox选择分页条数。<br>参考extjs.com上提供的PageSizePlugin，这个插件是基于ext-2.0的，为了让它适用于ext-1.1做了一些修改。2.0的插件机制更令人心动。<br>',1,10),(7,'2007-10-13 00:00:00',1,'Lingo','anyone','2007-10-08 00:00:00','grid在翻页的时候保存checkbox状态',1,'','grid翻页以后，上一页选择的checkbox状态就会丢失。<br><br>需要设置一个功能让翻页的时候可以保存已选中行的状态，并且在翻回页的时候，显示那些已选中的行。<br><br>三个问题：<br><ol><li>翻页的时候，记录历史页选择的项目id</li><li>初始化的时候，让已经选中过的列，直接复制到this.historyIds里<br></li><li>返回历史页时，让已选择项目显示为已选择状态</li></ol>说实话，弄出来的东西，给我的感觉很不好，但非要这个效果不可的话，也只好先这么凑合了，等待评测。<br>',1,10),(8,'2007-10-11 00:00:00',1,'Lingo','anyone','2007-10-10 00:00:00','添加商机后，转入提示成功页面',2,'','目前，商机提交成功后，直接跳转到管理商机。<br>现在需要在成功后进入一个提示成功的页面。<br>',1,9),(9,'2007-10-10 00:00:00',1,'Lingo','anyone','2007-10-10 00:00:00','checkboxgrid在ie中出现错位',1,'','CheckBoxGrid在ie中显示，checkbox的位置出现错误，而且将整行撑大，使整体表格异常难看。<br>参考以前得到的checkboxtree中的css，对每个checkbox加上对ie的特别处理，保证显示正常。<br>',1,10),(10,'2007-10-11 00:00:00',1,'Lingo','anyone','2007-10-11 00:00:00','网站帮助只显示两条帮助信息',2,'','据说网站帮助页面，不管后台输入多少信息，前台只显示两条。<br>尚未证实<br><br>',0,9),(11,'2007-10-11 00:00:00',1,'Lingo','anyone','2007-10-11 00:00:00','首页最新商机缺少信息',2,'','首页最新商机，使用ajax轮转信息的部分，现在每天都只显示商机标题。<br>需要加上：【中方或俄方供应或需求的标志】，标题，发布时间，发布公司<br><br>最右边加上发布信息的链接。<br>',0,9),(12,'2007-10-11 00:00:00',1,'Lingo','anyone','2007-10-11 00:00:00','新增时提交更新时间不是当前时间',1,'','新增时，提交时间和更新时间，总是与上次提交的任务时间相同，而不是当前日期。<br>怀疑是JsonGrid的问题，还要检查一下JsonTree是不是有同样问题。估计是对DateField初始化时出现了问题。<br><br>直接在DateField上调用reset()会变成空值，还需要单独判断date然后赋予一个默认值。<br>',1,11),(13,'2007-10-11 00:00:00',1,'Lingo','anyone','2007-10-11 00:00:00','frank新建工程的wizard很帅',1,'','暂时用不到。',2,11),(14,'2007-10-11 00:00:00',1,'Lingo','anyone','2007-10-11 00:00:00','frank多tab查看项目很帅',1,'','暂时用不到。',2,11),(15,'2007-10-11 00:00:00',2,'Lingo','anyone','2007-10-11 00:00:00','公司信息和联系方法都需要审核',2,'','网站助手中的公司介绍和联系方式，都需要审核才能发布到网上。',0,9),(16,'2007-10-12 00:00:00',1,'Lingo','anyone','2007-10-12 00:00:00','添加发送订单功能',1,'','交易管理中，现在只有我是买家和我是卖家两个功能，现在需要添加发送订单功能。',0,9),(17,'2007-10-12 00:00:00',1,'Lingo','anyone','2007-10-12 00:00:00','修改联系方式后，添加修改成功提示',1,'','现在添加联系方式后，没有任何提示，需要添加成功修改的提示',0,9);
/*!40000 ALTER TABLE `a_tracker_issue` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2007-10-15  9:55:26
