<?php
	include_once "../includes/Constants.php";

	//$db = new DbConnect();
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	 // array for json response
    $response = array();
    $response["contacts"] = array();

	if($con){
		// Mysql select query
		if($_SERVER['REQUEST_METHOD'] == 'POST'){
			if(isset($_POST['semester_id'])){

				$sem_id = $_POST['semester_id'];

			    $result = mysqli_query($con, "SELECT guardian_contact FROM student_info WHERE semester_id = '$sem_id' AND attendance_state = 'A'");
			     
			    while($row = mysqli_fetch_array($result)){
			        // temporary array
			        $tmp = array();
			        $tmp["guardian_contact"] = $row["guardian_contact"];
			         
			        // push semesters to final json array
			        array_push($response["contacts"], $tmp);
			    }
			     
		    }

		}
		else{
			$response['error'] = true;
			$response['message'] = 'Invalid Request!';
		}
		// keeping response header to json
		header('Content-Type: application/json');
		echo json_encode($response);
	}
	else{
		echo "Failed to connect with database.";
	}


?>