-- 菜单
CREATE TABLE sys_menu (
  menu_id bigint NOT NULL IDENTITY(1,1),
  parent_id bigint,
  name varchar(50),
  url varchar(200),
  perms varchar(500),
  type int,
  icon varchar(50),
  spread int DEFAULT 0,
  order_num int,
  PRIMARY KEY (menu_id)
);

-- 部门
CREATE TABLE sys_dept (
  dept_id bigint NOT NULL IDENTITY(1,1),
  parent_id bigint,
  name varchar(50),
  order_num int,
  del_flag int DEFAULT 0,
PRIMARY KEY (dept_id)
);

-- 系统用户
CREATE TABLE sys_user (
  user_id bigint NOT NULL IDENTITY(1,1),
  username varchar(50) NOT NULL,
  password varchar(100),
  salt varchar(20),
  email varchar(100),
  mobile varchar(100),
  status int,
  dept_id bigint,
  create_time datetime,
  PRIMARY KEY (user_id),
  UNIQUE (username)
);

-- 角色
CREATE TABLE sys_role (
  role_id bigint NOT NULL IDENTITY(1,1),
  role_name varchar(100),
  remark varchar(100),
  dept_id bigint,
  create_time datetime,
PRIMARY KEY (role_id)
);

-- 用户与角色对应关系
CREATE TABLE sys_user_role (
  id bigint NOT NULL IDENTITY(1,1),
  user_id bigint,
  role_id bigint,
PRIMARY KEY (id)
);

-- 角色与菜单对应关系
CREATE TABLE sys_role_menu (
  id bigint NOT NULL IDENTITY(1,1),
  role_id bigint,
  menu_id bigint,
PRIMARY KEY (id)
);

-- 角色与部门对应关系
CREATE TABLE sys_role_dept (
  id bigint NOT NULL IDENTITY(1,1),
  role_id bigint,
  dept_id bigint,
PRIMARY KEY (id)
);

-- 系统配置信息
CREATE TABLE sys_config (
  id bigint NOT NULL IDENTITY(1,1),
  param_key varchar(50),
  param_value varchar(2000),
  status int DEFAULT 1,
  remark varchar(500),
  PRIMARY KEY (id),
  UNIQUE (param_key)
);

-- 数据字典
CREATE TABLE sys_dict (
  id bigint NOT NULL IDENTITY(1,1),
  name varchar(100) NOT NULL,
  type varchar(100) NOT NULL,
  code varchar(100) NOT NULL,
  value varchar(1000) NOT NULL,
  order_num int DEFAULT 0,
  remark varchar(255),
  del_flag int DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE (type,code)
);

-- 系统日志
CREATE TABLE sys_log (
  id bigint NOT NULL IDENTITY(1,1),
  username varchar(50),
  operation varchar(50),
  method varchar(200),
  params varchar(5000),
  time bigint NOT NULL,
  ip varchar(64),
  create_date datetime,
  PRIMARY KEY (id)
);


-- 登录日志
CREATE TABLE sys_login_log (
  id bigint NOT NULL IDENTITY(1,1),
  username varchar(50),
  operation varchar(50),
  ip varchar(64),
  status int NOT NULL,
  create_date datetime,
  PRIMARY KEY (id)
);

-- 短信
CREATE TABLE sys_sms (
  id bigint NOT NULL IDENTITY(1,1),
  platform int NOT NULL,
  mobile varchar(20),
  params_1 varchar(50),
  params_2 varchar(50),
  params_3 varchar(50),
  params_4 varchar(50),
  status int NOT NULL,
  send_time datetime,
  PRIMARY KEY (id)
);

-- 邮件模板
CREATE TABLE sys_mail_template (
  id bigint NOT NULL IDENTITY(1,1),
  name varchar(100),
  subject varchar(200),
  content text,
  create_date datetime,
  PRIMARY KEY (id)
);

-- 邮件发送记录
CREATE TABLE sys_mail_log (
  id bigint NOT NULL IDENTITY(1,1),
  template_id bigint NOT NULL,
  mail_from varchar(200),
  mail_to varchar(400),
  mail_cc varchar(400),
  subject varchar(200),
  content text,
  status int NOT NULL,
  send_time datetime,
  PRIMARY KEY (id)
);

-- 文件上传
CREATE TABLE sys_oss (
  id bigint NOT NULL IDENTITY(1,1),
  url varchar(200),
  create_date datetime,
  PRIMARY KEY (id)
);

-- 定时任务
CREATE TABLE schedule_job (
  job_id bigint NOT NULL IDENTITY(1,1),
  bean_name varchar(200),
  method_name varchar(100),
  params varchar(2000),
  cron_expression varchar(100),
  status int,
  remark varchar(255),
  create_time datetime,
  PRIMARY KEY (job_id)
);

