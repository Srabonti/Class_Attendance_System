<?php
	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['semester_id']) and isset($_POST['semester_name'])){
			//Operate the data further

			$db = new DbOperations();

			$result = $db->insertSemester($_POST['semester_id'], $_POST['semester_name']);
			if($result == 1){
				$response['error'] = false;
				$response['message'] = "Semester records are saved successfully";
				$response['semester_id'] = $semester_id;
				$response['semester_name'] = $semester_name;
			}
			elseif ($result == 2){
				$response['error'] = true;
				$response['message'] = "Some error occurred, please try again";
			}
			elseif ($result == 0){
				$response['error'] = true;
				$response['message'] = "It seems you have already inserted this record, please choose a different record";
			}
		}
	}else{
		$response['error'] = true;
		$response['message'] = "Invalid request!";
	}

	echo json_encode($response);
?>