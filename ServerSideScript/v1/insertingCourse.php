<?php
	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['course_id']) 
			and isset($_POST['course_title'])
			and isset($_POST['course_credit'])
			and isset($_POST['course_type'])
			and isset($_POST['total_class_no'])
			and isset($_POST['semester_id'])){
			//Operate the data further

			$db = new DbOperations();

			$result = $db->insertCourse($_POST['course_id'], $_POST['course_title'], $_POST['course_credit'], $_POST['course_type'], $_POST['total_class_no'], $_POST['semester_id']);
			if($result == true){
				$response['error'] = false;
				$response['message'] = "Course records are saved successfully";
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