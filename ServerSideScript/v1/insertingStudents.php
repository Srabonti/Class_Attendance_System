<?php
	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['student_id']) 
			and isset($_POST['student_name'])
			and isset($_POST['student_contact'])
			and isset($_POST['student_email'])
			and isset($_POST['student_address'])
			and isset($_POST['guardian_name'])
			and isset($_POST['guardian_contact'])
			and isset($_POST['relation'])
			and isset($_POST['semester_id'])){
			//Operate the data further

			$db = new DbOperations();

			$result = $db->insertStudents($_POST['student_id'], $_POST['student_name'], $_POST['student_contact'], $_POST['student_email'], $_POST['student_address'], $_POST['guardian_name'], $_POST['guardian_contact'], $_POST['relation'], $_POST['semester_id']);
			if($result == true){
				$response['error'] = false;
				$response['message'] = "Student records are saved successfully";
			}
			elseif ($result == false){
				$response['error'] = true;
				$response['message'] = "Some error occurred, please try again";
			}
		}
	}else{
		$response['error'] = true;
		$response['message'] = "Invalid request!";
	}

	echo json_encode($response);
?>