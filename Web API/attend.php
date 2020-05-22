<?php
require_once 'connection.php';
	$mysql_hostname = 'lginstance.ceqcd6ogioly.ap-northeast-2.rds.amazonaws.com';
	$mysql_username = 'admin';
	$mysql_password = 'instancemaster';
	$mysql_database = 'lgchangwon';
	$mysql_port = '3306';
	$mysql_charset = 'utf8';

if ($connect->connect_error) {
     die("Connection failed: " . $connect->connect_error);
    }

	//$nic=$_POST['nic'];

  $group_roomnumber=$_POST['group_roomnumber'];

     $result = mysqli_query($connect, "SELECT user_managing.real_name,user_managing.school_number,user_managing.email,user_managing.warning_count
       FROM user_managing JOIN log_managing
       ON user_managing.nickname=log_managing.nickname
       AND log_managing.group_roomnumber='$group_roomnumber'");

	 $response = array();

    while($row = mysqli_fetch_array($result)) {
		array_push($response, array("real_name"=>$row[0],"school_number"=>$row[1],"email"=>$row[2],"warning_count"=>$row[3]));
	}

	echo json_encode(array("response"=>$response));



?>
