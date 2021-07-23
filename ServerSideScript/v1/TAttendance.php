<?php
	require_once '../includes/DbOperations.php';

	 // array for json response
    $response = array();


	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(isset($_POST['attendance_date']) and isset($_POST['attendance_state']) and isset($_POST['course_id']) and isset($_POST['semester_id']) and isset($_POST['student_id'])){

			$db = new DbOperations();

			$result = $db->insertInfo($_POST['attendance_date'], $_POST['attendance_state'], $_POST['course_id'], $_POST['semester_id'], $_POST['student_id']);

			//$res = $db->attendanceS($_POST['attendance_state'], $_POST['student_id']);
			/*$sql1 = "INSERT INTO student_info(attendance_date, course_id) values('$attendance_date', '$course_id') WHERE semester_id = '$semester_id'";*/
			
			$response['error'] = !$result;
			$response['message'] = $result?"Successful!":"Failed";
			//if($result == true){

				//$db->closeConnection();

				/*$db1 = new DbOperations();
				

				$res = $db1->attendanceS($_POST['attendance_state'], $_POST['student_id']);

			    if($res == true){
			    	$response['error'] = false;
					$response['message'] = "Attendance is taken successfully!";
			    }
			    else{
			    	$response['error'] = true;
					$response['message'] = "Some error occured in taking attendance!";
			    }*/

			  //  $response['error'] = false;
			//	$response['message'] = "Attendance is taken successfully!";
			//}
				
		}

	}
	else{
		$response['error'] = true;
		$response['message'] = "Invalid Request!";
	}
	    
	// echoing json result
	echo json_encode($response);


?>

