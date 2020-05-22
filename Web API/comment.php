<?php

$mysql_hostname = 'lginstance.ceqcd6ogioly.ap-northeast-2.rds.amazonaws.com';
	$mysql_username = 'admin';
	$mysql_password = 'instancemaster';
	$mysql_database = 'lgchangwon';
	$mysql_port = '3306';
	$mysql_charset = 'utf8';


$conn = new mysqli($mysql_hostname, $mysql_username, $mysql_password, $mysql_database, $mysql_port, $mysql_charset);



$comment_member = $_POST['comment_member'];
$comment_content = $_POST['comment_content'];
$group_roomnumber = $_POST['group_roomnumber'];
$comment_time =  date('Y-m-d H:i:s');

$group_roomnumber =(int)$group_roomnumber;
//$comment_time=strtotime( $comment_time);



$stmt = mysqli_query($conn, "INSERT INTO comment_managing (comment_member,comment_content,group_roomnumber,comment_time)
 VALUES('$comment_member', '$comment_content', '$group_roomnumber', '$comment_time')");



//if (isset($errMSG)) echo $errMSG;
//if (isset($successMSG)) echo $successMSG;

$result = mysqli_query($conn, "SELECT  comment_member,comment_content,comment_time
  FROM comment_managing WHERE group_roomnumber=$group_roomnumber ORDER BY comment_time ASC");
$response = array();

	while($row = mysqli_fetch_array($result)) {
			array_push($response, array("comment_member"=>$row[0], "comment_content"=>$row[1],"comment_time"=>$row[2]
		));
		}
    echo json_encode(array("response"=>$response));
		//echo json_encode(array("response"=>$response));
?>
