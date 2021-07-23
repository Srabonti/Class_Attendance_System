<?php
	include_once "../includes/Constants.php";

	//$db = new DbConnect();
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	 // array for json response
    $response = array();
    $response["courses"] = array();

	if($con){
		// Mysql select query
	    $result = mysqli_query($con, "SELECT course_id, course_title, total_class_no FROM course_info");
	     
	    while($row = mysqli_fetch_array($result)){
	        // temporary array
	        $tmp = array();
	        $tmp["course_id"] = $row["course_id"];
	        $tmp["course_title"] = $row["course_title"];
	        $tmp["total_class_no"] = $row["total_class_no"];
	         
	        // push semesters to final json array
	        array_push($response["courses"], $tmp);
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