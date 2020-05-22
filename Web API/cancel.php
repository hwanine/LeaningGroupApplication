<?php 
	 require_once 'connection.php';

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

	$nic=$_POST['nic'];
	$roomnum=$_POST['roomnum'];
	$response = array();



   if(!isset($errMSG)){
                $stmt = mysqli_query($connect, "DELETE FROM log_managing WHERE nickname='$nic' && group_roomnumber='$roomnum'");

              $num = "SELECT nickname FROM log_managing WHERE group_roomnumber = '$roomnum'";
   	   $result_set = mysqli_query($connect, $num);
 	   $count = mysqli_num_rows($result_set);

if($count < 1){
	$stmt = mysqli_query($connect, "DELETE FROM group_managing WHERE group_roomnumber='$roomnum'");
}
else{
$stmt = mysqli_query($connect, "UPDATE group_managing SET pre_usernum='$count' WHERE group_roomnumber='$roomnum'");
}

	
            	$response['success'] = true;	
	echo json_encode($response);
}

?>