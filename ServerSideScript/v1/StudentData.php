<?php
include_once "../includes/Constants.php";
$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
$response = array();

if($con){
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['student_name'])){
			$std = $_POST['student_name'];
			$s = "INSERT INTO student(id, name) VALUES(NULL, 'std')";
			$result = mysqli_query($con, $s);

			if($result == true){
				$response['error'] = false;
				$response['message'] = 'Student name is inserted successfully';
			}
			else{
				$response['error'] = true;
				$response['message'] = 'Student name is not inserted';
			}
		}
		
	}
	else{
		$response['error'] = true;
		$response['message'] = 'Invalid request';
	}

	echo json_encode($response);

}
else{
	echo 'Database is not connected';
}

?>