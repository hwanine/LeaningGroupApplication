<?php

	header("Content-Type:text/html;charset=utf-8");

	$mysql_hostname = 'lginstance.ceqcd6ogioly.ap-northeast-2.rds.amazonaws.com';
	$mysql_username = 'admin';
	$mysql_password = 'instancemaster';
	$mysql_database = 'lgchangwon';
	$mysql_port = '3306';
	$mysql_charset = 'utf8';


	//1. DB 연결
	$connect = new mysqli($mysql_hostname, $mysql_username, $mysql_password, $mysql_database, $mysql_port, $mysql_charset);
	
	mysqli_query($connect, "set session character_set_connection=utf8;");
	mysqli_query($connect, "set session character_set_results=utf8;");
	mysqli_query($connect, "set session character_set_client=utf8;");

	if($connect->connect_errno){
		echo '[연결실패] : '.$connect->connect_error.''; 
	} else {
		echo '[연결성공]';
	}

	//2. 문자셋 지정
	if(! $connect->set_charset($mysql_charset))// (php >= 5.0.5)
	{
		echo '[문자열변경실패] : '.$connect->connect_error;
	}

	//3. 쿼리 생성
	$query = "select * from user_managing";

	//4. 쿼리 실행
	$result = $connect->query($query) or die($this->_connect->error);
?>

<!DOCTYPE html>
<html>
<body>

<h1><?while($row = $result->fetch_array())
	{
		print_r($row);
	}?>
	</h1>
</body>
</html?

<? $connect->close(); ?>