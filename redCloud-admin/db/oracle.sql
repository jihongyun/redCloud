-- 菜单
CREATE TABLE sys_menu (
  menu_id NUMBER(20, 0) NOT NULL,
  parent_id NUMBER(20, 0) NOT NULL,
  name varchar2(50),
  url varchar2(200),
  perms varchar2(500),
  type NUMBER(2, 0),
  icon varchar2(50),
  spread NUMBER(2, 0) DEFAULT 0 NOT NULL,
  order_num int,
  PRIMARY KEY (menu_id)
);

-- 部门
CREATE TABLE sys_dept (
  dept_id NUMBER(20, 0) NOT NULL,
  parent_id NUMBER(20, 0) NOT NULL,
  name varchar2(50),
  order_num NUMBER(10, 0) NOT NULL,
  del_flag NUMBER(2, 0) DEFAULT 0 NOT NULL,
  PRIMARY KEY (dept_id)
);

-- 系统用户
CREATE TABLE sys_user (
  user_id NUMBER(20, 0) NOT NULL,
  username varchar2(50) NOT NULL,
  password varchar2(100),
  salt varchar2(20),
  email varchar2(100),
  mobile varchar2(100),
  status NUMBER(2, 0) NOT NULL,
  dept_id NUMBER(20, 0),
  create_time timestamp,
  PRIMARY KEY (user_id)
);
CREATE UNIQUE INDEX index_username on sys_user(username);


-- 角色
CREATE TABLE sys_role (
  role_id NUMBER(20, 0) NOT NULL,
  role_name varchar2(100),
  remark varchar2(100),
  dept_id NUMBER(20, 0) NOT NULL,
  create_time timestamp,
  PRIMARY KEY (role_id)
);

-- 用户与角色对应关系
CREATE TABLE sys_user_role (
  id NUMBER(20, 0) NOT NULL,
  user_id NUMBER(20, 0) NOT NULL,
  role_id NUMBER(20, 0) NOT NULL,
  PRIMARY KEY (id)
);

-- 角色与菜单对应关系
CREATE TABLE sys_role_menu (
  id NUMBER(20, 0) NOT NULL,
  role_id NUMBER(20, 0) NOT NULL,
  menu_id NUMBER(20, 0) NOT NULL,
  PRIMARY KEY (id)
);


-- 角色与部门对应关系
CREATE TABLE sys_role_dept (
  id NUMBER(20, 0) NOT NULL,
  role_id NUMBER(20, 0) NOT NULL,
  dept_id NUMBER(20, 0) NOT NULL,
  PRIMARY KEY (id)
);

