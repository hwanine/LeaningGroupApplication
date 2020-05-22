<?php
 require_once 'connection.php';
 
 $response = array();
 
 if(isset($_GET['action'])){
    switch($_GET['action']){
		
		case 'authentication':
		if(isValid('email')){
			
			$email = $_POST['email'];
			$stmt = $connect->prepare("SELECT email, nickname From user_managing WHERE email = ?");
			$stmt->bind_param("s",$email);
			$stmt->execute();
			
			$stmt->store_result();
			$stmt->bind_result($email, $nickname);
            $stmt->fetch();

					$user = array(
                        'email'=>$email, 
                        'nickname'=>$nickname
                    );
					
			if($stmt->num_rows ==0){ //이메일 등록 가능
			
					$response['error'] = false; 
                    $response['message'] = '성공적으로 회원가입 되었습니다'; 
			
			}else{ //이메일 등록 불가능, 이미 해당 이메일이 존재
			
				$response['error'] = true; 
                $response['message'] = '이미 해당 사용자가 존재하고 있어, 사용할 수 없는 이메일입니다'; 
				$response['user'] = $user; 
			}
			
		}
		break;
		
        case 'signup':
        if(isValid(array('email','nickname','passwd','school_number','real_name'))) {
            $email = $_POST['email']; 
            $nickname = $_POST['nickname']; 
            $passwd = md5($_POST['passwd']);
			$school_number = $_POST['school_number']; 
			$real_name = $_POST['real_name']; 
            
            $stmt = $connect->prepare("SELECT email FROM user_managing WHERE nickname = ? or school_number = ? ");
            $stmt->bind_param("ss", $nickname, $school_number);
            $stmt->execute();
            $stmt->store_result();
            if($stmt->num_rows == 0) {
                 //if user is new creating an insert
                $stmt = $connect->prepare("INSERT INTO user_managing (email, nickname, passwd, school_number, real_name) VALUES (?, ?, ?, ?, ?)");
                $stmt->bind_param("sssss", $email, $nickname, $passwd, $school_number, $real_name); 
				
                //if the user is successfully added to the database 
				//여기서부터 11/25 코딩시작해야함(로그아웃 버튼 만들고 회원가입 테스트)
                if($stmt->execute()){
                    $stmt = $connect->prepare("SELECT email, nickname FROM user_managing WHERE email = ?"); 
                    $stmt->bind_param("s",$email);
                    $stmt->execute();
                    $stmt->bind_result($email, $nickname);
                    $stmt->fetch();

                    $user = array(
                        'email'=>$email, 
                        'nickname'=>$nickname
                    );
                    //adding the user data in response 
                    $response['error'] = false; 
                    $response['message'] = '성공적으로 회원가입 되었습니다'; 
                    $response['user'] = $user; 
                } else {
                    $response['error'] = true; 
                    $response['message'] = '회원가입을 완료할 수 없습니다'; 
                }
            } else {
                $response['error'] = true;
                $response['message'] = '이미 등록된 사용자입니다';
            }
            $stmt->close();
        } else {
            $response['error'] = true; 
            $response['message'] = '데이터가 완전하지 않습니다';
        }
        break; 
		
		//얘만 고쳤어요 회원가입 쓰지말기
        case 'login':

        if(isValid(array('email', 'passwd'))){
            //getting values 
            $email = $_POST['email'];
            $passwd = md5($_POST['passwd']); 
            
            //creating the check query 
            $stmt = $connect->prepare("SELECT email, nickname FROM user_managing WHERE email = ? AND passwd = ?");
            $stmt->bind_param("ss",$email, $passwd);
            $stmt->execute();
            $stmt->store_result();
            
            //if the user exist with given credentials 

            if($stmt->num_rows > 0) {

                $stmt->bind_result($email, $nickname);
                $stmt->fetch();

                $user = array(
                'email'=>$email, 
                'nickname'=>$nickname
                );

                $response['error'] = false; 
                $response['message'] = '성공적으로 로그인 되었습니다'; 
                $response['user'] = $user;
 
            }else{
                //if the user not found 
                $response['error'] = true; 
                $response['message'] = '유효하지 않은 이메일이거나 유효하지 않은 비밀번호 입니다';
            }
        } else {
            $response['error'] = true; 
            $response['message'] = '유효하지 않은 데이터입니다';
        }
        break;

        default;

            $response['error'] = true; 
            $response['message'] = '유효하지 않은 동작입니다';

        break;
		
		//이메일 찾기
		case 'find_email':
		if(isValid(array('school_number','real_name'))){
			
			$school_number = $_POST['school_number'];
			$real_name = $_POST['real_name'];
				
			$stmt = $connect->prepare("SELECT email, nickname FROM user_managing WHERE school_number = ? AND real_name = ?");
			$stmt->bind_param("ss",$school_number, $real_name);
			$stmt->execute();
			$stmt->store_result();
			
			//행이 존재 -> 해당 이메일을 가진 사람있음 이제 그사람의 email을 리턴할거닷
			if($stmt->num_rows > 0) {
                $stmt->bind_result($email, $nickname);
                $stmt->fetch();
                $user = array(
                'email'=>$email, 
                'nickname'=>$nickname
                );
                $response['error'] = false; 
                $response['message'] = '해당 이메일을 찾았습니다'; 
                $response['user'] = $user; 
            }else{
                //if the user not found 
                $response['error'] = true; 
                $response['message'] = '유효하지 않은 이메일 입니다';
            }
        } else {
            $response['error'] = true; 
            $response['message'] = '유효하지 않은 데이터 입니다';
        }
        break;
		
		case 'change_passwd':
		
		if(isValid(array('email','school_number','real_name'))){
			
			$email = $_POST['email'];
			$school_number = $_POST['school_number'];
			$real_name = $_POST['real_name'];
				
			$stmt = $connect->prepare("SELECT email, nickname FROM user_managing WHERE email = ? AND school_number = ? AND real_name = ?");
			
			$stmt->bind_param("sss",$email, $school_number, $real_name);
			$stmt->execute();
			$stmt->store_result();
			
			// 행이 존재하면 해당 사람의 닉네임, 이메일을 보여주고 해당 사용자가 맞으십니까?하고 다이얼로그를 띄운다.
			// 이때 이사람이 맞으면 다음 화면으로 넘어간다.
			// 이 사람이 아니면 그냥 취소
			// 넘어갈때 인텐트로 이메일 닉네임을 주고 해당 사용자의 비밀번호를 바꾼닷.....
			if($stmt->num_rows > 0) {
				
                $stmt->bind_result($email, $nickname);
                $stmt->fetch();
				
                $user = array(
                'email'=>$email, 
                'nickname'=>$nickname
                );
				
                $response['error'] = false; 
                $response['message'] = '해당 사용자를 찾았습니다'; 
                $response['user'] = $user; 
            }else{
                //if the user not found 
                $response['error'] = true; 
                $response['message'] = '유효하지 않은 사용자 정보입니다';
            }
        } else {
            $response['error'] = true; 
            $response['message'] = '유효하지 않은 데이터 입니다';
        }
        break;
		
		case 'new_passwd':
		
		if(isValid(array('passwd','email','nickname'))){
			
			$passwd = md5($_POST['passwd']);
			$email = $_POST['email'];
			$nickname = $_POST['nickname'];
				
			$stmt = $connect->prepare("UPDATE user_managing SET passwd = ? WHERE email = ? AND nickname = ?");
			
			$stmt->bind_param("sss", $passwd, $email, $nickname);
			$stmt->execute();
			$stmt->store_result();
			
			// 행이 존재하면 해당 사람의 닉네임, 이메일을 보여주고 해당 사용자가 맞으십니까?하고 다이얼로그를 띄운다.
			//이때 이사람이 맞으면 ㅈ다음 화면으로 넘어간다.
			//이 사람이 아니면 그냥 취고
			// 넘어갈때 인텐트로 이메일 닉네임을 주고 해당 사용자의 비밀번호를 바꾼닷.....
			if(!mysqli_error()) {
				
                $response['error'] = false; 
                $response['message'] = '비밀번호 변경 성공'; 

		$connect->close();
				
            }else{
                //if the user not found 
                $response['error'] = true; 
                $response['message'] = '유효하지 않은 사용자 정보입니다';
            }
        } else {
            $response['error'] = true; 
            $response['message'] = '유효하지 않은 데이터 입니다';
        }
        break;
		

		case 'report_on':
		
			if(isValid(array('email','school_number','real_name'))){
		
			$email = $_POST['email'];
			$school_number = $_POST['school_number'];
			$real_name = $_POST['real_name'];
			
			$stmt = $connect->prepare("UPDATE user_managing SET warning_count = warning_count + 1 WHERE email = ? AND school_number = ? AND real_name = ?");
			
			$stmt->bind_param("sss",$email,$school_number,$real_name);
			$stmt->execute();
			$stmt->store_result();
			
			if(!mysqli_error()) {
				
				
                $response['error'] = false; 
                $response['message'] = '신고 성공'; 
				
            }else{
                //if the user not found 
                $response['error'] = true; 
                $response['message'] = '신고 에러';
			
		}
	}else{
            $response['error'] = true; 
            $response['message'] = '유효하지 않은 데이터 입니다';
        }
        break;	


    }
 } else {
    $response['error'] = true; 
    $response['message'] = 'Invalid Request.';
 }
 
 function isValid($params){
    foreach($params as $param) {
        //if the paramter is not available or empty
        if(isset($_POST[$param])) {
            if(empty($_POST[$param])){
                return false;
            }
        } else {
            return false;
        }
    }
    //return true if every param is available and not empty 
    return true; 
}
echo json_encode($response);
?>