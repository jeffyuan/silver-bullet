-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.20-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE TABLE IF NOT EXISTS `sys_auth_user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(32) NOT NULL COMMENT 'MD5(password+loginname)',
  `salt` varchar(32) NOT NULL DEFAULT 'salt',
  `comments` varchar(128) DEFAULT NULL,
  `user_type` char(1) DEFAULT NULL COMMENT '1:超级用户 2:系统用户 3:其他 4: 系统管理员 5：安全管理员 6：审计员',
  `create_user` varchar(32) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `modify_user` varchar(32) NOT NULL,
  `modify_time` DATETIME NOT NULL,
  `state` char(1) NOT NULL COMMENT '1: 正常 2:锁定',
  `login_time` DATETIME DEFAULT NULL,
  `image_id` varchar(32) DEFAULT NULL,
  `classify` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO `sys_auth_user` (`id`, username, `name`, `password`, `salt`, `comments`, `user_type`, `create_user`, `create_time`, `modify_user`, `modify_time`, `state`, `login_time`, `image_id`, `classify`) VALUES('40288b854a329988014a329a12f30002', 'SYSADMIN', '管理员', '45A6964B87BEC90B5B6C6414FAF397A7', '8d78869f470951332959580424d4bf4f', '', '2', '40288b854a329988014a329a12f30002', now(), '40288b854a329988014a329a12f30002', now(), 1, now(), null, 0);


CREATE TABLE IF NOT EXISTS `sys_auth_organization` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `parent_ids` varchar(256) NOT NULL,
  `comments` varchar(128) DEFAULT NULL,
  `state` char(1) NOT NULL,
  `sort` int(11) DEFAULT NULL,
  `create_user` varchar(32) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `modify_user` varchar(32) NOT NULL,
  `modify_time` DATETIME NOT NULL,
  `type` char(1) NOT NULL COMMENT '组织类型：1科室、2班组、3队组、0其他',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织类型：科室、班组、队组、其他';

INSERT INTO `sys_auth_organization` (`id`, `name`, `parent_id`, `parent_ids`, `comments`, `state`, `sort`, `create_user`, `create_time`, `modify_user`,`modify_time`, `type`) VALUES('402888ac547fe1050154800171f30000','公司总部','NONE', '402888ac547fe1050154800171f30000','', '1', 1, '40288b854a329988014a329a12f30002',now(),'40288b854a329988014a329a12f30002', now(), '1');


CREATE TABLE IF NOT EXISTS `sys_auth_post` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `create_username` varchar(64) NOT NULL,
  `create_user` varchar(32) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `modify_username` varchar(64) NOT NULL,
  `modify_user` varchar(32) NOT NULL,
  `modify_time` DATETIME NOT NULL,
  `state` varchar(2) NOT NULL,
  `organization_id` varchar(32) NOT NULL,
  `is_extends` varchar(2) NOT NULL COMMENT '0:否1:是（默认否）',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_01` (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '岗位角色表';

INSERT INTO `sys_auth_post` (`id`, `name`, `create_username`, `create_user`, `create_time`,`modify_username`, `modify_user`, `modify_time`,`state`, `organization_id`, `is_extends`) VALUES('402888ac547fe10501548001be3a0001','系统管理员','系统管理员', '40288b854a329988014a329a12f30002', now(),'系统管理员','40288b854a329988014a329a12f30002', now(),'1', '402888ac547fe1050154800171f30000', '10');



CREATE TABLE IF NOT EXISTS `sys_auth_userorg` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `organization_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户机构对应';

INSERT INTO `sys_auth_userorg` (`id`, `user_id`, `organization_id`) VALUES('40288a81611296f4016112a10d9a007a', '40288b854a329988014a329a12f30002', '402888ac547fe1050154800171f30000');


CREATE TABLE IF NOT EXISTS `sys_auth_action` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `url` varchar(128) NOT NULL,
  `resource_type` varchar(20) NOT NULL COMMENT '资源类型，[menu|button]',
  `parent_id` VARCHAR(32) DEFAULT NULL,
  `parent_ids` varchar(256) NOT NULL,
  `permission` VARCHAR(50) NOT NULL,
  `comments` varchar(128) DEFAULT NULL,
  `create_user` varchar(32) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `modify_user` varchar(32) NOT NULL,
  `modify_time` DATETIME NOT NULL,
  `state` char(1) NOT NULL,
  `is_need_check` char(1) NOT NULL COMMENT '是否需要检查权限',
  `is_need_login` char(1) NOT NULL DEFAULT '1' COMMENT '是否需要登录，默认所有ACTION都要登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能权限表';

INSERT INTO `sys_auth_action` (`id`, `name`, `url`,`resource_type`, `parent_id`, `parent_ids`,`permission`,`comments`, `create_user`, `create_time`,`modify_user`, `modify_time`, `state`, `is_need_check`, `is_need_login`) VALUES('402888a84f970dad014f971444550009', '系统管理_用户管理', 'auth/sysauthuser/list', 'menu', 'NONE', '402888a84f970dad014f971444550009','auth:sysauthuser:list','','40288b854a329988014a329a12f30002', now(), '40288b854a329988014a329a12f30002', now(),'1','0','1');
INSERT INTO `sys_auth_action` (`id`, `name`, `url`,`resource_type`, `parent_id`, `parent_ids`,`permission`,`comments`, `create_user`, `create_time`,`modify_user`, `modify_time`, `state`, `is_need_check`, `is_need_login`) VALUES('402888a850472ac50150472d85070009', '系统管理_用户管理_添加', 'auth/sysauthuser/add', 'button', '402888a84f970dad014f971444550009402888a850472ac50150472d85070009', '402888a84f970dad014f971444550009','auth:sysauthuser:add','','40288b854a329988014a329a12f30002', now(), '40288b854a329988014a329a12f30002', now(),'1','0','1');


CREATE TABLE IF NOT EXISTS `sys_auth_actiontree` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `sort` int(11) NOT NULL,
  `parent_id` varchar(32) NOT NULL,
  `parent_ids` varchar(256) NOT NULL,
  `comments` varchar(128) DEFAULT NULL,
  `type` char(1) NOT NULL COMMENT '分类、菜单、权限',
  `create_user` varchar(32) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `modify_user` varchar(32) NOT NULL,
  `modify_time` DATETIME NOT NULL,
  `state` char(1) NOT NULL,
  `action_id` varchar(32) DEFAULT NULL,
  `domain` varchar(64) NOT NULL,
  `icon` varchar(64) DEFAULT NULL,
  `params` varchar(128) DEFAULT NULL COMMENT '参数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_04` (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能权限的树状结构，主要针对menu类型功能';

INSERT INTO `sys_auth_actiontree` (`id`, `name`, `sort`,`parent_id`, `parent_ids`, `comments`,`type`, `create_user`, `create_time`,`modify_user`, `modify_time`, `state`, `action_id`, `domain`,`icon`,`params`) VALUES('40288b854a38408e014a384c4f3c0002', '系统管理', 1, 'NONE', '40288b854a38408e014a384c4f3c0002', '','1','40288b854a329988014a329a12f30002', now(), '40288b854a329988014a329a12f30002', now(),'1','','back', 'icon-sprocket_dark','');
INSERT INTO `sys_auth_actiontree` (`id`, `name`, `sort`,`parent_id`, `parent_ids`, `comments`,`type`, `create_user`, `create_time`,`modify_user`, `modify_time`, `state`, `action_id`, `domain`,`icon`,`params`) VALUES('40288b854a38974f014a38b93d0a0023', '用户管理', 1, '40288b854a38408e014a384c4f3c0002', '40288b854a38408e014a384c4f3c000240288b854a38974f014a38b93d0a0023', '','1','40288b854a329988014a329a12f30002', now(), '40288b854a329988014a329a12f30002', now(),'1','402888a84f970dad014f971444550009','back', 'icon-sprocket_dark','');

CREATE TABLE IF NOT EXISTS `sys_auth_postaction` (
  `id` varchar(32) NOT NULL,
  `action_id` varchar(32) NOT NULL,
  `post_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_02` (`action_id`),
  KEY `FK_Reference_03` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位角色和功能权限对应表';

INSERT INTO `sys_auth_postaction` (`id`, `action_id`, `post_id`) VALUES('4028b8815f2dddbd015f2e2d9c210031', '402888a84f970dad014f971444550009', '402888ac547fe10501548001be3a0001');


CREATE TABLE IF NOT EXISTS `sys_auth_userpost` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `post_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色对应表';

INSERT INTO `sys_auth_userpost` (`id`, `user_id`, `post_id`) VALUES('40288a81611296f4016112a10d9a007b', '40288b854a329988014a329a12f30002', '402888ac547fe10501548001be3a0001');


CREATE TABLE IF NOT EXISTS `sys_log_applog` (
  `id` varchar(32) NOT NULL,
  `create_time` varchar(16) NOT NULL,
  `action_url` varchar(1024) NOT NULL,
  `descripts` varchar(1024) NOT NULL,
  `create_user` varchar(32) NOT NULL,
  `log_levels` varchar(32) DEFAULT '',
  `method` varchar(128) DEFAULT '',
  `class_name` varchar(128) DEFAULT '',
  `ip` varchar(32) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_05` (`create_user`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='操作日志表';