-- 系统配置信息
CREATE TABLE sys_config (
  id NUMBER(20, 0) NOT NULL,
  param_key varchar2(50),
  param_value varchar2(4000),
  status NUMBER(2, 0) DEFAULT 1 NOT NULL,
  remark varchar2(500),
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX index_param_key on sys_config(param_key);

-- 数据字典
CREATE TABLE sys_dict (
  id NUMBER(20, 0) NOT NULL,
  name varchar2(100) NOT NULL,
  type varchar2(100) NOT NULL,
  code varchar2(100) NOT NULL,
  value varchar2(1000) NOT NULL,
  order_num NUMBER(10, 0) DEFAULT 0 NOT NULL,
  remark varchar2(255),
  del_flag NUMBER(2, 0) DEFAULT 0 NOT NULL,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX index_type_code on sys_dict(type, code);

-- 系统日志
CREATE TABLE sys_log (
  id NUMBER(20, 0) NOT NULL,
  username varchar2(50),
  operation varchar2(50),
  method varchar2(200),
  params clob,
  time NUMBER(20, 0) NOT NULL,
  ip varchar2(64),
  create_date timestamp,
  PRIMARY KEY (id)
);

-- 登录日志
CREATE TABLE sys_login_log (
  id NUMBER(20, 0) NOT NULL,
  username varchar2(50),
  operation varchar2(50),
  ip varchar2(64),
  status NUMBER(2, 0) NOT NULL,
  create_date timestamp,
  PRIMARY KEY (id)
);

-- 短信
CREATE TABLE sys_sms (
  id NUMBER(20, 0) NOT NULL,
  platform NUMBER(2, 0) NOT NULL,
  mobile varchar2(20),
  params_1 varchar2(50),
  params_2 varchar2(50),
  params_3 varchar2(50),
  params_4 varchar2(50),
  status NUMBER(2, 0) NOT NULL,
  send_time timestamp,
  PRIMARY KEY (id)
);

-- 邮件模板
CREATE TABLE sys_mail_template (
  id NUMBER(20, 0) NOT NULL,
  name varchar2(100),
  subject varchar2(200),
  content clob,
  create_date timestamp,
  PRIMARY KEY (id)
);

-- 邮件发送记录
CREATE TABLE sys_mail_log (
  id NUMBER(20, 0) NOT NULL,
  template_id NUMBER(20, 0) NOT NULL,
  mail_from varchar2(200),
  mail_to varchar2(400),
  mail_cc varchar2(400),
  subject varchar2(200),
  content clob,
  status NUMBER(2, 0) NOT NULL,
  send_time timestamp,
  PRIMARY KEY (id)
);

-- 文件上传
CREATE TABLE sys_oss (
  id NUMBER(20, 0) NOT NULL,
  url varchar2(200),
  create_date timestamp,
  PRIMARY KEY (id)
);

-- 定时任务
CREATE TABLE schedule_job (
  job_id NUMBER(20, 0) NOT NULL,
  bean_name varchar2(200),
  method_name varchar2(100),
  params varchar2(2000),
  cron_expression varchar2(100),
  status NUMBER(2, 0) NOT NULL,
  remark varchar2(255),
  create_time timestamp,
  PRIMARY KEY (job_id)
);

-- 定时任务日志
CREATE TABLE schedule_job_log (
  log_id NUMBER(20, 0) NOT NULL,
  job_id NUMBER(20, 0) NOT NULL,
  bean_name varchar2(200),
  method_name varchar2(100),
  params varchar2(2000),
  status NUMBER(2, 0) NOT NULL,
  error varchar2(2000),
  times NUMBER(10, 0) NOT NULL,
  create_time timestamp,
  PRIMARY KEY (log_id)
);
CREATE INDEX index_job_id on schedule_job_log(job_id);

-- 新闻管理
CREATE TABLE tb_news (
  id NUMBER(20, 0) NOT NULL,
  title varchar2(100),
  content clob,
  pub_time timestamp,
  create_date timestamp,
  PRIMARY KEY (id)
);




-- 初始数据
INSERT INTO sys_user(user_id, username, password, salt, email, mobile, status, dept_id, create_time) VALUES (1, 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', 1, 1, CURRENT_DATE);


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



INSERT INTO sys_dict(id, name, type, code, value, order_num, remark, del_flag) VALUES (1, '性别', 'sex', '0', '女', 0, NULL, 0);
INSERT INTO sys_dict(id, name, type, code, value, order_num, remark, del_flag) VALUES (2, '性别', 'sex', '1', '男', 1, NULL, 0);
INSERT INTO sys_dict(id, name, type, code, value, order_num, remark, del_flag) VALUES (3, '性别', 'sex', '2', '未知', 3, NULL, 0);
INSERT INTO sys_dept(dept_id, parent_id, name, order_num, del_flag) VALUES (1, 0, 'RedCloud开源集团', 0, 0);
INSERT INTO sys_dept(dept_id, parent_id, name, order_num, del_flag) VALUES (2, 1, '长沙分公司', 1, 0);
INSERT INTO sys_dept(dept_id, parent_id, name, order_num, del_flag) VALUES (3, 1, '上海分公司', 2, 0);
INSERT INTO sys_dept(dept_id, parent_id, name, order_num, del_flag) VALUES (4, 3, '技术部', 0, 0);
INSERT INTO sys_dept(dept_id, parent_id, name, order_num, del_flag) VALUES (5, 3, '销售部', 1, 0);
INSERT INTO sys_config(id, param_key, param_value, status, remark) VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","aliyunDomain":"","aliyunEndPoint":"","aliyunPrefix":"","qcloudBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuBucketName":"mall","qiniuDomain":"http://7xlij2.com1.z0.glb.clouddn.com","qiniuPrefix":"upload","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","type":1}', '0', '云存储配置信息');
INSERT INTO sys_config(id, param_key, param_value, status, remark) VALUES (2, 'SMS_CONFIG_KEY', '{"platform":1,"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunSignName":"","aliyunTemplateCode":"","qcloudAppId":null,"qcloudAppKey":"","qcloudSignName":"","qcloudTemplateId":""}', 0, '短信配置信息');
INSERT INTO sys_config(id, param_key, param_value, status, remark) VALUES (3, 'MAIL_CONFIG_KEY', '{"smtp":"smtp.163.com","port":25,"username":"renrenio_test@163.com","password":"renren123456"}', 0, '邮件配置信息');


INSERT INTO schedule_job (job_id, bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES (1, 'testTask', 'test', 'renren', '0 0/30 * * * ?', '0', '有参数测试', CURRENT_DATE);
INSERT INTO schedule_job (job_id, bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES (2, 'testTask', 'test2', NULL, '0 0/30 * * * ?', '1', '无参数测试', CURRENT_DATE);

INSERT INTO sys_mail_template(id, name, subject, content, create_date) VALUES (1, '验证码模板', 'RedCloud注册验证码', '<p>RedCloud注册验证码：${code}</p>', CURRENT_DATE);



--  创建触发器，主键自增
create sequence sys_menu_seq start with 61 increment by 1;
create trigger sys_menu_trigger before insert on sys_menu for each row when(new.menu_id is null)
begin
  select sys_menu_seq.nextval into :new.menu_id from dual;
end;
/

create sequence sys_dept_seq start with 6 increment by 1;
create trigger sys_dept_trigger before insert on sys_dept for each row when(new.dept_id is null)
begin
  select sys_dept_seq.nextval into :new.dept_id from dual;
end;
/

create sequence sys_user_seq start with 2 increment by 1;
create trigger sys_user_trigger before insert on sys_user for each row when(new.user_id is null)
begin
  select sys_user_seq.nextval into :new.user_id from dual;
end;
/

create sequence sys_role_seq start with 1 increment by 1;
create trigger sys_role_trigger before insert on sys_role for each row when(new.role_id is null)
begin
  select sys_role_seq.nextval into :new.role_id from dual;
end;
/

create sequence sys_user_role_seq start with 1 increment by 1;
create trigger sys_user_role_trigger before insert on sys_user_role for each row when(new.id is null)
begin
  select sys_user_role_seq.nextval into :new.id from dual;
end;
/

create sequence sys_role_menu_seq start with 1 increment by 1;
create trigger sys_role_menu_trigger before insert on sys_role_menu for each row when(new.id is null)
begin
  select sys_role_menu_seq.nextval into :new.id from dual;
end;
/

create sequence sys_role_dept_seq start with 1 increment by 1;
create trigger sys_role_dept_trigger before insert on sys_role_dept for each row when(new.id is null)
begin
  select sys_role_dept_seq.nextval into :new.id from dual;
end;
/

create sequence sys_config_seq start with 4 increment by 1;
create trigger sys_config_trigger before insert on sys_config for each row when(new.id is null)
begin
  select sys_config_seq.nextval into :new.id from dual;
end;
/

create sequence sys_dict_seq start with 4 increment by 1;
create trigger sys_dict_trigger before insert on sys_dict for each row when(new.id is null)
begin
  select sys_dict_seq.nextval into :new.id from dual;
end;
/

create sequence sys_log_seq start with 1 increment by 1;
create trigger sys_log_trigger before insert on sys_log for each row when(new.id is null)
begin
  select sys_log_seq.nextval into :new.id from dual;
end;
/

create sequence sys_login_log_seq start with 1 increment by 1;
create trigger sys_login_log_trigger before insert on sys_login_log for each row when(new.id is null)
begin
  select sys_login_log_seq.nextval into :new.id from dual;
end;
/

create sequence sys_sms_seq start with 1 increment by 1;
create trigger sys_sms_trigger before insert on sys_sms for each row when(new.id is null)
begin
  select sys_sms_seq.nextval into :new.id from dual;
end;
/

create sequence sys_mail_template_seq start with 2 increment by 1;
create trigger sys_mail_template_trigger before insert on sys_mail_template for each row when(new.id is null)
begin
  select sys_mail_template_seq.nextval into :new.id from dual;
end;
/

create sequence sys_mail_log_seq start with 1 increment by 1;
create trigger sys_mail_log_trigger before insert on sys_mail_log for each row when(new.id is null)
begin
  select sys_mail_log_seq.nextval into :new.id from dual;
end;
/

create sequence sys_oss_seq start with 1 increment by 1;
create trigger sys_oss_trigger before insert on sys_oss for each row when(new.id is null)
begin
  select sys_oss_seq.nextval into :new.id from dual;
end;
/

create sequence schedule_job_seq start with 3 increment by 1;
create trigger schedule_job_trigger before insert on schedule_job for each row when(new.job_id is null)
begin
  select schedule_job_seq.nextval into :new.job_id from dual;
end;
/

create sequence schedule_job_log_seq start with 1 increment by 1;
create trigger schedule_job_log_trigger before insert on schedule_job_log for each row when(new.log_id is null)
begin
  select schedule_job_log_seq.nextval into :new.log_id from dual;
end;
/

create sequence tb_news_seq start with 1 increment by 1;
create trigger tb_news_trigger before insert on tb_news for each row when(new.id is null)
begin
  select tb_news_seq.nextval into :new.id from dual;
end;
/


--  quartz自带表结构
CREATE TABLE qrtz_job_details
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  JOB_NAME  VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250) NULL,
  JOB_CLASS_NAME   VARCHAR2(250) NOT NULL,
  IS_DURABLE VARCHAR2(1) NOT NULL,
  IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
  IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
  REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
  JOB_DATA BLOB NULL,
  CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);
CREATE TABLE qrtz_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  JOB_NAME  VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250) NULL,
  NEXT_FIRE_TIME NUMBER(13) NULL,
  PREV_FIRE_TIME NUMBER(13) NULL,
  PRIORITY NUMBER(13) NULL,
  TRIGGER_STATE VARCHAR2(16) NOT NULL,
  TRIGGER_TYPE VARCHAR2(8) NOT NULL,
  START_TIME NUMBER(13) NOT NULL,
  END_TIME NUMBER(13) NULL,
  CALENDAR_NAME VARCHAR2(200) NULL,
  MISFIRE_INSTR NUMBER(2) NULL,
  JOB_DATA BLOB NULL,
  CONSTRAINT QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_TRIGGER_TO_JOBS_FK FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);
CREATE TABLE qrtz_simple_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  REPEAT_COUNT NUMBER(7) NOT NULL,
  REPEAT_INTERVAL NUMBER(12) NOT NULL,
  TIMES_TRIGGERED NUMBER(10) NOT NULL,
  CONSTRAINT QRTZ_SIMPLE_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_SIMPLE_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_cron_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  CRON_EXPRESSION VARCHAR2(120) NOT NULL,
  TIME_ZONE_ID VARCHAR2(80),
  CONSTRAINT QRTZ_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_CRON_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_simprop_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  STR_PROP_1 VARCHAR2(512) NULL,
  STR_PROP_2 VARCHAR2(512) NULL,
  STR_PROP_3 VARCHAR2(512) NULL,
  INT_PROP_1 NUMBER(10) NULL,
  INT_PROP_2 NUMBER(10) NULL,
  LONG_PROP_1 NUMBER(13) NULL,
  LONG_PROP_2 NUMBER(13) NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 VARCHAR2(1) NULL,
  BOOL_PROP_2 VARCHAR2(1) NULL,
  CONSTRAINT QRTZ_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_SIMPROP_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_blob_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  BLOB_DATA BLOB NULL,
  CONSTRAINT QRTZ_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_BLOB_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_calendars
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  CALENDAR_NAME  VARCHAR2(200) NOT NULL,
  CALENDAR BLOB NOT NULL,
  CONSTRAINT QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);
CREATE TABLE qrtz_paused_trigger_grps
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_GROUP  VARCHAR2(200) NOT NULL,
  CONSTRAINT QRTZ_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_fired_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  ENTRY_ID VARCHAR2(95) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  FIRED_TIME NUMBER(13) NOT NULL,
  SCHED_TIME NUMBER(13) NOT NULL,
  PRIORITY NUMBER(13) NOT NULL,
  STATE VARCHAR2(16) NOT NULL,
  JOB_NAME VARCHAR2(200) NULL,
  JOB_GROUP VARCHAR2(200) NULL,
  IS_NONCONCURRENT VARCHAR2(1) NULL,
  REQUESTS_RECOVERY VARCHAR2(1) NULL,
  CONSTRAINT QRTZ_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);
CREATE TABLE qrtz_scheduler_state
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
  CHECKIN_INTERVAL NUMBER(13) NOT NULL,
  CONSTRAINT QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);
CREATE TABLE qrtz_locks
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  LOCK_NAME  VARCHAR2(40) NOT NULL,
  CONSTRAINT QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);


