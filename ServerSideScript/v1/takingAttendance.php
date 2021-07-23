<?php
	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['attendance_date']) and isset($_POST['attendance_state']) and isset($_POST['course_id']) and isset($_POST['semester_id']) and isset($_POST['student_id'])){

			$db = new DbOperations();

			$result = $db->giveAttendance($_POST['attendance_date'], $_POST['attendance_state'], $_POST['course_id'], $_POST['semester_id'], $_POST['student_id']);
			if($result == true){
				$response['error'] = false;
				$response['message'] = "Attendance is taken successfully!";
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