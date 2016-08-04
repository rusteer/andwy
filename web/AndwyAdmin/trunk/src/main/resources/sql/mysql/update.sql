2015-02-23
alter table t_app add column daily_install_limit int not null default 1000000;
alter table t_app add column private_app int not null default 0;
alter table t_package_stat add column all_push_count bigint(20),add column all_install_count bigint(20),add column all_install_earning float(10,2);
alter table t_client_stat add column all_push_count bigint(20),add column all_install_count bigint(20),add column all_install_earning float(10,2);
update t_package_stat set all_push_count=push_count,all_install_count=install_count,all_install_earning=install_earning;
update t_client_stat set all_push_count=push_count,all_install_count=install_count,all_install_earning=install_earning;
