<?php
	include_once "../includes/Constants.php";

	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	//$cid = mysqli_real_escape_string($con, $_GET['cid']);

	//$cid = $_GET['cid'];

	 // array for json response
    $response = array();
    $response["attendanceSheet"] = array();

	if($con){
		$cid = $_POST['cid'];

		// Mysql select query
	    $result = mysqli_query($con, "SELECT student_id, student_name, attendance_state FROM student_info where course_id='$cid'");

	    if($result == false){
	    	echo mysqli_error($con);
	    	$response['error'] = true;
	    	$response['message'] = "Some error occured";
	    }
	    else{
	    	while($row = mysqli_fetch_array($result)){
	        // temporary array
	        	$tmp = array();
	        	$tmp["student_id"] = $row["student_id"];
	        	$tmp["student_name"] = $row["student_name"];
	        	$tmp["attendance_state"] = $row["attendance_state"];
	         
	        // push semesters to final json array
	        	array_push($response["attendanceSheet"], $tmp);
	   	 	}
	    }
	    // keeping response header to json
	    header('Content-Type: application/json');
	     
	    // echoing json result
	    echo json_encode($response);
	}
	else{
		echo "Failed to connect with database.";
	}

?>








