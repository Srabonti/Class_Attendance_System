<?php
	include_once "../includes/Constants.php";

	//$db = new DbConnect();
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	$sem = $_GET["sem"];

	 // array for json response
    $response = array();
    $response["courseIds"] = array();

	if($con){
		// Mysql select query
	    $result = mysqli_query($con, "SELECT course_id FROM course_info where semester_id='$sem'");
	     
	    while($row = mysqli_fetch_array($result)){
	        // temporary array
	        $tmp = array();
	        $tmp["course_id"] = $row["course_id"];
	         
	        // push semesters to final json array
	        array_push($response["courseIds"], $tmp);
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