 USE PLUGIN;


-- 建立存储过程
drop procedure if exists clear_biz_data;
delimiter //
CREATE PROCEDURE clear_biz_data (IN biz_id bigint(21),IN from_time timestamp, IN to_time timestamp)
BEGIN 
    DECLARE TMP_TABLE_EXISTS INT;
	DECLARE result INT;
	select  count(1) INTO TMP_TABLE_EXISTS  from INFORMATION_SCHEMA.TABLES where TABLE_NAME='tt_mytemp1';
	##SELECT TMP_TABLE_EXISTS;
	IF  TMP_TABLE_EXISTS  THEN
		SET result=1;
	ELSE

		#################t_stat_fee_client###########################
		#--------------clear date date--------------------
		SET @sqlstr = CONCAT("create table tt_mytemp1 as select left(order_time,10) as order_date,fee_id,b.price as price,client_id,count(1) as mycount from t_order_record  a, t_biz b  where a.fee_id=",biz_id, "  and a.fee_id=b.id and order_time>='",from_time,"'  and order_time<='",to_time,"'  group by order_date,fee_id,client_id;");
		#SELECT @sqlstr;
		PREPARE stmt FROM @sqlstr; EXECUTE stmt; DEALLOCATE PREPARE stmt;
		#SELECT * FROM tt_mytemp1;		
		#select a.* from t_stat_fee_client a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.client_id=b.client_id and a.stat_date=b.order_date;
		UPDATE t_stat_fee_client a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.client_id=b.client_id and a.stat_date=b.order_date SET a.date_order_money=a.date_order_money-b.mycount*b.price;
		#select a.* from t_stat_fee_client a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.client_id=b.client_id and a.stat_date=b.order_date;
		#SELECT * FROM tt_mytemp1;
		DROP TABLE tt_mytemp1;
		
		#--------------clear month date--------------------
		SET @sqlstr =CONCAT( "create table tt_mytemp1 as select left(order_time,7) as order_month,fee_id,b.price as price,client_id,count(1) as mycount from t_order_record  a, t_biz b  where a.fee_id=",biz_id , " and a.fee_id=b.id and  order_time>='",from_time,"'  and order_time<='",to_time,"'  group by order_month,fee_id,client_id;");
		#SELECT @sqlstr;
		PREPARE stmt FROM @sqlstr; EXECUTE stmt; DEALLOCATE PREPARE stmt;
		#SELECT * FROM tt_mytemp1;
		#select a.* from t_stat_fee_client a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.client_id=b.client_id and a.stat_date=b.order_date;
		UPDATE t_stat_fee_client a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.client_id=b.client_id and a.stat_month=b.order_month SET a.month_order_money=a.month_order_money-b.mycount*b.price;
		#select a.* from t_stat_fee_client a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.client_id=b.client_id and a.stat_date=b.order_date;
		#SELECT * FROM tt_mytemp1;
		DROP TABLE tt_mytemp1;		
				 

		#################t_stat_fee_clients ###########################
		#-------------clear date date------------------------------
		SET @sqlstr = CONCAT("create table tt_mytemp1 as select left(order_time,10) as order_date,fee_id,b.price as price,count(1) as mycount from t_order_record  a, t_biz b  where a.fee_id=",biz_id , " and a.fee_id=b.id and order_time>='",from_time,"'  and order_time<='",to_time,"'  group by  order_date,fee_id;");
		#SELECT @sqlstr;
		PREPARE stmt FROM @sqlstr; EXECUTE stmt; DEALLOCATE PREPARE stmt;
		#select a.* from t_stat_fee_clients a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_date=b.order_date;
		UPDATE t_stat_fee_clients a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_date=b.order_date SET a.date_order_money=a.date_order_money-b.mycount*b.price ;
		#select a.* from t_stat_fee_clients a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_date=b.order_date;
		DROP TABLE tt_mytemp1;

		#--------------clear month date-----------------------------
		SET @sqlstr = CONCAT("create table tt_mytemp1 as select left(order_time,7) as order_month,fee_id,b.price as price,count(1) as mycount from t_order_record  a, t_biz b  where a.fee_id=",biz_id , " and a.fee_id=b.id and order_time>='",from_time,"'  and order_time<='",to_time,"' group by  order_month,fee_id;");
		#SELECT @sqlstr;
		PREPARE stmt FROM @sqlstr; EXECUTE stmt; DEALLOCATE PREPARE stmt;
		#select a.* from t_stat_fee_clients a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_month=b.order_month
		UPDATE t_stat_fee_clients a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_month=b.order_month SET  a.month_order_money=a.month_order_money-b.mycount*b.price;
		#select a.* from t_stat_fee_clients a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_month=b.order_month
		DROP TABLE tt_mytemp1;
 
		
		#################t_stat_fee_province ###########################
		#--------------clear date date-----------------------------
		SET @sqlstr = CONCAT("create table tt_mytemp1 as select left(order_time,10) as order_date,fee_id,b.price as price,province_id ,count(1) as mycount from t_order_record  a, t_biz b  where a.fee_id=",biz_id , " and a.fee_id=b.id and  order_time>='",from_time,"'  and order_time<='",to_time,"'  group by order_date,fee_id,province_id;");
		#SELECT @sqlstr;
		PREPARE stmt FROM @sqlstr; EXECUTE stmt; DEALLOCATE PREPARE stmt;
		#select a.* from t_stat_fee_province a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_date=b.order_date and a.province_id =b.province_id;
		UPDATE t_stat_fee_province a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_date=b.order_date and a.province_id =b.province_id SET a.date_order_money=a.date_order_money-b.mycount*b.price ;
		#select a.* from t_stat_fee_province a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_date=b.order_date and a.province_id =b.province_id;
		DROP TABLE tt_mytemp1;
		#--------------clear month date-----------------------------
		SET @sqlstr = CONCAT("create table tt_mytemp1 as select left(order_time,7) as order_month,fee_id,b.price as price,province_id ,count(1) as mycount from t_order_record  a, t_biz b where fee_id=",biz_id , " and a.fee_id=b.id and order_time>='",from_time,"' and order_time<='",to_time,"' group by order_month,fee_id,province_id;");
		#SELECT @sqlstr;
		PREPARE stmt FROM @sqlstr; EXECUTE stmt; DEALLOCATE PREPARE stmt;
		#select a.* from t_stat_fee_province a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_month=b.order_month and a.province_id =b.province_id;
		UPDATE t_stat_fee_province a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_month=b.order_month and a.province_id =b.province_id SET  a.month_order_money=a.month_order_money-b.mycount*b.price;
		#select a.* from t_stat_fee_province a INNER JOIN tt_mytemp1 b  ON a.fee_id=b.fee_id and a.stat_month=b.order_month and a.province_id =b.province_id;
		DROP TABLE tt_mytemp1;
		#################t_stat_fees_client ###########################
		#--------------clear date date-----------------------------
 
		select @bizPrice:=price from t_biz where id=biz_id;

		SET @sqlstr = CONCAT("create table tt_mytemp1 as select left(order_time,10) as order_date,client_id,",@bizPrice," as price,count(1) as mycount from t_order_record where  fee_id=",biz_id , " and order_time>='",from_time,"'  and order_time<='",to_time,"'  group by order_date,client_id;");

		PREPARE stmt FROM @sqlstr; EXECUTE stmt; DEALLOCATE PREPARE stmt;
		#select a.* from t_stat_fees_client a INNER JOIN tt_mytemp1 b  ON a.client_id=b.client_id and a.stat_date=b.order_date;
		UPDATE t_stat_fees_client a INNER JOIN tt_mytemp1 b  ON a.client_id=b.client_id and a.stat_date=b.order_date SET a.date_order_money=a.date_order_money-b.mycount*b.price ;
		#select a.* from t_stat_fees_client a INNER JOIN tt_mytemp1 b  ON a.client_id=b.client_id and a.stat_date=b.order_date;
		DROP TABLE tt_mytemp1;
		#--------------clear month date-----------------------------
		SET @sqlstr = CONCAT("create table tt_mytemp1 as select left(order_time,7) as order_month,client_id,",@bizPrice," as price,count(1) as mycount from t_order_record where fee_id=",biz_id , " and order_time>='",from_time,"'  and order_time<='",to_time,"'  group by order_month,client_id;");
		#SELECT @sqlstr;
		PREPARE stmt FROM @sqlstr; EXECUTE stmt; DEALLOCATE PREPARE stmt;
		#select a.* from t_stat_fees_client a INNER JOIN tt_mytemp1 b  ON a.client_id=b.client_id and a.stat_month=b.order_month;
		UPDATE t_stat_fees_client a INNER JOIN tt_mytemp1 b  ON a.client_id=b.client_id and a.stat_month=b.order_month SET a.month_order_money=a.month_order_money-b.mycount*b.price ;
		#select a.* from t_stat_fees_client a INNER JOIN tt_mytemp1 b  ON a.client_id=b.client_id and a.stat_month=b.order_month;
		DROP TABLE tt_mytemp1;		
		####print result######

		#################clear t_order_record ###########################
		SET @sqlstr = CONCAT("delete from t_order_record where fee_id=",biz_id," and order_time>='",from_time,"'  and order_time<='",to_time,"'");
		#SELECT @sqlstr;
		PREPARE stmt FROM @sqlstr; EXECUTE stmt; DEALLOCATE PREPARE stmt;
		
		SET result=0;		
	END if;
	SELECT result;
END;
//
delimiter ;

-- 测试

call clear_biz_data(137,'2014-03-03 11:00:00','2014-03-03 23:00:00' );



#select * from t_stat_fee_client

