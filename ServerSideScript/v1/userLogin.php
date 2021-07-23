<?php
	require_once '../includes/operation.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['admin_email']) and isset($_POST['admin_pass'])){
			$db = new operations();

			if($db->userLogin($_POST['admin_email'], $_POST['admin_pass'])){
				$user = $db->getUserByUserEmail($_POST['admin_email']);
				$response['error'] = false;
				//$response['admin_id'] = $user['admin_id'];
				$response['admin_email'] = $user['admin_email'];
				$response['admin_contact'] = $user['admin_contact'];
				$response['admin_name'] = $user['admin_name'];
			}else{
				$response['error'] = true;
				$response['message'] = "Please, fill up the empty fields";
			}
		}else{
			$response['error'] = true;
			$response['message'] = "Required fileds are missing";
		}
	}
	else{
		$response['error'] = true;
		$response['message'] = "Invalid request!";
	}

	echo json_encode($response);


?>