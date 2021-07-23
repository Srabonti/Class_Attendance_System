<?php
	include_once "../includes/Constants.php";

	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	 // array for json response
    $response = array();
    $response["spreadsheetData"] = array();

	if($con){
		
		if($_SERVER['REQUEST_METHOD'] == 'POST'){

			if(isset($_POST['semester_id']) and isset($_POST['course_id'])){

				$sem_id = $_POST['semester_id'];
				$c_id = $_POST['course_id'];
  
				// Mysql select query
			    $result = mysqli_query($con, "SELECT ar.student_id, ar.student_name, ar.attendance_state, ar.attendance_date, ar.course_id, ar.semester_id, cs.course_title FROM course_info AS cs, attendancerecords AS ar where ar.course_id='$c_id' AND ar.semester_id = '$sem_id' AND ar.course_id = cs.course_id");

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
			        	$tmp["attendance_date"] = $row["attendance_date"];
			        	$tmp["course_id"] = $row["course_id"];
			        	$tmp["semester_id"] = $row["semester_id"];
			        	$tmp["course_title"] = $row["course_title"];
			         
			        // push semesters to final json array
			        	array_push($response["spreadsheetData"], $tmp);
			   	 	}
			    }
			    // keeping response header to json
			    header('Content-Type: application/json');
			     
			    // echoing json result
			    //echo json_encode($response);
			}
			else{
				$response['error'] = true;
				$response['message'] = "Data are not posted!";
			}
		}
		else{
			$response['error'] = true;
			$response['message'] = "Invalid request!";
		}
		echo json_encode($response);
	}
	else{
		echo "Failed to connect with database.";
	}

?>








