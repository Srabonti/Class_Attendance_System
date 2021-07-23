<?php
	require_once '../includes/operation.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['admin_name']) and isset($_POST['admin_email']) and isset($_POST['admin_contact'])  and isset($_POST['admin_pass'])){
			//Operate the data further

			$db = new operation();

			$result = $db->createAdmin($_POST['admin_name'], $_POST['admin_email'], $_POST['admin_contact'], $_POST['admin_pass']);
			if($result == 1){
				$response['error'] = false;
				$response['message'] = "User registered successfully";
			}
			elseif ($result == 2){
				$response['error'] = true;
				$response['message'] = "Some error occurred, please try again";
			}
			elseif ($result == 0){
				$response['error'] = true;
				$response['message'] = "It seems you are already registered, please choose a different username and email";
			}
		}
	}else{
		$response['error'] = true;
		$response['message'] = "Invalid request!";
	}

	echo json_encode($response);
?>