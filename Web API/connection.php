<?php

	$mysql_hostname = 'lginstance.ceqcd6ogioly.ap-northeast-2.rds.amazonaws.com';
	$mysql_username = 'admin';
	$mysql_password = 'instancemaster';
	$mysql_database = 'lgchangwon';
	$mysql_port = '3306';
	$mysql_charset = 'utf8';


	$connect = new mysqli($mysql_hostname, $mysql_username, $mysql_password, $mysql_database, $mysql_port, $mysql_charset);
	
	if ($connect->connect_error) {
    die("Database connection failed: " . $connect->connect_error);
}
?>
