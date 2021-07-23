<?php 
	include_once "../includes/Constants.php";

	//$db = new DbConnect();
	$con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	 // array for json response
    $response = array();
    
    $response["attendance"] = array();

    if($con->connect_error){
		echo "Failed to connect with database: ".$con->connect_error;
		$response['error'] = true;
		$response['message'] = "Failed to connect to the database!";
		
	}
	else{
		// Mysql select query

	    $stmt = $con->prepare('SELECT student_id, student_name, attendance_state FROM student_info WHERE course_id=?');
		if ( !$stmt ) {
		    $response['error'] = true;
			$response['message'] = "Error 1!"; // This error is found when I open this file in the browser
		}
		else if ( !$stmt->bind_param('s', $_GET['cid']) ) {
		    $response['error'] = true;
			$response['message'] = "Error in binding the param!";
		}
		else if ( !$stmt->execute() ) {
		    $response['error'] = true;
			$response['message'] = "Error in executing the statement!";
		}
		else {
		    $result = $stmt->get_result();
		    
		    foreach( $result as $row ) {
		    	$tmp = array();
		        $tmp["student_id"] = $row["student_id"];
		        $tmp["student_name"] = $row["student_name"];
		        $tmp["attendance_state"] = $row["attendance_state"];
		         
		        // push semesters to final json array
		        array_push($response["attendance"], $tmp);

		    }

		    $response['error'] = false;
			$response['message'] = "Attendance records are retrieved successfully!";
		}
	    
	}

	// echoing json result
	echo json_encode($response);

?>