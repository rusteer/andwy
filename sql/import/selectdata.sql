SELECT
`t_release`.`product_id`,
`t_release`.`market_account_id`,
`t_release`.`package_name`,
`t_release`.`release_product_name`,
`t_release`.`version_description`,
'Y',
0,
'Waiting for Build',
NULL,
0,
0,
NULL,
NULL,
NULL,
NULL,
`t_release`.`status`,
`t_release`.`create_time`,
`t_release`.`update_time`
FROM `andwyadmin`.`t_release` WHERE market_account_id=9

