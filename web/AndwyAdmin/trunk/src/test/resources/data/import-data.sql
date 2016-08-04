use andwyadmin;
truncate table t_task;
insert into t_task (id, title, description, user_id) values
	(1, 'Study PlayFramework 2.0','http://www.playframework.org/', 2),
	(2, 'Study Grails 2.0','http://www.grails.org/', 2),
	(3, 'Try SpringFuse','http://www.springfuse.com/', 2),
	(4, 'Try Spring Roo','http://www.springsource.org/spring-roo', 2),
	(5, 'Release SpringSide 4.0','As soon as posibble.', 2);

truncate table t_user;
insert into t_user (id, login_name, name, password, salt, roles, register_date) values
	(1,'admin','Admin','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-04 01:00:00'),
 	(2,'user','Calvin','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','user','2012-06-04 02:00:00');

truncate table t_market;
insert into t_market(id,name,short_name,url,status,create_time,update_time) values 
	(6,'Google Play','gp','https://play.google.com/apps/publish/','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(1,'91市场','sky91','http://market.sj.91.com','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(2,'安卓市场','hiapk','http://dev.apk.hiapk.com/','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(5,'机锋','gfan','http://dev.gfan.com/','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(3,'360助手','open360','http://open.shouji.360.cn','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(7,'木蚂蚁','mumayi','http://dev.mumayi.com','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(4,'QQ手机市场','qq','http://tap.myapp.com/android/index.jsp','E','2013-04-01 00:31:00','2013-04-01 00:31:00');
	
truncate table t_developer;
insert into t_developer(id,name,email,email_password,developer_name,qq,mobile,status,create_time,update_time) values 
	(1,'微赢天成','techwy@outlook.com','!@34QWer','Techwy','1841974161','','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(2,'于二刚','andwy@outlook.com','!@34QWer','Andwy','1841974161','','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),	
	(3,'毕艳琼','android1616@outlook.com','!@34QWer','Apkwy','1841974161','','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(4,'杜莹莹','angelaboy2008@hotmail.com','androidwy08','Andstudio','527559653','','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),	
	(5,'李永健','hike200808@outlook.com','!@34QWer','Wystudio','228213','','E','2013-04-01 00:31:00','2013-04-01 00:31:00');	
	
truncate table t_market_account;	
insert into t_market_account(id,market_id,developer_id,login_name,login_password,status,create_time,update_time) values 
	(1,2,1,'techwy@outlook.com','androidwy08','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(2,3,1,'techwy@outlook.com','androidwy08','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(3,4,1,'androidwin','androidwy08','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),	
	(4,5,1,'techwy@outlook.com','androidwy08','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),		
	(5,6,1,'androidwin','androidwy08','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),	
	(6,7,1,'androidwin','androidwy08','E','2013-04-01 00:31:00','2013-04-01 00:31:00');		
 
truncate table t_product;	
insert into t_product(id,base_package,project_name,product_name,version,batch_id,publish_interval,category1,category2,category3,language, fee_type,description,status,create_time,update_time) values 
	(1,' ','LinkupFruit','水果连连看2','20130409001','1','10','休闲娱乐','category2','category3','简体中文','免费软件','本软件免费,谢谢使用','E','2013-04-01 00:31:00','2013-04-01 00:31:00'),
	(2,' ','LinkupAnimal','动物连连看2','20130409001','1','10','休闲娱乐','category2','category3','简体中文','免费软件','本软件免费,谢谢使用','E','2013-04-01 00:31:00','2013-04-01 00:31:00'), 
	(3,' ','LinkupAnimal','植物大战僵尸连连看','20130409001','1','10','休闲娱乐','category2','category3','简体中文','免费软件','本软件免费,谢谢使用','E','2013-04-01 00:31:00','2013-04-01 00:31:00');

	
truncate table t_product;	
insert into t_publish(id,release_id,market_account_id,try_count,market_status,market_description,status,create_time,update_time) values
	(1,1,1,1,'Pass','Pass','E','2013-04-01 00:31:00','2013-04-01 00:31:00');
	