-- 定时任务日志
CREATE TABLE schedule_job_log (
  log_id bigint NOT NULL IDENTITY(1,1),
  job_id bigint NOT NULL,
  bean_name varchar(200),
  method_name varchar(100),
  params varchar(2000),
  status int NOT NULL,
  error varchar(2000),
  times int NOT NULL,
  create_time datetime,
  PRIMARY KEY (log_id),
  INDEX job_id (job_id)
);

-- 新闻管理
CREATE TABLE tb_news (
  id bigint NOT NULL IDENTITY(1,1),
  title varchar(100),
  content text,
  pub_time datetime,
  create_date datetime,
  PRIMARY KEY (id)
);


-- 初始数据
SET IDENTITY_INSERT sys_user ON;
INSERT INTO sys_user (user_id, username, password, salt, email, mobile, status, dept_id, create_time) VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11');
SET IDENTITY_INSERT sys_user OFF;

SET IDENTITY_INSERT sys_menu ON;
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (2, 46, '用户管理', 'modules/sys/user.html', NULL, 1, 'layui-icon-username', 0, 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (3, 46, '角色管理', 'modules/sys/role.html', NULL, 1, 'layui-icon-group', 0, 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (4, 42, '菜单管理', 'modules/sys/menu.html', NULL, 1, 'layui-icon-menu-fill', 0, 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (5, 47, 'SQL监控', 'druid/sql.html', NULL, 1, 'layui-icon-chart', 0, 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (6, 42, '定时任务', 'modules/job/schedule.html', NULL, 1, 'layui-icon-log', 0, 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:perms', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:perms', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (27, 42, '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'layui-icon-file', 0, 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (29, 47, '系统日志', 'modules/sys/log.html', 'sys:log:list', 1, 'layui-icon-list', 0, 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (30, 42, '文件上传', 'modules/oss/oss.html', 'sys:oss:all', 1, 'layui-icon-upload-drag', 0, 4);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (31, 46, '部门管理', 'modules/sys/dept.html', NULL, 1, 'layui-icon-user', 0, 3);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (32, 31, '查看', NULL, 'sys:dept:list,sys:dept:info', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (33, 31, '新增', NULL, 'sys:dept:save,sys:dept:select', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (34, 31, '修改', NULL, 'sys:dept:update,sys:dept:select', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (35, 31, '删除', NULL, 'sys:dept:delete', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (36, 42, '字典管理', 'modules/sys/dict.html', NULL, 1, 'layui-icon-code-circle', 0, 3);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (37, 36, '查看', NULL, 'sys:dict:list,sys:dict:info', 2, NULL, 0, 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (38, 36, '新增', NULL, 'sys:dict:save', 2, NULL, 0, 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (39, 36, '修改', NULL, 'sys:dict:update', 2, NULL, 0, 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (40, 36, '删除', NULL, 'sys:dict:delete', 2, NULL, 0, 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (41, 48, '文章管理', 'modules/demo/news.html', 'demo:news:list,demo:news:info,demo:news:save,demo:news:update,demo:news:delete', 1, 'layui-icon-form', 0, 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (42, 0, '系统设置', NULL, NULL, 0, 'layui-icon-auz', 0, 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (46, 0, '权限管理', NULL, NULL, 0, 'layui-icon-set', 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (47, 0, '系统监控', NULL, NULL, 0, 'layui-icon-find-fill', 0, 3);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (48, 0, '功能示例', NULL, NULL, 0, 'layui-icon-app', 0, 4);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (51, 2, '导出', NULL, 'sys:user:export', 2, NULL, 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (52, 57, '流程管理', 'modules/activiti/process.html', NULL, 1, 'layui-icon-survey', 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (53, 57, '模型管理', 'modules/activiti/model.html', NULL, 1, 'layui-icon-survey', 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (54, 57, '运行中的流程', 'modules/activiti/running.html', NULL, 1, 'layui-icon-survey', 0, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (55, 47, '登录日志', 'modules/sys/login_log.html', 'sys:loginlog:list', 1, 'layui-icon-auz', 0, 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (56, 58, '短信服务', 'modules/message/sms.html', 'sys:sms:all', 1, 'layui-icon-auz', 0, 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (57, 0, '工作流程', NULL, NULL, 0, 'layui-icon-form', 0, 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (58, 0, '消息管理', NULL, NULL, 0, 'layui-icon-reply-fill', 0, 4);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (59, 58, '邮件模板', 'modules/message/mail_template.html', 'sys:mail:all', 1, 'layui-icon-auz', 0, 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, spread, order_num) VALUES (60, 58, '邮件发送记录', 'modules/message/mail_log.html', 'sys:mail:all', 1, 'layui-icon-auz', 0, 6);

SET IDENTITY_INSERT sys_menu OFF;

SET IDENTITY_INSERT sys_dept ON;
INSERT INTO sys_dept (dept_id, parent_id, name, order_num, del_flag) VALUES ('1', '0', 'RedCloud', '0', '0');
INSERT INTO sys_dept (dept_id, parent_id, name, order_num, del_flag) VALUES ('2', '1', '长沙分公司', '1', '0');
INSERT INTO sys_dept (dept_id, parent_id, name, order_num, del_flag) VALUES ('3', '1', '上海分公司', '2', '0');
INSERT INTO sys_dept (dept_id, parent_id, name, order_num, del_flag) VALUES ('4', '3', '技术部', '0', '0');
INSERT INTO sys_dept (dept_id, parent_id, name, order_num, del_flag) VALUES ('5', '3', '销售部', '1', '0');
SET IDENTITY_INSERT sys_dept OFF;

SET IDENTITY_INSERT sys_dict ON;
INSERT INTO sys_dict(id, name, type, code, value, order_num, remark, del_flag) VALUES (1, '性别', 'sex', '0', '女', 0, NULL, 0);
INSERT INTO sys_dict(id, name, type, code, value, order_num, remark, del_flag) VALUES (2, '性别', 'sex', '1', '男', 1, NULL, 0);
INSERT INTO sys_dict(id, name, type, code, value, order_num, remark, del_flag) VALUES (3, '性别', 'sex', '2', '未知', 3, NULL, 0);
SET IDENTITY_INSERT sys_dict OFF;

INSERT INTO sys_config (param_key, param_value, status, remark) VALUES ('CLOUD_STORAGE_CONFIG_KEY', '{"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","aliyunDomain":"","aliyunEndPoint":"","aliyunPrefix":"","qcloudBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuBucketName":"mall","qiniuDomain":"http://7xlij2.com1.z0.glb.clouddn.com","qiniuPrefix":"upload","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","type":1}', '0', '云存储配置信息');
INSERT INTO sys_config(param_key, param_value, status, remark) VALUES ('SMS_CONFIG_KEY', '{"platform":1,"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunSignName":"","aliyunTemplateCode":"","qcloudAppId":null,"qcloudAppKey":"","qcloudSignName":"","qcloudTemplateId":""}', 0, '短信配置信息');
INSERT INTO sys_config(param_key, param_value, status, remark) VALUES ('MAIL_CONFIG_KEY', '{"smtp":"smtp.163.com","port":25,"username":"renrenio_test@163.com","password":"renren123456"}', 0, '邮件配置信息');


INSERT INTO schedule_job (bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES ('testTask', 'test', 'renren', '0 0/30 * * * ?', '0', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO schedule_job (bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES ('testTask', 'test2', NULL, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56');

INSERT INTO sys_mail_template(name, subject, content, create_date) VALUES ('验证码模板', 'RedCloud注册验证码', '<p>RedCloud注册验证码：${code}</p>', '2018-07-23 14:55:56');

--  quartz自带表结构
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS]') AND OBJECTPROPERTY(id, N'ISFOREIGNKEY') = 1)
  ALTER TABLE [dbo].[QRTZ_TRIGGERS] DROP CONSTRAINT FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISFOREIGNKEY') = 1)
  ALTER TABLE [dbo].[QRTZ_CRON_TRIGGERS] DROP CONSTRAINT FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISFOREIGNKEY') = 1)
  ALTER TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] DROP CONSTRAINT FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISFOREIGNKEY') = 1)
  ALTER TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS] DROP CONSTRAINT FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_CALENDARS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_CALENDARS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_CRON_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_CRON_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_BLOB_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_BLOB_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_FIRED_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_FIRED_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_PAUSED_TRIGGER_GRPS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_PAUSED_TRIGGER_GRPS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_SCHEDULER_STATE]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_SCHEDULER_STATE]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_LOCKS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_LOCKS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_JOB_DETAILS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_JOB_DETAILS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_SIMPLE_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_SIMPROP_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_TRIGGERS]
GO

CREATE TABLE [dbo].[QRTZ_CALENDARS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [CALENDAR_NAME] [VARCHAR] (200)  NOT NULL ,
  [CALENDAR] [IMAGE] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_CRON_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [CRON_EXPRESSION] [VARCHAR] (120)  NOT NULL ,
  [TIME_ZONE_ID] [VARCHAR] (80)
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_FIRED_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [ENTRY_ID] [VARCHAR] (95)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [INSTANCE_NAME] [VARCHAR] (200)  NOT NULL ,
  [FIRED_TIME] [BIGINT] NOT NULL ,
  [SCHED_TIME] [BIGINT] NOT NULL ,
  [PRIORITY] [INTEGER] NOT NULL ,
  [STATE] [VARCHAR] (16)  NOT NULL,
  [JOB_NAME] [VARCHAR] (200)  NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NULL ,
  [IS_NONCONCURRENT] [VARCHAR] (1)  NULL ,
  [REQUESTS_RECOVERY] [VARCHAR] (1)  NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_PAUSED_TRIGGER_GRPS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_SCHEDULER_STATE] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [INSTANCE_NAME] [VARCHAR] (200)  NOT NULL ,
  [LAST_CHECKIN_TIME] [BIGINT] NOT NULL ,
  [CHECKIN_INTERVAL] [BIGINT] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_LOCKS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [LOCK_NAME] [VARCHAR] (40)  NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_JOB_DETAILS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [JOB_NAME] [VARCHAR] (200)  NOT NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NOT NULL ,
  [DESCRIPTION] [VARCHAR] (250) NULL ,
  [JOB_CLASS_NAME] [VARCHAR] (250)  NOT NULL ,
  [IS_DURABLE] [VARCHAR] (1)  NOT NULL ,
  [IS_NONCONCURRENT] [VARCHAR] (1)  NOT NULL ,
  [IS_UPDATE_DATA] [VARCHAR] (1)  NOT NULL ,
  [REQUESTS_RECOVERY] [VARCHAR] (1)  NOT NULL ,
  [JOB_DATA] [IMAGE] NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [REPEAT_COUNT] [BIGINT] NOT NULL ,
  [REPEAT_INTERVAL] [BIGINT] NOT NULL ,
  [TIMES_TRIGGERED] [BIGINT] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [STR_PROP_1] [VARCHAR] (512) NULL,
  [STR_PROP_2] [VARCHAR] (512) NULL,
  [STR_PROP_3] [VARCHAR] (512) NULL,
  [INT_PROP_1] [INT] NULL,
  [INT_PROP_2] [INT] NULL,
  [LONG_PROP_1] [BIGINT] NULL,
  [LONG_PROP_2] [BIGINT] NULL,
  [DEC_PROP_1] [NUMERIC] (13,4) NULL,
  [DEC_PROP_2] [NUMERIC] (13,4) NULL,
  [BOOL_PROP_1] [VARCHAR] (1) NULL,
  [BOOL_PROP_2] [VARCHAR] (1) NULL,
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_BLOB_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [BLOB_DATA] [IMAGE] NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [JOB_NAME] [VARCHAR] (200)  NOT NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NOT NULL ,
  [DESCRIPTION] [VARCHAR] (250) NULL ,
  [NEXT_FIRE_TIME] [BIGINT] NULL ,
  [PREV_FIRE_TIME] [BIGINT] NULL ,
  [PRIORITY] [INTEGER] NULL ,
  [TRIGGER_STATE] [VARCHAR] (16)  NOT NULL ,
  [TRIGGER_TYPE] [VARCHAR] (8)  NOT NULL ,
  [START_TIME] [BIGINT] NOT NULL ,
  [END_TIME] [BIGINT] NULL ,
  [CALENDAR_NAME] [VARCHAR] (200)  NULL ,
  [MISFIRE_INSTR] [SMALLINT] NULL ,
  [JOB_DATA] [IMAGE] NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_CALENDARS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_CALENDARS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [CALENDAR_NAME]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_CRON_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_CRON_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_FIRED_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_FIRED_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [ENTRY_ID]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_PAUSED_TRIGGER_GRPS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_PAUSED_TRIGGER_GRPS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_SCHEDULER_STATE] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_SCHEDULER_STATE] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [INSTANCE_NAME]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_LOCKS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_LOCKS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [LOCK_NAME]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_JOB_DETAILS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_JOB_DETAILS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [JOB_NAME],
      [JOB_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_SIMPLE_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_SIMPROP_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_CRON_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS] FOREIGN KEY
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    ) REFERENCES [dbo].[QRTZ_TRIGGERS] (
    [SCHED_NAME],
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS] FOREIGN KEY
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    ) REFERENCES [dbo].[QRTZ_TRIGGERS] (
    [SCHED_NAME],
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS] FOREIGN KEY
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    ) REFERENCES [dbo].[QRTZ_TRIGGERS] (
    [SCHED_NAME],
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS] FOREIGN KEY
    (
      [SCHED_NAME],
      [JOB_NAME],
      [JOB_GROUP]
    ) REFERENCES [dbo].[QRTZ_JOB_DETAILS] (
    [SCHED_NAME],
    [JOB_NAME],
    [JOB_GROUP]
  )
GO

